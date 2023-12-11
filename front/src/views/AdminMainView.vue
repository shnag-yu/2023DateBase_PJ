<template>
    <Navbar />
    <div class="admin-homepage">
        <div class="management-buttons">
        <el-button type="primary" @click="goTo('adminuser')">用户管理</el-button>
        <el-button type="primary" @click="goTo('adminmerchant')">商户管理</el-button>
        <el-button type="primary" @click="goTo('platform-management')">平台管理</el-button>
        <el-button type="primary" @click="goTo('adminprodcut')">商品管理</el-button>
        </div>
  </div>
</template>
    
<script>
import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8080';
import Navbar from '../components/UserNav.vue';
import { useRouter } from 'vue-router';

export default {
    components: {
        Navbar,
    },
    data() {
        return {
           
        };
    },
    mounted() {
       

    },
    methods: {
        goTo(path) {
            this.$router.push({ path });
        }
       

    },
};
</script>
    


<style>
.admin-homepage .management-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 200px;
}

.admin-homepage .management-buttons .el-button {
  margin-bottom: 50px;
  width: 200px; /* 设置按钮宽度 */
}
</style>