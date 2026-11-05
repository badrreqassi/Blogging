package com.Platform.Blogging.post;

import java.util.Set;

public record PostRequest(String title , String content, String category , Set<String> tags) { }
