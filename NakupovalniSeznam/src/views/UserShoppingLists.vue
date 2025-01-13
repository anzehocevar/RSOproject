<template>
  <div class="user-shopping-lists-container">
    <div class="split-screen">
      <!-- Left Section: Shopping Lists -->
      <div class="left-section">
        <h1>Your Shopping Lists</h1>

        <!-- Add New Shopping List -->
        <div class="add-shopping-list box">
          <h2>Add New Shopping List</h2>
          <form @submit.prevent="addShoppingList">
            <div class="form-group">
              <input
                  v-model="newList.name"
                  type="text"
                  class="form-input"
                  placeholder="List Name"
                  required
              />
            </div>
            <div class="form-group">
              <textarea
                  v-model="newList.items"
                  class="form-textarea"
                  placeholder="Comma-separated item IDs (optional)"
              ></textarea>
            </div>
            <div class="form-group">
              <textarea
                  v-model="newList.boughtItems"
                  class="form-textarea"
                  placeholder="Comma-separated bought item IDs (optional)"
              ></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Add List</button>
          </form>
        </div>

        <!-- Shopping Lists -->
        <div v-if="loading" class="loading">Loading...</div>
        <div v-else-if="shoppingLists && shoppingLists.length === 0" class="no-lists">No shopping lists found.</div>
        <div v-else>
          <div
              v-for="list in shoppingLists"
              :key="list.id"
              class="shopping-list-box box"
          >
            <h2>{{ list.name }}</h2>
            <p><strong>ID:</strong> {{ list.id }}</p>
            <div>
              <strong>Items:</strong>
              <ul>
                <li v-for="item in list.itemIds || []" :key="item">Item ID: {{ item }}</li>
              </ul>
            </div>
            <div>
              <strong>Bought Items:</strong>
              <ul>
                <li v-for="item in list.boughtItems || []" :key="item">Item ID: {{ item }}</li>
              </ul>
            </div>
            <div>
              <strong>Users:</strong>
              <ul>
                <li v-for="user in list.userIds || []" :key="user">User ID: {{ user }}</li>
              </ul>
            </div>
            <!-- Edit and Delete Buttons -->
            <div class="buttons">
              <button @click="startEditing(list)" class="btn btn-secondary">Edit</button>
              <button @click="deleteShoppingList(list.id)" class="btn btn-danger">Delete</button>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Section: Items -->
      <div class="right-section">
        <h1>Available Items</h1>
        <div v-if="loadingItems" class="loading">Loading...</div>
        <div v-else-if="items.length === 0" class="no-items">No items available.</div>
        <div v-else>
          <div v-for="item in items" :key="item.id" class="item-box box">
            <h2>{{ item.name }}</h2>
            <p><strong>Description:</strong> {{ item.description }}</p>
            <p><strong>Price:</strong> ${{ item.price.toFixed(2) }}</p>
          </div>
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
      items: [],
      loading: true,
      loadingItems: true,
      newList: {
        name: '',
        items: '',
        boughtItems: '',
      },
      editingList: null,
    };
  },
  methods: {
    async fetchUserShoppingLists() {
      try {
        const userId = 201; // Replace with actual user ID logic
        const response = await fetch(`/api/shopping-lists/user/${userId}`);
        if (!response.ok) throw new Error('Failed to fetch shopping lists');
        this.shoppingLists = await response.json();
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async fetchItems() {
      try {
        const response = await fetch('http://20.31.172.254/api/items');
        if (!response.ok) {
          throw new Error(`Failed to fetch items: ${response.statusText}`);
        }
        const data = await response.json();
        console.log('Fetched items:', data); // Debug log
        this.items = data;
      } catch (error) {
        console.error('Error fetching items:', error.message);
      } finally {
        this.loadingItems = false;
      }
    },
  },
  mounted() {
    this.fetchUserShoppingLists();
    this.fetchItems();
  },
};
</script>

<style>
/* Split-Screen Layout */
.split-screen {
  display: flex;
  gap: 20px;
}

.left-section,
.right-section {
  width: 50%;
}

.user-shopping-lists-container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.add-shopping-list {
  margin-bottom: 20px;
  padding: 20px;
  border: 2px solid #007bff;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.shopping-list-box,
.item-box {
  margin-bottom: 16px;
  padding: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 1.8rem;
  margin-bottom: 20px;
}

.loading {
  font-size: 1.2rem;
  color: #6c757d;
}

.no-lists,
.no-items {
  font-size: 1.2rem;
  color: #dc3545;
}
</style>
