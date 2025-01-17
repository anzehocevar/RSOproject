<template>
  <div>
    <!-- Navigation Bar -->
    <nav class="navbar">
      <ul>
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/user-shopping-lists">My Shopping Lists</router-link></li>
        <li><a href="/api/items">Items</a></li>
      </ul>
      <div class="weather">
        <span v-if="weather">{{ weather }}</span>
        <span v-else>Loading weather...</span>
      </div>
    </nav>
    <!-- Main Content -->
    <router-view />
  </div>
</template>

<script>

import axios from "axios";

export default {
  name:"App",
  data() {
    return {
      weather: null, // To store weather information
    };
  },
  mounted() {
    this.fetchWeather();
  },
  methods: {
    async fetchWeather() {
      try {
        const response = await axios.get("https://wttr.in/?format=%C+%t"); // wttr.in URL with format query
        this.weather = response.data.trim(); // Clean up whitespace
      } catch (error) {
        console.error("Failed to fetch weather:", error);
        this.weather = "Unable to fetch weather.";
      }
    },
  },
};

</script>
<style>
/* Optional global styles */
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}

.navbar {
  background-color: #333;
  color: white;
  padding: 10px 20px;
}

.navbar ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  gap: 15px;
}

.navbar ul li {
  display: inline;
}

.navbar ul li a {
  text-decoration: none;
  color: white;
  font-weight: bold;
}

.navbar ul li a:hover {
  text-decoration: underline;
}
</style>
