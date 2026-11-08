package com.example.employeejobs;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class EmployeeJobId implements Serializable {
    @Column(name = "emp_id", nullable = false)
    private Long empId;
    @Column(name = "job_id", nullable = false)
    private Long jobId;
    protected EmployeeJobId() {}
    public EmployeeJobId(Long empId, Long jobId) { this.empId = empId; this.jobId = jobId; }
    public Long getEmpId() { return empId; }
    public void setEmpId(Long value) { this.empId = value; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long value) { this.jobId = value; }
    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof EmployeeJobId id)) return false;
        return Objects.equals(empId, id.empId) && Objects.equals(jobId, id.jobId);
    }
    @Override public int hashCode() { return Objects.hash(empId, jobId); }
}
