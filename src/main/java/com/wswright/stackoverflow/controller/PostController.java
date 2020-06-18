package com.wswright.stackoverflow.controller;

import com.wswright.stackoverflow.model.Post;
import com.wswright.stackoverflow.service.IPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
public class PostController {
	private IPostService postService;

	public PostController(IPostService postService) {
		this.postService = postService;
	}



	@GetMapping("/{id}")
	public Mono<Post> getPostById(@PathVariable Long id) {
		return postService.getPostById(id);
	}

	@GetMapping
	public Flux<Post> getAllPosts() {
		return postService.getAllPosts();
	}
}
