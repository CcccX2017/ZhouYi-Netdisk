import {getToken, setToken, removeToken} from '@/utils/token'
import {postRequest} from "../../api";

const user = {
    state: {
        token: getToken(),
        name: '',
        avatar: '',
        role: ''
    },
    mutations:{
        SET_TOKEN: (state, token) => {
            state.token = token
        }
    },
    actions: {
        // 登录
        Login({commit}, loginForm){
            return new Promise((resolve, reject) => {
                postRequest("/portal/login", loginForm).then(resp => {
                    setToken(resp.data.token)
                    commit('SET_TOKEN', resp.data.token)
                    resolve();
                }).catch(error => {
                    reject(error)
                })
            })
        }
    }
}

export default user
