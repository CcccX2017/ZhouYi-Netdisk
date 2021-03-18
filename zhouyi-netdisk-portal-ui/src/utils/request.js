import axios from 'axios'
import { from } from 'core-js/core/array'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import { getToken } from "./token"
import { errorCode } from './errorCode'

// 实例化axios
const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: process.env.VUE_APP_BASE_API,
    // 超时
    timeout: 10000
})

// 请求拦截器
service.interceptors.request.use(config => {
    // 是否需要设置 token
    const isToken = (config.headers || {}).isToken === false
    if (getToken() && !isToken) {
        // 如果存在token，每个请求都携带这个token
        config.headers['Authorization'] = 'Bearer ' + getToken()
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(response => {
    // 未设置状态码则默认成功状态
    const code = response.data.status || 200;

    // 获取错误信息
    const msg = errorCode[code] || res.data.msg || errorCode['default']
    if (code === 401) {
        MessageBox.confirm('用户登录已过期，您可以继续留在当前页面或者重新登录', '系统提示', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            store.dispatch('LogOut').then(() => {
                location.href = '/'
            })
        })
    } else if (code === 500 || code === 401 || code === 403) {
        if (response.data.data) {
            return response.data;
        }
        Message({
            message: msg,
            type: 'error'
        })
        return Promise.reject(new Error(msg))
    } else if (code === 20) {
        return response.data;
    } else if (code !== 200) {
        Message({
            message: msg,
            type: 'error'
        })
        return Promise.reject(new Error(msg))
    } else {
        Message.success({ message: response.data.msg })
        return response.data
    }

}, error => {
    console.log('error ==> ' + error)
    let { msg } = error;
    if (msg == "Network Error") {
        msg = "后端接口连接异常";
    } else if (msg.includes("timeout")) {
        msg = "系统接口请求超时";
    } else if (msg.includes("Request failed with status code")) {
        msg = "系统接口" + msg.substr(msg.length - 3) + "异常";
    }

    Message.error({ message: msg, duration: 5 * 1000 })

    return Promise.reject(error)
})

let baseUrl = ''

// post请求
export const postRequest = (url, params) => {
    return service({
        method: 'POST',
        url: `${baseUrl}${url}`,
        data: params
    })
}

// put请求
export const putRequest = (url, params) => {
    return service({
        method: 'PUT',
        url: `${baseUrl}${url}`,
        data: params
    })
}

// delete请求
export const deleteRequest = (url, params) => {
    return service({
        method: 'DELETE',
        url: `${baseUrl}${url}`,
        data: params
    })
}

// get请求
export const getRequest = (url, params) => {
    return service({
        method: 'GET',
        url: `${baseUrl}${url}`,
        data: params
    })
}