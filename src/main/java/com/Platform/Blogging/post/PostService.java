package com.Platform.Blogging.post;

import com.Platform.Blogging.tag.Tag;
import com.Platform.Blogging.tag.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository){
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id)  {
        return  PostResponse.from(findPostOrThrow(id));
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(String term) {
        if(term == null || term.isBlank()){
         return this.postRepository.findAll().stream().map(PostResponse::from).sorted(Comparator.comparing(PostResponse::createdAt)).toList();
        }
        return this.postRepository.findByTitleOrCategoryOrContent(term.trim()).stream().map(PostResponse ::from).toList();

    }
    @Transactional()
    public PostResponse createPost(PostRequest request){
        Set<String> names = this.normalizeTagNames(request.tags());
        Set<Tag> tags = this.findTagsNamesOrCreateNewOnes(names) ;
        Post save = new Post(request.title(),request.content(),request.category(),tags);
        return  PostResponse.from(this.postRepository.save(save));

    }
    @Transactional
    public PostResponse updatePost(Long id , PostRequest request){
         Post updatePost = this.findPostOrThrow(id);
         updatePost.setTitle(request.title());
         updatePost.setCategory(request.category());
         updatePost.setContent(request.content());
         Set<String> names = this.normalizeTagNames(request.tags());
         Set<Tag> tags = this.findTagsNamesOrCreateNewOnes(names) ;
         updatePost.setTags(tags);
         return PostResponse.from(postRepository.saveAndFlush(updatePost));
    }
    public void deletePost(Long id){
        Post updatePost = this.findPostOrThrow(id);
        this.postRepository.deleteById(updatePost.getId());

    }


        private Post findPostOrThrow(Long id ){
         return this.postRepository.findById(id)
                 .orElseThrow(()-> new PostNotFoundException(" Post with id "+ id +" are not found"));

    }

        // trim the names and make them upperCase
    private Set<String> normalizeTagNames(Set<String> names){
        if(names== null || names.isEmpty() ) return new HashSet<>();
        return names.stream().map(String::trim).filter(c-> !c.isEmpty()).map(name -> name.toUpperCase(Locale.ROOT)).collect(Collectors.toSet());
    }

    private Set<Tag> findTagsNamesOrCreateNewOnes(Set<String> names){
        if(names.isEmpty()) return new HashSet<>();
         Set<Tag> existing = this.tagRepository.findByNameIn(names);
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
