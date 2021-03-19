import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: () => import("@/views/index")
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  }
]

const router = new VueRouter({
  mode: 'hash',
  routes
})

export default router
