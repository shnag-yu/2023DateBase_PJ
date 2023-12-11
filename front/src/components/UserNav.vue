<template>
  <div class="navbar-container">
    <el-menu mode="horizontal" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"
      :menu-align-right="true">
      <!-- 主页 -->
      <el-menu-item index="0" @click="goTo('/home')" v-if="userid != 1">主页</el-menu-item>

      <!-- 我的收藏 -->
      <el-menu-item index="1" @click="goTo('/my-favorites' )" v-if="userid != 1">我的收藏</el-menu-item>

      <!-- 个人主页 -->
      <el-menu-item index="2" @click="goTo('/usermain')" v-if="userid != 1">个人主页</el-menu-item>

      <!--消息列表-->>
      <el-menu-item index="3" @click="goTo('/msglist')" v-if="userid != 1">消息列表</el-menu-item>

      <el-menu-item index="4" @click="goTo('/adminmain')" v-if="userid == 1">管理员主页</el-menu-item>

      <!-- 进阶查询 -->
      <el-sub-menu index="5">
        <template #title>进阶查询</template>
        <el-menu-item index="3-1" @click="goTo('/advanced_query_user')">用户</el-menu-item>
        <el-menu-item index="3-2" @click="goTo('/advanced_query_product')">商品</el-menu-item>
        <el-menu-item index="3-3" @click="goTo('/advanced_query_merchant')">商家</el-menu-item>
        <!-- <el-sub-menu index="3-4">
          <template #title>item four</template>
          <el-menu-item index="3-4-1">item one</el-menu-item>
          <el-menu-item index="3-4-2">item two</el-menu-item>
          <el-menu-item index="3s-4-3">item three</el-menu-item>
        </el-sub-menu> -->
      </el-sub-menu>

      <!-- 退出登录 -->
      <el-menu-item index="6" @click="logout">退出登录</el-menu-item>
      
      
    </el-menu>
  </div>
</template>
  
<script>
import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8080/auth';

export default {
  data() {
    return {
      userid: null,
    };
  },
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
  mounted(){
    this.userid = localStorage.getItem('user_id');
    console.log(this.userid);
  },
};
</script>
  
<style scoped>
.navbar-container {
  padding: 10px;
}
</style>
s  