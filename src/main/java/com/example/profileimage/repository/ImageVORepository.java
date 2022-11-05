package com.example.profileimage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.profileimage.model.ImageVO;

public interface ImageVORepository extends MongoRepository<ImageVO, String> {

}
