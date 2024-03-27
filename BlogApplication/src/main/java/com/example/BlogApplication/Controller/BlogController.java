package com.example.BlogApplication.Controller;
import com.example.BlogApplication.Entity.Tag;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Service.TagService;
import com.example.BlogApplication.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import com.example.BlogApplication.Entity.Post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.BlogApplication.Service.PostService;
import com.example.BlogApplication.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BlogController {
    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;
    private final UserService userService;
    @Autowired
    public BlogController(PostService postService, CommentService commentService,TagService tagService,UserService userService) {

        this.postService = postService;
        this.commentService=commentService;
        this.tagService=tagService;
        this.userService=userService;
    }

    @GetMapping("/createpost")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "create_post";
    }

    @PostMapping("/publish")
    public String publishPost(@RequestParam("title") String title,
                            @RequestParam("tags") List<String> tags,
                            @RequestParam("content") String content) {
        Post postToSave=new Post();
        postToSave.setTitle(title);
        postToSave.setContent(content);
        postToSave.setTags(tags);
        postService.savePost(postToSave);
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getAllPosts( Model model,@RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {

        int pageNumber = page.orElse(1);
        int pageSize = size.orElse(4);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Post> posts = postService.getAllPosts(pageable);

        model.addAttribute("posts", posts);
        List<Tag> tags = tagService.getAllTags();
        List<User> users = userService.getAllUsers();
        model.addAttribute("tags", tags);
        model.addAttribute("users", users);
        int totalPages = posts.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "all_posts";
    }

    @GetMapping("/delete_post/{postId}")
    public String deletePost(@PathVariable("postId") Integer postId) {
        postService.deletePost(postId);
        return "redirect:/posts";
    }
    @GetMapping("/modify_post/{postId}")
    public String showModifyPostForm(@PathVariable("postId") Integer postId, Model model) {
        Post post = postService.getPostById(postId);
        model.addAttribute("post", post);
        return "modify_post";
    }

    @GetMapping("/update_post/{postId}")
    public String updatePost(@PathVariable("postId") Integer postId, Model model) {
        Post postToUpdate = postService.getPostById(postId);
        model.addAttribute("post", postToUpdate);
        return "update_post";
    }
    @PostMapping("/create_updated_post")
    public String createUpdatedPost(@RequestParam("postId") Integer postId,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    @RequestParam("tags") String tags) {
        postService.updatePost(postId, title, content, tags);
        return "redirect:/posts";
    }

    @PostMapping("/create_comment/{postId}")
    public String createComment(@PathVariable("postId") int postId,
                                @RequestParam("comment") String comment) {
        commentService.addComment(postId, comment);
        return "redirect:/modify_post/{postId}";
    }
    @PostMapping("/update_comment/{commentId}/{postId}")
    public String updateComment(@RequestParam("commentId") Integer commentId,
                                @RequestParam("comment") String comment) {
        commentService.updateComment(commentId, comment);
        return "redirect:/modify_post/{postId}";
    }

    @PostMapping("/delete_comment/{commentId}/{postId}")
    public String deleteComment(@PathVariable("commentId") Integer commentId) {
         commentService.deleteComment(commentId);
        return "redirect:/modify_post/{postId}";
    }

    @GetMapping("/searchPosts")
    public String searchPosts(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<Post> searchResults = postService.searchPosts(searchTerm);
        model.addAttribute("posts", searchResults);
        return "all_posts";
    }

    @GetMapping("/sortPosts")
    public String sortPosts(@RequestParam("sortOption") String sortOption, Model model) {
        List<Post> sortedPosts = postService.sortPosts(sortOption);
        model.addAttribute("posts", sortedPosts);
        model.addAttribute("sortOption",sortOption);
        return "all_posts";
    }


    @GetMapping("/filterPosts")
    public String filterPosts(@RequestParam(name = "tags", required = false) List<Integer> tagIds,
                                  @RequestParam(name = "users", required = false) List<Integer> userIds,
                                  Model model) {
        List<Post> filteredPosts =  new ArrayList<>();

        if (!tagIds.isEmpty() || !userIds.isEmpty()) {
            filteredPosts.addAll(postService.getFilteredPosts(tagIds,userIds));
        }
        if(filteredPosts.size()!=0){
            model.addAttribute("posts", filteredPosts);
        }
        List<Tag> tags = tagService.getAllTags();
        List<User> users = userService.getAllUsers();
        model.addAttribute("tags", tags);
        model.addAttribute("users", users);
        model.addAttribute("selectedTagIds", tagIds != null ? tagIds : Collections.emptyList());
        model.addAttribute("selectedUserIds", userIds != null ? userIds : Collections.emptyList());
        return "all_posts";
    }







}
