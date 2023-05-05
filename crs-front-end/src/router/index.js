import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView";
import HomeView from "@/views/HomeView";
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

export default router
