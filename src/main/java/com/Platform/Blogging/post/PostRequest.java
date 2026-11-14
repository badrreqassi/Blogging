package com.Platform.Blogging.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PostRequest(@NotBlank @Size(max = 150) String title , @NotBlank String content, @NotBlank @NotBlank @Size(max = 255)String category , @NotEmpty Set<@NotBlank() @Size(max =20)String> tags) { }
