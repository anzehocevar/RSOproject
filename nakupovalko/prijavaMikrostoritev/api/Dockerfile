services:
  postgres:
    image: postgres:16.2
    volumes:
      - D:\SpringBoot-Project-Work\keycloak\Docker\postgres_data:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    networks:
      - keycloak_network
    healthcheck:
      test: ["CMD", "pg_isready", "-U", "${POSTGRES_USER}"]  # Check if PostgreSQL is ready
      interval: 5s   # Interval between health checks
      timeout: 5s    # Timeout for each health check
      retries: 3     # Number of retries before marking as unhealthy

  keycloak:
    image: quay.io/keycloak/keycloak:23.0.6
    command: start
    environment:
      KC_HOSTNAME: localhost
      KC_HOSTNAME_PORT: 9090
      KC_HOSTNAME_STRICT_BACKCHANNEL: false
      KC_HTTP_ENABLED: true
      KC_HOSTNAME_STRICT_HTTPS: false
      KC_HEALTH_ENABLED: true
      KEYCLOAK_ADMIN: ${KEYCLOAK_ADMIN}
      KEYCLOAK_ADMIN_PASSWORD: ${KEYCLOAK_ADMIN_PASSWORD}
      KC_DB: postgres
      KC_DB_URL: jdbc:postgresql://postgres/${POSTGRES_DB}
      KC_DB_USERNAME: ${POSTGRES_USER}
      KC_DB_PASSWORD: ${POSTGRES_PASSWORD}
    ports:
      - "9090:8080"  # Expose Keycloak on port 9090
    restart: always
    depends_on:
      postgres:
        condition: service_healthy  # Ensure Keycloak starts only if PostgreSQL is healthy
    networks:
      - keycloak_network

networks:
  keycloak_network:
    driver: bridge