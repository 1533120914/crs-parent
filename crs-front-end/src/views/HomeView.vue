<template>
  <el-container>
    <el-aside width="200px">
      <div style="display: flex; flex-direction: column; align-items: center">
        <el-avatar size="large" :src="account.avatar"></el-avatar>
        <div style="font-size: 24px;font-weight: bold; color: #409EFF;margin-top: 15px">欢迎:{{ account.username}} </div>
      </div>
      <el-menu class="el-menu-vertical-demo" style="height: 100vh">
        <el-menu-item v-if="account.identity==1">
          <i class="el-icon-menu"></i>
          <span slot="title">汽车品牌</span>
        </el-menu-item>
        <el-menu-item>
          <i class="el-icon-menu"></i>
          <span slot="title">汽车查询</span>
        </el-menu-item>
        <el-menu-item v-if="account.identity==1">
          <i class="el-icon-menu"></i>
          <span slot="title">业务员管理</span>
        </el-menu-item>
        <el-menu-item v-if="account.identity!=3">
          <i class="el-icon-menu"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item v-if="account.identity!=3">
          <i class="el-icon-menu"></i>
          <span slot="title">租赁记录管理</span>
        </el-menu-item>
        <el-menu-item v-if="account.identity!=1">
          <i class="el-icon-menu"></i>
          <span slot="title">我的租赁记录</span>
        </el-menu-item>
        <el-menu-item>
          <i class="el-icon-menu"></i>
          <span slot="title">个人信息</span>
        </el-menu-item>
        <el-menu-item>
          <i class="el-icon-menu"></i>
          <span slot="title">修改密码</span>
        </el-menu-item>
        <el-menu-item>
          <i class="el-icon-menu"></i>
          <span slot="title">退出系统</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      account: null
    }
  },
  methods: {

  },
  mounted() {
    this.account = JSON.parse(localStorage.getItem('account'))
  }
}
</script>

<style scoped>

</style>