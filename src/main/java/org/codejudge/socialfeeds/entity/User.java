package org.codejudge.socialfeeds.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private String username;
    private Set<UserFollower> followers;
    private Set<UserFollowing> following;
    private Set<Post> posts;

    public User() {
    }

    @Id
    @Column(name = "username", length = 100, unique = true)
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties
    public Set<UserFollower> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollower> followers) {
        this.followers = followers;
    }

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties
    public Set<UserFollowing> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserFollowing> following) {
        this.following = following;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties
    @JsonProperty("posts")
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
