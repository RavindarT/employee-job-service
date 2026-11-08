CREATE TABLE employee_job (
    emp_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    salary NUMERIC(14,2) NOT NULL CHECK (salary >= 0),
    PRIMARY KEY (emp_id, job_id)
);
