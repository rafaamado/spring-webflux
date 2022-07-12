package com.app.config;

import com.app.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Slf4j
@Configuration
@Profile("!test")
public class DynamoDbConfig {

    @Value("${aws.dynamodb.access-key}")
    private String accessKey;

    @Value("${aws.dynamodb.secret-key}")
    private String secretKey;

    @Value("${aws.dynamodb.region}")
    private String region;


    @Bean
    public AwsCredentialsProvider credentialsProvider() {
        log.info("Iniciando configuracao para comunicacao com a AWS com acessos!");
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
    }

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient(AwsCredentialsProvider credentialsProvider) {
        log.info("Obtendo configuracoes para comunicacao com o DynamoDB.");
        return DynamoDbAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        log.info("Iniciando configuração de conexão com o DynamoDB.");
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }

    @Bean
    public DynamoDbAsyncTable<Employee> employeeDbAsyncTable(final DynamoDbEnhancedAsyncClient asyncClient) {
        return asyncClient.table("employee", TableSchema.fromBean(Employee.class));
    }
}