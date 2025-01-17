import { createRouter, createWebHistory } from 'vue-router';
import ShoppingList from '../views/ShoppingList.vue';
import UserShoppingLists from "../views/UserShoppingLists.vue";

const routes = [
    {
        path: '/',
        name: 'ShoppingList',
        component: ShoppingList,
    },
    {
        path: '/user-shopping-lists',
        name: 'UserShoppingLists',
        component: UserShoppingLists,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;