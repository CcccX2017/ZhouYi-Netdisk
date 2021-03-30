import {constantRoutes} from "@/router"
import {getRequest} from '@/api'
import {encrypt, decrypt} from '@/utils/jsencrypt'

const menus = {
	state: {
		menus: [],
		routes: [],
		addRoutes: []
	},
	mutations: {
		SET_ROUTES: (state, routes) => {
			state.addRoutes = routes
			state.routes = constantRoutes.concat(routes)
		},
		SET_MENUS: (state, menus) => {
			state.menus = menus
		}
	},
	actions: {
		// 初始化菜单
		InitMenu({ commit }) {
			return new Promise(resolve => {
				getRequest('/portal/menu/').then(resp => {
					const menus = formatMenu(resp.data)
					let rootMenu = [
						{
							path: '/',
							redirect: '/netdisk/',
							name: 'Index',
							component: () => import("@/views/index"),
							hidden: true,
							children:[
								{
									path: '/netdisk/',
									redirect: '/netdisk/allFile',
									name: '网盘',
									component: () => import("@/views/Netdisk"),
									children: menus
								},
								{
									path: '/share',
									name: '分享',
									component: () => import("@/views/Share")
								}
							]
						},
						{
							path: '*',
							name: '404',
							hidden: true,
							component: () => import("@/views/404")
						}
					]
					commit('SET_ROUTES', rootMenu)
					commit('SET_MENUS', menus)
					resolve(rootMenu)
				})
			})
		}
	}
}

// 格式化菜单数据，生成组件对象
function formatMenu(routers) {
	let fmtRouters = []

	routers.forEach(router => {
		let {
			menuId,
			menuTitle,
			iconClass,
			path,
			children,
			component
		} = router
		if (children && children instanceof Array) {
			// 递归
			children = formatMenu(children)
		}

		let fmRoute = {
			menuId: menuId,
			path: path === '/' ? 'allFile' : path,
			name: menuTitle,
			iconCls: iconClass,
			children: children,
			component(resolve) {
				require(['@/components/' + component + '.vue'], resolve)
			}
		}

		fmtRouters.push(fmRoute)

	})

	return fmtRouters
}

export default menus
