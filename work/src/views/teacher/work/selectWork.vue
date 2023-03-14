<!--查询所有考试-->
<template>
  <div class="exam">
    <el-table row-click="" :data="pagination.records" row-key="paperId">
      <el-table-column fixed="left" label="">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="changeStatus(scope.row)"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column fixed="left" prop="description" label="作业名称" width="180"></el-table-column>
      <el-table-column prop="source" label="学科" width="200"></el-table-column>
      <el-table-column prop="paperId" label="试题号" width="200"></el-table-column>
      <el-table-column prop="deadline" label="截止日期" width="120"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="120"></el-table-column>
      <el-table-column prop="type" label="作业类型" width="120"></el-table-column>
      <el-table-column prop="tips" label="答题须知" width="400"></el-table-column>
      <el-table-column fixed="right" label="操作" width="200">
        <template slot-scope="scope">
          <el-button @click="check(scope.row.workCode)" type="success" size="small" round>批阅</el-button>
          <el-popover
            @show="showQuestion(scope.row.paperId)"
            @hide="quitQuestion"
            width="1000"
            placement="left"
            trigger="click">
            <el-collapse v-model="activeCol" accordion>
              <el-collapse-item title="已有题目" name="1">
                <el-table
                  max-height="450"
                  stripe
                  :data="questionData">
                  <el-table-column
                    label="操作"
                    width="180">
                    <template slot-scope="scope">
                      <el-button type="text" @click="deleteQuestionFrPaper(scope.row)"><i class="el-icon-remove" style="color: #dd5862"></i></el-button>
                    </template>
                  </el-table-column>
                  <el-table-column prop="questionId" label="题号"></el-table-column>
                  <el-table-column
                    label="题型"
                    width="180">
                    <template slot-scope="scope">
                      <el-tag v-if="scope.row.questionType === '1'" type="primary">选择题</el-tag>
                      <el-tag v-if="scope.row.questionType === '2'" type="success">填空题</el-tag>
                      <el-tag v-if="scope.row.questionType === '3'" type="warning">判断题</el-tag>
                      <el-tag v-if="scope.row.questionType === '4'" type="danger">简答题</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="题目"
                    width="300">
                    <template slot-scope="scope">
                      {{ scope.row.question }}
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="分值">
                    <template slot-scope="scope">
                      {{ scope.row.score }}
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="section"
                    label="章节">
                  </el-table-column>
                </el-table>
              </el-collapse-item>
              <el-collapse-item name="2">
                <template slot="title">
                  <i class="el-icon-circle-plus" style="color: #409eff;margin-left: 9px;"></i>
                </template>
                <add-question :paper-id="scope.row.paperId" :subject="scope.row.source" :question-data="questionData"
                              @showQuestion="showQuestion"></add-question>
              </el-collapse-item>
            </el-collapse>
            <el-button slot="reference" type="primary" size="small" round>组卷</el-button>
          </el-popover>
          <el-popover
            placement="top"
            width="80"
            trigger="hover">
            <el-button @click="edit(scope.row.workCode)" type="warning" size="small" round>编辑</el-button>
            <el-button @click="deleteRecord(scope.row.workCode)" type="danger" size="small" round>删除</el-button>
            <el-button slot="reference" icon="el-icon-more" size="mini" circle></el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 8, 10, 20]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total" class="page">
    </el-pagination>
    <!-- 批阅对话框 -->
    <el-dialog title="学生列表" :visible.sync="checkDialogVisible">
      <el-table
        :data="studentData"
        border
        style="width: 100%">
        <el-table-column
          prop="studentName"
          label="姓名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="obScore"
          label="客观题得分"
          width="120">
        </el-table-column>
        <el-table-column
          prop="suScore"
          label="主观题得分"
          width="120">
        </el-table-column>
        <el-table-column
          prop="score"
          label="总分"
          width="100">
        </el-table-column>
        <el-table-column
          prop="answerDate"
          label="提交时间"
          width="120">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button v-if="scope.row.score === null && scope.row.suScore === null" @click="toMarking(scope.row)"
                       type="text" size="small">批阅
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑试卷信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="试卷名称">
            <el-input v-model="form.source"></el-input>
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="form.description"></el-input>
          </el-form-item>
          <el-form-item label="所属学院">
            <el-input v-model="form.institute"></el-input>
          </el-form-item>
          <el-form-item label="所属专业">
            <el-input v-model="form.major"></el-input>
          </el-form-item>
          <el-form-item label="年级">
            <el-input v-model="form.grade"></el-input>
          </el-form-item>
          <el-form-item label="考试日期">
            <el-col :span="11">
              <el-date-picker type="date" placeholder="选择日期" v-model="form.examDate"
                              style="width: 100%;"></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="持续时间">
            <el-input v-model="form.totalTime"></el-input>
          </el-form-item>
          <el-form-item label="总分">
            <el-input v-model="form.totalScore"></el-input>
          </el-form-item>
          <el-form-item label="试卷类型">
            <el-input v-model="form.type"></el-input>
          </el-form-item>
          <el-form-item label="考生提示">
            <el-input type="textarea" v-model="form.tips"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import addQuestion from "../../../components/teacher/work/addQuestion";

