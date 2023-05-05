import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView";
import HomeView from "@/views/HomeView";
import {MessageBox} from "element-ui";
Vue.use(VueRouter)

const routes = [
  { path:'/', component: LoginView},
  { path:'/home', component: HomeView},

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  let account = localStorage.getItem('account');
  if (to.path == '/') {
    next()
    return
  }
  if (account == null){
    MessageBox.alert('请重新登录', '登录过期', {
      confirmButtonText: '确定',
      callback: action => {
        next('/')
      }
    })
  }else {
    next();
  }
})

export default router
