package com.wswright.stackoverflow.service;

import com.wswright.stackoverflow.model.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPostService {
	Mono<Post> getPostById(Long id);
	Flux<Post> getAllPosts();
}
