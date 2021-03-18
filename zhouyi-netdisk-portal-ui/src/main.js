import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import {postRequest} from './utils/request'
import {putRequest} from './utils/request'
import {deleteRequest} from './utils/request'
import {getRequest} from './utils/request'

Vue.use(ElementUI, {size: 'medium'})

Vue.config.productionTip = false

// 插件形式使用axios请求
Vue.prototype.postRequest = postRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.deleteRequest = deleteRequest
Vue.prototype.getRequest = getRequest

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
