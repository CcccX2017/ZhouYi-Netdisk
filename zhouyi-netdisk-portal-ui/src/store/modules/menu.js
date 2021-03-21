import {constantRoutes} from "@/router"
import {getRequest} from '@/api'

const menus = {
    state: {
        routes: [],
        addRoutes: []
    },
    mutations: {
        SET_ROUTES: (state, routes) => {
            state.addRoutes = routes
            state.routes = constantRoutes.concat(routes)
        }
    },
    actions: {
        // 初始化菜单
        InitMenu({commit}) {
            return new Promise(resolve => {
                getRequest('/portal/menu/').then(resp => {
                    const menus = formatMenu(resp.data)
                    menus.push({path: '*', redirect: '/404', hidden: true})
                    commit('SET_ROUTES', menus)
                    resolve(menus)
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
            children
        } = router

        if (children && children instanceof Array) {
            // 递归
            children = formatMenu(children)
        }

        if (path === '/') {
            path = '/all'
        } else {
            path = '/' + path
        }

        let fmRoute = {
            path: path,
            name: menuTitle,
            iconCls: iconClass,
            children: children
        }

        fmtRouters.push(fmRoute)

    })

    return fmtRouters
}

export default menus