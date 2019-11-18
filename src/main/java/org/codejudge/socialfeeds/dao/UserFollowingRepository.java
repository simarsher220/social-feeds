package org.codejudge.socialfeeds.dao;

import org.codejudge.socialfeeds.entity.UserFollowing;
import org.codejudge.socialfeeds.entity.UserFollowingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowingRepository extends JpaRepository<UserFollowing, UserFollowingKey> {
}
