<template>
  <div class="register-container">
    <el-card class="register-card">
      <div class="register-title">用户注册</div>
      <el-form ref="registerForm" :model="registerForm" label-width="80px" class="register-form" :rules="rules">
        <!-- Username Field -->
        <el-form-item label="用户名" prop="name">
          <el-input v-model="registerForm.name" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
        </el-form-item>

        <!-- Password Field -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"
            prefix-icon="el-icon-lock"></el-input>
        </el-form-item>

        <!-- Age Field -->
        <el-form-item label="年龄" prop="age">
          <el-input v-model="registerForm.age" placeholder="请输入年龄" prefix-icon="el-icon-time"></el-input>
        </el-form-item>

        <!-- Gender Field -->
        <el-form-item label="性别" prop="gender">
          <el-select v-model="registerForm.gender" placeholder="请选择性别">
            <el-option label="男" value="Male"></el-option>
            <el-option label="女" value="Female"></el-option>
          </el-select>
        </el-form-item>

        <!-- Tel Field -->
        <el-form-item label="电话" prop="tel">
          <el-input v-model="registerForm.tel" placeholder="请输入电话" prefix-icon="el-icon-phone"></el-input>
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
        age: '',
        gender: '',
        tel: '',
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
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { max: 3, message: '年龄过大', trigger: 'blur' },
          { pattern: /^\d+$/, message: '年龄只能是数字', trigger: 'blur' },
        ],

        gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
        tel: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { max: 11, message: '电话号码不超过11位', trigger: 'blur' },
          { pattern: /^\d+$/, message: '电话号码只能是数字', trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    register() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          axios.post('/auth/register', this.registerForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('注册成功，请登录');
                // 注册成功后跳转到登录页面
                this.$router.push('/login');
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
  