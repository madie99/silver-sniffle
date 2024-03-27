package com.example.BlogApplication.Service;
import com.example.BlogApplication.Entity.Comment;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(int postId, String content) {
        User author = new User("wfjhb", "bf@gmail.com", "buhbu");
        Comment commentToAdd=new Comment();
        commentToAdd.setName(author.getName());
        commentToAdd.setEmail(author.getEmail());
        commentToAdd.setComment(content);
        commentToAdd.setPost_id(postId);
        commentToAdd.setCreated_at(LocalDateTime.now());
        commentToAdd.setUpdated_at(LocalDateTime.now());
        commentRepository.save(commentToAdd);
    }

    public void updateComment(Integer commentId, String content) {
        Comment commentToUpdate = commentRepository.findById(commentId).orElse(null);
        if (commentToUpdate != null) {
            commentToUpdate.setComment(content);
            commentToUpdate.setUpdated_at(LocalDateTime.now());
            commentRepository.save(commentToUpdate);
        }
    }

    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }
}
