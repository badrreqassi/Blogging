package com.Platform.Blogging.post;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record PostRequest(@NotBlank String title , @NotBlank String content, @NotBlank String category ,  @NotBlank Set<String> tags) { }
