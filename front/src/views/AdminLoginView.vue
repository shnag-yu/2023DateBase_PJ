<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-title">管理员登录</div>
      <el-form ref="loginForm" :model="loginForm" label-width="80px" class="login-form" :rules="rules">
        <el-form-item label="管理员名" prop="name" :rules="getRule('name')">
          <el-input v-model="loginForm.name" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" :rules="getRule('password')">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"
            prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="register-link">
        <router-link to="/login">去用户登入</router-link>
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
        name: [{ required: true, message: '请输入管理员名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
      },
    };
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        console.log(axios.defaults.baseURL)
        if (valid) {
          const params = new URLSearchParams();
          params.append('username', this.loginForm.name);
          params.append('password', this.loginForm.password);
          console.log(params);
          axios.post('/auth/admin/login', params)
            .then(response => {
              console.log(response.data); // 处理响应，例如：显示登录成功或失败的消息
              localStorage.setItem('user_id', response.data.id);
              this.$message.success('登录成功');
              this.$router.push('/adminmain');
            })
            .catch(error => {
              console.error('登录请求失败:', error); // 处理错误
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
  