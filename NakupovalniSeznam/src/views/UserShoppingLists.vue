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
            <!-- Edit Shopping List -->
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
                  v-model="editingList.boughtItems"
                  class="form-textarea"
                  placeholder="Comma-separated bought item IDs"
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
      <!-- Add New Item Section -->
      <div class="right-section">
      <h1>Items</h1>
        <div class="add-item-box box">
          <h2>Add New Item</h2>
          <form @submit.prevent="addItem">
            <div class="form-group">
              <input
                  v-model="newItem.name"
                  type="text"
                  class="form-input"
                  placeholder="Item Name"
                  required
              />
            </div>
            <div class="form-group">
          <textarea
              v-model="newItem.description"
              class="form-textarea"
              placeholder="Item Description"
          ></textarea>
            </div>
            <div class="form-group">
              <input
                  v-model="newItem.price"
                  type="number"
                  class="form-input"
                  placeholder="Price"
                  required
              />
            </div>
            <button type="submit" class="btn btn-primary">Add Item</button>
          </form>
        </div>
        <div v-if="loadingItems" class="loading">Loading...</div>
        <div v-else-if="items.length === 0" class="no-items">No items available.</div>
        <div v-else>
          <div v-for="item in items" :key="item.id" class="item-box box">
            <h2>{{ item.name }} (ID: {{ item.id }})</h2>
            <p><strong>Description:</strong> {{ item.description }}</p>
            <p><strong>Price:</strong> ${{ item.price.toFixed(2) }}</p>
            <!-- Buttons for managing items -->
            <div class="buttons">
              <button @click="editItem(item)" class="btn btn-secondary">Edit</button>
              <button @click="deleteItem(item.id)" class="btn btn-danger">Remove</button>
            </div>
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
      newItem: {
        name: '',
        description: '',
        price: null,
      },
      editingItem: null, // To track the item being edited
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
    async deleteShoppingList(listId) {
      try {
        console.log('Deleting shopping list with ID:', listId);
        const response = await fetch(`/api/shopping-lists/${listId}`, {
          method: 'DELETE',
        });
        if (!response.ok) {
          const error = await response.text();
          throw new Error(`Failed to delete shopping list: ${error}`);
        }
        // Update local state
        this.shoppingLists = this.shoppingLists.filter((list) => list.id !== listId);
        console.log('Shopping list deleted successfully');
      } catch (error) {
        console.error('Error deleting shopping list:', error.message);
      }
    },
    startEditing(list) {
      console.log('Editing shopping list:', list);
      this.editingList = {
        ...list,
        items: (list.itemIds || []).join(','), // Convert `itemIds` to a comma-separated string
        boughtItems: (list.boughtItems || []).join(','), // Convert `boughtItems` to a string
        userIds: (list.userIds || []).join(','), // Convert `userIds` to a string
      };
    },
    cancelEditing() {
      console.log('Cancelled editing');
      this.editingList = null;
    },
    async saveShoppingList() {
      try {
        console.log('Saving shopping list:', this.editingList);
        const response = await fetch(`/api/shopping-lists/${this.editingList.id}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            name: this.editingList.name,
            itemIds: this.editingList.items
                ? this.editingList.items.split(',').map((id) => parseInt(id.trim()))
                : [],
            boughtItems: this.editingList.boughtItems
                ? this.editingList.boughtItems.split(',').map((id) => parseInt(id.trim()))
                : [],
            userIds: this.editingList.userIds
                ? this.editingList.userIds.split(',').map((id) => parseInt(id.trim()))
                : [],
          }),
        });

        if (!response.ok) {
          const error = await response.text();
          throw new Error(`Failed to update shopping list: ${error}`);
        }

        const updatedList = await response.json();
        console.log('Shopping list updated successfully:', updatedList);

        // Update the list in the UI
        const index = this.shoppingLists.findIndex((list) => list.id === updatedList.id);
        if (index !== -1) {
          this.shoppingLists.splice(index, 1, updatedList);
        }

        // Exit edit mode
        this.editingList = null;
      } catch (error) {
        console.error('Error saving shopping list:', error.message);
      }
    },
    async addShoppingList() {
      try {
        console.log('Submitting new list:', this.newList);
        const userId = 201; // Replace with actual user ID logic
        const response = await fetch('/api/shopping-lists', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            name: this.newList.name,
            itemIds: this.newList.items
                ? this.newList.items.split(',').map(Number)
                : [],
            boughtItems: this.newList.boughtItems
                ? this.newList.boughtItems.split(',').map(Number)
                : [],
            userIds: [userId],
          }),
        });

        if (!response.ok) {
          const error = await response.text();
          throw new Error(`Failed to add list: ${error}`);
        }

        const newList = await response.json();
        console.log('List added successfully:', newList);
        this.shoppingLists.push(newList);

        // Reset the form fields
        this.newList = { name: '', items: '', boughtItems: '' };
      } catch (error) {
        console.error('Error adding shopping list:', error.message);
      }
    },

    async fetchItems() {
      try {
        const response = await fetch('http://20.31.172.254/api/items');
        if (!response.ok) {
          throw new Error(`Failed to fetch items: ${response.statusText}`);
        }
        const data = await response.json();
        console.log('Fetched items:', data);
        this.items = data;
      } catch (error) {
        console.error('Error fetching items:', error.message);
      } finally {
        this.loadingItems = false;
      }
    },
    async addItem() {
      try {
        const response = await fetch('http://20.31.172.254/api/items', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.newItem),
        });
        if (!response.ok) throw new Error('Failed to add item');
        const addedItem = await response.json();
        this.items.push(addedItem);
        // Reset new item fields
        this.newItem = { name: '', description: '', price: null };
      } catch (error) {
        console.error('Error adding item:', error.message);
      }
    },
    async deleteItem(itemId) {
      try {
        const response = await fetch(`http://20.31.172.254/api/items/${itemId}`, {
          method: 'DELETE',
        });
        if (!response.ok) throw new Error('Failed to delete item');
        // Remove item from the local list
        this.items = this.items.filter((item) => item.id !== itemId);
      } catch (error) {
        console.error('Error deleting item:', error.message);
      }
    },
    editItem(item) {
      this.editingItem = { ...item }; // Create a copy of the item being edited
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

.add-item-box {
  margin-top: 20px;
  padding: 20px;
  border: 2px solid #28a745;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.add-item-box h2 {
  color: #28a745;
}

.buttons {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}
</style>
