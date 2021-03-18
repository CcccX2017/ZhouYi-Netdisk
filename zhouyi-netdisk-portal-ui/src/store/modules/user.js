import {getToken, setToken, removeToken} from '@/utils/token'
const user = {
    state: {
        token: getToken(),
        name: '',
        avatar: '',
        role: ''
    }
}

export default user