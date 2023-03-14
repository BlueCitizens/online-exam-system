<!--考生答题界面-->
<template>
  <div id="answer">
    <!--顶部信息栏-->
    <div class="top">
      <ul class="item">
        <li><i :class="slider_flag?'el-icon-s-fold':'el-icon-s-unfold'" ref="toggle"
               @click="slider_flag = !slider_flag"></i></li>
        <li>{{ workData.source }}</li>
        <li>
          <el-popover
            v-if="isShow"
            width="200"
            trigger="hover">
            <div class="msg">
              <el-row>
                <el-col :span="16"><p>姓名：{{ topic[0].studentName }}</p>
                  <p>学号: {{ topic[0].studentId }}</p></el-col>
                <el-col :span="8">
                  <el-button @click="exit">结束</el-button>
                </el-col>
              </el-row>
            </div>
            <el-avatar slot="reference" size="small" shape="square"> {{
                topic[0].studentName.substring(0, 1)
              }}
            </el-avatar>
          </el-popover>
        </li>
      </ul>
    </div>
    <div class="flexarea">
      <!--左边题目编号区-->
      <transition name="slider-fade">
        <div class="left" v-if="slider_flag">
          <ul class="l-top">
            <li>
              <el-badge :is-dot="false" class="item" type="success">
                <el-button size="mini" :loading="true" type="" circle></el-button>
              </el-badge>
              <span>当前</span>
            </li>
            <li>
              <el-badge :is-dot="true" class="item" type="success">
                <el-button size="" :loading="false" type="" circle></el-button>
              </el-badge>
              <span>标记</span>
            </li>
          </ul>
          <div class="l-bottom">
            <div class="item">
              <p>简答题部分</p>
              <ul>
                <li v-for="(list, index1) in topic" :key="index1">
                  <el-badge :is-dot="topic[index1].isMark === true" class="item" type="success">
                    <el-button @click="shortAnswer(parseInt(index1))" :loading="index === parseInt(index1)" circle>
                      {{
                        index === parseInt(index1) ? '' : parseInt(index1) + 1
                      }}
                    </el-button>
                  </el-badge>
                </li>
              </ul>
            </div>
            <div class="final">
              <el-button @click="commit()" type="primary" plain>提交成绩</el-button>
            </div>
          </div>
        </div>
      </transition>
      <!--右边选择答题区-->
      <transition name="slider-fade">
        <div class="right">
          <div class="title">
            <p>{{ title }}</p>
          </div>
          <div class="content">
            <p class="topic">
              <el-tag size="mini">{{ number }}</el-tag>&nbsp;{{ showQuestion }}
            </p>
            <!--            简答题-->
            <div class="judge">
              <el-input type="textarea" placeholder="未作答"
                        v-model="shortAnswerAnswer[index][0]"
                        disabled>
              </el-input>
              <div class="analysis" v-if="isShow">
                <ul>
                  <li>
                    <el-tag type="success">正确答案：{{
                        topic[index].rightAnswer == null ? '暂无答案' : topic[index].rightAnswer
                      }}
                    </el-tag>
                  </li>
                  <li>
                    <el-tag>题目解析：{{ topic[index].analysis == null ? '暂无解析' : topic[index].analysis }}</el-tag>
                  </li>
                </ul>
                <div>
                  <el-tag type="warning">得分</el-tag>
                  <el-input-number v-model="shortAnswerAnswer[index][1]" :precision="1" :step="0.5" :min="0"
                                   :max="topic[index].fullScore" controls-position="right">
                  </el-input-number>
                </div>
              </div>
            </div>
          </div>
          <div class="operation">
            <ul style="margin-left: 10px">
              <el-progress v-if="isShow" :percentage="((parseInt(index)+1)/topicCount[0])*100"
                           :format="format"></el-progress>
            </ul>
            <ul class="end">
              <el-button-group>
                <el-button @click="previous()" type="primary" icon="el-icon-arrow-left">上一题</el-button>
                <el-button @click="mark()" type="primary" icon="el-icon-collection-tag">标记</el-button>
                <el-button @click="next()" type="primary">下一题<i class="el-icon-arrow-right el-icon--right"></i>
                </el-button>
              </el-button-group>
            </ul>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import store from '@/vuex/store'
