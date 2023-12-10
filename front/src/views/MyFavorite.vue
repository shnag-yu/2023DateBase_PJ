
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
            <el-table-column label="商品详情" width="200" align="center" >
                    <template #default="{ row }">
                        <el-button @click="goToProductDetails(row.productId)" type="primary">Details</el-button>
                    </template>
            </el-table-column>
            <!-- 添加更多列来显示产品的其他细节 -->
        </el-table>
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
        };
        },
        methods: {
            async goToProductDetails(productId) {
            // 逻辑来处理跳转到产品详情页
            // 例如，使用 Vue Router 进行导航
            this.$router.push(`/product/${productId}`)
            }
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
                price_LB:   favorite.price_LB,
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
    