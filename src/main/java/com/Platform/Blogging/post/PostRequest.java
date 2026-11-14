package com.Platform.Blogging.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PostRequest(@NotBlank @Size(max = 150) String title , @NotBlank String content, @NotBlank String category , @NotEmpty Set<@NotBlank()String> tags) { }
