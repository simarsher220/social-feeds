package org.codejudge.socialfeeds.controller;

import org.codejudge.socialfeeds.entity.Post;
import org.codejudge.socialfeeds.entity.User;
import org.codejudge.socialfeeds.error.exception.CustomException;
import org.codejudge.socialfeeds.error.exception.NotFoundException;
import org.codejudge.socialfeeds.model.PostDto;
import org.codejudge.socialfeeds.model.SuccessResponse;
import org.codejudge.socialfeeds.model.UserDto;
import org.codejudge.socialfeeds.service.FeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeedsController {

    @Autowired
    private FeedsService feedsService;

    @RequestMapping(value = "/create-user/", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) throws CustomException {
        UserDto userDto = feedsService.createUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/follow/{userA}/{userB}", method = RequestMethod.POST)
    public ResponseEntity addFriend(@PathVariable("userA") String userA, @PathVariable("userB") String userB) throws CustomException {
        feedsService.addFriend(userA, userB);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/create-post/{user}")
    public ResponseEntity sharePost(@RequestBody Post post, @PathVariable("user") String user) throws CustomException {
        Post.validate(post);
        PostDto postDto = feedsService.createPost(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/get-user/{user}")
    public ResponseEntity getUser( @PathVariable("user") String username) throws NotFoundException {
        UserDto user = feedsService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/get-user/{user}/following")
    public ResponseEntity getUsersFollowing( @PathVariable("user") String username) throws NotFoundException {
        UserDto user = feedsService.getUser(username);
        return new ResponseEntity<>(user.getFollowing(), HttpStatus.OK);
    }

    @GetMapping(value = "/get-user/{user}/followers")
    public ResponseEntity getUsersFollowers( @PathVariable("user") String username) throws NotFoundException {
        UserDto user = feedsService.getUser(username);
        return new ResponseEntity<>(user.getFollowers(), HttpStatus.OK);
    }

//    @PostMapping("/upvote-post/{user}/{postId}")
//    public  ResponseEntity upvotePost(@PathVariable("user") String username, @PathVariable("postId") Integer postId) {
//        feedsService.upvotePost(username, postId);
//        return null;
//    }

//    @PostMapping("/comment-post/{postId}")
//    public  ResponseEntity commentPost(@PathVariable("postId") Integer postId, @RequestBody Comment comment) {
//        Comment.validate(comment);
//        feedsService.commentPost(postId, comment);
//        return null;
//    }

    @GetMapping("/all-posts/{user}")
    public ResponseEntity getPosts(@PathVariable("user") String username) throws CustomException {
        return new ResponseEntity<>(feedsService.getPosts(username), HttpStatus.OK);
    }


}
