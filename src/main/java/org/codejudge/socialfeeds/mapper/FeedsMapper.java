package org.codejudge.socialfeeds.mapper;

import org.codejudge.socialfeeds.entity.Post;
import org.codejudge.socialfeeds.entity.User;
import org.codejudge.socialfeeds.entity.UserFollower;
import org.codejudge.socialfeeds.entity.UserFollowing;
import org.codejudge.socialfeeds.model.PostDto;
import org.codejudge.socialfeeds.model.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FeedsMapper {

    public static UserDto getUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        List<String> followers = new ArrayList<>(), following = new ArrayList<>();
        List<PostDto> postDtos;
        userDto.setUsername(user.getUsername());
        if (user.getFollowing() != null) {
            for (UserFollowing userFollowing : user.getFollowing()) {
                following.add(userFollowing.getId().getFollowingName());
            }
            userDto.setFollowing(following);
        }
        if (user.getFollowers() != null) {
            for (UserFollower userFollower : user.getFollowers()) {
                followers.add(userFollower.getId().getFollowerName());
            }
            userDto.setFollowers(followers);
        }
        if (user.getPosts() != null) {
            postDtos = getPostDtosFromPosts(user.getPosts());
            userDto.setPosts(postDtos);
        }
        return userDto;
    }

    public static PostDto getPostDtoFromPost(Post post) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setImageUrl(post.getUrl());
        postDto.setCaption(post.getDescription());
        postDto.setUpvotes(post.getUpvotes());
        return postDto;
    }

    public static List<PostDto> getPostDtosFromPosts(Set<Post> posts) {
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(getPostDtoFromPost(post));
        }
        return postDtos;
    }

    public static List<UserDto> getAllUserDtosFromUsers(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(getUserDtoFromUser(user));
        }
        return userDtos;
    }
}
