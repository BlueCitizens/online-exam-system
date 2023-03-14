package com.exam.question.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.paper.entity.Marking;
import com.exam.question.entity.ShortAnswerQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//填空题
@Mapper
public interface ShortAnswerQuestionMapper {

    @Select("select * from short_answer_question where subject = #{subject} and section = #{section}")
    List<ShortAnswerQuestion> findBySubAndSec(String subject,String section);

    @Select("select * from short_answer_question where questionId in (select questionId from paper_manage where questionType = 4 and paperId = #{paperId})")
    List<ShortAnswerQuestion> findByIdAndType(Integer paperId);

    @Select("SELECT `as`.*, saq.question, saq.analysis, saq.answer AS rightAnswer, saq.score AS fullScore FROM answer_sheet `as` LEFT JOIN short_answer_question saq ON saq.questionId = `as`.questionId WHERE `as`.questionType = 1 AND `as`.paperId = #{paperId} AND `as`.studentId = #{studentId};")
    List<Answer> findByIdAndTypeAndStudent(Integer paperId, Integer studentId);

    @Select("select * from short_answer_question")
    IPage<ShortAnswerQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     * @return ShortAnswerQuestion
     */
    @Select("select questionId from short_answer_question order by questionId desc limit 1")
    ShortAnswerQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true,keyProperty ="questionId" )
    @Insert("insert into short_answer_question(subject,question,answer,analysis,level,section,score) values " +
            "(#{subject,},#{question},#{answer},#{analysis},#{level},#{section},#{score})")
    int add(ShortAnswerQuestion fillQuestion);

    @Select("select questionId from short_answer_question where subject = #{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(String subject,Integer pageNo);

    @Select("select ash.*, st.studentName, saq.score as fullScore, saq.question, saq.analysis, saq.answer as rightAnswer from answer_sheet ash left join short_answer_question saq on ash.questionId = saq.questionId left join student st on ash.studentId = st.studentId where ash.paperId = #{paperId} and ash.studentId = #{studentId} and ash.questionType = 4")
    List<Marking> selectByPaperAndStudent(Integer paperId, Integer studentId);
}
