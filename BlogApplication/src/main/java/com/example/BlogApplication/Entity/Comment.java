package com.example.BlogApplication.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="comment")
public class Comment {
    @Id
    @SequenceGenerator(
            name="comment_sequence",
            sequenceName="comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="comment_sequence"
    )
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="comment")
    private String comment;
    @Column(name="post_id")
    private Integer post_id;
    @Column(name="created_at")
    private LocalDateTime created_at;
    @Column(name="updated_at")
    private LocalDateTime updated_at;

    public Comment(String name, String email, String comment, Integer post_id, LocalDateTime created_at, LocalDateTime updated_at) {
        this.name = name;
        this.email = email;
        this.comment = comment;
        this.post_id = post_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Comment() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                ", post_id=" + post_id +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
