<template>
  <div>
    <!-- Navigation Bar -->
    <nav class="navbar">
      <ul>
        <li>
          <!-- Redirect Home to another frontend -->
          <a href="/" class="nav-link">Home</a>
        </li>
        <li>
          <!-- Redirect My Shopping Lists to another frontend -->
          <a href="/user-shopping-lists" class="nav-link">My Shopping Lists</a>
        </li>
        <li>
          <!-- Internal link for Items -->
          <router-link to="/items" class="nav-link">Items</router-link>
        </li>
      </ul>
    </nav>
    <!-- Main Content -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
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

.navbar ul li a,
.navbar ul li .nav-link {
  text-decoration: none;
  color: white;
  font-weight: bold;
}

.navbar ul li a:hover,
.navbar ul li .nav-link:hover {
  text-decoration: underline;
}
</style>
