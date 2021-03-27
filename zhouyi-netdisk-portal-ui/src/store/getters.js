const getters = {
    token: state => state.user.token,
    name: state => state.user.name,
    nickname: state => state.user.nickname,
    avatar: state => state.user.avatar,
    role: state => state.user.role,
	groupId: state => state.user.groupId,
    menus: state => state.menus.menus
}

export default getters