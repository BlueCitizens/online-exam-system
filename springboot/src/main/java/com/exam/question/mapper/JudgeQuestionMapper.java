package com.exam.question.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.question.entity.JudgeQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//判断题

@Mapper
public interface JudgeQuestionMapper {

    @Select("select * from judge_question where subject = #{subject} and section = #{section}")
    List<JudgeQuestion> findBySubAndSec(String subject, String section);

    @Select("select * from judge_question where questionId in (select questionId from paper_manage where questionType = 3 and paperId = #{paperId})")
    List<JudgeQuestion> findByIdAndType(Integer paperId);

    @Select("SELECT `as`.*, jq.question, jq.analysis, jq.answer AS rightAnswer, jq.score AS fullScore FROM answer_sheet `as` LEFT JOIN judge_question jq ON jq.questionId = `as`.questionId WHERE `as`.questionType = 1 AND `as`.paperId = #{paperId} AND `as`.studentId = #{studentId};")
    List<Answer> findByIdAndTypeAndStudent(Integer paperId, Integer studentId);

    @Select("select * from judge_question")
    IPage<JudgeQuestion> findAll(Page page);

    /**
     * 查询最后一条记录的questionId
     * @return JudgeQuestion
     */
    @Select("select questionId from judge_question order by questionId desc limit 1")
    JudgeQuestion findOnlyQuestionId();

    @Insert("insert into judge_question(subject,question,answer,analysis,level,section,score) values " +
            "(#{subject},#{question},#{answer},#{analysis},#{level},#{section},#{score})")
    int add(JudgeQuestion judgeQuestion);

    @Select("select questionId from judge_question  where subject=#{subject}  order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(String subject,Integer pageNo);
}
