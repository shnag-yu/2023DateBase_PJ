<template>
  <div>
    <!-- 导航栏 -->
    <Navbar />

    <!-- 商品详细信息 -->
    <div class="product-detail">
      <div style="margin-left: 5%; width: 20%;">
        <h2>{{ product.name }}</h2>
        <!-- ... 其他商品详细信息 ... -->
        <!-- 商品详细信息 -->
        <!-- <p>商品ID：{{ product.product_id }}</p> -->
        <p>类别：{{ product.category }}</p>
        <p>产地：{{ product.prodRegion }}</p>
        <p>生产日期：{{ product.prodDate }}</p>
        <p>价格：{{ product.price }}</p>
        <p>商家：{{ product.merchantName }}</p>
        <p>平台：{{ product.platformName }}</p>
        <p>商品描述：{{ product.description }}</p>

        <!-- 收藏按钮 -->
        <el-button type="primary" @click="toggleFavorite">{{ isFavorite ? '取消收藏' : '收藏商品' }}</el-button>
      </div>
      <!-- 商品历史价格折线图 -->
      <div style="width: 60%;">
        <el-select v-model="selectedTimespan" placeholder="选择时间跨度" style="align-items: center;">
          <el-option label="近一年" value="year"></el-option>
          <el-option label="近一月" value="month"></el-option>
          <el-option label="近一周" value="week"></el-option>
        </el-select>
        <!-- 使用 v-if 控制图表是否显示 -->
        <div v-if="selectedTimespan" id="PHC" style="height: 600px; width: 100%;"></div>
        <div v-if="selectedTimespan" id="lowestPrice" style="text-align: center;">最低价：{{ lowestPrice }}</div>
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
      selectedTimespan: 'year',
      lowestPrice: 0.0,
    };
  },
  watch: {
    selectedTimespan: 'getHistoricalPrices',
  },
  mounted() {
    // 获取商品详细信息
    this.getProductDetail();
    this.getHistoricalPrices();
    this.getIfFfavorite();
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
          tooltip: {
            trigger: 'axis', // 触发类型，可以设置为 'item' 单项触发，也可以设置为 'axis' 坐标轴触发
            axisPointer: {
              type: 'cross' // 十字准星指示器
            },
            formatter: function (params) {
              return 'Date: ' + params[0].axisValue + '<br/>Price: ' + params[0].data.toFixed(2);
            }
          },
          series: [
            {
              data: res.data.map((item) => item.price),
              type: 'line',
              markPoint: {
                data: [{
                  type: 'min', // 标记最小值
                  name: '最低点'
                }]
              }
            },
          ],
        });
      });
      axios.get(`/product/lowestprice/${productId}`, {
        params: {
          timespan: this.selectedTimespan,
        }
      }
      ).then((res) => {
        this.lowestPrice = res.data;
      });
    },
    getIfFfavorite() {
      const productId = this.$route.params.productId;
      const userId = localStorage.getItem("user_id")
      axios.get(`/favorite/get`,{
        params: {
          user_id: userId,
          product_id: productId,
        }
      }).then((res) => {
        this.isFavorite = res.data.code === 200? true : false;
      });
    },
    toggleFavorite() {
      if(this.isFavorite){
        const productId = this.$route.params.productId;
        const userId = localStorage.getItem("user_id")
        axios.delete(`/favorite/delete`,
          {
            user_id: userId,
            product_id: productId,
          }
        ).then((res) => {
          this.isFavorite = false;
        });
      }
      else{
        const productId = this.$route.params.productId;
        const userId = localStorage.getItem("user_id")
        axios.post(`/favorite/add`,
          {
            user_id: userId,
            product_id: productId,
          }
        ).then((res) => {
          this.isFavorite = true;
        });
      }
    },
  },
};
</script>

<style scoped>
.product-detail {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin: 10px;
}

.product-detail div {
  width: 70%;
}

.product-detail h2 {
  margin-bottom: 10px;
}
</style>
