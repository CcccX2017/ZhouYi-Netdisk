import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

export const constantRoutes = [
    {
        path: '/',
		redirect: '/netdisk',
        name: 'Index',
        component: () => import("@/views/index"),
        hidden: true,
		children:[
			{
			    path: '/netdisk',
			    name: '网盘',
			    component: () => import("@/views/Netdisk")
			},
			{
			    path: '/share',
			    name: '分享',
			    component: () => import("@/views/Share")
			}
		]
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
