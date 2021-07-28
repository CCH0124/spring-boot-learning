package com.example.cch.mongodemo.controller;

import java.util.List;

import com.example.cch.mongodemo.mongo.entity.Gnss;
import com.example.cch.mongodemo.mongo.mapping.GnssMappingVO;
import com.example.cch.mongodemo.mongo.request.VO.GnssListRequestVO;
import com.example.cch.mongodemo.mongo.service.GnssService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GnssController {
    @Autowired
    GnssService gnssService;

    @GetMapping("/gnss/all")
    public ResponseEntity<?> getAllList() {
        return new ResponseEntity<>(gnssService.getAllGnss(), HttpStatus.OK);
    }

    @GetMapping("/gnss/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(gnssService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/gnss/device/{id}")
    public ResponseEntity<?> getByDeviceId(@PathVariable("id") String id) {
        return new ResponseEntity<>(gnssService.getListDeviceId(id), HttpStatus.OK);
    }

    @GetMapping("/gnss/device/custom/{id}")
    public ResponseEntity<?> getByDeviceIdCustom(@PathVariable("id") String id) {
        return new ResponseEntity<>(gnssService.getListDeviceIdCustom(id), HttpStatus.OK);
    }

    @PostMapping("/gnss/list")
    public ResponseEntity<?> getListByTimestampRange(@RequestBody GnssListRequestVO requestVO) {
        log.debug("Request Body: {}", requestVO.toString());
        return new ResponseEntity<>(gnssService.getListByTimestampRange(requestVO), HttpStatus.OK);
    }

    @PostMapping("/gnss/list/basic")
    public ResponseEntity<?> getListByTimestampRangeBasicQuery(@RequestBody GnssListRequestVO requestVO) {
        log.debug("Request Body: {}", requestVO.toString());
        return new ResponseEntity<>(gnssService.getListByTimestampRangeBasicQuery(requestVO), HttpStatus.OK);
    }

    @PostMapping("/gnss/list/bson")
    public ResponseEntity<?> getListByTimestampRangeBson(@RequestBody GnssListRequestVO requestVO) {
        log.debug("Request Body: {}", requestVO.toString());
        return new ResponseEntity<>(gnssService.getListByTimestampRangeBson(requestVO), HttpStatus.OK);
    }

    @PostMapping("/gnss/list/join")
    public ResponseEntity<?> getListByJoin(@RequestBody GnssListRequestVO requestVO) {
        log.debug("Request Body: {}", requestVO.toString());
        return new ResponseEntity<>(gnssService.getListByJoin(requestVO), HttpStatus.OK);
    }
}
