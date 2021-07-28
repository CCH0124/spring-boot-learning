package com.example.cch.mongodemo.mongo.entity;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "gnss")
public class Gnss {
    @Id
    private String id;

    private Integer contentRevision;
    
    private String sensorName;

    private Map<String, Object> coords; // Json format
    
    private Double coordsAccuracy;

    private String service;

    private Double speed;
    
    private Double heading;
    
    private Long timestamp;

    private String deviceId;

    private Long contentTimestamp;
}


// Field annotation 可以幫助與 mongoDB 中定義的 field 進行映射