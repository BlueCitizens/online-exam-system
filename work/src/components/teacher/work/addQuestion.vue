<!--添加题库-->
<template>
  <div class="add">
    <el-tabs v-model="activeName">
      <el-tab-pane name="first">
        <span slot="label">手动添加</span>
        <section class="append">
          <el-row :gutter="20">
            <el-col :span="4">
              <el-select v-model="optionValue" placeholder="请选择题型">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="6" v-if="optionValue === '1'">
              <el-autocomplete
                style="width: 100%"
                v-model="postChange.section"
                :fetch-suggestions="querySearch"
                placeholder="请输入对应章节">
                <template slot-scope="{ item }">
                  {{ item.value }}
                  <span style="font-size: 10px;color: #9a9a9a">{{ item.label }}</span>
                </template>
              </el-autocomplete>
            </el-col>
            <el-col :span="6" v-if="optionValue === '2'">
              <el-autocomplete
                style="width: 100%"
                v-model="postFill.section"
                :fetch-suggestions="querySearch"
                placeholder="请输入对应章节">
                <template slot-scope="{ item }">
                  {{ item.value }}
                  <span style="font-size: 10px;color: #9a9a9a">{{ item.label }}</span>
                </template>
              </el-autocomplete>
            </el-col>
            <el-col :span="6" v-if="optionValue === '3'">
              <el-autocomplete
                style="width: 100%"
                v-model="postJudge.section"
                :fetch-suggestions="querySearch"
                placeholder="请输入对应章节">
                <template slot-scope="{ item }">
                  {{ item.value }}
                  <span style="font-size: 10px;color: #9a9a9a">{{ item.label }}</span>
                </template>
              </el-autocomplete>
            </el-col>
            <el-col :span="6" v-if="optionValue === '4'">
              <el-autocomplete
                style="width: 100%"
                v-model="postShortAnswer.section"
                :fetch-suggestions="querySearch"
                placeholder="请输入对应章节">
                <template slot-scope="{ item }">
                  {{ item.value }}
                  <span style="font-size: 10px;color: #9a9a9a">{{ item.label }}</span>
                </template>
              </el-autocomplete>
            </el-col>
            <el-col :span="2" v-if="optionValue === '1'">
              <el-tooltip content="选择难度等级" placement="top">
                <el-select v-model="postChange.level">
                  <el-option
                    v-for="item in levels"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-tooltip>
            </el-col>
            <el-col :span="4" v-if="optionValue === '2'">
              <el-select v-model="postFill.level" placeholder="选择难度等级">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4" v-if="optionValue === '3'">
              <el-select v-model="postJudge.level" placeholder="选择难度等级">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4" v-if="optionValue === '4'">
              <el-select v-model="postShortAnswer.level" placeholder="选择难度等级">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4" v-if="optionValue === '1'">
              <el-tooltip content="选择分值" placement="top">
                <el-input-number style="margin-top: 4px" size="small" v-model="postChange.score"></el-input-number>
              </el-tooltip>
            </el-col>
            <el-col :span="4" v-if="optionValue === '2'">
              <el-tooltip content="选择分值" placement="top">
                <el-input-number style="margin-top: 4px" size="small" v-model="postFill.score"></el-input-number>
              </el-tooltip>
            </el-col>
            <el-col :span="4" v-if="optionValue === '3'">
              <el-tooltip content="选择分值" placement="top">
                <el-input-number style="margin-top: 4px" size="small" v-model="postJudge.score"></el-input-number>
              </el-tooltip>
            </el-col>
            <el-col :span="4" v-if="optionValue === '4'">
              <el-tooltip content="选择分值" placement="top">
                <el-input-number style="margin-top: 4px" size="small" v-model="postShortAnswer.score"></el-input-number>
              </el-tooltip>
            </el-col>
            <el-col :span="8" v-if="optionValue === '1'">
              <el-select @change="testMulti" v-model="postChange.rightAnswer" multiple placeholder="选择正确答案"
                         style="width: 100%">
                <el-option
                  v-for="item in rights"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <!-- 选择题部分 -->
          <div class="change" v-if="optionValue === '1'">
            <div class="title">
              <el-input
                type="textarea"
                rows="2"
                v-model="postChange.question"
                placeholder="请输入题目内容,如:衬衫的价格是()"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="options">
              <ul>
                <li>
                  <el-tag type="success">A</el-tag>
                  <el-input
                    placeholder="选项A"
                    v-model="postChange.answerA"
                    clearable="">
                  </el-input>
                </li>
                <li>
                  <el-tag type="success">B</el-tag>
                  <el-input
                    placeholder="选项B"
                    v-model="postChange.answerB"
                    clearable="">
                  </el-input>
                </li>
                <li>
                  <el-tag type="success">C</el-tag>
                  <el-input
                    placeholder="选项C"
                    v-model="postChange.answerC"
                    clearable="">
                  </el-input>
                </li>
                <li>
                  <el-tag type="success">D</el-tag>
                  <el-input
                    placeholder="选项D"
                    v-model="postChange.answerD"
                    clearable="">
                  </el-input>
                </li>
              </ul>
            </div>
            <div class="title">
              <el-input
                type="textarea"
                rows="2"
                v-model="postChange.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="changeSubmit()">立即添加，同时添加到题库</el-button>
            </div>
          </div>
          <!-- 填空题部分 -->
          <div class="change fill" v-if="optionValue === '2'">
            <div class="title">
              <el-input
                type="textarea"
                rows="2"
                v-model="postFill.question"
                placeholder="请输入题目内容"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="fillAnswer">
              <el-tag>正确答案:</el-tag>
              <el-input v-model="postFill.answer"></el-input>
            </div>
            <div class="title analysis">
              <el-tag type="danger">缺空使用()英文半角括号标记，答案使用^标记，注意每一空的答案后都要有该标记。如：字母分为()和()字母。 答案：大写^小写^</el-tag>
              <el-input
                type="textarea"
                rows="4"
                v-model="postFill.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="fillSubmit()">立即添加，同时添加到题库</el-button>
            </div>
          </div>
          <!-- 判断题 -->
          <div class="change judge" v-if="optionValue === '3'">
            <div class="title">
              <el-input
                type="textarea"
                rows="2"
                v-model="postJudge.question"
                placeholder="请输入题目内容"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="judgeAnswer">
              <el-radio v-model="postJudge.answer" label="T">正确</el-radio>
              <el-radio v-model="postJudge.answer" label="F">错误</el-radio>
            </div>
            <div class="title">
              <el-input
                type="textarea"
                rows="3"
                v-model="postJudge.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="judgeSubmit()">立即添加，同时添加到题库</el-button>
            </div>
          </div>
          <div class="change fill" v-if="optionValue === '4'">
            <div class="title">
              <el-input
                type="textarea"
                rows="2"
                v-model="postShortAnswer.question"
                placeholder="请输入题目内容"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="fillAnswer">
              <el-tag>参考答案:</el-tag>
              <el-input v-model="postShortAnswer.answer"></el-input>
            </div>
            <div class="title analysis">
              <el-input
                type="textarea"
                rows="4"
                v-model="postShortAnswer.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="shortAnswerSubmit()">立即添加，同时添加到题库</el-button>
            </div>
          </div>
        </section>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label">题库选择</span>
        <div class="block">
          <el-cascader
            style="width: 100%"
            v-model="dbValue"
            :options="dbOptions"
            @change="handleChange"></el-cascader>
        </div>
        <el-table
          ref="filterTable"
          style="width: 100%"
          :data="dbQuestions"
          max-height="450">
          <el-table-column
            prop="questionId"
            label="序号"
            width="180">
          </el-table-column>
          <el-table-column
            label="地址">
            <template slot-scope="scope">
              {{ scope.row.question.length > 15 ? scope.row.question.slice(0, 15) : scope.row.question }}
            </template>
          </el-table-column>
          <el-table-column
            prop="level"
            label="难度"
            sortable
            width="180"
            :filters="[{text: '1', value: '1'}, {text: '2', value: '2'}, {text: '3', value: '3'}, {text: '4', value: '4'}, {text: '5', value: '5'}]"
            :filter-method="filterHandler">
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button v-if="scope.row.isExist === 1" size="mini" disabled>添加</el-button>
              <el-button :loading="scope.row.loading" v-else size="mini" @click="addFrDb2Paper(scope.row)">添加
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane name="third">
        <span slot="label">自动组卷</span>
        <div class="box">
          <ul>
            <li>
              <span>试题难度:</span>
              <el-select v-model="difficultyValue" placeholder="试题难度">
                <el-option
                  v-for="item in difficulty"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </li>
            <li>
              <span>选择题数量：</span>
              <el-input type="text" v-model="changeNumber"></el-input>
            </li>
            <li>
              <span>填空题数量：</span>
              <el-input type="text" v-model="fillNumber"></el-input>
            </li>
            <li>
              <span>判断题数量：</span>
              <el-input type="text" v-model="judgeNumber"></el-input>
            </li>
            <li>
              <el-button type="primary" @click="create()">立即组卷</el-button>
            </li>
          </ul>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  props: {
    paperId: Number,
    subject: String,
    questionData: Array,
    required: true
  },
  data() {
    return {
      dbQuestions: [],
      dbValue: [],
      dbOptions: [],
      sections: [],
      changeNumber: null, //选择题出题数量
      fillNumber: null, //填空题出题数量
      judgeNumber: null, //判断题出题数量
      activeName: 'first',  //活动选项卡
      options: [ //题库类型
        {
          value: '1',
          label: '选择题'
        },
        {
          value: '2',
          label: '填空题'
        },
        {
          value: '3',
          label: '判断题'
        },
        {
          value: '4',
          label: '简答题'
        },
      ],
      difficulty: [ //试题难度
        {
          value: '简单',
          label: '简单'
        },
        {
          value: '一般',
          label: '一般'
        },
        {
          value: '困难',
          label: '困难'
        }
      ],
      difficultyValue: '简单',
      levels: [ //难度等级
        {
          value: '1',
          label: '1'
        },
        {
          value: '2',
          label: '2'
        },
        {
          value: '3',
          label: '3'
        },
        {
          value: '4',
          label: '4'
        },
        {
          value: '5',
          label: '5'
        },
      ],
      rights: [ //正确答案
        {
          value: 'A',
          label: 'A'
        },
        {
          value: 'B',
          label: 'B'
        },
        {
          value: 'C',
          label: 'C'
        },
        {
          value: 'D',
          label: 'D'
        },
      ],
      optionValue: '1', //题型选中值
      postChange: { //选择题提交内容
        subject: '', //试卷名称
        level: '1', //难度等级选中值
        rightAnswer: '', //正确答案选中值
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
        answerA: '',
        answerB: '',
        answerC: '',
        answerD: '',
        isMulti: 0,
        score: 2,
      },
      postFill: { //填空题提交内容
        subject: '', //试卷名称
        level: '1', //难度等级选中值
        answer: '', //正确答案
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
        score: 2,
      },
      postJudge: { //判断题提交内容
        subject: '', //试卷名称
        level: '1', //难度等级选中值
        answer: '', //正确答案
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
        score: 2,
      },
      postShortAnswer: { //填空题提交内容
        subject: '', //试卷名称
        level: '1', //难度等级选中值
        answer: '', //正确答案
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
        score: 5,
      },
      postPaper: { //考试管理表对应字段
        paperId: null,
        questionType: null, // 试卷类型 1--选择题  2--填空题   3--判断题
        questionId: null,
      }
    };
  },
  created() {
    this.getParams()
    this.initCascader()
  },
  methods: {
    // handleClick(tab, event) {
    //   console.log(tab, event);
    // },
    testMulti() {
      console.log(this.postChange.rightAnswer, this.postChange.rightAnswer.length)
    },
    initCascader() {
      this.$axios({
        url: '/api/menu/question',
        method: 'get',
      }).then(res => {
        this.dbOptions = JSON.parse(JSON.stringify([...res.data.data]))
        this.sections = JSON.parse(JSON.stringify([...res.data.data]))
      })
    },
    handleChange() {
      this.$axios({
        url: `/api/db/question?type=${this.dbValue[0]}&subject=${this.dbValue[1]}&section=${this.dbValue[2] !== null ? this.dbValue[2] : ""}`,
        method: 'get',
      }).then(res => {
        this.dbQuestions = [...res.data]
        this.dbQuestions.forEach((e, index) => {
          this.$set(e, 'isExist', 0)
          this.$set(e, 'loading', false)
          this.questionData.forEach(ele => {
            if (e.questionId === ele.questionId && ele.questionType === this.dbValue[0]) {
              console.log("is looped?")
              this.$set(e, 'isExist', 1)
            }
          })
        })
      })
    },
    /**
     * 输入章节的搜索建议
     * @param queryString
     * @param cb
     */
    querySearch(queryString, cb) {
      let options = this.sections //用级联选择器的选项数组即可
      let results = []
      options.forEach(e => {  //遍历第一级
        e.children.forEach(ele => { //遍历第二级
          let result = queryString ? ele.children.filter(this.createFilter(queryString)) : ele.children;
          result.forEach(el => {  //将第二级的每一个元素的label置为父元素的值(章节/课程) 不影响原来的选项数组
            el.label = ele.label
            results.push(el)
          })
        })
      })
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (option) => {
        if (option.value !== null) {  //防空值
          return (option.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        }
      };
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    create() {
      this.$axios({
        url: '/api/item',
        method: 'post',
        data: {
          changeNumber: this.changeNumber,
          fillNumber: this.fillNumber,
          judgeNumber: this.judgeNumber,
          paperId: this.paperId,
          subject: '计算机网络' //题目数量太少，指定为计算机网络出题
        }
      }).then(res => {
        console.log(res)
        let data = res.data
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({path: '/selectAnswer'})
          }, 1000)
          this.$message({
            message: data.message,
            type: 'success'
          })
        } else if (data.code === 400) {
          this.$message({
            message: data.message,
            type: 'error'
          })
        }

      })
    },
    getParams() {
      //获取paperId
      console.log(this.paperId)
      this.postPaper.paperId = this.paperId
    },
    changeSubmit() { //选择题题库提交
      this.postChange.subject = this.subject
      if (this.postChange.rightAnswer.length > 1) {
        this.postChange.isMulti = 1
      }
      this.postChange.rightAnswer = this.postChange.rightAnswer.join(',')
      this.$axios({ //提交数据到选择题题库表
        url: '/api/MultiQuestion',
        method: 'post',
        data: {
          ...this.postChange
        }
      }).then(res => { //添加成功显示提示
        let status = res.data.code
        console.log(res.data.data)
        if (status === 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postPaper.questionId = res.data.data
          this.postPaper.questionType = 1
          this.$axios({
            url: '/api/paperManage',
            method: 'Post',
            data: {
              ...this.postPaper
            }
          })
          this.$emit('showQuestion', this.paperId)
          this.initCascader()
          this.postChange = {}
        }
      })
      /*        .then(() => {
              this.$axios(`/api/multiQuestionId`).then(res => { //获取当前题目的questionId
                let questionId = res.data.data.questionId
                this.postPaper.questionId = questionId
                this.postPaper.questionType = 1
                this.$axios({
                  url: '/api/paperManage',
                  method: 'Post',
                  data: {
                    ...this.postPaper
                  }
                })
              })
            })*/
    },
    fillSubmit() { //填空题提交
      this.postFill.subject = this.subject
      this.$axios({
        url: '/api/fillQuestion',
        method: 'post',
        data: {
          ...this.postFill
        }
      }).then(res => {
        let status = res.data.code
        if (status == 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postFill = {}
        }
      }).then(() => {
        this.$axios(`/api/fillQuestionId`).then(res => { //获取当前题目的questionId
          let questionId = res.data.data.questionId
          this.postPaper.questionId = questionId
          this.postPaper.questionType = 2
          this.$axios({
            url: '/api/paperManage',
            method: 'Post',
            data: {
              ...this.postPaper
            }
          })
        })
        this.$emit('showQuestion', this.paperId)
        this.initCascader()
      })
    },
    judgeSubmit() { //判断题提交
      this.postJudge.subject = this.subject
      this.$axios({
        url: '/api/judgeQuestion',
        method: 'post',
        data: {
          ...this.postJudge
        }
      }).then(res => {
        let status = res.data.code
        if (status == 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postJudge = {}
        }
      }).then(() => {
        this.$axios(`/api/judgeQuestionId`).then(res => { //获取当前题目的questionId
          let questionId = res.data.data.questionId
          this.postPaper.questionId = questionId
          this.postPaper.questionType = 3
          this.$axios({
            url: '/api/paperManage',
            method: 'Post',
            data: {
              ...this.postPaper
            }
          })
        })
        this.$emit('showQuestion', this.paperId)
        this.initCascader()
      })
    },
    shortAnswerSubmit() { //填空题提交
      this.postShortAnswer.subject = this.subject
      this.$axios({
        url: '/api/shortAnswerQuestion',
        method: 'post',
        data: {
          ...this.postShortAnswer
        }
      }).then(res => {
        let status = res.data.code
        if (status == 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postShortAnswer = {}
        }
      }).then(() => {
        this.$axios(`/api/shortAnswerQuestionId`).then(res => { //获取当前题目的questionId
          let questionId = res.data.data.questionId
          this.postPaper.questionId = questionId
          this.postPaper.questionType = 4
          this.$axios({
            url: '/api/paperManage',
            method: 'Post',
            data: {
              ...this.postPaper
            }
          })
        })
        this.$emit('showQuestion', this.paperId)
        this.initCascader()
      })
    },
    refreshFatherQuestionData() {
      return this.$emit('showQuestion', this.paperId)
    },
    addFrDb2Paper(row) {
      row.loading = true
      let flag = false
      this.questionData.some(e => {
        if (e.questionId === row.questionId && row.questionType === this.dbValue[0]) {
          flag = true
          return true
        }
      })
      if (flag === false) {
        this.postPaper.questionId = row.questionId
        this.postPaper.questionType = this.dbValue[0]
        this.$axios({
          url: '/api/paperManage',
          method: 'Post',
          data: {
            ...this.postPaper
          }
        }).then(res => {
          this.$message({
            message: '已添加',
            type: 'success'
          })
          this.refreshFatherQuestionData()

          setTimeout(() => {
            this.handleChange()
            row.loading = false
          }, 500);

        })
      } else {
        row.loading = false
        this.$message({
          message: '已存在',
          type: 'warning'
        })
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.add {
  margin: 0px 40px;

  .box {
    padding: 0px 20px;

    ul li {
      margin: 10px 0px;
      display: flex;
      align-items: center;

      .el-input {
        width: 6%;
      }
    }
  }

  .el-icon-circle-plus {
    margin-right: 10px;
  }

  .icon-daoru-tianchong {
    margin-right: 10px;
  }

  .append {
    margin: 0px 20px;

    ul {
      display: flex;
      align-items: center;

      li {
        margin-right: 20px;
      }
    }

    .change {
      margin-top: 20px;
      padding: 20px 16px;
      background-color: #E7F6F6;
      border-radius: 4px;

      .title {
        padding-left: 6px;
        color: #2f4f4f;

        span:nth-child(1) {
          margin-right: 6px;
        }

        .answer {
          margin: 20px 0px 20px 8px;
        }

        .el-textarea {
          width: 98% !important;
        }
      }

      .options {
        ul {
          display: flex;
          flex-direction: column;
        }

        ul li {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 98%;
          margin: 10px 0px;

          span {
            margin-right: 20px;
          }
        }
      }

      .submit {
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }

    .fill {
      .fillAnswer {
        display: flex;
        justify-content: center;
        align-items: center;

        span {
          margin-right: 6px;
        }

        .el-input {
          width: 91% !important;
        }
      }

      .analysis {
        margin-top: 20px;
        margin-left: 5px;
      }
    }

    .judge {
      .judgeAnswer {
        margin-left: 20px;
        margin-bottom: 20px;
      }
    }

    li:nth-child(2) {
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}
</style>


