<template>
  <div class="shopping-list-container">
    <h1>Shopping Lists</h1>
    <div v-for="list in shoppingLists" :key="list.id" class="shopping-list-box">
      <h2>{{ list.name }}</h2>
      <p><strong>ID:</strong> {{ list.id }}</p>
      <div>
        <strong>Items:</strong>
        <ul>
          <li v-for="item in list.itemIds" :key="item">Item ID: {{ item }}</li>
        </ul>
      </div>
      <div>
        <strong>Bought Items:</strong>
        <ul>
          <li v-for="item in list.boughtItems" :key="item">Item ID: {{ item }}</li>
        </ul>
      </div>
      <div>
        <strong>Users:</strong>
        <ul>
          <li v-for="user in list.userIds" :key="user">User ID: {{ user }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      shoppingLists: [] // Fetch data from API on mount
    };
  },
  methods: {
    async fetchShoppingLists() {
      try {
        const response = await fetch('/api/shopping-lists');
        if (!response.ok) throw new Error('Failed to fetch shopping lists');
        this.shoppingLists = await response.json();
      } catch (error) {
        console.error(error);
      }
    },
  },
  mounted() {
    this.fetchShoppingLists();
  },
};
</script>

<style>
.shopping-list-container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.shopping-list-box {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.shopping-list-box h2 {
  margin-top: 0;
}
</style>
