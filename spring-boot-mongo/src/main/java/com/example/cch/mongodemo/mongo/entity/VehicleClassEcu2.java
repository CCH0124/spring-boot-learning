package com.example.cch.mongodemo.mongo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "vehicleClassEcus")
// @CompoundIndex(def = "{'brand': 1, 'model': 1, 'year': 1}", unique = true, name = "compound_index") not working
public class VehicleClassEcu2 {
    @Id
    private String id;
    private String brand;
    private String model;
    private int year;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
    private List<Ecu> ecus;

}

//  db.aiot.createIndex({'brand': 1, 'model': 1, 'year': 1}, { unique: true })