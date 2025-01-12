<template>
  <div class="user-shopping-lists-container">
    <h1>Your Shopping Lists</h1>
    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="shoppingLists.length === 0" class="no-lists">No shopping lists found.</div>
    <div v-else>
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      shoppingLists: [],
      loading: true,
    };
  },
  methods: {
    async fetchUserShoppingLists() {
      try {
        const userId = 1; // Replace with the actual user ID (e.g., fetched from auth context or props)
        const response = await fetch(`/api/shopping-lists/user/${userId}`);
        if (!response.ok) throw new Error('Failed to fetch user shopping lists');
        this.shoppingLists = await response.json();
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
  },
  mounted() {
    this.fetchUserShoppingLists();
  },
};
</script>

<style>
.user-shopping-lists-container {
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
.loading {
  font-size: 18px;
  text-align: center;
}
.no-lists {
  font-size: 18px;
  color: gray;
  text-align: center;
}
</style>
