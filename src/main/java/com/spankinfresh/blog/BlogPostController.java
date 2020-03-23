package com.spankinfresh.blog;

import com.spankinfresh.blog.data.BlogPostRepository;
import com.spankinfresh.blog.domain.BlogPost;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/articles")
public class BlogPostController {
/*
    @PostMapping
    public ResponseEntity createBlogEntry () {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost/api/articles/1");
        return new ResponseEntity("", headers, HttpStatus.CREATED);
    }
*/
    private final BlogPostRepository blogPostRepository;
    public BlogPostController(
            BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }
    @PostMapping
    public ResponseEntity<BlogPost> createBlogEntry(
            @Valid @RequestBody BlogPost blogPost,
            UriComponentsBuilder uriComponentsBuilder) {
        blogPost.setDatePosted(new Date());
        BlogPost savedItem = blogPostRepository.save(blogPost);
        UriComponents uriComponents =
                uriComponentsBuilder.path("/api/articles/{id}")
                        .buildAndExpand(savedItem.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uriComponents.toUri().toString());
        return new ResponseEntity<>(savedItem, headers,
                HttpStatus.CREATED);
    }
}
