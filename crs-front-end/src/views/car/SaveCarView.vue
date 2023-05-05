<template>
<div style="display: flex; align-items: stretch">
  <el-card class="box-card" style="width: 50%;margin-right: 10px">
    <div slot="header" style="display: flex; justify-content: space-between;align-items: center">
      <span>汽车基础信息</span>
    </div>
    <div>
      <el-form ref="carForm" :model="car" :rules="rules" label-width="100px">
        <el-form-item label="汽车名称" prop="carName">
          <el-input v-model="car.carName"  placeholder="请输入汽车名称"></el-input>
        </el-form-item>
        <el-form-item label="汽车品牌" prop="brandId">
          <el-select v-model="car.brandId" placeholder="请选择品牌" style="width: 100%">
            <el-option v-for="brand in brands" :label="brand.brandName" :value="brand.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="汽车类型" prop="carType">
          <el-select v-model="car.carType" placeholder="请选择类型" style="width: 100%">
            <el-option v-for="type in typeList" :label="type" :value="type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="座位数" prop="searNum">
          <el-input v-model="car.seatNum" placeholder="请输入座位数"></el-input>
        </el-form-item>
        <el-form-item label="百公里加速" prop="searNum">
          <el-input v-model="car.acceCapa" placeholder="请输入百公里加速"></el-input>
        </el-form-item>
        <el-form-item label="油耗" prop="searNum">
          <el-input v-model="car.oilWear" placeholder="请输入油耗"></el-input>
        </el-form-item>
        <el-form-item label="日租金" prop="searNum">
          <el-input v-model="car.dailyRent" placeholder="请输入日租金"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"  @click="submitForm('carForm')">保存</el-button>
          <el-button  @click="resetForm('carForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-card>
  <el-card class="box-card" style="width: 50%">
    <div slot="header" style="display: flex; justify-content: space-between;align-items: center">
      <span>汽车图片</span>
    </div>
    <div>
      <el-upload
          class="upload-demo"
          ref="upload"
          action="http://localhost:8888/car/picture/upload"
          :data="{id: this.carId}"
          :auto-upload="false"
          :before-remove="handleBeforeRemove"
          :on-success="handleSuccess"
          :file-list="fileList"
          list-type="picture">
        <el-button slot="trigger" size="small" type="primary" :disabled="carId==null">选择图片</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </div>
  </el-card>
</div>
</template>

<script>
export default {
  name: "SaveCarView",
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
      carId: null,
      car: {
        carName: null,
        brandId: null,
        carType: null,
        seatNum: null,
        acceCapa: null,
        oilWear: null,
        dailyRent: null,
        state:0
      },
      fileList:[],
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
    this.getBrandList()
  }
}
</script>

<style scoped>

</style>