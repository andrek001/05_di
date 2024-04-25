package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

@Configuration
public class AppConfig {
    @Bean
    public PostController createController (PostService postService) {
        return new PostController(postService);
    }
    @Bean
    public PostService createService (PostRepository postRepository) {
        return new PostService(postRepository);
    }

    @Bean
    public PostRepository createRepository () {
        return new PostRepository();
    }

}
