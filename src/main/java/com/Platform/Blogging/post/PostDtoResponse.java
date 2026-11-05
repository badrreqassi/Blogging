package com.Platform.Blogging.post;

import com.Platform.Blogging.tag.Tag;

import java.util.Set;
import java.util.stream.Collectors;

public record PostDtoResponse(Long id, String title ,String content, String category , Set<String> tags ) {
     public static PostDtoResponse from(Post p){  return new PostDtoResponse(p.getId(),p.getTitle(),p.getContent(),p.getCategory()
            ,p.getTags().stream().map(Tag::getName ).collect(Collectors.toSet()));
    }
}
