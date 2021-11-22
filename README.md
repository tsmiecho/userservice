## How to run the project

### Prerequisites

This project requires you to install the following dependencies:

- docker: https://docs.docker.com/install/
- docker-compose: https://docs.docker.com/compose/install/
- java 11 or higher: https://sdkman.io/usage -> use sdkman to select the version (any provider)
- maven 3.6.0: https://maven.apache.org/install.html

### Build & Run Tests

From within the project, run: mvn clean install

### Run

From within the project, run `docker-compose up -d`

### Verify the service is up and running

Running `curl http://localhost:8080/actuator/health` should return `{"status": "UP"}`

### Apply Changes

After you've made changes, run the above two commands again

### View logs

From within the project, run `docker-compose logs -f userservice`

### Run integration tests

Go to userservice-e2e-tests directory and use mvn clean install.
Open userservice-e2e-tests/target/site/serenity/index.html in your browser to have detailed view about tests results.
