import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/iconfont/iconfont.css'
import './permission'
import {
	postRequest,
	putRequest,
	postParamsRequest,
	getRequest,
	deleteRequest
} from "./api";
import vuescroll from 'vuescroll';

Vue.use(ElementUI, {
	size: 'medium'
})

Vue.use(vuescroll, {
	ops: {
		bar: {
			keepShow: true,
			background: "rgba(120, 120, 120, 0.4)"
		}
	}
})

Vue.config.productionTip = false

// 插件形式使用axios请求
Vue.prototype.postRequest = postRequest
Vue.prototype.postParamsRequest = postParamsRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.deleteRequest = deleteRequest
Vue.prototype.getRequest = getRequest

new Vue({
	router,
	store,
	render: h => h(App)
}).$mount('#app')
