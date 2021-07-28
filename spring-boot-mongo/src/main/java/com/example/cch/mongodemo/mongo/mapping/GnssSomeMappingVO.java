package com.example.cch.mongodemo.mongo.mapping;

import java.util.List;
import java.util.Map;

// import com.example.cch.mongodemo.mongo.entity.Connectivity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GnssSomeMappingVO {
    private String deviceId;
    private Map<String, Object> coords;
    private List<Connectivity> connectivities;
    private Long timestamp;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class Connectivity {
    private Integer signalStrength;
    private String deviceId;
}