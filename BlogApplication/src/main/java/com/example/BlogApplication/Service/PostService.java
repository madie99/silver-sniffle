package com.example.BlogApplication.Service;

import com.example.BlogApplication.Entity.Post;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

@Service
public class PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public void savePost(Post post) {
        User author = new User("wfjhb", "bf@gmail.com", "buhbu");
        post.setAuthor(author);
        post.setPublished_at(LocalDateTime.now());
        post.setIs_published(true);
        post.setCreated_at(LocalDateTime.now());
        post.setUpdated_at(LocalDateTime.now());
        post.setExcerpt(post.getContent().substring(0, 10));
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public Page<Post> getAllPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }

    public Post getPostById(Integer postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElse(null);
    }

    public void updatePost(Post postToUpdate) {
        postRepository.save(postToUpdate);
    }

    public void updatePost(Integer postId, String title, String content, String tags) {
        Post postToUpdate = postRepository.findById(postId).orElse(null);
        if (postToUpdate != null) {
            postToUpdate.setTitle(title);
            postToUpdate.setContent(content);
            postToUpdate.setUpdated_at(LocalDateTime.now());
            postRepository.save(postToUpdate);
        }
    }

    public List<Post> searchPosts(String searchTerm) {
        return postRepository.searchPosts(searchTerm);
    }

    public List<Post> sortPosts(String sortOption) {
        if (sortOption.equals("latest")) {
            return postRepository.findAllByOrderByPublishedAtDesc();
        } else if (sortOption.equals("oldest")) {
             return postRepository.findAllByOrderByPublishedAtAsc();
        } else {
            return null;
        }
    }

    public List<Post> getFilteredPosts(List<Integer> tagIds,List<Integer> userIds) {
        return postRepository.getFilteredPosts(tagIds,userIds);

    }
}

