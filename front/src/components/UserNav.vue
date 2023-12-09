<template>
  <div class="navbar-container">
    <el-menu mode="horizontal" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" :menu-align-right="true">
      <!-- 主页 -->
      <el-menu-item index="0" @click="goTo('/home')">主页</el-menu-item>

      <!-- 我的收藏 -->
      <el-menu-item index="1" @click="goTo('/my-favorites')">我的收藏</el-menu-item>

      <!-- 个人主页 -->
      <el-menu-item index="2" @click="goTo('/usermain')">个人主页</el-menu-item>

      <!-- 退出登录 -->
      <el-menu-item index="3" @click="logout">退出登录</el-menu-item>
    </el-menu>
  </div>
</template>
  
<script>
import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8080/auth';

export default {
  methods: {
    goTo(route) {
      // 导航到指定路由
      this.$router.push(route);
    },
    logout() {
      this.$router.push('/login');
      localStorage.clear();
    },
  },
};
</script>
  
<style scoped>
.navbar-container {
  padding: 10px;
}
</style>
s  