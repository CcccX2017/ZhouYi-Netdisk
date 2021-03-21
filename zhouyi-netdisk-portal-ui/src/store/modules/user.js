import {getToken, setToken, removeToken} from '@/utils/token'
import {getRequest, postRequest} from "../../api";

const user = {
    state: {
        token: getToken(),
        name: '',
        nickname: '',
        avatar: '',
        role: '',
        maxFileSize: 0,
        maxStorageSpace: 0
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_NICKNAME: (state, nickname) => {
            state.nickname = nickname
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_ROLE: (state, role) => {
            state.role = role
        },
        SET_MAX_FILE_SIZE: (state, maxFileSize) => {
            state.maxFileSize = maxFileSize
        },
        SET_MAX_STORAGE_SPACE: (state, maxStorageSpace) => {
            state.maxStorageSpace = maxStorageSpace
        }
    },
    actions: {
        // 登录
        Login({commit}, loginForm) {
            return new Promise((resolve, reject) => {
                postRequest("/portal/login", loginForm).then(resp => {
                    setToken(resp.data.token)
                    commit('SET_TOKEN', resp.data.token)
                    resolve();
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 获取登录用户信息
        GetInfo({commit}) {
            return new Promise((resolve, reject) => {
                getRequest('/portal/user/').then(resp => {
                    const user = resp.data;
                    const avatar = user.avatar === "" ? require("@/assets/image/profile.jpg") : process.env.VUE_APP_BASE_API + user.avatar;
                    commit('SET_NAME', user.username)
                    commit('SET_NICKNAME', user.nickname)
                    commit("SET_AVATAR", avatar)
                    commit('SET_ROLE', user.groupName)
                    commit('SET_MAX_FILE_SIZE', user.maxFileSize)
                    commit('SET_MAX_STORAGE_SPACE', user.maxStorageSpace)
                    resolve(resp)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 退出
        LogOut({commit}) {
            return new Promise((resolve, reject) => {
                postRequest('/logout').then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLE', '')
                    removeToken()
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // token失效，清空前端数据，登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                removeToken()
                resolve()
            })
        }
    }
}

export default user
