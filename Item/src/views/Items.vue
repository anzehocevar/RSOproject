<template>
  <div class="items-container">
    <h1>Available Items</h1>
    <router-link to="/" class="nav-link">Back to Home</router-link>
    <div v-if="error" class="error">
      <p>{{ error }}</p>
    </div>
    <div v-if="items.length === 0 && !error">
      <p>No items available.</p>
    </div>
    <div v-else class="items-list">
      <div v-for="item in items" :key="item.id" class="item-card">
        <h2>{{ item.name }}</h2>
        <p><strong>Description:</strong> {{ item.description }}</p>
        <p><strong>Price:</strong> ${{ item.price.toFixed(2) }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [],
      error: null,
    };
  },
  methods: {
    async fetchItems() {
      try {
        const response = await fetch('/api/items'); // Ensure the API is reachable from the frontend
        if (!response.ok) {
          throw new Error('Failed to fetch items');
        }
        this.items = await response.json();
      } catch (err) {
        this.error = err.message;
        console.error(err);
      }
    },
  },
  mounted() {
    this.fetchItems();
  },
};
</script>

<style>
.items-container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.nav-link {
  display: inline-block;
  margin-bottom: 20px;
  text-decoration: none;
  color: #007bff;
}

.nav-link:hover {
  text-decoration: underline;
}

.items-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.item-card {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 16px;
  width: 300px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.item-card h2 {
  margin-top: 0;
}

.error {
  color: red;
}
</style>
