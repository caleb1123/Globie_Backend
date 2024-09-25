package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.Post;
import com.product.globie.entity.PostCategory;
import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.request.CreatePostRequest;
import com.product.globie.payload.request.UpdatePostRequest;
import com.product.globie.repository.PostCategoryRepository;
import com.product.globie.repository.PostRepository;
import com.product.globie.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Util util;

    @Autowired
    PostCategoryRepository postCategoryRepository;

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();

        List<PostDTO> postDTOS = posts.stream()
                .map(post -> {
                    PostDTO postDTO = mapper.map(post, PostDTO.class);

                    if (post.getPostCategory() != null) {
                        postDTO.setPostCategoryId(post.getPostCategory().getPostCategoryId());
                    }
                    if (post.getUser() != null) {
                        postDTO.setUserId(post.getUser().getUserId());
                    }

                    return postDTO;
                })
                .collect(Collectors.toList());

        return postDTOS.isEmpty() ? null : postDTOS;
    }

    @Override
    public List<PostDTO> getPostByUser(int uId) {

        List<Post> posts = postRepository.findPostByUser(uId);
        if(posts.isEmpty()){
            throw new RuntimeException("There are no Posts of this User Id: " + uId);
        }
        List<PostDTO> postDTOS = posts.stream()
                .map(post -> {
                    PostDTO postDTO = mapper.map(post, PostDTO.class);

                    if (post.getPostCategory() != null) {
                        postDTO.setPostCategoryId(post.getPostCategory().getPostCategoryId());
                    }
                    if (post.getUser() != null) {
                        postDTO.setUserId(post.getUser().getUserId());
                    }

                    return postDTO;
                })
                .collect(Collectors.toList());

        return postDTOS.isEmpty() ? null : postDTOS;
    }

    @Override
    public List<PostDTO> getAllPostStatusTrue() {
        List<Post> posts = postRepository.findAll();

        List<PostDTO> postDTOS = posts.stream()
                .filter(Post :: isStatus)
                .map(post -> {
                    PostDTO postDTO = mapper.map(post, PostDTO.class);

                    if (post.getPostCategory() != null) {
                        postDTO.setPostCategoryId(post.getPostCategory().getPostCategoryId());
                    }
                    if (post.getUser() != null) {
                        postDTO.setUserId(post.getUser().getUserId());
                    }

                    return postDTO;
                })
                .collect(Collectors.toList());

        return postDTOS.isEmpty() ? null : postDTOS;
    }

    @Override
    public List<PostDTO> getAllPostStatusFalse() {
        List<Post> posts = postRepository.findAll();

        List<PostDTO> postDTOS = posts.stream()
                .filter(Post -> !Post.isStatus())
                .map(post -> {
                    PostDTO postDTO = mapper.map(post, PostDTO.class);

                    if (post.getPostCategory() != null) {
                        postDTO.setPostCategoryId(post.getPostCategory().getPostCategoryId());
                    }
                    if (post.getUser() != null) {
                        postDTO.setUserId(post.getUser().getUserId());
                    }

                    return postDTO;
                })
                .collect(Collectors.toList());

        return postDTOS.isEmpty() ? null : postDTOS;
    }

    @Override
    public PostDTO createPost(CreatePostRequest postRequest) {
        Post post = new Post();
        post.setPostTitle(postRequest.getPostTitle());
        post.setPostContent(postRequest.getPostContent());
        post.setCreatedTime(new Date());
        post.setStatus(false);
        post.setUser(util.getUserFromAuthentication());

        PostCategory postCategory = postCategoryRepository.findById(postRequest.getPostCategoryId())
                .orElseThrow(() -> new RuntimeException("Post category not Found with Id: " + postRequest.getPostCategoryId()));
        post.setPostCategory(postCategory);

        Post savedPost = postRepository.save(post);

        PostDTO postDTO = mapper.map(savedPost, PostDTO.class);
        postDTO.setPostCategoryId(savedPost.getPostCategory().getPostCategoryId());
        postDTO.setUserId(savedPost.getUser().getUserId());

        return postDTO;
    }

    @Override
    public void deletePost(int pId) {
        Post post = postRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Post not found with Id: " + pId));
        postRepository.delete(post);
    }

    @Override
    public PostDTO updatePost(UpdatePostRequest postRequest, int pId) {
        Post post = postRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Post not found with Id: " + pId));
        post.setPostTitle(postRequest.getPostTitle());
        post.setPostContent(postRequest.getPostContent());
        post.setUpdatedTime(new Date());

        Post savedPost = postRepository.save(post);

        PostDTO postDTO = mapper.map(savedPost, PostDTO.class);
        postDTO.setPostCategoryId(savedPost.getPostCategory().getPostCategoryId());
        postDTO.setUserId(savedPost.getUser().getUserId());

        return postDTO;
    }

    @Override
    public void updateStatusPost(int pId) {
        Post post = postRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Post not found with Id: " + pId));
        post.setStatus(!post.isStatus());
        post.setUpdatedTime(new Date());

        postRepository.save(post);
    }

    @Override
    public PostDTO getPostDetail(int pId) {
        Post post = postRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Post not found with Id: " + pId));

        PostDTO postDTO = mapper.map(post, PostDTO.class);
        postDTO.setPostCategoryId(post.getPostCategory().getPostCategoryId());
        postDTO.setUserId(post.getUser().getUserId());

        return postDTO;    }

    @Override
    public List<PostDTO> getPostByCategory(int cId) {
        List<Post> posts = postRepository.findPostByCategory(cId);
        if(posts.isEmpty()){
            throw new RuntimeException("There are no Posts of this Category Id: " + cId);
        }
        List<PostDTO> postDTOS = posts.stream()
                .map(post -> {
                    PostDTO postDTO = mapper.map(post, PostDTO.class);

                    if (post.getPostCategory() != null) {
                        postDTO.setPostCategoryId(post.getPostCategory().getPostCategoryId());
                    }
                    if (post.getUser() != null) {
                        postDTO.setUserId(post.getUser().getUserId());
                    }

                    return postDTO;
                })
                .collect(Collectors.toList());

        return postDTOS.isEmpty() ? null : postDTOS;
    }
}
