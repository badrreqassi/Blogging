package com.Platform.Blogging.post;

import com.Platform.Blogging.error.notFoundException;

public class PostNotFoundException  extends notFoundException {
    public PostNotFoundException(String message) { super(message); }

}
