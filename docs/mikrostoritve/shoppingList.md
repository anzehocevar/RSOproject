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

2. **Run the Application**
   Use Docker Compose to set up and run the application along with its dependencies:
   ```bash
   docker-compose up --build
   ```

3. **Verify the Setup**
   - Access the API documentation at: `http://localhost:8090/swagger-ui.html`
   - Monitor Prometheus metrics at: `http://localhost:8090/actuator/prometheus`
