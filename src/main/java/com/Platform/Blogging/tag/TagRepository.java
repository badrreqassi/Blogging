package com.Platform.Blogging.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag , Long> {

     Set<Tag> findByNameIn(Set<String> names);
}
