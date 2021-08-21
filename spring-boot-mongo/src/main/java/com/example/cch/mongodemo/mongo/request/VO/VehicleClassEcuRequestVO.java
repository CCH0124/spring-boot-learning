package com.example.cch.mongodemo.mongo.request.VO;

import java.util.List;

import com.example.cch.mongodemo.mongo.entity.Ecu;

import lombok.Value;

@Value
public class VehicleClassEcuRequestVO {
    private String brand;
    private String model;
    private int year;
    private List<Ecu> ecus;

}
