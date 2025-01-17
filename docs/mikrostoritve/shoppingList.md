### Description
The ShoppingList service manages shopping lists and integrates with the following technologies:
- **MySQL** for data persistence.
- **Prometheus** for monitoring metrics.
- **Logstash** for centralized logging.

### Supported Functionalities
- **Create Shopping Lists:** Add new shopping lists.
- **Read Shopping Lists:** View details of existing shopping lists.
- **Update Shopping Lists:** Edit information in shopping lists.
- **Delete Shopping Lists:** Remove shopping lists.
- **Manage Items in Lists:** Add, update, or remove items from specific shopping lists.

---

### Running Locally

#### Pre-requisites
- **Docker** and **Docker Compose** installed.

#### Steps

1. **Clone the Repository**
    ```bash
    git clone https://github.com/anzehocevar/RSOproject.git
    cd shoppinglist-service
    ```


2. **Configure the Database**
   Update the database configuration in `application.properties` or `application.yml`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/shoppping_list_db
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
   - Access the API documentation at: `http://localhost:8090/swagger-ui.html`
   - Monitor Prometheus metrics at: `http://localhost:8090/actuator/prometheus`
