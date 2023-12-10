<template>
  <Navbar />
  <div style="margin: 0 20px;">

    <!-- 问题 -->
    <h2>用户数据分析: 统计不同用户群体收藏商品的分布情况</h2>

    <!-- 查询参数 -->
    <el-form :model="formData" ref="form" label-width="80px">
      <el-form-item label="性别">
        <el-select v-model="formData.gender" placeholder="请选择">
          <el-option label="男性" value="male"></el-option>
          <el-option label="女性" value="female"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="年龄段">
        <el-input v-model="formData.startAge" placeholder="开始年龄" style="width: 10%;"></el-input>
        <span>岁到</span>
        <el-input v-model="formData.endAge" placeholder="结束年龄" style="width: 10%;"> </el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="fetchData">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 查询结果 -->
    <div v-if="result" style="display: flex;">

      <!-- (1) 不同群体用户收藏前五的商品 -->
      <div style="width: 40%;">
        <h3>该群体用户收藏前十的商品：</h3><br>
        <el-table :data="result.topProducts" style="width: 100%" stripe>
          <el-table-column prop="product_name" label="商品名称"></el-table-column>
          <el-table-column prop="favorite_count" label="收藏次数">
            <!-- <template slot-scope="scope">{{ scope.row.favorite_count }}次收藏</template> -->
          </el-table-column>
        </el-table>
      </div>

      <div style="width: 60%;">
        <h3>该群体用户收藏商品类别的比例：</h3>
        <!-- 这里可以使用第三方图表库，如echarts，来展示饼状图 -->
        <div id="pieChart" style="width: 100%; height: 600px;"></div>
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
    // 在组件挂载后，可以使用第三方图表库初始化饼状图
    // 这里以 echarts 为例，你需要在项目中安装 echarts，并引入相应的模块
    // 这只是一个简单的示例，实际上需要根据项目的需求进行修改
    this.$nextTick(() => {

    });
  },
};
</script>
  
<style scoped>
/* 样式可以根据实际需求进行修改 */
</style>
  