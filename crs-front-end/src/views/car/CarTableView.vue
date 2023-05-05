<template>
  <el-card class="box-card">
    <!-- 卡片头部的父元素可以用flex布局, 水平两端对齐, 垂直居中对其-->
    <div slot="header" style="display: flex; justify-content: space-between;align-items: center">
      <!-- 左边放标题 -->
      <span>汽车列表</span>
      <!-- 左边放按钮 -->
      <el-button v-if="account.identity==1" @click="$router.push('/car/save')" type="primary">汽车上架</el-button>
    </div>
    <!-- 头部下面放卡片主体 -->
    <div>
      <!-- 搜索的表单 :inline="true"表示表单水平排列 -->
      <el-form :inline="true" :model="param" style="min-width: 1000px">
        <el-form-item>
          <el-input v-model="param.carName" placeholder="输入汽车名称"/>
        </el-form-item>
        <el-form-item>
          <el-select v-model="param.brandId" placeholder="选择汽车品牌">
            <el-option v-for="brand in brands" :key="brand.id" :label="brand.brandName" :value="brand.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="param.lowDailyRent" placeholder="最低日租金"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="param.highDailyRent" placeholder="最高日租金"/>
        </el-form-item>
        <!-- 搜索按钮也是调用getCarList方法 -->
        <el-button type="primary" @click="getTableData">搜索</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form>
      <el-table :data="tableData" stripe border max-height="600" style="width: 100%">
        <el-table-column prop="id" label="汽车编号" min-width="150"></el-table-column>
        <el-table-column prop="carName" label="汽车名称" min-width="150"></el-table-column>
        <el-table-column prop="seatNum" label="座位数" min-width="150"></el-table-column>
        <el-table-column prop="acceCapa" label="百公里加速" min-width="150"></el-table-column>
        <el-table-column prop="carType" label="汽车类型" min-width="150"></el-table-column>
        <el-table-column prop="oilWear" label="百公里油耗" min-width="150"></el-table-column>
        <el-table-column prop="dailyRent" label="日租金" min-width="150"></el-table-column>
        <el-table-column label="操作" min-width="300px" v-if="account.identity==1">
          <template v-slot="scope">
            <el-button size="mini" @click="$router.push('/car/detail/' + scope.row.id)">查看详情</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row.id)">删除</el-button>
            <el-button type="primary" v-if="scope.row.state==0" size="mini">租赁</el-button>
            <el-button type="success" v-if="scope.row.state==1" size="mini">归还</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="300px" v-if="account.identity==2">
          <template v-slot="scope">
            <el-button size="mini" @click="$router.push('/car/detail/' + scope.row.id)">查看详情</el-button>
            <el-button type="primary" v-if="scope.row.state==0" size="mini">租赁</el-button>
            <el-button type="success" v-if="scope.row.state==1" size="mini">归还</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="300px" v-if="account.identity==3">
          <template v-slot="scope">
            <el-button size="mini" @click="$router.push('/car/detail/' + scope.row.id)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 表格下面放一个分页器组件 -->
      <el-pagination
          style="margin-top: 20px"
          background
          layout="sizes,prev, pager, next, jumper, total"
          :page-size="param.size"
          :current-page="param.current"
          :page-sizes="sizes"
          :total="total"
          :page-count="pages"
          @current-change="currentChange"
          @size-change="sizeChange">
      </el-pagination>
    </div>
  </el-card>
</template>

<script>
export default {
  name: "CarTableView",
  data() {
    return {
      account: null,
      // 绑定下拉框的汽车品牌数组
      brands: [],
      // 查询条件对象param (跟随http请求发给后端)
      param : {
        carName: null,
        brandId: null,
        lowDailyRent: null,
        highDailyRent: null,
        current: 1,
        size: 10
      },
      // 绑定给表格的数组 (http响应的数据data(就是后端的分页器对象)中取出records赋给它)
      tableData: [],
      // 查询到的总行数 (http响应的数据data(就是后端的分页器对象)中取出total赋给它)
      total:0,
      // 查询到的总行数 (http响应的数据data(就是后端的分页器对象)中取出pages赋给它)
      pages:0,
      // 绑定给分页器组件的数组, 控制每页多少行的下拉框选项
      sizes:[5, 10, 20, 50, 100],
    }
  },
  methods: {
    getBrandList() {
      this.$http.get('/brand/list')
      .then((data)=> {
        this.brands = data
      })
    },
    getTableData() {
      this.$http.get('/car/query', {
        params: this.param
      }).then((data) => {
        this.tableData = data.records
        this.total = data.total
        this.pages = data.pages
      })
    },
    handleDelete(id) {
      this.$confirm('是否确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        this.$http.get('/car/del/' + id)
            .then((data) => {
              // 删除后的回调函数中从新调用getTableData函数查询一次, 即可刷新表格
              this.getTableData()
            })
      })
    },
    // 当页码current改变时的回调行数
    currentChange(val){
      this.param.current = val
      this.getTableData()
    },
    // 当size改变时的回调行数
    sizeChange(val){
      this.param.size = val
      this.getTableData()
    },
    resetForm() {
      this.param.carName = null
      this.param.brandId = null
      this.param.lowDailyRent = null
      this.param.highDailyRent = null
    }
  },
  created() {
    // 组件创建成功后
    // 1. 获取当前账户
    this.account = JSON.parse(localStorage.getItem('account'))
    // 2. 加载品牌列表 (绑定品牌下拉框)
    this.getBrandList()
    // 3. 加载汽车列表 (绑定汽车表格)
    this.getTableData()
  },
}
</script>

<style scoped>

</style>