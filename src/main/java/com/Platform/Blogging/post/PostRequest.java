package com.Platform.Blogging.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

import static com.Platform.Blogging.post.Post.CONTENT_MAX;
import static com.Platform.Blogging.post.Post.TEXT_MAX;

public record PostRequest(@NotBlank @Size(max = 150) String title , @NotBlank @Size(max = CONTENT_MAX) String content, @NotBlank @Size(max = TEXT_MAX)String category , @NotEmpty @Size(max = 20) Set<@NotBlank()  @Size(max = TEXT_MAX) String> tags) { }
