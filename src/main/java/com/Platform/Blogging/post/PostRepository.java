package com.Platform.Blogging.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(String title, String category , String content);

    @Query("select p from Post p where p.title LIKE %:title% OR p.category LIKE %:category% OR p.content LIKE %:content% ORDER BY p.createdAt")
    List<Post> findByTitleOrCategoryOrContent(String title, String category , String content);


}
