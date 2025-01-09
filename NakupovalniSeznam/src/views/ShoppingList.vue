<template>
  <div>
    <h1>Shopping List Manager</h1>

    <!-- Shopping Lists -->
    <div v-if="shoppingLists.length > 0">
      <h2>All Shopping Lists</h2>
      <ul>
        <li v-for="list in shoppingLists" :key="list.id">
          <h3>{{ list.name }}</h3>
          <p><strong>Shopping List ID:</strong> {{ list.id }}</p>

          <!-- Items in the Shopping List -->
          <h4>Items:</h4>
          <ul>
            <li v-for="itemId in list.itemIds" :key="itemId">
              Item ID: {{ itemId }}
              <span v-if="list.boughtItems.includes(itemId)"> - Bought ✅</span>
            </li>
          </ul>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No shopping lists available. Create one!</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      shoppingLists: [], // Holds the shopping lists fetched from the API
    };
  },
  methods: {
    // Fetch shopping lists from the backend API
    async fetchShoppingLists() {
      try {
        const response = await axios.get('/api/shopping-lists');
        this.shoppingLists = response.data;
      } catch (error) {
        console.error('Error fetching shopping lists:', error);
      }
    },
  },
  mounted() {
    // Fetch data when the component is mounted
    this.fetchShoppingLists();
  },
};
</script>

<style>
/* Styles for better readability */
h1, h2 {
  color: #2c3e50;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin-bottom: 10px;
}
</style>