import {mapState} from 'vuex'

export default {
  store,
  data() {
    return {
      scoreId: null,
      isShow: false,
      startTime: null, //考试开始时间
      endTime: null, //考试结束时间
      time: null, //考试持续时间
      reduceAnswer: [],  //vue官方不支持3层以上数据嵌套,如嵌套则会数据渲染出现问题,此变量直接接收3层嵌套时的数据。
      answerScore: 0, //答题总分数
      bg_flag: false, //已答标识符,已答改变背景色
      isFillClick: false, //选择题是否点击标识符
      slider_flag: true, //左侧显示隐藏标识符
      flag: false, //个人信息显示隐藏标识符
      currentType: 1, //当前题型类型  1--选择题  2--填空题  3--判断题  4--简答题
      radio: [], //保存考生所有选择题的选项
      title: "请选择正确的选项",
      index: 0, //全局index
      userInfo: { //用户信息
        name: null,
        id: null
      },
      topicCount: [],//每种类型题目的总数
      score: [],  //每种类型分数的总数
      workData: { //考试信息
        // source: null,
        // totalScore: null,
      },
      topic: {  //试卷信息

      },
      showQuestion: [], //当前显示题目信息
      showAnswer: {}, //当前题目对应的答案选项
      number: 1, //题号
      part: null, //填空题的空格数量
      fillAnswer: [[]], //二维数组保存所有填空题答案
      judgeAnswer: [], //保存所有判断题答案
      topic1Answer: [],  //学生选择题作答编号,
      shortAnswerAnswer: [[]],
    }
  },
  created() {
    this.getCookies()
    this.getMarkingData()
  },
  methods: {
    format(percentage) {
      console.log(percentage)
      return ``;
    },
    getTime(date) { //日期格式化
      let year = date.getFullYear()
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    getCookies() {  //获取cookie
      this.userInfo.name = this.$cookies.get("cname")
      this.userInfo.id = this.$cookies.get("cid")
    },
    calcuScore() { //计算答题分数

    },
    getMarkingData() { //获取当前试卷所有信息
      let date = new Date()
      this.startTime = this.getTime(date)
      let workCode = this.$route.query.workCode //获取路由传递过来的作业编号
      let studentId = this.$route.query.studentId //获取路由传递过来的学号
       //获取路由传递过来的学号
      this.scoreId = this.$route.query.scoreId
      console.log(this.scoreId)
      console.log(workCode)
      this.$axios(`/api/work/${workCode}`).then(res => {  //通过workCode请求试卷详细信息
        this.workData = {...res.data.data} //获取考试详情
        this.index = 0
        this.time = this.workData.totalScore //获取分钟数
        let paperId = this.workData.paperId
        this.$axios(`/api/work/${paperId}/${studentId}`).then(res => {  //通过paperId获取试题题目信息
          this.topic = {...res.data.data}
          let fatherSaq = []//清空father数组，给简答题预分配数组
          this.shortAnswerAnswer = fatherSaq
          let keys = Object.keys(this.topic) //对象转数组
          console.log(keys)
          this.topicCount.push(keys.length)
          console.log(this.topicCount)
          let currentScore = 0
          keys.forEach(e => {
            let data = this.topic[e]
            currentScore += data.fullScore
            let children = new Array(2)
            fatherSaq.push(children)
            fatherSaq[e][0] = data.answer
            fatherSaq[e][1] = data.fullScore
          })
          this.score.push(currentScore)
          console.log(this.score)
          let dataInit = this.topic[0]
          this.number = 1
          this.showQuestion = dataInit.question
          this.showAnswer = dataInit
          this.isShow = true;
        })
      })
    },
    shortAnswer(index) { //填空题
      let len = this.topicCount[0]
      this.index = index
      if (index < len) {
        if (this.index <= 0) {
          this.index = 0
        }
        console.log(`总长度${len}`)
        console.log(`当前index:${index}`)
        this.title = "请选择正确的选项"
        let Data = this.topic
        // console.log(Data)
        this.showQuestion = Data[this.index].question //获取题目信息
        this.showAnswer = Data[this.index]
        this.number = this.index - (-1)
      } else if (index >= len) {
        this.index--
        console.log(`总长度${len}`)
        console.log(`当前index:${index}`)
      }
      this.$nextTick(() => {
        this.$forceUpdate()
      })
    },
    previous() { //上一题
      this.index--
      this.shortAnswer(this.index)
    },
    next() { //下一题
      this.index++
      this.shortAnswer(this.index)
    },
    mark() { //标记功能
      this.topic[this.index]["isMark"] = this.topic[this.index]["isMark"] !== true //简答题标记
      this.$nextTick(() => {
        this.$forceUpdate()
      })
    },
    commit() { //答案提交计算分数
      if (this.time !== 0) {
        this.$confirm("是否确认提交", "提示", {
          confirmButtonText: '立即提交',
          cancelButtonText: '再等一下',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '提交中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.3)'
          })
          /* 计算选择题总分 */
          let finalScore = 0
          /** 计算判断题总分 */
          let topicAnswer = this.shortAnswerAnswer
          topicAnswer.forEach((element, index) => {
            finalScore += element[1]
            //每条分数更新
            let id = this.topic[index].id
            let score = element[1]
            console.log(id + ' ' + score)
            this.$axios({
              url: `/api/score/${score}/id/${id}`,
              method: 'patch',
            })
          })
          console.log(`目前总分${finalScore}`)
          let date = new Date()
          this.endTime = this.getTime(date)
          let answerDate = this.endTime.substr(0, 10)
          //提交成绩信息
          let scoreId = this.scoreId
          this.$axios({
            url: `/api/totalScore/${finalScore}/id/${scoreId}`,
            method: 'patch',
          }).then(res => {
            if (res.data.code === 200) {
              setTimeout(() => {
                loading.close();
              }, 200);
              this.$router.push({
                path: '/selectWork'
              })
            }
          })
        }).catch(() => {
          console.log("继续答题")
        })
      }
    },
    exit() {
      this.$router.push({
        path: '/selectWork'
      })
    }
  },
  computed: mapState(["isPractice"])
}
</script>

