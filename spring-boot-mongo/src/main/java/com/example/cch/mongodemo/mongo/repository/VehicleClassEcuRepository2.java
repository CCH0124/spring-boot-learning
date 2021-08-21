package com.example.cch.mongodemo.mongo.repository;

import com.example.cch.mongodemo.mongo.entity.VehicleClassEcu2;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VehicleClassEcuRepository2 extends MongoRepository<VehicleClassEcu2, String>{

}
