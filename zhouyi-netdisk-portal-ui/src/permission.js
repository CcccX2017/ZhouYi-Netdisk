import router from './router'
import store from './store/index.js'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from "./utils/token"

NProgress.configure({showSpinner: false})

const whitelist = ['/login', '/register', '/forgot']

router.beforeEach((to, from, next) => {
    NProgress.start()
    if (getToken()) {
        // 有token值，已经登录
        if (to.path === "/login") {
            next({path: '/'})
            NProgress.done()
        } else {
            // 判断当前用户是否已经有用户信息
            if (!store.getters.role) {
                store.dispatch('GetInfo').then(resp => {
                    // 初始化菜单
                    store.dispatch('InitMenu').then(resp => {
                        router.addRoutes(resp)
                        next({...to, replace: true})
                    })
                }).catch(error => {
                    store.dispatch('FedLogOut').then(() => {
                        // Message.error(error)
                        // NProgress.done()
                    })
                })
            }else {
                next()
            }
        }
    } else {
        // 没有登录
        if (whitelist.indexOf(to.path) !== -1) {
            // 白名单中，直接放行
            next()
        } else {
            next(`/login?redirect=${to.fullPath}`);
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    NProgress.done()
})