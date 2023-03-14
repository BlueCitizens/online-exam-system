package com.exam.question.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.question.entity.FillQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//填空题
@Mapper
public interface FillQuestionMapper {

    @Select("select * from fill_question where subject = #{subject} and section = #{section}")
    List<FillQuestion> findBySubAndSec(String subject, String section);

    @Select("select * from fill_question where questionId in (select questionId from paper_manage where questionType = 2 and paperId = #{paperId})")
    List<FillQuestion> findByIdAndType(Integer paperId);


    @Select("SELECT `as`.*, fq.question, fq.analysis, fq.answer AS rightAnswer, fq.score AS fullScore FROM answer_sheet `as` LEFT JOIN fill_question fq ON fq.questionId = `as`.questionId WHERE `as`.questionType = 1 AND `as`.paperId = #{paperId} AND `as`.studentId = #{studentId};")
    List<Answer> findByIdAndTypeAndStudent(Integer paperId, Integer studentId);

    @Select("select * from fill_question")
    List<FillQuestion> findAll();

    @Select("select * from fill_question")
    IPage<FillQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     * @return FillQuestion
     */
    @Select("select questionId from fill_question order by questionId desc limit 1")
    FillQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true,keyProperty ="questionId" )
    @Insert("insert into fill_question(subject,question,answer,analysis,level,section,score) values " +
            "(#{subject,},#{question},#{answer},#{analysis},#{level},#{section},#{score})")
    int add(FillQuestion fillQuestion);

    @Select("select questionId from fill_question where subject = #{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(String subject,Integer pageNo);
}
