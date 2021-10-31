package com.example.cch.mongodemo.mongo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
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
public class VehicleClassEcu {
    @Id
    private CompositeKey id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Ecu> ecus;

    @Value
    @Builder
    public static class CompositeKey implements Serializable {
        @Indexed
        private String brand;
        @Indexed
        private String model;
        @Indexed
        private int year;
    }
}
