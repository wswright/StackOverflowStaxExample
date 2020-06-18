package com.wswright.stackoverflow.service;

import com.wswright.stackoverflow.model.Post;
import com.wswright.stackoverflow.repository.ReactivePostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class PostService implements IPostService {
	private final ReactivePostRepository postRepo;

	public PostService(ReactivePostRepository postRepo) {
		this.postRepo = postRepo;
	}

	@PostConstruct
	public void PostConstruct() {
		log.info("PostService Constructed!");
	}

	@Override
	public Mono<Post> getPostById(Long id) {
		return postRepo.findById(id);
	}

	@Override
	public Flux<Post> getAllPosts() {
		return postRepo.findAll();
	}
}
