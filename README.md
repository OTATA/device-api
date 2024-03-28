## Device App
* There are operations available in the rest-api for **Device**:
  * POST
  * PATCH
  * PUT
  * DELETE
### Rules
Some rules have been assumed for creating a Device, they are:
* **Name** and **Brand** cannot be blank and must contain more than two characters
* There may be **Devices** with the same **Name**

### Dependencies
To start the environment, Docker and Docker Compose are required
### Run tests
``
./gradle test
``
#### Coverage Report

build/reports/jacoco/index.html

### Run App
``
docker-compose up
``

After the containers are UP, the app will be available at http://localhost:8080/v1/devices

### Postman Collection UI
You can use this file [DeviceApi.postman_collection.json](DeviceApi.postman_collection.json) to try the api via postman
