package com.example.cch.mongodemo.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.cch.mongodemo.mongo.entity.VehicleClassEcu;
import com.example.cch.mongodemo.mongo.entity.VehicleClassEcu2;
import com.example.cch.mongodemo.mongo.repository.VehicleClassEcuRepository;
import com.example.cch.mongodemo.mongo.repository.VehicleClassEcuRepository2;
import com.example.cch.mongodemo.mongo.request.VO.VehicleClassEcuRequestVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VehicleClassEcuController {
    @Autowired
    VehicleClassEcuRepository vehicleClassEcuRepository;

    @Autowired
    VehicleClassEcuRepository2 vehicleClassEcuRepository2;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody VehicleClassEcuRequestVO requestVO) {
        VehicleClassEcu.CompositeKey svc = VehicleClassEcu.CompositeKey.builder().brand(requestVO.getBrand()).model(requestVO.getModel()).year(requestVO.getYear()).build();
        VehicleClassEcu v  = VehicleClassEcu.builder().id(svc).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).ecus(requestVO.getEcus()).build();
        return new ResponseEntity<>(vehicleClassEcuRepository.save(v), HttpStatus.OK);
    }

    @PostMapping("/add2")
    public ResponseEntity<?> add2(@RequestBody VehicleClassEcuRequestVO requestVO) {
        VehicleClassEcu2 v  = VehicleClassEcu2.builder()
        .brand(requestVO.getBrand())
        .model(requestVO.getModel())
        .year(requestVO.getYear())
        .createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).ecus(requestVO.getEcus()).build();
        int s = requestVO.getEcus().size();
        for (int i=0; i<s; i++){
            requestVO.getEcus().get(i).setId(UUID.randomUUID().toString());
            requestVO.getEcus().get(i).setCreateTime(LocalDateTime.now());
            requestVO.getEcus().get(i).setUpdateTime(LocalDateTime.now());
        }
        return new ResponseEntity<>(vehicleClassEcuRepository2.save(v), HttpStatus.OK);
    }
}
