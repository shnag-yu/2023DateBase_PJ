<template>
  <div>
    <!-- 导航栏 -->
    <Navbar />

    <!-- 商品搜索框和按钮居中 -->
    <div style="display: flex; justify-content: center; align-items: center; margin: 10px 0;">
      <el-input v-model="searchKeyword" placeholder="请输入商品关键字" @input="handleSearchInput" style="width: 30%;height: 40px;"></el-input>
      <el-button type="primary" @click="searchProducts" style="margin-left: 10px;">搜索</el-button>
    </div>

    <!-- 搜索结果显示 -->
    <div v-if="searchResults.length > 0">
      <div id="result_brief" style="margin: 20px 40%;">
        <!-- <span style="margin: 35%;">共 {{ totalItems }} 条结果</span> -->
        <el-pagination :total="totalItems" :page-size="pageSize" @current-change="handlePageChange"></el-pagination>
      </div>

      <div class="product-grid">
        <!-- 遍历搜索结果，显示商品简略信息 -->
        <div v-for="product in paginatedResults" :key="product.productId" class="product-item">
          <div style="font-size: large; font-weight: bolder; margin: 10px 0;">{{ product.name }}</div>
          <div>商家：{{ product.merchantName }}</div>
          <div>平台：{{ product.platformName }}</div>
          <div>价格：{{ product.price }}</div>
          <el-button @click="goToProductDetails(product.productId)" style="margin-top: 10px;">查看详情</el-button>
        </div>
      </div>
    </div>
    <div v-else>
      <p style="margin: 20px 0;">没有找到相关商品</p>
    </div>
  </div>
</template>
  
<script>
import Navbar from '../components/UserNav.vue'; 
import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8080';

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      searchKeyword: '',
      searchResults: [], // 搜索结果
      totalItems: 0, // 总条目数
      currentPage: 1, // 当前页码
      pageSize: 20, // 每页显示条目数
    };
  },
  computed: {
    // 计算分页后的结果
    paginatedResults() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.searchResults.slice(start, end);
    },
  },
  methods: {
    // 执行商品搜索
    searchProducts() {
      axios.get('/product/search/keyword='+this.searchKeyword).then((res) => {
        this.searchResults = res.data;
        this.totalItems = res.data.length;
      });
    },
    // 处理分页变化
    handlePageChange(currentPage) {
      this.currentPage = currentPage;
    },
    // 跳转到商品详情页
    goToProductDetails(productId) {
      this.$router.push(`/product/${productId}`)
    },
  },
};
</script>
  
<style scoped>
.product-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  margin: 20px;
  gap: 20px;
}

.product-item {
  border: 1px solid #ddd;
  margin: 10px;
  padding: 10px;
}
</style>
  