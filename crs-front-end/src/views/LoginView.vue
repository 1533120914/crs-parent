<template>
  <div class="login-container">
    <el-card class="box-card">
      <div class="login-body">
        <div class="login-title">蔚来汽车租赁系统</div>
        <el-form ref="form" :model="account">
          <el-input placeholder="请输入登录名..." v-model="account.username" class="login-input">
            <template slot="prepend">
              <div class="el-icon-user-solid"></div>
            </template>
          </el-input>
          <el-input placeholder="请输入密码..." v-model="account.password" class="login-input"
                    @keyup.enter.native="login"  show-password>
            <template slot="prepend">
              <div class="el-icon-lock"></div>
            </template>
          </el-input>
          <div class="login-submit">
            <el-button type="primary" @click="login">登录</el-button>
          </div>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "LoginView",
  data() {
    return {
      account: {
        username: null,
        password: null,
        identity: null
      }
    };
  },

  methods: {
    login() {
      this.$http.post('/user/login', this.account).then(data => {
        localStorage.setItem('user', JSON.stringify(data))
        this.$router.push('/home')
      });
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100%;
  background-color: #f1f1f1;
}

.login-body {
  padding: 30px;
  width: 400px;
  height: 100%;
}

.login-title {
  padding-bottom: 30px;
  text-align: center;
  font-weight: 600;
  font-size: 20px;
  color: #409EFF;
  cursor: pointer;
}

.login-input {
  margin-bottom: 20px;
}

.login-submit {
  display: flex;
  justify-content: center;
}

.sign-in-container {
  padding: 0 10px;
}

.sign-in-text {
  color: #409EFF;
  font-size: 16px;
  text-decoration: none;
  line-height:28px;
}
.other-submit{
  display:flex;
  justify-content: space-between;
  margin-top: 10px;
}
</style>