import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

export const constantRoutes = [
    {
        path: '/',
        name: 'Index',
        component: () => import("@/views/index"),
        hidden: true
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/Login"),
        hidden: true
    }
]

const router = new VueRouter({
    mode: 'history',
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

export default router
