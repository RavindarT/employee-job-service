package com.example.employeejobs;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class EmployeeJobService {
    private final EmployeeJobRepository repository;
    public EmployeeJobService(EmployeeJobRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<EmployeeJob> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public EmployeeJob get(Long empId, Long jobId) { return repository.findById(new EmployeeJobId(empId, jobId)).orElseThrow(() -> new ResourceNotFoundException("EmployeeJob not found")); }
    @Transactional
    public EmployeeJob create(EmployeeJobRequest request) {
        if (repository.existsById(new EmployeeJobId(request.empId(), request.jobId()))) throw new DuplicateResourceException("EmployeeJob already exists");
        EmployeeJob record = new EmployeeJob();
        record.setId(new EmployeeJobId(request.empId(), request.jobId()));
        record.setSalary(request.salary());
        return repository.save(record);
    }
    @Transactional
    public EmployeeJob update(Long empId, Long jobId, EmployeeJobRequest request) {
        EmployeeJob record = get(empId, jobId);
        record.setSalary(request.salary());
        return repository.save(record);
    }
    @Transactional
    public void delete(Long empId, Long jobId) { repository.delete(get(empId, jobId)); }
}
