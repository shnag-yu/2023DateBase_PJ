
<template>
    <div>
        <Navbar />
        <el-table :data="favorites" style="width: 100%">
            <el-table-column prop="productId" label="商品 ID"></el-table-column>
            <el-table-column prop="productName" label="商品名"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column prop="price_LB" label="价格下限"></el-table-column>
            <el-table-column prop="category" label="商品种类"></el-table-column>
            <el-table-column prop="prod_region" label="产地"></el-table-column>
            <!-- <el-table-column label="Actions">
                <template slot-scope="scope">
                    <el-button @click="goToProductDetails(scope.row.productId)" type="primary">Details</el-button>
                </template>
            </el-table-column> -->
            <el-table-column label="商品详情" width="200" align="center">
                <template #default="{ row }">
                    <el-button @click="goToProductDetails(row.productId)" type="primary">Details</el-button>
                </template>
            </el-table-column>
            <el-table-column label="设置最低价格" width="200" align="center">
                <template #default="{ row }">
                    <el-button @click="editingpricelb(row)" type="success">设置最低价格</el-button>
                </template>
            </el-table-column>
            <!-- 添加更多列来显示产品的其他细节 -->
        </el-table>
        <div v-if="showEditingpricelb" class="edit-dialog-overlay">
            <!-- Edit Form Container -->
            <div class="edit-dialog-container">
                <el-form :model="currentfavorite">
                    <el-form-item label="Name">
                        <el-input v-model="currentfavorite.productName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="Price">
                        <el-input v-model="currentfavorite.price" type="number" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="Price_LB">
                        <el-input v-model="currentfavorite.price_LB" type="number"  ></el-input>
                    </el-form-item>
                </el-form>
                <div class="dialog-footer">
                    <el-button @click="showEditingpricelb = false">Return</el-button>
                    <el-button type="primary" @click="submitEdit(currentfavorite)">Submit</el-button>
                </div>
            </div>
        </div>
    </div>
</template>
    
<script>
import axios from 'axios';
import Navbar from '../components/UserNav.vue';
export default {
    components: {
        Navbar,
    },
    data() {
        return {
            favorites: [],
            showEditingpricelb: false,
            currentfavorite: null,
        };
    },
    methods: {
        async goToProductDetails(productId) {
            // 逻辑来处理跳转到产品详情页
            // 例如，使用 Vue Router 进行导航
            this.$router.push(`/product/${productId}`)
        },
        editingpricelb(favorite) {
            this.showEditingpricelb = true;
            this.currentfavorite = favorite;
            console.log(this.currentfavorite)
        },
        submitEdit(currentfavorite){
            this.showEditingpricelb = false;
            const userId = localStorage.getItem("user_id")
            const favorite ={
                user_id: userId,
                product_id: currentfavorite.productId,
                price_LB: currentfavorite.price_LB,
            }
            console.log(favorite)
            axios.post(`http://localhost:8080/favorite/setPriceLimit`, favorite)
            .then((res) => {
                if (res.data.code === 200) {
                    this.$message.success('设置成功');
                    window.location.reload();
                    
                } else {
                    this.$message.error('设置失败');
                }
            })
        },

    },
    async created() {
        try {
            const userId = localStorage.getItem("user_id"); // 假设的用户 ID，您可能需要动态获取或从路由参数中获取
            const response = await axios.get(`http://localhost:8080/favorite/user/${userId}`);
            const favoritesData = response.data;

            for (const favorite of favoritesData) {
                const productResponse = await axios.get(`http://localhost:8080/product/${favorite.product_id}`);
                const product = productResponse.data;
                this.favorites.push({
                    productId: product.productId,
                    productName: product.name,
                    category: product.category,
                    prod_region: product.prodRegion,
                    price: product.price,
                    price_LB: favorite.price_LB,
                    // 添加更多产品细节
                });
            }
        } catch (error) {
            console.error(error);
            // 处理错误情况
        }
    },
};
</script>

<style scoped>
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

/* 编辑表单容器样式 */
.edit-dialog-container {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}

/* 底部按钮区域样式 */
.dialog-footer {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
}

</style>