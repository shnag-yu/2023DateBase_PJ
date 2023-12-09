<template>
  <Navbar />
  <div class="profile-container">
    <el-card class="profile-card">
      <div class="profile-title">用户主页</div>
      <el-form :model="userProfile" label-width="80px" class="profile-form">
        <!-- 用户名字段 -->
        <el-form-item label="用户名">
          <el-input v-model="userProfile.name" disabled></el-input>
        </el-form-item>

        <!-- 密码字段 -->
        <el-form-item label="密码">
          <el-input v-model="userProfile.password" type="password" disabled></el-input>
        </el-form-item>

        <!-- 年龄字段 -->
        <el-form-item label="年龄">
          <el-input v-model="userProfile.age" disabled></el-input>
        </el-form-item>

        <!-- 性别字段 -->
        <el-form-item label="性别">
          <el-input v-model="userProfile.gender" disabled></el-input>
        </el-form-item>

        <!-- 电话字段 -->
        <el-form-item label="电话">
          <el-input v-model="userProfile.tel" disabled></el-input>
        </el-form-item>
      </el-form>

      <!-- 修改信息按钮 -->
      <!-- <div class="edit-button">
        <el-button type="primary" @click="editProfile">修改信息</el-button>
      </div> -->
    </el-card>
  </div>
</template>
  
<script>
import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8080';
import Navbar from '../components/UserNav.vue';

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      userProfile: {
        name: '',
        password: '',
        age: '',
        gender: '',
        tel: '',
      },
    };
  },
  mounted() {
    // 在页面加载时获取用户信息
    this.fetchUserProfile();

  },
  methods: {
    fetchUserProfile() {
      let id = localStorage.getItem("user_id")
      console.log(id)
      axios.get('/user/' + id)
        .then((res) => {
          if (res.data.code === 200) {
            console.log(res.data.data)
            this.userProfile = res.data.data;
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch((err) => {
          console.error(err);
          this.$message.error('获取用户信息失败');
        });
    },
    editProfile() {
      // 编辑信息的逻辑，例如跳转到编辑页面
      this.$router.push('/edit-profile');
    },
  },
};
</script>
  
<style scoped>
.profile-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.profile-card {
  width: 400px;
}

.profile-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
}

.profile-form {
  width: 100%;
}

.edit-button {
  margin-top: 20px;
  text-align: center;
}
</style>
  