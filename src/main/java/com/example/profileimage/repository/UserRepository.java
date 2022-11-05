package com.example.profileimage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.profileimage.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