<style lang="scss">
.iconfont.icon-time {
  color: #2776df;
  margin: 0px 6px 0px 20px;
}

.analysis {
  margin-top: 20px;

  .right {
    color: #2776df;
    font-size: 18px;
    border: 1px solid #2776df;
    padding: 0px 6px;
    border-radius: 4px;
    margin-left: 20px;
  }

  ul li:nth-child(2) {
    margin: 20px 0px;
  }

  ul li:nth-child(3) {
    padding: 10px;
    background-color: #d3c6c9;
    border-radius: 4px;
  }
}

.analysis span:nth-child(1) {
  font-size: 18px;
}

.mark {
  position: absolute;
  width: 4px;
  height: 4px;
  content: "";
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 22px;
}

.border {
  position: relative;
  border: 1px solid #FF90AA !important;
}

.bg {
  background-color: #5188b8 !important;
}

.fill .el-input {
  display: inline-flex;
  width: 150px;
  margin-left: 20px;

  .el-input__inner {
    border: 1px solid transparent;
    border-bottom: 1px solid #eee;
    padding-left: 20px;
  }
}

/* slider过渡效果 */
.slider-fade-enter-active {
  transition: all .3s ease;
}

.slider-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slider-fade-enter, .slider-fade-leave-to {
  transform: translateX(-100px);
  opacity: 0;
}

