# Employee Job Service

A Spring Boot REST API for creating employees and job roles and assigning one role per employee. A new assignment replaces the previous role. The service listens on localhost and stores its data in a local H2 database under `data/`.

## Run locally

Use JDK 21 and Maven 3.6.3 or later.

```sh
mvn clean package
java -jar target/employee-job-service-0.0.1-SNAPSHOT.jar
```

Send requests to `http://localhost:8080` with `Content-Type: application/json` when including a JSON body.

| Method | Path | Example body |
| --- | --- | --- |
| POST | /api/employees | `{"name":"Alex Rivera","email":"alex@example.com"}` |
| GET | /api/employees | |
| GET | /api/employees/{id} | |
| POST | /api/jobs | `{"title":"Software Engineer"}` |
| GET | /api/jobs | |
| GET | /api/jobs/{id} | |
| PUT | /api/employees/{id}/job | `{"jobId":"<job UUID>"}` |

Use the UUIDs returned by the create endpoints to assign a role. Invalid requests return HTTP 400, unknown IDs return HTTP 404, and duplicate employee emails return HTTP 409. Employee email uniqueness is case-insensitive.

The API has no authentication and is intended for local development.
