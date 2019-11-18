package org.codejudge.socialfeeds.dao;

import org.codejudge.socialfeeds.entity.UserFollower;
import org.codejudge.socialfeeds.entity.UserFollowerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower, UserFollowerKey> {
}
