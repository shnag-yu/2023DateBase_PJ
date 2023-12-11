<template>
    <Navbar />
    <div style="margin: 0 20px;">
  
      <!-- 问题 -->
      <h2>商品数据分析: 分析商品历史价格极差的分布情况</h2>
  
      <!-- 查询参数 -->
      <el-form :model="formData" ref="form" label-width="80px">
        <el-form-item label="品类">
          <el-select v-model="formData.category" placeholder="请选择">
            <el-option label="所有品类" value="所有"></el-option>
            <el-option label="书籍" value="书籍"></el-option>
            <el-option label="日用品" value="日用品"></el-option>
            <el-option label="玩具" value="玩具"></el-option>
            <el-option label="运动器材" value="运动器材"></el-option>
            <el-option label="食品" value="食品"></el-option>
            <el-option label="服装" value="服装"></el-option>
            <el-option label="化妆品" value="化妆品"></el-option>
            <el-option label="电子产品" value="电子产品"></el-option>
            <el-option label="家具" value="家具"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="时间跨度">
          <el-select v-model="formData.timespan" placeholder="请选择">
            <el-option label="近一年" value="year"></el-option>
            <el-option label="近一月" value="month"></el-option>
            <el-option label="近一周" value="week"></el-option>
          </el-select>
        </el-form-item>
  
        <el-form-item>
          <el-button type="primary" @click="fetchData">查询</el-button>
        </el-form-item>
      </el-form>
  
      <!-- 查询结果 -->
      <div v-if="result" style="display: flex;">
  
        <!-- (1) 不同群体用户收藏前五的商品 -->
        <div style="width: 45%;margin: 20px 20px;">
          <h3>历史价格极差最大前十的商品</h3>
          <el-table :data="result.maxProducts" style="width: 100%" stripe>
            <el-table-column prop="product_name" label="商品名称"></el-table-column>
            <el-table-column prop="price_range" label="价格极差">
            </el-table-column>
          </el-table>
        </div>
  
        <div style="width: 45%;margin: 20px 20px;">
          <h3>历史价格极差最小前十的商品</h3>
          <el-table :data="result.minProducts" style="width: 100%" stripe>
            <el-table-column prop="product_name" label="商品名称"></el-table-column>
            <el-table-column prop="price_range" label="价格极差">
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </template>
      
  <script>
  import axios from 'axios';
  axios.defaults.baseURL = 'http://localhost:8080';
  import Navbar from '../../components/UserNav.vue';
  export default {
    components: {
      Navbar,
    },
    data() {
      return {
        formData: {
          category: '',
          timespan: '',
        },
        result: {
          maxProducts: [],
          minProducts: [],
        },
      };
    },
    methods: {
      fetchData() {
        axios.get('/product/maxPriceRanges', {
          params: this.formData
        }
        ).then((res) => {
          this.result.maxProducts = res.data;
          // console.log(this.result);
        });
        axios.get('/product/minPriceRanges', {
          params: this.formData
        }
        ).then((res) => {
          this.result.minProducts = res.data;
          // console.log(this.result);
        });
      },
    },
    mounted() {
      this.$nextTick(() => {
      });
    },
  };
  </script>
      
  <style scoped>
  /* 样式可以根据实际需求进行修改 */
  </style>
      