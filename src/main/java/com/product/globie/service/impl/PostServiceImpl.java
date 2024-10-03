package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.*;
import com.product.globie.payload.DTO.PostDTO;
import com.product.globie.payload.DTO.PostImageDTO;
import com.product.globie.payload.DTO.ProductImageDTO;
import com.product.globie.payload.request.CreatePostRequest;
import com.product.globie.payload.request.UpdatePostRequest;
import com.product.globie.repository.PostCategoryRepository;
import com.product.globie.repository.PostImageRepository;
import com.product.globie.repository.PostRepository;
import com.product.globie.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    PostImageRepository postImageRepository;

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
    public List<PostDTO> getPostByStaff() {
        int userId = util.getUserFromAuthentication().getUserId();
        List<Post> posts = postRepository.findPostByUser(userId);
        if(posts.isEmpty()){
            throw new RuntimeException("There are no Posts of this User Id: " + userId);
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

    @Override
    public List<PostImageDTO> uploadMultiplePostImages(MultipartFile[] multipartFiles, int postId) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with Id: " + postId));

        Integer imageCount = postImageRepository.countImageByPost(postId);
        if(imageCount == null) imageCount = 0;
        if(imageCount >= 10) throw new RuntimeException("Maximum of 10 images already uploaded for this post.");

        List<PostImageDTO> uploadedImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            File file = convertMultiPartToFile(multipartFile);
            Map uploadResult = cloudinaryService.uploadFile(file);

            PostImage postImage = new PostImage();
            postImage.setPost(post);
            postImage.setImagePath(uploadResult.get("url").toString());
            postImage.setPostImageCode(uploadResult.get("public_id").toString());
            postImage.setStatus(true);

            PostImage savedPostImage = postImageRepository.save(postImage);

            PostImageDTO postImageDTO = mapper.map(savedPostImage, PostImageDTO.class);
            postImageDTO.setPostId(savedPostImage.getPost().getPostId());

            uploadedImages.add(postImageDTO);
        }
        return uploadedImages;
    }

    @Override
    public List<PostImageDTO> getAllImageByPost(int postId) {
        List<PostImage> postImages = postImageRepository.getPostImageByPostId(postId)
                .orElseThrow(() -> new RuntimeException("Image not found with Post Id: " + postId));

        List<PostImageDTO> postImageDTOS = postImages.stream()
                .map(image -> {
                    PostImageDTO postImageDTO = mapper.map(image, PostImageDTO.class);

                    if (image.getPost() != null) {
                        postImageDTO.setPostId(image.getPost().getPostId());
                    }

                    return postImageDTO;
                })
                .collect(Collectors.toList());

        return postImageDTOS.isEmpty() ? null : postImageDTOS;
    }

    @Override
    public List<PostImageDTO> getAllImageByPostStatusTrue(int postId) {
        List<PostImage> postImages = postImageRepository.getPostImageByPostId(postId)
                .orElseThrow(() -> new RuntimeException("Image not found with Post Id: " + postId));

        List<PostImageDTO> postImageDTOS = postImages.stream()
                .filter(PostImage :: isStatus)
                .map(image -> {
                    PostImageDTO postImageDTO = mapper.map(image, PostImageDTO.class);

                    if (image.getPost() != null) {
                        postImageDTO.setPostId(image.getPost().getPostId());
                    }

                    return postImageDTO;
                })
                .collect(Collectors.toList());

        return postImageDTOS.isEmpty() ? null : postImageDTOS;
    }

    @Override
    public void deletePostImage(String imageCode) throws IOException {
        PostImage postImage = postImageRepository.getPostImageByImageCode(imageCode)
                .orElseThrow(() -> new RuntimeException("Image not found with Post Image Code: " + imageCode));

        String publicId = extractPublicId(postImage.getImagePath());

        Map result = cloudinaryService.deleteFile(publicId);
        if ("ok".equals(result.get("result"))) {
            postImage.setStatus(false);
            postImageRepository.save(postImage);
        } else {
            throw new RuntimeException("Failed to delete image from Cloudinary: " + result);
        }
    }

    private String extractPublicId(String imageUrl) {
        // Kiểm tra định dạng URL
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty");
        }

        // Ví dụ: https://res.cloudinary.com/demo/image/upload/v1234567890/public_id.jpg
        // Trích xuất public_id "v1234567890/public_id"
        int startIndex = imageUrl.lastIndexOf("/") + 1; // Vị trí bắt đầu của public_id
        int endIndex = imageUrl.lastIndexOf("."); // Vị trí kết thúc trước đuôi file
        if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
            throw new RuntimeException("Invalid image URL format: " + imageUrl);
        }
        return imageUrl.substring(startIndex, endIndex); // Trả về public_id
    }

    @Override
    public PostImageDTO getPostImageByCode(String imageCode) {
        PostImage postImage = postImageRepository.getPostImageByImageCode(imageCode)
                .orElseThrow(() -> new RuntimeException("Image not found with Post Image Code: " + imageCode));

        PostImageDTO postImageDTO = mapper.map(postImage, PostImageDTO.class);
        postImageDTO.setPostId(postImage.getPost().getPostId());

        return postImageDTO;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
