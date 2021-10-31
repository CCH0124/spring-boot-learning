package com.example.cch.mongodemo.mongo.repository;

import com.example.cch.mongodemo.mongo.entity.VehicleClassEcu;
import com.example.cch.mongodemo.mongo.entity.VehicleClassEcu.CompositeKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VehicleClassEcuRepository extends MongoRepository<VehicleClassEcu, CompositeKey>{

}
