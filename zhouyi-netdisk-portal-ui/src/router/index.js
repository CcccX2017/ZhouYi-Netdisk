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
        name: '登录',
        component: () => import("@/views/Login"),
        hidden: true
    },
	{
	    path: '/register',
	    name: '注册',
	    component: () => import("@/views/Register"),
	    hidden: true
	},
	{
	    path: '/forgot',
	    name: '忘记密码',
	    component: () => import("@/views/Forgot"),
	    hidden: true
	}
]

const router = new VueRouter({
    mode: 'history',
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

export default router
