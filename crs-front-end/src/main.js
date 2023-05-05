import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 导入ElementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

// 导入Axios
import http from "@/api/http"
Vue.prototype.$http = http

// 导入Echarts
import * as echarts from "echarts"
Vue.prototype.$echarts = echarts

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
