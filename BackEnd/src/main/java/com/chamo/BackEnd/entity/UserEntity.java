package com.chamo.BackEnd.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@Builder
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    @Indexed(unique = true)
    private String username;
}
