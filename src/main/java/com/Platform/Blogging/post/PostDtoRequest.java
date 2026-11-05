package com.Platform.Blogging.post;

import java.util.Set;

public record PostDtoRequest(String title , String content, String category , Set<String> tags) { }
