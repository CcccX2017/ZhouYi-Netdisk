import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决路由跳转原路由或者刷新出错
const originalReplace = VueRouter.prototype.replace;
VueRouter.prototype.replace = function replace(location) {
    return originalReplace.call(this, location).catch(err => err);
};
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err);
}

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
