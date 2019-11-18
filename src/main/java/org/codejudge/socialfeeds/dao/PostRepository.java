package org.codejudge.socialfeeds.dao;

import org.codejudge.socialfeeds.entity.Post;
import org.codejudge.socialfeeds.entity.User;
import org.codejudge.socialfeeds.entity.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByPostId(Integer postId);

    List<Post> findByUser_username(User username);

    List<Post> findByUser(User user);
}
