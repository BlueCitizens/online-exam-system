<!-- 添加考试 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="科目">
        <el-input v-model="form.source"></el-input>
      </el-form-item>
      <el-form-item label="作业名">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
<!--      <el-form-item label="班级">
        <el-input v-model="form.clazz"></el-input>
      </el-form-item>-->
      <el-form-item label="截止日期">
        <el-col :span="11">
          <el-date-picker placeholder="选择日期" v-model="form.deadline" style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>
<!--      <el-form-item label="作业类型">
        <el-input v-model="form.type"></el-input>
      </el-form-item>-->
      <el-form-item label="答题须知">
        <el-input type="textarea" v-model="form.tips"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button type="text" @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  data() {
    return {
      form: { //表单数据初始化
        source: null,
        description: null,
        clazz: null,
        createTime: null,
        deadline: null,
        totalScore: null,
        type: null,
        tips: null,
        paperId: null,
      }
    };
  },
  methods: {
    formatTime(date) { //日期格式化
      let year = date.getFullYear()
      let month= date.getMonth()+ 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day=date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours=date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes=date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    onSubmit() {
      let date = new Date()
      this.form.createTime =this.formatTime(date).substr(0,10)
      let deadline = this.formatTime(this.form.deadline)
      this.form.deadline = deadline.substr(0,10)
      this.$axios(`/api/workManagePaperId`).then(res => {
        this.form.paperId = res.data.data.paperId + 1 //实现paperId自增1
        this.$axios({
          url: '/api/work',
          method: 'post',
          data: {
            ...this.form
          }
        }).then(res => {
          if(res.data.code == 200) {
            this.$message({
              message: '数据添加成功',
              type: 'success'
            })
            this.$router.push({path: '/selectWork'})
          }
        })
      })
    },
    cancel() { //取消按钮
      this.form = {}
    },

  }
};
</script>
<style lang="scss" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

