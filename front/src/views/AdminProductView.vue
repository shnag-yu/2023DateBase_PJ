<template>
    <Navbar />
    <div class="product-management">
        <el-table :data="pagedProducts" style="width: 100%">
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
            <el-table-column label="历史价格">
                <template #default="{ row }">
                    <el-button @click="goToProductDetails(row.productId)" type="success">历史价格</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
                :page-size="pageSize" :total="products.length" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
        </div>
    </div>

    <!-- 修改商品信息 -->
    <div v-if="editDialogVisible" class="edit-dialog-overlay">
        <!-- Edit Form Container -->
        <div class="edit-dialog-container">
            <el-form :model="currentProduct">
                <!-- <el-form-item label="Description">
                        <el-input v-model="currentProduct.description"></el-input>
                    </el-form-item>
                    <el-form-item label="Price">
                        <el-input v-model="currentProduct.price" type="number"></el-input>
                    </el-form-item> -->
                <el-form-item label="名称" prop="name">
                    <el-input v-model="currentProduct.name"></el-input>
                </el-form-item>
                <el-form-item label="类别" prop="category">
                    <el-input v-model="currentProduct.category"></el-input>
                </el-form-item>
                <el-form-item label="产地" prop="prodRegion">
                    <el-input v-model="currentProduct.prodRegion"></el-input>
                </el-form-item>
                <el-form-item label="生产日期" prop="prodDate">
                    <el-date-picker v-model="currentProduct.prodDate" type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="currentProduct.price" type="number"></el-input>
                </el-form-item>
                <el-form-item label="商家ID">
                    <el-input v-model="currentProduct.merchantId" disabled></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input type="textarea" v-model="currentProduct.description"></el-input>
                </el-form-item>
                <el-form-item label="商家名称">
                    <el-input v-model="currentProduct.merchantName" disabled></el-input>
                </el-form-item>
                <el-form-item label="平台名称" prop="platformName">
                    <el-input v-model="currentProduct.platformName" disabled></el-input>
                </el-form-item>
            </el-form>
            <div class="dialog-footer">
                <el-button @click="editDialogVisible = false">Return</el-button>
                <el-button type="primary" @click="submitEdit">Submit</el-button>
            </div>
        </div>
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
            products: [],
            pagedProducts: [], // 存储当前页的用户数据
            currentPage: 1,
            pageSize: 5,
            editDialogVisible: false,
            currentProduct: null,
        };
    },
    methods: {
        fetchProducts() {
            axios.get('/product').then((res) => {
                this.products = res.data;
                this.updatePagedProducts();
            });
            axios
        },
        updatePagedProducts() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            this.pagedProducts = this.products.slice(startIndex, endIndex);
            console.log(this.pagedProducts);
        },
        handleSizeChange(newSize) {
            this.pageSize = newSize;
            this.updatePagedProducts();
        },
        handleCurrentChange(newPage) {
            this.currentPage = newPage;
            this.updatePagedProducts();
        },
        startEditingProduct(product) {
            this.currentProduct = Object.assign({}, product); // Clone product to avoid mutating original data
            this.editDialogVisible = true;
            console.log(this.currentProduct)
        },
        submitEdit() {
            const productId = this.currentProduct.productId;
            const product = this.currentProduct;
            if (product.prodDate && product.prodDate instanceof Date) {
                // 将 Date 对象转换为 ISO 字符串格式，然后提取日期部分
                product.prodDate.setDate(product.prodDate.getDate() + 1);
                product.prodDate = product.prodDate.toISOString().split('T')[0];
            }
            console.log(product);
            axios.put(`/product/${productId}`, product)
                .then(() => {
                    this.fetchProducts();
                    this.editDialogVisible = false;
                    this.$message.success('修改成功');
                })
                .catch((error) => {
                    console.error('Error updating product:', error);
                });
        },
        goToProductDetails(productId) {
            this.$router.push(`/adminpricehistory/${productId}`)
        },
        deleteProduct(product) {
            const productId = product.productId;
            axios.delete(`/product/${productId}`)
                .then(() => {
                    this.fetchProducts();
                    this.$message.success('删除成功');
                })
                .catch((error) => {
                    console.error('Error deleting product:', error);
                });
        },
    },
    mounted() {
        this.fetchProducts();
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

.product-management .pagination-container {
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
.product-management{
    margin-left: 50px;
    margin-right: 50px;
    margin-top: 50px;
}
</style>
  