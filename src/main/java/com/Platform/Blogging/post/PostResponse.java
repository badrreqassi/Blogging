package com.Platform.Blogging.post;

import com.Platform.Blogging.tag.Tag;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record PostResponse(Long id, String title , String content, String category , Set<String> tags , Instant createdAt , Instant updatedAt ) {
     public static PostResponse from(Post p){
         return new PostResponse(
                 p.getId(),
                 p.getTitle(),
                 p.getContent(),
                 p.getCategory(),
                 p.getTags().stream().map(Tag::getName ).sorted().collect(Collectors.toSet()),
                 p.getCreatedAt(),
                 p.getUpdatedAt());
    }
}
