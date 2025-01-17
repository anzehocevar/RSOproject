<template>
  <div class="items-container">
    <h1>Available Items</h1>
    <router-link to="/" class="nav-link">Back to Home</router-link>

    <!-- Error Message -->
    <div v-if="error" class="error">
      <p>{{ error }}</p>
    </div>

    <!-- Add New Item -->
    <div class="add-item box">
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
        <button type="submit" class="btn btn-primary">Add Item</button>
      </form>
    </div>

    <!-- Items List -->
    <div v-if="items.length === 0 && !error" class="no-items">
      <p>No items available.</p>
    </div>
    <div v-else class="items-list">
      <div v-for="item in items" :key="item.id" class="item-card box">
        <h2>{{ item.name }}</h2>
        <p><strong>Description:</strong> {{ item.description }}</p>
        <div class="item-actions">
          <button @click="startEditing(item)" class="btn btn-secondary">Edit</button>
          <button @click="deleteItem(item.id)" class="btn btn-danger">Delete</button>
        </div>

        <!-- Edit Item Form -->
        <div v-if="editingItem && editingItem.id === item.id" class="edit-box">
          <form @submit.prevent="saveItem">
            <div class="form-group">
              <input
                  v-model="editingItem.name"
                  type="text"
                  class="form-input"
                  placeholder="Item Name"
                  required
              />
            </div>
            <div class="form-group">
              <textarea
                  v-model="editingItem.description"
                  class="form-textarea"
                  placeholder="Item Description"
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
export default {
  data() {
    return {
      items: [],
      newItem: {
        name: "",
        description: "",
      },
      editingItem: null,
      error: null,
    };
  },
  methods: {
    async fetchItems() {
      try {
        const response = await fetch("/api/items");
        if (!response.ok) throw new Error("Failed to fetch items");
        this.items = await response.json();
      } catch (err) {
        this.error = err.message;
        console.error(err);
      }
    },
    async addItem() {
      try {
        const response = await fetch("/api/items", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(this.newItem),
        });
        if (!response.ok) throw new Error("Failed to add item");
        const addedItem = await response.json();
        this.items.push(addedItem);
        this.newItem = { name: "", description: "" };
      } catch (err) {
        this.error = err.message;
        console.error(err);
      }
    },
    startEditing(item) {
      this.editingItem = { ...item };
    },
    cancelEditing() {
      this.editingItem = null;
    },
    async saveItem() {
      try {
        const response = await fetch(`/api/items/${this.editingItem.id}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(this.editingItem),
        });
        if (!response.ok) throw new Error("Failed to update item");
        const updatedItem = await response.json();
        const index = this.items.findIndex((i) => i.id === updatedItem.id);
        if (index !== -1) this.items.splice(index, 1, updatedItem);
        this.editingItem = null;
      } catch (err) {
        this.error = err.message;
        console.error(err);
      }
    },
    async deleteItem(itemId) {
      try {
        const response = await fetch(`/api/items/${itemId}`, {
          method: "DELETE",
        });
        if (!response.ok) throw new Error("Failed to delete item");
        this.items = this.items.filter((item) => item.id !== itemId);
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

.box {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.items-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.item-card h2 {
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

.item-actions {
  margin-top: 10px;
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

.error {
  color: red;
}

.no-items {
  color: gray;
  font-size: 18px;
  text-align: center;
}
</style>
