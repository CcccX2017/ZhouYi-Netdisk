import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import menus from "./modules/menu";
import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        user,
        menus
    },
    getters
})
