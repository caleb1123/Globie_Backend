package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.Comment;
import com.product.globie.entity.Post;
import com.product.globie.payload.DTO.CommentDTO;
import com.product.globie.payload.DTO.ProductDTO;
import com.product.globie.payload.request.CommentRequest;
import com.product.globie.payload.request.UpdateCommentRequest;
import com.product.globie.repository.CommentRepository;
import com.product.globie.repository.PostRepository;
import com.product.globie.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Util util;

    @Autowired
    PostRepository postRepository;


    @Override
    public List<CommentDTO> getAllCommentOfPost(int pId) {
        List<Comment> comments = commentRepository.getCommentByPost(pId);

        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);

                    if (comment.getPost() != null) {
                        commentDTO.setPostId(comment.getPost().getPostId());
                    }
                    if (comment.getUser() != null) {
                        commentDTO.setUserId(comment.getUser().getUserId());
                    }

                    return commentDTO;
                })
                .collect(Collectors.toList());

        return commentDTOS.isEmpty() ? null : commentDTOS;
    }

    @Override
    public void addComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setCreatedTime(new Date());
        comment.setUser(util.getUserFromAuthentication());

        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(()-> new RuntimeException("Post not found!"));
        comment.setPost(post);

        commentRepository.save(comment);
    }

    @Override
    public void updateComment(UpdateCommentRequest commentRequest, int id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));
        comment.setContent(commentRequest.getContent());
        comment.setUpdatedTime(new Date());

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int cId) {
        Comment comment = commentRepository.findById(cId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        commentRepository.delete(comment);
    }
}
