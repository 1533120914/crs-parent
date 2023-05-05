<template>
  <el-container>
    <el-aside width="200px">
    <!-- 菜单组件 -->
      <el-menu class="el-menu-vertical-demo" style="height: 100vh">
        <!-- 用户头像 -->
        <div style="display: flex; flex-direction: column; align-items: center;padding: 20px 0px;border-bottom: 1px solid #409EFF">
          <el-upload
              style="background-color: #409EFF"
              class="avatar-uploader"
              action="http://localhost:8888/file/upload/avatar"
              :data="account"
              :on-success="handleAvatarUploadSuccess"
              :show-file-list="false">
            <img v-if="account.avatar" :src="avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div style="font-size: 16px;font-weight: bold; color: #409EFF;margin-top: 15px">
            <span v-if="account.identity==1">管理员:</span>
            <span v-if="account.identity==2">业务员:</span>
            <span v-if="account.identity==3">租客:</span>
            {{ account.username}}
          </div>
        </div>
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
          <span slot="title" @click="logout">退出系统</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!-- 右侧主体 -->
    <el-main style="background-color: #f1f1f1; padding: 20px">
      <!-- 放一个子路由挂载器 -->
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
    logout() {
      // 销毁存储的account
      localStorage.removeItem('account')
      // 回到登录界面
      this.$router.push('/')
    },
    handleAvatarUploadSuccess(response) {
      this.account.avatar = response.data
    }
  },
  computed: {
    avatar() {
      return 'http://localhost:8888/file/download/avatar?fileName=' + this.account.avatar
    }
  },
  created() {
    // 立刻从localStorage读取当前用户
    this.account = JSON.parse(localStorage.getItem('account'))
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>