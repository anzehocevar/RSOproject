import { createRouter, createWebHistory } from 'vue-router';
import items from '../views/Items.vue';
// import UserShoppingLists from "../views/UserShoppingLists.vue";

const routes = [
    {
        path: '/items',
        name: 'items',
        component: items,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;