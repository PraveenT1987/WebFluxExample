package com.mkyong.reactive.repository;

import com.mkyong.reactive.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieRepository {

    Flux<Movie> findAll();
    Flux<Movie> findByName(String mname);

}
