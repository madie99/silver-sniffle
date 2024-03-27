package com.example.BlogApplication.Service;

import com.example.BlogApplication.Entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.BlogApplication.Repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagService {
    private final TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
