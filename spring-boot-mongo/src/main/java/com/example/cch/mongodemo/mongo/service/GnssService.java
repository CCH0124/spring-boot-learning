package com.example.cch.mongodemo.mongo.service;

import java.util.List;

import com.example.cch.mongodemo.mongo.entity.Gnss;
import com.example.cch.mongodemo.mongo.mapping.GnssMappingVO;
import com.example.cch.mongodemo.mongo.mapping.GnssSomeMappingVO;
import com.example.cch.mongodemo.mongo.request.VO.GnssListRequestVO;

import org.springframework.data.domain.Page;


public interface GnssService {

    List<Gnss> getAllGnss();

    Gnss getById(String id);
    
    List<GnssMappingVO> getListDeviceId(String id);

    List<GnssMappingVO> getListDeviceIdCustom(String id);

    List<Gnss> getListByTimestampRange(GnssListRequestVO requestVO);
    
    List<Gnss> getListByTimestampRangeBasicQuery(GnssListRequestVO requestVO);

    List<Gnss> getListByTimestampRangeBson(GnssListRequestVO requestVO);

    List<GnssSomeMappingVO> getListByJoin(GnssListRequestVO requestVO);
}
