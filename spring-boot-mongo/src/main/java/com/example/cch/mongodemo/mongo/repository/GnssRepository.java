package com.example.cch.mongodemo.mongo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.cch.mongodemo.mongo.entity.Gnss;
import com.example.cch.mongodemo.mongo.mapping.GnssMappingVO;

@Repository
public interface GnssRepository extends MongoRepository<Gnss, String> {
    @Query("{'deviceId' : ?0 }")
    List<GnssMappingVO> getByDeviceId(@Param("id") String id);
}
