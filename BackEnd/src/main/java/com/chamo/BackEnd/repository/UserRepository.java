package com.chamo.BackEnd.repository;

import com.chamo.BackEnd.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

}