export default {
  components: {addQuestion},
  data() {
    return {
      activeCol: '1',
      questionData: [],
      studentData: [],
      form: {}, //保存点击以后当前试卷的信息
      pagination: { //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4 //每页条数
      },
      dialogVisible: false,
      checkDialogVisible: false
    }
  },
  created() {
    this.getWorkInfo()
  },
  methods: {
    changeStatus(val) {
      console.log(val)
      this.$axios({ //提交
        url: `/api/work/${val.workCode}/status/${val.status}`,
        method: 'put',
      }).then(res => { //添加成功显示提示
        let status = res.data.code
        if (status === 200) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
        }
        this.getWorkInfo()
      })
    },
    showQuestion(paperId) {
      this.questionData = []//显示已有题目
      this.$axios(`/api/paper/${paperId}`).then(res => { //根据作业id请求后台
        let data = res.data
        let keys = Object.keys(data) //对象转数组
        keys.forEach(e => {
          data[e].forEach(ele => {
            this.$set(ele, 'questionType', e)
            this.$set(ele, 'paperId', paperId)
            if (ele.question.length > 15) {
              ele.question = ele.question.slice(0, 15) + '...'
            }
            this.questionData.push(ele)
          })
        })
        console.log(this.questionData)
      })
    },
    quitQuestion() {
      this.questionData = []
    },
    check(workCode) { //批阅试卷
      this.checkDialogVisible = true
      this.$axios(`/api/unMarked/${workCode}`).then(res => { //根据作业id请求后台
        if (res.data.code === 200) {
          this.studentData = res.data.data
        }
      })
    },
    edit(workCode) { //编辑试卷
      this.dialogVisible = true
      this.$axios(`/api/work/${workCode}`).then(res => { //根据试卷id请求后台
        if (res.data.code == 200) {
          this.form = res.data.data
        }
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {
      });
    },
    submit() { //提交修改后的试卷信息
      this.dialogVisible = false
      this.$axios({
        url: '/api/exam',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if (res.data.code === 200) {
          this.$message({ //成功修改提示
            message: '更新成功',
            type: 'success'
          })
        }
        this.getWorkInfo()
      })
    },
    deleteRecord(workCode) {
      this.$confirm("确定永久删除该记录吗", "提示", {
        confirmButtonText: '确定',
        cancelButtonText: '算了',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/work/${workCode}`,
          method: 'delete',
        }).then(res => {
          this.getWorkInfo()
        })
      }).catch(() => {

      })
    },
    deleteQuestionFrPaper(row) {
      let paperId = row.paperId
      this.$axios({
        url: '/api/paperManage',
        method: 'Delete',
        data: {
          paperId: paperId,
          questionType: row.questionType,
          questionId: row.questionId
        }
      }).then(res=>{
        this.$message({
          message: '已删除',
          type: 'success'
        })
        this.showQuestion(paperId)
      })
    },
    getWorkInfo() { //分页查询所有试卷信息
      this.$axios(`/api/works/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data
        console.log(res.data.data)
      }).catch(error => {
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val
      this.getWorkInfo()
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getWorkInfo()
    },
    toMarking(val) {
      this.$router.push({
        path: "/work/marking",
        query: {workCode: val.workCode, studentId: val.studentId, scoreId: val.scoreId}
      })
    }
  },
};
</script>
<style lang="scss" scoped>
.exam {
  padding: 0 40px;

  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .edit {
    margin-left: 20px;
  }
}
</style>
