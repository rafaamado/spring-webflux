package com.app.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@DynamoDbBean
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("employeeId")
    public String getEmployeeId() {
        return employeeId;
    }

    @DynamoDbAttribute("firstName")
    public String getFirstName() {
        return firstName;
    }

    @DynamoDbAttribute("lastName")
    public String getLastName() {
        return lastName;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }
}
