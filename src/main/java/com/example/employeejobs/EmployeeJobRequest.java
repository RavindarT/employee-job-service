package com.example.employeejobs;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record EmployeeJobRequest(
    @NotNull @Positive Long empId,
    @NotNull @Positive Long jobId,
    @NotNull @DecimalMin("0.00") BigDecimal salary
) {}
