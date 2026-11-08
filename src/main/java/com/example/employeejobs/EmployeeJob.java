package com.example.employeejobs;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "employee_job")
public class EmployeeJob {
    @EmbeddedId
    private EmployeeJobId id;
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;
    public EmployeeJobId getId() { return id; }
    public void setId(EmployeeJobId value) { id = value; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal value) { salary = value; }
}
