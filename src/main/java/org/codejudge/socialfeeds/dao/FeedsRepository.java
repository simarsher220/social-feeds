package org.codejudge.socialfeeds.dao;

import org.codejudge.socialfeeds.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedsRepository extends JpaRepository<User, String> {
    
    User findByUsername(String username);
}
