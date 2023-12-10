<template>
  <Navbar />
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
  