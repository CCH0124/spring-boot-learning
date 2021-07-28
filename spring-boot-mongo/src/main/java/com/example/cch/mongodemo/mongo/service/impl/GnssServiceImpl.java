package com.example.cch.mongodemo.mongo.service.impl;


import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.cch.mongodemo.config.JsonDateTimeCoverter;
import com.example.cch.mongodemo.mongo.entity.Connectivity;
import com.example.cch.mongodemo.mongo.entity.Gnss;
import com.example.cch.mongodemo.mongo.mapping.GnssMappingVO;
import com.example.cch.mongodemo.mongo.mapping.GnssSomeMappingVO;
import com.example.cch.mongodemo.mongo.repository.GnssRepository;
import com.example.cch.mongodemo.mongo.request.VO.GnssListRequestVO;
import com.example.cch.mongodemo.mongo.service.GnssService;
import com.google.gson.Gson;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

@Slf4j
@Service
public class GnssServiceImpl implements GnssService {
    @Autowired
    GnssRepository gnssRepository;
    
    @Autowired
    MongoTemplate mongoTemplate;

    private String collection = "gnss";

    @Override
    public List<Gnss> getAllGnss() {
        // basic query
        return gnssRepository.findAll();
    }

    @Override
    public Gnss getById(String id) {
        // basic query
        log.debug("Find Gnss by id:{}", id);
        return gnssRepository.findById(id).orElse(null);
    }

    @Override
    public List<GnssMappingVO> getListDeviceId(String id) {
        // basic query
        List<GnssMappingVO> res = new ArrayList<>();
        gnssRepository.getByDeviceId(id).forEach(res::add);
        return res;
    }

    @Override
    public List<GnssMappingVO> getListDeviceIdCustom(String id) {
        // Use Query method
        Query query = new Query();
        query.fields().include("id").include("sensorName").include("coords").include("deviceId"); // like SQL SELECT
        query.addCriteria(Criteria.where("deviceId").is(id)); // WHERE condition
        query.with(Sort.by(Sort.Direction.ASC, "sensorName", "deviceId")); // like SQL ORDER BY
        
        List<Gnss> gnssList = mongoTemplate.find(query, Gnss.class);
        List<GnssMappingVO> res = new ArrayList<>();
        /**
         * 回傳資料而外處理
         */
        for (int i=0; i<gnssList.size(); i++){
            GnssMappingVO gnssMappingVO = new GnssMappingVO().builder()
                .id(gnssList.get(i).getId())
                .sensorName(gnssList.get(i).getSensorName())
                .coords(gnssList.get(i).getCoords())
                .deviceId(gnssList.get(i).getDeviceId())
                .build();

                res.add(gnssMappingVO);
        }

        return res;
    }
    /**
     * is fails
     */
    @Override
    public List<Gnss> getListByTimestampRange(GnssListRequestVO queryVO) {
        // Use Query method
        String deviceId_column = "deviceId";
        String timestamp_column = "timestamp";
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        query.addCriteria(Criteria.where(deviceId_column).is(queryVO.getQuery().getDeviceId()));
        if (queryVO.getQuery().getStart() != null) {
            criteriaList.add(Criteria.where(timestamp_column).gte(queryVO.getQuery().getStart()));
        }
        if (queryVO.getQuery().getEnd() != null) {
            criteriaList.add(Criteria.where(timestamp_column).lte(queryVO.getQuery().getEnd()));
        }

        log.debug("[Start Time]: {}", queryVO.getQuery().getStart().getTime());
        log.debug("[End Time]: {}", queryVO.getQuery().getEnd().getTime());
        query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]))); // implement WHERE and 
        query.with(Sort.by(Sort.Direction.ASC, timestamp_column));
        log.debug("[MongoDB query]: {}", query.toString());

        query.addCriteria(Criteria.where(timestamp_column).gte(queryVO.getQuery().getStart().getTime()).lte(queryVO.getQuery().getEnd().getTime()));
        System.out.println("[MongoDB query]: " + query.toString());
        return mongoTemplate.find(query, Gnss.class, collection);
    }

    @Override
    public List<Gnss> getListByTimestampRangeBasicQuery(GnssListRequestVO queryVO) {
        String deviceId_column = "deviceId";
        String timestamp_column = "timestamp";
        /**
         * Use the original query method
         */
        String q = "{ \""+ deviceId_column+ "\" :\"" + queryVO.getQuery().getDeviceId() + "\", \"$and\" : [{ \"" + timestamp_column + "\" : { \"$gte\" : NumberLong(\"" + queryVO.getQuery().getStart().getTime() +
        "\") } }, {" + "\"" + timestamp_column + "\" : {  \"$lte\" : NumberLong(\"" + queryVO.getQuery().getEnd().getTime() + "\") } }] }";
        
        Query query2 = new BasicQuery(q);
        System.out.println("Start Time... "+ queryVO.getQuery().getStart());
        System.out.println("End Time... "+ queryVO.getQuery().getEnd());
        return mongoTemplate.find(query2, Gnss.class, collection);
    }

    @Override
    public List<Gnss> getListByTimestampRangeBson(GnssListRequestVO queryVO) {
        String deviceId_column = "deviceId";
        String timestamp_column = "timestamp";
        String gps_column = "coords";
        /**
         * User Bson query
         */
        Bson bson = Filters.and(Arrays.asList(
                Filters.eq(deviceId_column, queryVO.getQuery().getDeviceId()),
                Filters.gte(timestamp_column, queryVO.getQuery().getStart().getTime()),
                Filters.lte(timestamp_column, queryVO.getQuery().getEnd().getTime())));
        Bson match = Aggregates.match(bson);
        Bson sort = Aggregates.sort(Sorts.ascending(timestamp_column));
        Bson fields = Aggregates.project(Projections.include(deviceId_column, timestamp_column, gps_column));

        AggregateIterable<Document> findIterable = mongoTemplate.getCollection(collection).aggregate(Arrays.asList(match, fields, sort));

        Gson gson = new Gson();
        JsonWriterSettings settings = JsonWriterSettings.builder()
                .dateTimeConverter(new JsonDateTimeCoverter())
                .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
                .build();
        
        List<Gnss> res = StreamSupport.stream(findIterable.spliterator(), false)
                .map(i -> gson.fromJson(i.toJson(settings), Gnss.class))
                .collect(Collectors.toList());

        return res;
    }

    @Override
    public List<GnssSomeMappingVO> getListByJoin(GnssListRequestVO queryVO) {
        // TODO Auto-generated method stub
        /**
         * if data too large, the response is timeout.
         * https://newbedev.com/mongodb-lookup-in-spring-data-mongo
         */
        String deviceId_column = "deviceId";
        String collectionJoin = "connectivity";
        MatchOperation matchStage = Aggregation.match(Criteria.where(deviceId_column).is(queryVO.getQuery().getDeviceId()));
        LookupOperation lookupOperation = LookupOperation.newLookup()
            .from(collectionJoin) // Left join
            .localField(deviceId_column)
            .foreignField(deviceId_column)
            .as("connectivities");
        ProjectionOperation fields = Aggregation.project("deviceId", "coords", "timestamp", "connectivities");
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "timestamp");
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).cursorBatchSize(100).build();
        Aggregation aggregation = Aggregation.newAggregation( matchStage, lookupOperation, fields, sort).withOptions(options);
        log.debug("[getListByJoin]: {}", aggregation.toString());

        return mongoTemplate.aggregate(aggregation, Gnss.class, GnssSomeMappingVO.class).getMappedResults();
    }
}
