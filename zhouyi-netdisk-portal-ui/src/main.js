import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/iconfont/iconfont.css'

import {postRequest, putRequest, getRequest, deleteRequest} from "./api";

Vue.use(ElementUI, {size: 'medium'})

Vue.config.productionTip = false

// 插件形式使用axios请求
Vue.prototype.postRequest = postRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.deleteRequest = deleteRequest
Vue.prototype.getRequest = getRequest

router.beforeEach((to, from, next) => {
  // 有token值，已经登录
  console.log(store.state.user.token);
  if (store.state.user.token){
    next();
  }else{
    // 没有登录
    if (to.path === '/login'){
      next();
    }else{
      next("/login?redirect=" + to.path);
    }
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
