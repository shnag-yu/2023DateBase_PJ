<template>
    <Navbar />
    <div style="margin: 0 20px;">
  
      <!-- 问题 -->
      <h2>商品数据分析: 分析商品价格差极值的分布情况</h2>
  
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
        <div style="width: 50%;margin: 20px 20px;">
          <h3>历史价格差最大前十的商品</h3>
          <el-table :data="result.topProducts" style="width: 100%" stripe>
            <el-table-column prop="product_name" label="商品名称"></el-table-column>
            <el-table-column prop="favorite_count" label="收藏次数">
              <!-- <template slot-scope="scope">{{ scope.row.favorite_count }}次收藏</template> -->
            </el-table-column>
          </el-table>
        </div>
  
        <div style="width: 50%;margin: 20px 20px;">
          <h3>历史价格差最小值前十的商品</h3>
          <el-table :data="result.topProducts" style="width: 100%" stripe>
            <el-table-column prop="product_name" label="商品名称"></el-table-column>
            <el-table-column prop="favorite_count" label="收藏次数">
              <!-- <template slot-scope="scope">{{ scope.row.favorite_count }}次收藏</template> -->
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
  import * as echarts from 'echarts';
  export default {
    components: {
      Navbar,
    },
    data() {
      return {
        formData: {
          gender: '',
          startAge: '',
          endAge: '',
        },
        result: {
          topProducts: [],
          categoryRatio: [],
        },
      };
    },
    methods: {
      fetchData() {
        // 根据选择的性别和年龄段发起数据请求
        // 在实际项目中，你需要调用后端API来获取相应的数据
        // 然后更新 result 的值，根据需要更新饼状图的数据
        // 这里只是一个简单的示例，实际上需要根据项目的需求进行修改
        // console.log(this.formData);
        axios.get('/user/favoriteProducts', {
          params: this.formData
        }
        ).then((res) => {
          this.result.topProducts = res.data;
          // console.log(this.result);
        });
        axios.get('/user/favoriteCategoryRatio', {
          params: this.formData
        }
        ).then((res) => {
          this.result.categoryRatio = res.data;
          console.log(this.result);
          const chart = echarts.init(document.getElementById('pieChart'));
          chart.setOption({
            legend: {
              top: 'bottom'
            },
            toolbox: {
              show: true,
              feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true }
              }
            },
            tooltip: {  // 添加 tooltip 配置
              trigger: 'item',
              formatter: '{b}:  ({d}%)'
            },
            series: [
              {
                name: 'Nightingale Chart',
                type: 'pie',
                radius: [50, 250],
                center: ['50%', '50%'],
                roseType: 'area',
                itemStyle: {
                  borderRadius: 8
                },
                data: this.result.categoryRatio
              }
            ]
          });
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
    