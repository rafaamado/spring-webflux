package com.app.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.app.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmployeeRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public Employee save(Employee employee){
        dynamoDBMapper.save(employee);
        return employee;
    }

    public Employee getEmployeeById(String id){
        return dynamoDBMapper.load(Employee.class, id);
    }

    public Employee delete(String id){
        Employee emp = dynamoDBMapper.load(Employee.class, id);
        dynamoDBMapper.delete(emp);
        return emp;
    }

    public String update(String id, Employee employee){
        dynamoDBMapper.save(employee,
                new DynamoDBSaveExpression().withExpectedEntry("employeeId",
                                            new ExpectedAttributeValue( new AttributeValue().withS(id)))
                );
        return id;
    }
}
