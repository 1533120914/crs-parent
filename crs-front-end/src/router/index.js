import Vue from 'vue'
import VueRouter from 'vue-router'
import {MessageBox} from "element-ui";
import LoginView from "@/views/LoginView";
import HomeView from "@/views/HomeView";
import ModifyPasswordView from "@/views/ModifyPasswordView";
import CarTableView from "@/views/car/CarTableView";
import SaveCarView from "@/views/car/SaveCarView";
import CarDetailView from "@/views/car/CarDetailView";
Vue.use(VueRouter)

const routes = [
  { path:'/', component: LoginView},
  { path:'/home', component: HomeView, children: [
      //注册为home的子路由
      { path:'/modify-password', component: ModifyPasswordView},
      { path:'/car', component: CarTableView},
      { path:'/car/save', component: SaveCarView},
      { path:'/car/detail/:id', component: CarDetailView}
    ]},

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫 (路由拦截器, 拦截每一次的路由请求, 不要与http的拦截器搞混了)
// beforeEach()这个函数在进入每个路由之前触发
// to 是要去的路由对象
// from 是从哪来的路由对象
// next 是函数 next()放行 next('路由地址')去指定的路由地址
router.beforeEach((to, from, next) => {
  // 如果要去的路由地址是'/' (去登录界面)
  if (to.path == '/') {
    next() // 直接放行
    return
  }
  // 如果不是去登录界面, 就要校验他有没有登录
  // 1. 从存储中获取account
  let account = localStorage.getItem('account');
  // 2. 判断account是否为null
  if (account == null){ // account为null
    MessageBox.alert('请重新登录', '登录过期', {
      confirmButtonText: '确定',
      callback: action => {
        // 点确定后, 跳转去登录界面
        next('/')
      }
    })
  }else { // account不为null, 直接放行
    next();
  }
})

export default router