.operation .end li:nth-child(2) {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(39, 118, 223);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  color: #fff;
}

.operation .end li {
  cursor: pointer;
  margin: 0 100px;
}

.operation {
  background-color: #fff;
  border-radius: 4px;
  padding: 10px 0px;
  margin-right: 10px;
}

.operation .end {
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(39, 118, 223);
}

.content .number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  background-color: rgb(39, 118, 223);
  border-radius: 4px;
  margin-right: 4px;
}

.content {
  padding: 0px 20px;
}

.content .topic {
  padding: 30px 0px 20px;
}

.right .content {
  background-color: #fff;
  margin: 10px;
  margin-left: 0px;
  height: 470px;
}

.content .el-radio-group label {
  color: #000;
  margin: 5px 0px;
}

.content .el-radio-group {
  display: flex;
  flex-direction: column;
}

.right .title p {
  margin-left: 20px;
}

.flexarea {
  display: flex;
}

.flexarea .right {
  flex: 1;
}

.auto-right {
  margin-left: auto;
  color: #2776df;
  margin-right: 10px;
}

.right .title {
  margin-right: 10px;
  padding-right: 30px;
  display: flex;
  margin-top: 10px;
  background-color: #fff;
  height: 50px;
  line-height: 50px;
}

.clearfix {
  clear: both;
}

.l-bottom .final {
  cursor: pointer;
  display: inline-block;
  text-align: center;
  width: 240px;
  margin: 22px 0 20px 10px;
}

#answer .left .item {
  padding: 0px;
  font-size: 16px;
}

.l-bottom {
  border-radius: 4px;
  background-color: #fff;
}

.l-bottom .item p {
  margin-bottom: 15px;
  margin-top: 10px;
  color: #000;
  margin-left: 10px;
  letter-spacing: 2px;
}

.l-bottom .item li {
  width: 15%;
  margin-left: 5px;
  margin-bottom: 10px;
}

.l-bottom .item {
  display: flex;
  flex-direction: column;
}

.l-bottom .item ul {
  width: 100%;
  margin-bottom: -8px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.l-bottom .item ul li a {
  position: relative;
  justify-content: center;
  display: inline-flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #eee;
  text-align: center;
  color: #000;
  font-size: 16px;
}

.left .l-top {
  display: flex;
  justify-content: space-around;
  padding: 16px 0px;
  border: 1px solid #eee;
  border-radius: 4px;
  margin-bottom: 10px;
  background-color: #fff;
}

.left {
  width: 260px;
  height: 100%;
  margin: 10px 10px 0px 10px;
}

.left .l-top li:nth-child(2) a {
  border: 1px solid #eee;
}

.left .l-top li:nth-child(3) a {
  background-color: #5188b8;
  border: none;
}

.left .l-top li:nth-child(4) a {
  position: relative;
  border: 1px solid #eee;
}

.left .l-top li:nth-child(4) a::before {
  width: 4px;
  height: 4px;
  content: " ";
  position: absolute;
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 16px;
}

.left .l-top li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.left .l-top li a {
  display: inline-block;
  padding: 10px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #FF90AA;
}

#answer .top {
  background-color: rgba(39, 118, 223, 0.84);
}

#answer .item {
  color: #fff;
  display: flex;
  padding: 20px;
  font-size: 20px;
}

#answer .top .item li:nth-child(1) {
  margin-right: 10px;
}

#answer .top .item li:nth-child(3) {
  position: relative;
  margin-left: auto;
}

#answer {
  padding-bottom: 30px;
}

.icon20 {
  font-size: 20px;
  font-weight: bold;
}

.item .msg {
  padding: 10px 15px;
  border-radius: 4px;
  top: 47px;
  right: -30px;
  color: #6c757d;
  position: absolute;
  border: 1px solid rgba(0, 0, 0, .15);
  background-color: #fff;
}

.item .msg p {
  font-size: 16px;
  width: 200px;
  text-align: left;
}
</style>
