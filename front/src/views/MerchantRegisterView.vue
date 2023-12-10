<template>
    <div class="register-container">
      <el-card class="register-card">
        <div class="register-title">商户注册</div>
        <el-form ref="registerForm" :model="registerForm" label-width="80px" class="register-form" :rules="rules">
          <!-- Username Field -->
          <el-form-item label="商户名" prop="name">
            <el-input v-model="registerForm.name" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
          </el-form-item>
  
          <!-- Password Field -->
          <el-form-item label="密码" prop="password">
            <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"
              prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
         
          <el-form-item label="地址" prop="addr">
            <el-input v-model="registerForm.addr" placeholder="请输入地址" prefix-icon="el-icon-time"></el-input>
          </el-form-item>
  
          <!-- Register Button -->
          <el-form-item>
            <el-button type="primary" @click="register">注册</el-button>
          </el-form-item>
        </el-form>
  
        <!-- Login Link -->
        <div class="login-link">
          已有账号？<router-link to="/login">去登录</router-link>
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
        registerForm: {
          name: '',
          password: '',
          addr : '',
        },
        rules: {
          name: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { max: 32, message: '用户名不能超过32字符', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 8, message: '密码不少于8位', trigger: 'blur' },
          ],
          addr: [
            { required: true, message: '请输入地址', trigger: 'blur' },
            { max: 32, message: '地址不能超过32个字符', trigger: 'blur' }
          ],
        },
      };
    },
    methods: {
      register() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            axios.post('/auth/merchantregister', this.registerForm)
              .then((res) => {
                if (res.data.code === 200) {
                  this.$message.success('注册成功，请登录');
                  // 注册成功后跳转到登录页面
                  this.$router.push('/merchantlogin');
                } else {
                  this.$message.error(res.data.msg);
                }
              })
              .catch((err) => {
                this.$message.error('注册失败，请检查信息是否符合要求');
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
  .register-container {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100vh;
  }
  
  .register-card {
    width: 400px;
  }
  
  .register-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 20px;
    text-align: center;
  }
  
  .register-form {
    width: 100%;
  }
  
  .login-link {
    margin-top: 20px;
    text-align: center;
  }
  </style>
    