package com.example.cch.mongodemo.mongo.request.VO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GnssListRequestVO {

    private ListQueryVO query;
    // private Integer current;
    // private Integer pageSize;
    
    @Data
    public class ListQueryVO {
        private String deviceId;
        private Date start;
        private Date end;
    }
}

