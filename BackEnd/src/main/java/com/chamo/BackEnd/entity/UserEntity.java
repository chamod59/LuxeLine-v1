package com.chamo.BackEnd.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection ="user")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

@Id
private String id;

private String name;

@Indexed(unique = true)
private String email;

@Indexed(unique = true)
private String username;
private String password;

@CreatedDate
private Date createdAt;
private String verificationCode;
private Boolean status;

@DBRef
private List<ProductEntity> products;

public UserEntity(String name, String email, String username, String password) {
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
    this.createdAt = new Date();
    this.verificationCode = "";
    this.status = false;
}

}
