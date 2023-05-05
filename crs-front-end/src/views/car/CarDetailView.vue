<template>
  <div style="display: flex; align-items: stretch">
    <el-card class="box-card" style="width: 50%;margin-right: 10px">
      <div slot="header" style="display: flex; justify-content: space-between;align-items: center">
        <span>汽车详细信息</span>
      </div>
      <div>
        <el-descriptions class="margin-top" :title="car.carName" :column="1" border>
          <template slot="extra"></template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              汽车名称
            </template>
            {{ car.carName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              汽车品牌
            </template>
            {{ car.brandId }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              汽车类型
            </template>
            {{ car.carType }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              座位数
            </template>
            {{ car.seatNum }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              百公里加速
            </template>
            {{ car.acceCapa }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              汽车油耗
            </template>
            {{ car.oilWear }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              日租金
            </template>
            {{ car.dailyRent }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              状态
            </template>
            <el-tag size="small" type="primary" v-if="car.state==1">已出租</el-tag>
            <el-tag size="small"  v-if="car.state==0">未出租</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              上架日期
            </template>
            {{ car.createDate }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              修改日期
            </template>
            {{ car.modifyDate }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>
    <el-card class="box-card" style="width: 50%">
      <div slot="header" style="display: flex; justify-content: space-between;align-items: center">
        <span>汽车图片</span>
      </div>
      <div v-if="pictures.length == 0">
        <el-empty description="暂无图片"></el-empty>
      </div>
      <div v-else>
        <el-image style="width: 100px; height: 100px; margin: 10px" v-for="picture in pictures" :src="picture.url"></el-image>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CarDetailView",
  data() {
    // 自定义校验函数 data 里面 return 上面
    let checkCarNameExists = (rule, value, callback) => {
      this.$http.get('/car/exists/' + value)
          .then((data) => {
            if(data == '已存在') {
              return callback(new Error('汽车名称已存在'));
            }else {
              callback()
            }
          })
    }
    return {
      brands: [],
      typeList: ['轿车', '豪华轿车', '跑车', '婚车'],
      car: {},
      pictures:[],
      rules: {
        carName: [
          {required: true, message:'汽车名称不能为空', trigger:'blur'},
          // 当失去焦点时, 调用checkCarNameExists函数校验名称是否已存在
          {validator: checkCarNameExists, trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    getCarDetail() {
      this.$http.get('/car/detail/' + this.car.id)
      .then((data) => {
        this.car = data.car
        data.pictureList.forEach((pic) => {
          this.pictures.push({
            name: pic.id,
            url:'http://localhost:8888/car/picture/download?fileName=' + pic.imageUrl
          })
        })
      })
    },
    getBrandList() {
      this.$http.get('/brand/list')
          .then((data)=> {
            this.brands = data
          })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post('/car/save', this.car)
              .then((data)=> {
                this.carId = data.id
                this.$message.success('保存成功!')
              })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.carId = null
      this.$refs.upload.clearFiles();
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleBeforeRemove(file) {
      // 在删除的时候, 把file.id传给后端, 用于SQL语句的where条件
      this.$http.get('/car/picture/del' + file.id)
    },
    handleSuccess(response, file) {
      if(response.code = '000000') {
        this.$message.success(file.name + '图片上传成功!')
        // 保存成功后, 将后端返回的图片的id赋给file.id
        file.id = response.data.id
      }else {
        this.$message.error(`${response.code}:${response.msg}`)
      }
    }
  },
  created() {
    this.car.id = this.$route.params.id
    this.getBrandList()
    this.getCarDetail()
  }
}
</script>

<style scoped>

</style>