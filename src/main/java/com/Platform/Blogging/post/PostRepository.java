package com.Platform.Blogging.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.title LIKE CONCAT('%', :term, '%') OR p.category LIKE CONCAT('%', :term, '%')OR p.content LIKE CONCAT('%', :term, '%') ORDER BY p.createdAt DESC")
    List<Post> findByTitleOrCategoryOrContent(String term);
}
