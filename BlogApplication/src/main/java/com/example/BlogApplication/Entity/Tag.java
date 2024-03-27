package com.example.BlogApplication.Entity;
import com.example.BlogApplication.Entity.Post;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="tag")
public class Tag {
    @Id
    @SequenceGenerator(
            name="tag_sequence",
            sequenceName="tag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="tag_sequence"
    )
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="created_at")
    private LocalDateTime created_at;
    @Column(name="updated_at")
    private LocalDateTime updated_at;
    @ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name="post_tags",joinColumns = @JoinColumn(name="tag_id"),inverseJoinColumns = @JoinColumn(name="post_id"))
    private List<Post> posts;

    public Tag(String name, LocalDateTime created_at, LocalDateTime updated_at) {
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Tag() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
