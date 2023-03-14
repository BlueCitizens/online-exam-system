package com.exam.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: BlueCitizens
 * @Date: 2021/6/15 23:21
 * @Description:
 */
@Mapper
public interface MenuMapper {
    @Select("SELECT `subject` FROM multi_question GROUP BY `subject`")
    List<String> selectSubjectFromMulti();

    @Select("SELECT section,COUNT(questionId) AS count FROM multi_question GROUP BY `subject`,section HAVING `subject` = #{subject}")
    List<HashMap<String,Object>> selectSectionFromMultiBySubject(String subject);

    @Select("SELECT `subject` FROM fill_question GROUP BY `subject`")
    List<String> selectSubjectFromFill();

    @Select("SELECT section,COUNT(questionId) AS count FROM fill_question GROUP BY `subject`,section HAVING `subject` = #{subject}")
    List<HashMap<String,Object>> selectSectionFromFillBySubject(String subject);

    @Select("SELECT `subject` FROM judge_question GROUP BY `subject`")
    List<String> selectSubjectFromJudge();

    @Select("SELECT section,COUNT(questionId) AS count FROM judge_question GROUP BY `subject`,section HAVING `subject` = #{subject}")
    List<HashMap<String,Object>> selectSectionFromJudgeBySubject(String subject);

    @Select("SELECT `subject` FROM short_answer_question GROUP BY `subject`")
    List<String> selectSubjectFromShortAnswer();

    @Select("SELECT section,COUNT(questionId) AS count FROM short_answer_question GROUP BY `subject`,section HAVING `subject` = #{subject}")
    List<HashMap<String,Object>> selectSectionFromShortAnswerBySubject(String subject);
}
