<template>
    <div>
        <Navbar />
        <div v-if="merchant" class="merchant-info">
            <h2>商家信息</h2>
            <div class="info-item"><span>ID:</span>{{ merchant.id }}</div>
            <div class="info-item"><span>名称：</span>{{ merchant.name }}</div>
            <div class="info-item"><span>地址：</span>{{ merchant.addr }}</div>
            <el-button @click="showAddProduct()" type="success" size="large">新增商品</el-button>

        </div>
        <div>

            <div v-if="showAddProductDialog" class="overlay">
                <div class="add-product-dialog">
                    <h2>新增商品</h2>
                    <el-form :model="newProduct" :rules="rules">
                        <el-form-item label="名称" prop="name">
                            <el-input v-model="newProduct.name"></el-input>
                        </el-form-item>
                        <el-form-item label="类别" prop="category">
                            <el-input v-model="newProduct.category"></el-input>
                        </el-form-item>
                        <el-form-item label="产地" prop="prodRegion">
                            <el-input v-model="newProduct.prodRegion"></el-input>
                        </el-form-item>
                        <el-form-item label="生产日期" prop="prodDate">
                            <el-date-picker v-model="newProduct.prodDate" type="date" placeholder="选择日期"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="价格" prop="price">
                            <el-input v-model="newProduct.price" type="number"></el-input>
                        </el-form-item>
                        <el-form-item label="商家ID" >
                            <el-input v-model="newProduct.merchantId" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="描述" prop="description">
                            <el-input type="textarea" v-model="newProduct.description"></el-input>
                        </el-form-item>
                        <el-form-item label="商家名称">
                            <el-input v-model="newProduct.merchantName" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="平台名称" prop="platformName">
                            <el-input v-model="newProduct.platformName"></el-input>
                        </el-form-item>
                        <!-- 提交和返回按钮 -->
                        <el-form-item>
                            <el-button @click="submitProduct()">提交</el-button>
                            <el-button @click="showAddProductDialog = false">返回</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
        </div>

        <div :class="['main-content', bodyClass]">
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
        <!-- Edit Dialog Overlay -->
        <div v-if="editDialogVisible" class="edit-dialog-overlay">
            <!-- Edit Form Container -->
            <div class="edit-dialog-container">
                <el-form :model="currentProduct">
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
                    <el-button @click="cancelEdit">Return</el-button>
                    <el-button type="primary" @click="submitEdit">Submit</el-button>
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
            productList: [],
            editDialogVisible: false,
            currentProduct: null,
            merchant: null,
            showAddProductDialog: false,
            newProduct: {
                productId: null, // 商品ID通常由后端生成，这里初始化为null或空字符串
                name: '', // 名称
                category: '', // 类别
                prodRegion: '', // 产地
                prodDate: '', // 生产日期
                price: 0, // 价格
                merchantId: '', // 商家ID
                platformId: '', // 平台ID
                description: '', // 描述
                merchantName: '', // 商家名称
                platformName: '', // 平台名称
            },
            rules: {
        name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' },
        ],
        category: [
          { required: true, message: '请输入商品类别', trigger: 'blur' },
        ],
        prodRegion: [
          { required: true, message: '请输入商品产地', trigger: 'blur' },
        ],
        prodDate: [
          { type: 'date', required: true, message: '请选择生产日期', trigger: 'change' },
        ],
        price: [
          { required: true, message: '请输入商品价格', trigger: 'blur' },
          { pattern: /^\d+$/, message: '价格只能是数字', trigger: 'blur' },
        ],
        description: [
          { required: true, message: '请输入商品描述', trigger: 'blur' },
        ],
        platformName: [
          { required: true, message: '请输入平台名称', trigger: 'blur' },
        ],
        // 根据需要添加其他字段的验证规则
      }
        };
    },
    computed: {
        bodyClass() {
            return this.editDialogVisible ? 'body-no-events' : '';
        },
    },
    methods: {
        submitProduct() {
            if (this.newProduct.prodDate && this.newProduct.prodDate instanceof Date) {
                // 将 Date 对象转换为 ISO 字符串格式，然后提取日期部分
                this.newProduct.prodDate.setDate(this.newProduct.prodDate.getDate() + 1);
                this.newProduct.prodDate = this.newProduct.prodDate.toISOString().split('T')[0];
            }
            axios.post('/product', this.newProduct)
                .then(() => {
                    this.showAddProductDialog = false;
                    this.$message.success('添加成功');
                    this.fetchProducts();
                    // 刷新商品列表或执行其他操作
                })
                .catch(error => {
                    console.error('Error submitting product:', error);
                });
        },
        fetchProducts() {
            const merchantId = localStorage.getItem('merchant_id');
            axios
                .get(`http://localhost:8080/merchants/merchant/${merchantId}`)
                .then((response) => {
                    this.productList = response.data;
                })
                .catch((error) => {
                    console.error('Error fetching products:', error);
                });
        },
        fetchMerchantInfo() {
            const merchantId = localStorage.getItem('merchant_id');
            axios.get(`/merchants/merchantinfo/${merchantId}`)
                .then(response => {
                    this.merchant = response.data;

                })
                .catch(error => {
                    console.error('Error fetching merchant info:', error);
                });
        },
        startEditingProduct(product) {
            this.currentProduct = Object.assign({}, product); // Clone product to avoid mutating original data
            this.editDialogVisible = true;
            console.log(this.editDialogVisible)
        },
        cancelEdit() {
            this.editDialogVisible = false;
        },
        submitEdit() {
            const productId = this.currentProduct.productId;
            const product = this.currentProduct;
            console.log(product);
            if (product.prodDate && product.prodDate instanceof Date) {
                // 将 Date 对象转换为 ISO 字符串格式，然后提取日期部分
                product.prodDate.setDate(product.prodDate.getDate() + 1);
                product.prodDate = product.prodDate.toISOString().split('T')[0];
            }
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
        returnHome() {
            // Logic to return to the home page
            // For example, if you are using vue-router:
            this.$router.push('/merchantmain');
        },
        deleteProduct(product) {
            const productId = product.productId;
            axios.delete(`/product/${productId}`)
                .then(() => {
                    this.fetchProducts();
                    this.editDialogVisible = false;
                    this.$message.success('删除成功');
                })
                .catch((error) => {
                    console.error('Error deleting product:', error);
                });

        },
        showAddProduct() {
            this.showAddProductDialog = true;
            this.newProduct.merchantId = localStorage.getItem('merchant_id');
            this.newProduct.merchantName = this.merchant.name;
            console.log(this.newProduct);
        },

    },
    created() {
        this.fetchProducts();
        this.fetchMerchantInfo();
    }
};
</script>
  

<style scoped>
/* 遮罩层样式 */
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

.body-no-events {
    pointer-events: none;
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

.info-item {
    margin: 10px 0;
    /* 信息项之间的垂直间距 */
}

.info-item span {
    font-weight: bold;
    /* 信息项标签加粗 */
}

.overlay {
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
}

.add-product-dialog {
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}
</style>