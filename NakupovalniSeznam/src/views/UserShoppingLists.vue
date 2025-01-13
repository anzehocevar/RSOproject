<template>
  <div class="user-shopping-lists-container">
    <h1>Your Shopping Lists</h1>

    <!-- Add New Shopping List -->
    <div class="add-shopping-list">
      <h2>Add New Shopping List</h2>
      <div class="box">
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
          <button type="submit" class="btn btn-primary">Add List</button>
        </form>
      </div>
    </div>

    <!-- Shopping Lists -->
    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="shoppingLists.length === 0" class="no-lists">No shopping lists found.</div>
    <div v-else>
      <div v-for="list in shoppingLists" :key="list.id" class="shopping-list-box box">
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
        <!-- Edit Shopping List -->
        <button @click="startEditing(list)" class="btn btn-secondary">Edit</button>
        <div v-if="editingList && editingList.id === list.id" class="edit-box">
          <form @submit.prevent="saveShoppingList">
            <div class="form-group">
              <input
                  v-model="editingList.name"
                  type="text"
                  class="form-input"
                  placeholder="List Name"
                  required
              />
            </div>
            <div class="form-group">
              <textarea
                  v-model="editingList.items"
                  class="form-textarea"
                  placeholder="Comma-separated item IDs"
              ></textarea>
            </div>
            <div class="form-group">
              <textarea
                  v-model="editingList.users"
                  class="form-textarea"
                  placeholder="Comma-separated user IDs"
              ></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
            <button @click="cancelEditing" class="btn btn-danger">Cancel</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import jwtDecode from 'jwt-decode';

export default {
  data() {
    return {
      shoppingLists: [],
      loading: true,
      newList: {
        name: '',
        items: ''
      },
      editingList: null
    };
  },
  methods: {
    async fetchUserShoppingLists() {
      try {
        // const token = localStorage.getItem('authToken'); // Assuming the token is stored in localStorage
        // const decodedToken = jwtDecode(token);
        const userId = 201; // Replace with the actual logic for fetching userId

        const response = await fetch(`/api/shopping-lists/user/${userId}`);
        if (!response.ok) throw new Error('Failed to fetch user shopping lists for' + userId);
        this.shoppingLists = await response.json();
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async addShoppingList() {
      try {
        // Replace with actual logic for fetching the userId
        const userId = 201;

        // Correct endpoint for creating a new shopping list
        const response = await fetch('/api/shopping-lists', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            name: this.newList.name,
            itemIds: this.newList.items
                ? this.newList.items.split(',').map(Number)
                : [],
            userIds: [userId] // Optionally associate the shopping list with the user
          })
        });

        if (!response.ok) {
          const errorText = await response.text(); // Get error details if any
          throw new Error(`Failed to add shopping list: ${errorText}`);
        }

        const newList = await response.json();
        this.shoppingLists.push(newList);

        // Reset input fields after successful addition
        this.newList.name = '';
        this.newList.items = '';
      } catch (error) {
        console.error('Error adding shopping list:', error.message);
      }
    },
    startEditing(list) {
      this.editingList = {
        ...list,
        items: list.itemIds.join(','),
        users: list.userIds.join(',')
      };
    },
    cancelEditing() {
      this.editingList = null;
    },
    async saveShoppingList() {
      try {
        // @PutMapping("/{shoppingListId}")
        const response = await fetch(`/api/shopping-lists/${this.editingList.id}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            name: this.editingList.name,
            itemIds: this.editingList.items
                ? this.editingList.items.split(',').map(Number)
                : [],
            userIds: this.editingList.users
                ? this.editingList.users.split(',').map(Number)
                : []
          })
        });
        if (!response.ok) throw new Error('Failed to update shopping list');
        const updatedList = await response.json();
        const index = this.shoppingLists.findIndex((list) => list.id === updatedList.id);
        if (index !== -1) this.shoppingLists.splice(index, 1, updatedList);
        this.editingList = null;
      } catch (error) {
        console.error(error);
      }
    }
  },
  mounted() {
    this.fetchUserShoppingLists();
  }
};
</script>

<style>
.user-shopping-lists-container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.box {
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

.form-group {
  margin-bottom: 10px;
}

.form-input,
.form-textarea {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

textarea.form-textarea {
  height: 80px;
}

.btn {
  display: inline-block;
  padding: 8px 16px;
  margin-right: 5px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
}

.btn-secondary {
  background-color: #6c757d;
  color: #fff;
}

.btn-danger {
  background-color: #dc3545;
  color: #fff;
}

.btn:hover {
  opacity: 0.9;
}
</style>
