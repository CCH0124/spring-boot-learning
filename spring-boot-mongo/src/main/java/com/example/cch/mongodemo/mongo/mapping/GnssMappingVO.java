package com.example.cch.mongodemo.mongo.mapping;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GnssMappingVO {
    private String id;
    
    private String sensorName;

    private Map<String, Object> coords;
    
    private String deviceId;
}
