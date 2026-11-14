package com.Platform.Blogging.post;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RequestMapping("/blogging")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
     }

     @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request) {
        PostResponse post = this.postService.createPost(request);
         URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(post.id())
                 .toUri();
         return ResponseEntity.created(location)
                 .header("X-Request-Id", UUID.randomUUID().toString()).body(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = this.postService.getPost(id);
        return ResponseEntity.ok()
                .header("X-Request-Id", UUID.randomUUID().toString()).body(post);
    }

    @GetMapping("/all/posts")
    public ResponseEntity< List<PostResponse>> getAllPost(@RequestParam(required = false) String term) {
        List<PostResponse> posts = this.postService.getAllPosts(term);
        return ResponseEntity.ok()
                .header("X-Request-Id", UUID.randomUUID().toString()).body(posts);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long id,@Valid @RequestBody PostRequest request) {
        PostResponse post = this.postService.updatePost(id ,request);
        return ResponseEntity.ok()
                .header("X-Request-Id", UUID.randomUUID().toString()).body(post);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PostResponse> deletePostById(@PathVariable Long id) {
         this.postService.deletePost(id);
         return ResponseEntity.noContent().build();

    }


}
