<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-title">用户登录</div>
      <el-form ref="loginForm" :model="loginForm" label-width="80px" class="login-form" :rules="rules">
        <el-form-item label="用户名" prop="name" :rules="getRule('name')">
          <el-input v-model="loginForm.name" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" :rules="getRule('password')">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="register-link">
        还没有账号？<router-link to="/register">去注册</router-link>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8080';

export default {
  data() {
    return {
      loginForm: {
        name: '',
        password: '',
      },
      rules: {
        name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
      },
    };
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        console.log(axios.defaults.baseURL)
        if (valid) {
          axios.post('/auth/login', this.loginForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('登录成功');
                localStorage.setItem('user_id', res.data.data.id);
                console.log(localStorage.getItem("user_id"))
                this.$router.push('/usermain');
              } else {
                this.$message.error(res.data.msg);
              }
            })
            .catch((err) => {
              this.$message.error('登录失败，请检查用户名和密码是否正确');
            });
        } else {
          this.$message.error('请检查表单信息是否填写正确');
        }
      });
    },
    getRule(key) {
      return this.rules[key];
    },
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.login-card {
  width: 400px;
}

.login-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
}

.login-form {
  width: 100%;
}

.register-link {
  margin-top: 20px;
  text-align: center;
}
</style>
