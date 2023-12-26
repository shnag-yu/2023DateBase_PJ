<template>
    <Navbar />
    <div class="user-management">
        <el-table :data="productList" style="width: 100%">
            <el-table-column prop="productId" label="Product ID"></el-table-column>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="category" label="Category"></el-table-column>
            <el-table-column prop="prodRegion" label="Region"></el-table-column>
            <el-table-column prop="prodDate" label="Date"></el-table-column>
            <el-table-column prop="price" label="Price"></el-table-column>
            <el-table-column prop="merchantId" label="Merchant ID"></el-table-column>
            <el-table-column prop="platformId" label="Platform ID"></el-table-column>
            <el-table-column prop="description" label="Description"></el-table-column>
            <el-table-column prop="merchantName" label="Merchant Name"></el-table-column>
            <el-table-column prop="platformName" label="Platform Name"></el-table-column>
            <el-table-column label="修改">
                <template #default="{ row }">
                    <el-button @click="startEditingProduct(row)" type="primary">修改</el-button>
                </template>
            </el-table-column>
            <el-table-column label="删除">
                <template #default="{ row }">
                    <el-button @click="deleteProduct(row)" type="primary">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
  
<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import Navbar from '../components/UserNav.vue';
axios.defaults.baseURL = 'http://localhost:8080';
export default {
    components: {
        Navbar,
    },
    data() {
        return {
            productList: [],
        };
    },
    methods: {
        fetchUsers() {
            axios.get('/product').then((res) => {
                this.productList = res.data;
            });
        },
    },
    mounted() {
        this.fetchUsers();
    }
};
</script>
  
<style>
.edit-dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    /* 确保遮罩层在其他内容之上 */
}

.edit-dialog-container {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}

.dialog-footer {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
}

.user-management .pagination-container {
    display: flex;
    justify-content: center;
    margin-top: 50px;
    /* 调整与表格的距离 */
    margin-bottom: 50px;
}

.merchant-info {
    text-align: center;
    /* 居中对齐 */
    font-size: larger;
    /* 字体放大 */
    background-color: #cbdae9;
    /* 背景为灰色 */
    margin: 20px auto;
    /* 与上下内容保持一定距离 */
    padding: 20px;
    /* 内部填充 */
    border-radius: 10px;
    /* 圆角边框 */
}
</style>
  