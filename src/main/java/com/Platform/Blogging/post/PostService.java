package com.Platform.Blogging.post;

import com.Platform.Blogging.tag.Tag;
import com.Platform.Blogging.tag.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PostService {
    public final PostRepository postRepository;
    public final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository){
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id)  {
        return  this.postRepository.findById(id).map(PostResponse :: from).orElseThrow(()-> new PostNotFoundException(" Post with id "+ id + " are not found"));

    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(String term)  {
        return this.postRepository.findByTitleOrCategoryOrContent(term).stream().map(PostResponse ::from).toList();
    }
    @Transactional()
    public PostResponse createPost(PostRequest request){
        Set<String> names = this.normalizeTagNames(request.tags());
        Set<Tag> tags = this.findTagsNamesOrCreateNewOnes(names) ;
        Post save = new Post(request.title(),request.content(),request.category(),tags);
       return  PostResponse.from(save);

    }
    // trim the names and make them upperCase
    private Set<String> normalizeTagNames(Set<String> names){
        return names.stream().map(String::toUpperCase).map(String::trim).collect(Collectors.toSet());

    }
    private Set<Tag> findTagsNamesOrCreateNewOnes(Set<String> names){
        if(names.isEmpty()) return new HashSet<>();
         Set<Tag> existing = this.tagRepository.findByNameIgnoreCaseIn(names);
         Set<String> existingNames = existing.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
         Set<Tag> newTags = names.stream().filter(c-> !existingNames.contains(c)).map(Tag::new).collect(Collectors.toSet());
         List<Tag> saveNew = this.tagRepository.saveAll(newTags);
         Set<Tag> result = new HashSet<>(existing);
         result.addAll(saveNew);
         return result;



    }
}
