package org.codejudge.socialfeeds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFollowerKey implements Serializable {

    private String username;
    private String followerName;

    public UserFollowerKey() {
    }

    public UserFollowerKey(String username, String followerName) {
        this.username = username;
        this.followerName = followerName;
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
    @Column(name = "follower_name")
    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserFollowerKey that = (UserFollowerKey) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(followerName, that.followerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, followerName);
    }

}
