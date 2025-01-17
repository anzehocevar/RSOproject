# Items Service

### Description
The Items service is a microservice responsible for managing operations related to items. It is integrated with:
- **MySQL** for data persistence.
- **Prometheus** for metrics monitoring.
- **Logstash** for centralized logging.

---

### Supported Functionalities
- **Create Items:** Add new items to the inventory.
- **Read Items:** Retrieve details of existing items.
- **Update Items:** Modify item information.
- **Delete Items:** Remove items from the inventory.
- **Search Items:** Query items based on various filters and criteria.

---

### Running Locally

#### Pre-requisites
- **Docker** and **Docker Compose** installed.
- **Java 17** or higher.
- **Maven** for building the project.
- MySQL available either locally or through Docker.

#### Steps

1. **Clone the Repository**
    ```bash
    git clone https://github.com/anzehocevar/RSOproject.git
    cd items-service
    ```

2. **Configure the Database**
   Update the database configuration in `application.properties` or `application.yml`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/itemdb
   spring.datasource.username=root
   spring.datasource.password=mysql
   ```

3. **Build and Run the Application**
   - Build the application:
     ```bash
     mvn clean package
     ```
   - Run the application:
     - Locally: Use Docker Compose to set up and run the application along with its dependencies:
     ```bash
     docker-compose up --build
     ```

     - Globaly: build and push docker

4. **Verify the Setup**
   - Access the API documentation at: `http://localhost:8089/swagger-ui.html`
   - Monitor Prometheus metrics at: `http://localhost:8089/actuator/prometheus`
