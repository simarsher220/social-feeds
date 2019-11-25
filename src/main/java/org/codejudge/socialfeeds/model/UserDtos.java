package org.codejudge.socialfeeds.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserDtos {

    public List<UserDto> users;

    @JsonProperty("users")
    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
