import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

export const constantRoutes = [
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
    mode: 'hash',
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

export default router
