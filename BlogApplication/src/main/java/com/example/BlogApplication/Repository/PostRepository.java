package com.example.BlogApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BlogApplication.Entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p ORDER BY p.published_at DESC")
    List<Post> findAllByOrderByPublishedAtDesc();

    @Query("SELECT p FROM Post p ORDER BY p.published_at ASC")
    List<Post> findAllByOrderByPublishedAtAsc();

    @Query(value = "SELECT DISTINCT p.* FROM post p " +
            "JOIN post_tags pt ON p.id = pt.post_id " +
            "JOIN tag t ON pt.tag_id = t.id " +
            "WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "UNION " +
            "SELECT DISTINCT p.* FROM post p " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " , nativeQuery = true)
    List<Post> searchPosts(@Param("searchTerm") String searchTerm);




    @Query("SELECT DISTINCT p FROM Post p " +
            "WHERE p.id IN (" +
            "  SELECT p.id FROM Post p JOIN p.tags pt WHERE pt.id IN :tagIds " +
            "  UNION " +
            "  SELECT p.id FROM Post p WHERE p.author.id IN :userIds)")

    List<Post> getFilteredPosts(List<Integer> tagIds, List<Integer> userIds);
}
