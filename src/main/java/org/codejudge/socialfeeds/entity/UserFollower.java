package org.codejudge.socialfeeds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_follower")
@NoArgsConstructor
public class UserFollower {

    private UserFollowerKey id;
    private User user;
    private User follower;

    public UserFollower(User user, User follower) {
        this.id = new UserFollowerKey(user.getUsername(), follower.getUsername());
        this.user = user;
        this.follower = follower;
    }

    @EmbeddedId
    public UserFollowerKey getId() {
        return id;
    }

    public void setId(UserFollowerKey id) {
        this.id = id;
    }

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username", referencedColumnName = "username")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @MapsId("followerName")
    @JoinColumn(name = "follower_name", referencedColumnName = "username")
    @JsonIgnore
    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

}
