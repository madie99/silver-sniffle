
package com.example.BlogApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BlogApplication.Entity.Comment;
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
