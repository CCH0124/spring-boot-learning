package com.example.cch.mongodemo.mongo.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "connectivity")
public class Connectivity {
    private String name;

    private Integer number;
    
    private String status;
    
    private Integer signalStrength;
    
    @Field("IPlist")
    private List<Map<String, Object>> ipList;
    
    private Integer contentRevision;
    
    private String deviceId;
    
    private Long contentTimestamp;
}
