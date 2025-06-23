package com.chamo.BackEnd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection ="user")
@Data
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
private String mobile;

@CreatedDate
private Date createdAt;
private String verificationCode;
private Boolean status;

}
