package org.codejudge.socialfeeds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_following")
@NoArgsConstructor
public class UserFollowing {

    private UserFollowingKey id;
    private User user;
    private User following;

    public UserFollowing(User user, User following) {
        this.id = new UserFollowingKey(user.getUsername(), following.getUsername());
        this.user = user;
        this.following = following;
    }

    @EmbeddedId
    public UserFollowingKey getId() {
        return id;
    }

    public void setId(UserFollowingKey id) {
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
    @MapsId("followingName")
    @JoinColumn(name = "following_name", referencedColumnName = "username")
    @JsonIgnore
    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
