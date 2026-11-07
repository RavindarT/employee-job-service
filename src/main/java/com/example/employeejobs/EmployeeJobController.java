package com.example.employeejobs;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class EmployeeJobController {
    private final JdbcClient db;

    public EmployeeJobController(JdbcClient db) {
        this.db = db;
    }

    public record Employee(UUID id, String name, String email, UUID jobId) {}
    public record Job(UUID id, String title) {}
    public record NewEmployee(@NotBlank @Size(max = 120) String name,
                              @NotBlank @Email @Size(max = 254) String email) {}
    public record NewJob(@NotBlank @Size(max = 120) String title) {}
    public record Assignment(@NotNull UUID jobId) {}

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody NewEmployee input) {
        Employee employee = new Employee(UUID.randomUUID(), input.name().strip(),
                input.email().toLowerCase(Locale.ROOT), null);
        try {
            db.sql("INSERT INTO employees (id, name, email) VALUES (?, ?, ?)")
                    .params(employee.id(), employee.name(), employee.email()).update();
        } catch (DuplicateKeyException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee email already exists", exception);
        }
        return ResponseEntity.created(URI.create("/api/employees/" + employee.id())).body(employee);
    }

    @GetMapping("/employees")
    public List<Employee> employees() {
        return db.sql("SELECT id, name, email, job_id FROM employees ORDER BY name, id")
                .query(Employee.class).list();
    }

    @GetMapping("/employees/{id}")
    public Employee employee(@PathVariable UUID id) {
        return db.sql("SELECT id, name, email, job_id FROM employees WHERE id = ?")
                .param(id).query(Employee.class).optional()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@Valid @RequestBody NewJob input) {
        Job job = new Job(UUID.randomUUID(), input.title().strip());
        db.sql("INSERT INTO jobs (id, title) VALUES (?, ?)").params(job.id(), job.title()).update();
        return ResponseEntity.created(URI.create("/api/jobs/" + job.id())).body(job);
    }

    @GetMapping("/jobs")
    public List<Job> jobs() {
        return db.sql("SELECT id, title FROM jobs ORDER BY title, id").query(Job.class).list();
    }

    @GetMapping("/jobs/{id}")
    public Job job(@PathVariable UUID id) {
        return db.sql("SELECT id, title FROM jobs WHERE id = ?").param(id).query(Job.class).optional()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }

    @PutMapping("/employees/{id}/job")
    @Transactional
    public Employee assignJob(@PathVariable UUID id, @Valid @RequestBody Assignment input) {
        job(input.jobId());
        db.sql("UPDATE employees SET job_id = ? WHERE id = ?").params(input.jobId(), id).update();
        return employee(id);
    }
}
