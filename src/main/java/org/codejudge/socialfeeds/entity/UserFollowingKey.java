package org.codejudge.socialfeeds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class UserFollowingKey implements Serializable {

    private String username;
    private String followingName;

    public UserFollowingKey() {
    }

    public UserFollowingKey(String username, String followingName) {
        this.username = username;
        this.followingName = followingName;
    }

    @NotNull
    @Column(name = "username")
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Column(name = "following_name")
    public String getFollowingName() {
        return followingName;
    }

    public void setFollowingName(String followingName) {
        this.followingName = followingName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserFollowingKey that = (UserFollowingKey) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(followingName, that.followingName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, followingName);
    }
}
