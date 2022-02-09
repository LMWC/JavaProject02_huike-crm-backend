import { constantRoutes } from '@/router'
import { getRouters } from '@/api/menu'
import Layout from '@/layout/index'
import ParentView from '@/components/ParentView';

const permission = {
  state: {
    routes: [],
    addRoutes: [],
    sidebarRouters: []
  },
  mutations: {
    SET_ROUTES: (state, routes) => {
      state.addRoutes = routes
      state.routes = constantRoutes.concat(routes)
    },
    SET_SIDEBAR_ROUTERS: (state, routers) => {
      state.sidebarRouters = constantRoutes.concat(routers)
    },
  },
  actions: {
    // 生成路由
    GenerateRoutes({ commit }) {
      return new Promise(resolve => {
        // 向后端请求路由数据
        getRouters().then(res => {
          if(res.data && res.data.length>0){
            res.data.forEach(item=>{
              if(item.children){
                if(item.children[0].name==='Clue'){
                  item.children.push({
                    hidden: true,
                    path: 'clues/details',
                    component: 'clues/clue/details/clues',
                    name: 'CluesDetails',
                    meta: { title: '线索跟进', activeMenu:'/clue' }
                  })
                }else if(item.children[0].name==='Business'){
                  item.children.push({
                    hidden: true,
                    path: 'business/follow',
                    component: 'clues/business/details/business-follow',
                    name: 'BusinessFollow',
                    meta: { title: '商机跟进', activeMenu:'/business' }
                  })
                  item.children.push({
                    hidden: true,
                    path: 'business/details',
                    component: 'clues/business/details/business',
                    name: 'BusinessDetails',
                    meta: { title: '商机详情', activeMenu:'/business' }
                  })
                }else if(item.children[0].name==='Contract'){
                  item.children.push({
                    hidden: true,
                    path: 'contract/details',
                    component: 'clues/contract/contract-details',
                    name: 'ContractDetails',
                    meta: { title: '合同详情', activeMenu:'/contract' }
                  })
                }
              }
            })
          }
          console.log(res.data)
          const sdata = JSON.parse(JSON.stringify(res.data))
          const rdata = JSON.parse(JSON.stringify(res.data))
          const sidebarRoutes = filterAsyncRouter(sdata)
          const rewriteRoutes = filterAsyncRouter(rdata, false, true)
          rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
          console.log('rewriteRoutes',rewriteRoutes)
          console.log('sidebarRoutes',sidebarRoutes)
          commit('SET_ROUTES', rewriteRoutes)
          commit('SET_SIDEBAR_ROUTERS', sidebarRoutes)
          resolve(rewriteRoutes)
        })
      })
    }
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
  return asyncRouterMap.filter(route => {
    if (type && route.children) {
      route.children = filterChildren(route.children)
    }
    if (route.component) {
      // Layout ParentView 组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else if (route.component === 'ParentView') {
        route.component = ParentView
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children, route, type)
    } else {
      delete route['children']
      delete route['redirect']
    }
    return true
  })
}

function filterChildren(childrenMap, lastRouter = false) {
  var children = []
  childrenMap.forEach((el, index) => {
    if (el.children && el.children.length) {
      if (el.component === 'ParentView') {
        el.children.forEach(c => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children, c))
            return
          }
          children.push(c)
        })
        return
      }
    }
    if (lastRouter) {
      el.path = lastRouter.path + '/' + el.path
    }
    children = children.concat(el)
  })
  return children
}

export const loadView = (view) => { // 路由懒加载
  return (resolve) => require([`@/views/${view}`], resolve)
}

export default permission
