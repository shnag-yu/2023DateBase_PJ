<template>
  <div>
    <!-- 导航栏 -->
    <Navbar />

    <!-- 商品详细信息 -->
    <div class="product-detail">
      <div style="margin-left: 10%;">
        <h2>{{ product.name }}</h2>
        <!-- ... 其他商品详细信息 ... -->
        <!-- 商品详细信息 -->
        <!-- <p>商品ID：{{ product.product_id }}</p> -->
        <p>类别：{{ product.category }}</p>
        <p>产地：{{ product.prodRegion }}</p>
        <p>生产日期：{{ product.prodDate }}</p>
        <p>价格：{{ product.price }}</p>
        <p>商家ID：{{ product.merchantId }}</p>
        <p>平台ID：{{ product.platformId }}</p>
        <p>商品描述：{{ product.description }}</p>

        <!-- 收藏按钮 -->
        <el-button type="primary" @click="toggleFavorite">{{ isFavorite ? '取消收藏' : '收藏商品' }}</el-button>
      </div>
      <!-- 商品历史价格折线图 -->
      <div>
        <el-select v-model="selectedTimespan" placeholder="选择时间跨度">
          <el-option label="近一周" value="week"></el-option>
          <el-option label="近一月" value="month"></el-option>
          <el-option label="近一年" value="year"></el-option>
        </el-select>
        <!-- 使用 v-if 控制图表是否显示 -->
        <div v-if="selectedTimespan" id="PHC" style="height: 400px; width: 80%;"></div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '../components/UserNav.vue';
import axios from 'axios';
import * as echarts from 'echarts';

export default {
  components: {
    Navbar,
  },
  data() {
    return {
      product: {},
      isFavorite: false,
      selectedTimespan: 'week',
    };
  },
  watch: {
    // 监听 selectedTimespan 的变化，变化时调用 getHistoricalPrices
    selectedTimespan: 'getHistoricalPrices',
  },
  mounted() {
    // 获取商品详细信息
    this.getProductDetail();
    // 初始化时获取商品历史价格
    this.getHistoricalPrices();
  },
  methods: {
    getProductDetail() {
      const productId = this.$route.params.productId;
      axios.get(`/product/${productId}`).then((res) => {
        this.product = res.data;
      });
    },
    getHistoricalPrices() {
      const productId = this.$route.params.productId;
      console.log(this.selectedTimespan)
      axios.get(`/product/pricehistory/${productId}`, {
        params: {
          timespan: this.selectedTimespan,
        }
      }
      ).then((res) => {
        const PHChart = echarts.init(document.getElementById('PHC'));
        PHChart.setOption({
          xAxis: {
            type: 'category',
            data: res.data.map((item) => item.date),
          },
          yAxis: {
            type: 'value',
          },
          series: [
            {
              data: res.data.map((item) => item.price),
              type: 'line',
            },
          ],
        });
      });
    },
    toggleFavorite() {
      this.isFavorite = !this.isFavorite;
    },
  },
};
</script>

<style scoped>
.product-detail {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin: 20px;
}

.product-detail div {
  width: 48%;
}

.product-detail h2 {
  margin-bottom: 10px;
}
</style>
