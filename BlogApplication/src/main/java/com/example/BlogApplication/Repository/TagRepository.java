package com.example.BlogApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BlogApplication.Entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
}
