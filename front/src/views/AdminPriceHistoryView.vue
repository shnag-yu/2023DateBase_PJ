<template>
  <div>
    <!-- 导航栏 -->
    <Navbar />

    <!-- 商品详细信息 -->
    <div class="product-detail">
      <div style="margin-left: 5%; width: 40%;">
        <h1>{{ product.name }}</h1>
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


        <div style="width: 90%;">
          <h3>此商品在其他商家的价格</h3>
          <el-table :data="result.otherMerchants" style="width: 100%" stripe>
            <el-table-column prop="merchant_name" label="商家名称"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column prop="price_diff" label="价格差"></el-table-column>
          </el-table>
        </div>
      </div>
      <!-- 商品历史价格折线图 -->
      <div>
        <el-select v-model="selectedTimespan" placeholder="选择时间跨度" style="align-items: center;">
          <el-option label="近一年" value="year"></el-option>
          <el-option label="近一月" value="month"></el-option>
          <el-option label="近一周" value="week"></el-option>
        </el-select>
        <!-- 使用 v-if 控制图表是否显示 -->
        <div v-if="selectedTimespan" id="PHC" style="height: 600px; width: 1000px;"></div>
        <div v-if="selectedTimespan" id="lowestPrice" style="text-align: center;">最低价：{{ lowestPrice }}</div>
      </div>
    </div>

    <div class="pricehistoryform">
      <el-table :data="pagedHistoryPrice" style="width: 100%">
        <el-table-column prop="productId" label="产品ID" width="180"></el-table-column>
        <el-table-column prop="date" label="日期" width="180"></el-table-column>
        <el-table-column prop="price" label="价格" width="180"></el-table-column>
        <el-table-column label="修改历史价格">
          <template #default="{ row }">
            <el-button @click="startEditing(row)" type="primary">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
          :page-size="pageSize" :total="priceHistory.length" layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </div>
    </div>
    <!-- 修改商品信息 -->
    <div v-if="editDialogVisible" class="edit-dialog-overlay">
      <!-- Edit Form Container -->
      <div class="edit-dialog-container">
        <el-form :model="currentHistoryPrice">
          <el-form-item label="Price">
            <el-input v-model="currentHistoryPrice.price" type="number"></el-input>
          </el-form-item>
        </el-form>
        <div class="dialog-footer">
          <el-button @click="editDialogVisible = false">Return</el-button>
          <el-button type="primary" @click="submitEdit">Submit</el-button>
        </div>
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
      result: {
        otherMerchants: [],
      },
      priceHistory: [],
      currentPage: 1,
      pageSize: 5,
      currentHistoryPrice: null,
      pagedHistoryPrice: null,
      editDialogVisible: false,
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
    // this.getOtherMerchantPrices();
  },
  methods: {
    getProductDetail() {
      const productId = this.$route.params.productId;
      axios.get(`/product/${productId}`).then((res) => {
        this.product = res.data;
        axios.get(`/product/otherMerchants`, {
          params: {
            product_name: this.product.name,
          }
        }
        ).then((res) => {
          console.log(res.data)
          this.result.otherMerchants = res.data;
          this.result.otherMerchants.forEach(item => {
            item.price_diff = (item.price - this.product.price).toFixed(2);
          });
        });
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
        this.priceHistory = res.data;
        this.updatePagedHistoryPrice();
        console.log(this.priceHistory)
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
      axios.get(`/favorite/get`, {
        params: {
          user_id: userId,
          product_id: productId,
        }
      }).then((res) => {
        this.isFavorite = res.data.code === 200 ? true : false;
      });
    },
    toggleFavorite() {
      if (this.isFavorite) {
        const productId = this.$route.params.productId;
        const userId = localStorage.getItem("user_id")
        axios.delete(`/favorite/delete`, {
          params: {
            user_id: userId,
            product_id: productId,
          }
        }
        ).then((res) => {
          this.isFavorite = false;
          this.$message.success('取消收藏成功');
        });
      }
      else {
        const productId = this.$route.params.productId;
        const userId = localStorage.getItem("user_id")
        axios.post(`/favorite/add`,
          {
            user_id: userId,
            product_id: productId,
          }
        ).then((res) => {
          this.isFavorite = true;
          this.$message.success('收藏成功');
        });
      }
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.updatePagedHistoryPrice();
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.updatePagedHistoryPrice();
    },
    updatePagedHistoryPrice() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      this.pagedHistoryPrice = this.priceHistory.slice(startIndex, endIndex);
      console.log(this.pagedHistoryPrice);
    },
    startEditing(price) {
      this.currentHistoryPrice = Object.assign({}, price); // Clone product to avoid mutating original data
      this.editDialogVisible = true;
      console.log(this.currentHistoryPrice)
    },
    submitEdit() {
      const pricehistory = this.currentHistoryPrice;
      axios.put(`/product/pricehistory`, pricehistory)
        .then(() => {
          this.getProductDetail();
          this.getHistoricalPrices();
          this.getIfFfavorite();
          this.editDialogVisible = false;
          this.$message.success('修改成功');
        })
        .catch((error) => {
          console.error('Error updating price:', error);
        });
    }
  },
};
</script>
  
<style scoped>
.pricehistoryform {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  /* 水平居中 */
  max-width: fit-content;
  /* 容器宽度适应内容 */
  padding: 20px;
  /* 添加一些内边距 */
  overflow: auto;
  /* 如果内容太宽，允许滚动 */
}

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

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 50px;
  /* 调整与表格的距离 */
  margin-bottom: 50px;
}

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
</style>
  