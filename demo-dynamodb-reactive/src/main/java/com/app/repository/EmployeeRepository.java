package com.app.repository;

import com.app.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class EmployeeRepository {

    private final DynamoDbAsyncTable<Employee> employeeDbAsyncTable;

    public Flux<Employee> findAll(){
        return Flux.from(employeeDbAsyncTable.scan().items());
    }

    public Mono<Employee> save(Employee employee){
        employee.setEmployeeId(UUID.randomUUID().toString());
        return Mono.fromFuture(employeeDbAsyncTable.putItem(employee)).thenReturn(employee);
    }

    public Mono<Employee> getEmployeeById(String id){
        return Mono.fromFuture(
                employeeDbAsyncTable.getItem(getKeyBuild(id)));
    }

    public Mono<Employee> findByEmail(String email){

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":emailVal", AttributeValue.builder().s(email).build());

        Expression expression = Expression.builder()
                .expression("email = :emailVal")
                .expressionValues(expressionValues)
                .build();

        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                .filterExpression(expression)
                .build();

        return Mono.from(employeeDbAsyncTable.scan(scanEnhancedRequest).items());
    }

    public Mono<Employee> findByName(String name){

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":name", AttributeValue.builder().s(name).build());

        Expression expression = Expression.builder()
                .expression("firstName = :name or lastName = :name")
                .expressionValues(expressionValues)
                .build();

        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                .filterExpression(expression)
                .build();

        return Mono.from(employeeDbAsyncTable.scan(scanEnhancedRequest).items());
    }

    public Mono<Employee> delete(String id){
       return Mono.fromFuture(employeeDbAsyncTable.deleteItem(getKeyBuild(id)));
    }

    public Mono<Employee> update(String id, Employee employee){
        employee.setEmployeeId(id);
        return Mono.fromFuture(employeeDbAsyncTable.updateItem(employee));
    }

    private Key getKeyBuild(String id) {
        return Key.builder().partitionValue(id)
                //.sortValue()
                .build();
    }
}
