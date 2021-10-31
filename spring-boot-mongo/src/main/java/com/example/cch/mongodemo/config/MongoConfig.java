package com.example.cch.mongodemo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongo_db}")
    private String database;

    @Value("${mongo_hosts}")
    private String hosts;

    @Value("${mongo_ports}")
    private String ports;

    @Value("${mongo_user_name}")
    private String user;

    @Value("${mongo_password}")
    private String password;

    @Override
    protected String getDatabaseName() {
        // TODO Auto-generated method stub
        return database;
    }

    @Override
    public MongoClient mongoClient() {
        // TODO Auto-generated method stub
        MongoCredential credential = MongoCredential.createScramSha256Credential(user, database, password.toCharArray());
        // createScramSha256Credential(user, database, password.toCharArray());

        List<ServerAddress> serverAddresses = new ArrayList<>();
        List<String> hostList = Arrays.asList(hosts.split(","));
        List<String> portList = Arrays.asList(ports.split(","));
        for (String host : hostList) {
            Integer index = hostList.indexOf(host);
            Integer port = Integer.parseInt(portList.get(index));

            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }

        MongoClient mongoClients = MongoClients.create(
            MongoClientSettings.builder()
            // .applyToSslSettings(builder -> builder.enabled(true))
            .applyToClusterSettings(builder -> builder.hosts(serverAddresses))
            .credential(credential)
            .build());

        log.info("Mongodb Server Addresses: {}", serverAddresses.toString());
        log.info("Mongo Client: {}", mongoClients.getDatabase(database));

        /**
         * 從 database 獲取 gnss collection
         */
        // MongoDatabase database = mongoClients.getDatabase(getDatabaseName());
        // MongoCollection<Document> coll = database.getCollection("gnss");
        // coll.find().forEach(d -> System.out.println(d.toJson()));

        return mongoClients;

    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        // TODO Auto-generated method stub
        return Collections.singleton("com.example.cch.mongo");
    }
    
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}
