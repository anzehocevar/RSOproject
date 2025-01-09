import { createRouter, createWebHistory } from 'vue-router';
import ShoppingList from '@/views/ShoppingList.vue';

const routes = [
    {
        path: '/',
        name: 'ShoppingList',
        component: ShoppingList,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
