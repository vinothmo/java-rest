package com.rest.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @JsonProperty("id")
    public int id;

    @JsonProperty("name")
    public String name;
    @JsonProperty("email")
    public String email;
    @JsonProperty("profilepicture")
    public String profilePicture;
    @JsonProperty("location")
    public String location;
    @JsonProperty("createdat")
    public String createDate;

}
