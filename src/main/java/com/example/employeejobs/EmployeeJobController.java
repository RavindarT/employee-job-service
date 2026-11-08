package com.example.employeejobs;
import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/employee-jobs")
public class EmployeeJobController {
    private final EmployeeJobService service;
    public EmployeeJobController(EmployeeJobService service) { this.service = service; }
    
    
    @GetMapping 
    public List<EmployeeJob> list() { 
    	return service.list();
    	
    }
    
    @GetMapping("/{empId}/{jobId}") public EmployeeJob get(@PathVariable Long empId, @PathVariable Long jobId) { return service.get(empId, jobId); }
    @PostMapping public ResponseEntity<EmployeeJob> create(@Valid @RequestBody EmployeeJobRequest request) {
        EmployeeJob created = service.create(request);
        return ResponseEntity.created(URI.create("/api/employee-jobs/" + created.getId().getEmpId() + "/" + created.getId().getJobId())).body(created);
    }
    @PutMapping("/{empId}/{jobId}") public EmployeeJob update(@PathVariable Long empId, @PathVariable Long jobId, @Valid @RequestBody EmployeeJobRequest request) { return service.update(empId, jobId, request); }
    @DeleteMapping("/{empId}/{jobId}") public ResponseEntity<Void> delete(@PathVariable Long empId, @PathVariable Long jobId) {
        service.delete(empId, jobId);
        return ResponseEntity.noContent().build();
    }
}
