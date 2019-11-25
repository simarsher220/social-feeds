package org.codejudge.socialfeeds.service;

import org.codejudge.socialfeeds.dao.FeedsRepository;
import org.codejudge.socialfeeds.dao.PostRepository;
import org.codejudge.socialfeeds.dao.UserFollowerRepository;
import org.codejudge.socialfeeds.dao.UserFollowingRepository;
import org.codejudge.socialfeeds.entity.Post;
import org.codejudge.socialfeeds.entity.User;
import org.codejudge.socialfeeds.entity.UserFollower;
import org.codejudge.socialfeeds.entity.UserFollowing;
import org.codejudge.socialfeeds.error.exception.CustomException;
import org.codejudge.socialfeeds.error.exception.NotFoundException;
import org.codejudge.socialfeeds.mapper.FeedsMapper;
import org.codejudge.socialfeeds.model.PostDto;
import org.codejudge.socialfeeds.model.UserDto;
import org.codejudge.socialfeeds.model.UserDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FeedsService {

    @Autowired
    private FeedsRepository feedsRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserFollowerRepository followerRepository;

    @Autowired
    private UserFollowingRepository followingRepository;

    public PostDto createPost(Post post, String username) throws CustomException {
        Post updatedPost = null;
        PostDto postDto = null;
        User user = null;
        try {
            user = feedsRepository.findByUsername(username);
            if (user == null) {
                 throw new Exception("user not found!!");
            }
            Set<Post> posts = user.getPosts();
            if (posts == null) {
                posts = new HashSet<>();
            }
            post.setUpvotes(0);
            post.setUser(user);
            post = postRepository.saveAndFlush(post);
            if (post == null) {
                // throw new Exception();
            }
            posts.add(post);
            user.setPosts(posts);
            user = feedsRepository.saveAndFlush(user);
            if (user == null) {
                 throw new Exception("error creating post to the system!!");
            }
            postDto = FeedsMapper.getPostDtoFromPost(post);
        }
        catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return postDto;
    }

    public void upvotePost(String username, Integer postId) {
//        Post post = null;
//        User user = null;
//        boolean flag = false;
//        try{
//            user = feedsRepository.findByUsername(username);
//            if (user == null) {
//                // throw error
//            }
//            post = postRepository.findByPostId(postId);
//            if (post == null) {
//                // throw error
//            }
//            List<User> followings = user.getFollowing();
//            if (followings == null || followings.size() == 0) {
//                // throw error, upvote not possible
//            }
//            for (User following : followings) {
//                if (post.getUser().getUsername().equals(following)) {
//                    flag = true;
//                    Integer upvotes = post.getUpvotes();
//                    post.setUpvotes(upvotes + 1);
//                    post = postRepository.saveAndFlush(post);
//                    break;
//                }
//            }
//            if (flag == false) {
//                // throw Exception ("operation not possible");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    public void commentPost(Integer postId, Comment comment) {
//        Post post = feedsRepository.findByPostId(postId);
//        if (post == null) {
////            throw new Exception();
//        }
//        List<String> comments = post.getComments();
//        if (comments == null) {
//            comments = new ArrayList<>();
//        }
//        comments.add(comment.getComment());
//        post.setComments(comments);
//        try {
//            post = feedsRepository.saveAndFlush(post);
//        }
//        catch (Exception e) {
//
//        }
//    }

    public List<PostDto> getPosts(String username) throws CustomException {
        User user = null;
        Set<Post> posts = new HashSet<>();
        List<PostDto> postDtos;
        try {
            user = feedsRepository.findByUsername(username);
            if (user == null) {
                throw new Exception("User not found!!");
            }
            Set<UserFollowing> following = user.getFollowing();
            if (following == null || following.size() == 0) {
                postDtos = new ArrayList<>();
                return postDtos;
            }
            for (UserFollowing followingUser : following) {
                List<Post> followingPosts = postRepository.findByUser(followingUser.getFollowing());
                if (followingPosts == null || followingPosts.size() == 0) {
                    continue;
                }
                posts.addAll(followingPosts);
            }
        }
        catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        postDtos = FeedsMapper.getPostDtosFromPosts(posts);
        return postDtos;
    }

    public UserDto createUser(User user) throws CustomException {
        User updatedUser;
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new CustomException("Invalid user object!!", HttpStatus.BAD_REQUEST);
        }
        updatedUser = feedsRepository.findByUsername(user.getUsername());
        if (updatedUser != null) {
            throw new CustomException("User already exists!!", HttpStatus.BAD_REQUEST);
        }
        UserDto userDto = null;
        try {
            updatedUser = feedsRepository.saveAndFlush(user);
            if (updatedUser == null) {
                 throw new Exception("error creating user to out system!!");
            }
            userDto = FeedsMapper.getUserDtoFromUser(user);
        }
        catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return userDto;
    }

    public void addFriend(String usernameA, String usernameB) throws CustomException {
        User userA = null, userB = null;
        UserFollowing following = null;
        UserFollower follower = null;
        try {
            userA = feedsRepository.findByUsername(usernameA);
            userB = feedsRepository.findByUsername(usernameB);
            if (userA == null || userB == null) {
                throw new Exception("Either of the users not found");
            }
            Set<UserFollowing> usersFollowing = null; Set<UserFollower> usersFollowers = null;
            if (userA.getFollowing() == null) {
                usersFollowing = new HashSet<>();
                if (userB != null) {
                    following = new UserFollowing(userA, userB);
                    usersFollowing.add(following);
                }
            }
            else {
                usersFollowing = userA.getFollowing();
                if (userB != null) {
                    following = new UserFollowing(userA, userB);
                    usersFollowing.add(following);
                }
            }
            userA.setFollowing(usersFollowing);
            if (userB.getFollowers() == null) {
                usersFollowers = new HashSet<>();
                if (userA != null) {
                    follower = new UserFollower(userB, userA);
                    usersFollowers.add(follower);
                }
            }
            else {
                usersFollowers = userB.getFollowers();
                if (userA != null) {
                    follower = new UserFollower(userB, userA);
                    usersFollowers.add(follower);
                }
            }
            userB.setFollowers(usersFollowers);
            following = followingRepository.saveAndFlush(following);
            follower = followerRepository.saveAndFlush(follower);
            if (following == null || follower == null) {
                throw new Exception("Error in processing the request!!");
            }
            userA = feedsRepository.saveAndFlush(userA);
            userB = feedsRepository.saveAndFlush(userB);
            if (userA == null || userB == null) {
                 throw new Exception("Error in processing the request!!");
            }
        }
        catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public UserDto getUser(String username) throws NotFoundException {
        UserDto userDto = null;
        User user = null;
        try {
            user = feedsRepository.findByUsername(username);
            if (user == null) {
                    throw new Exception();
            }
        } catch (Exception e) {
            throw new NotFoundException();
        }
        userDto = FeedsMapper.getUserDtoFromUser(user);
        return userDto;
    }

    public UserDtos getAllUsers() {

        List<UserDto> userDtos = null;
        List<User> users = null;
        users = feedsRepository.findAll();
        if (users == null) {
            users = new ArrayList<>();
        }
        userDtos = FeedsMapper.getAllUserDtosFromUsers(users);
        UserDtos allUsers = new UserDtos();
        allUsers.setUsers(userDtos);
        return allUsers;
    }
}
