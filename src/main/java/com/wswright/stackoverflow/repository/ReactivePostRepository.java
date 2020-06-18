package com.wswright.stackoverflow.repository;

import com.wswright.stackoverflow.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactivePostRepository extends ReactiveCrudRepository<Post, Long> {
}
