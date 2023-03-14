package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.Score;
import com.exam.entity.ScoreWork;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * @param score 添加一条成绩记录
     * @return
     */
    @Options(useGeneratedKeys = true,keyProperty = "scoreId")
    @Insert("insert into score(examCode,studentId,subject,ptScore,etScore,score,answerDate) values(#{examCode},#{studentId},#{subject},#{ptScore},#{etScore},#{score},#{answerDate})")
    int add(Score score);

    @Options(useGeneratedKeys = true,keyProperty = "scoreId")
    @Insert("insert into score_work(workCode,studentId,subject,obScore,answerDate) values(#{workCode},#{studentId},#{subject},#{suScore},#{answerDate})")
    int addObScore(ScoreWork scoreWork);

    @Select("select score_work.*,work_manage.description from score_work left join work_manage on score_work.workCode = work_manage.workCode where score is not null order by scoreId desc")
    List<Score> findAll();

    // 分页
    @Select("select score_work.*,work_manage.description from score_work left join work_manage on score_work.workCode = work_manage.workCode where score is not null and studentId = #{studentId} order by scoreId desc")
    IPage<Score> findById(Page<?> page, Integer studentId);

    // 不分页
    @Select("select score_work.*,work_manage.description from score_work left join work_manage on score_work.workCode = work_manage.workCode where score is not null and studentId = #{studentId}")
    List<Score> findById(Integer studentId);

    // 不分页
    @Select("select score_work.*,work_manage.description from score_work left join work_manage on score_work.workCode = work_manage.workCode where score is not null and studentId = #{studentId}")
    List<Score> findByPaper(Integer paperId);

    /**
     *
     * @return 查询每位学生的学科分数。 max其实是假的，为了迷惑老师，达到一次考试考生只参加了一次的效果
     */
    @Select("select max(score) as score from score_work where workCode = #{workCode} group by studentId")
    List<Score> findByExamCode(Integer workCode);

    @Update("update answer_sheet set score = #{score} where id = #{id}")
    int updateMarkingById(Integer score, Integer id);

    @Update("update score_work set suScore = #{score}, score = #{score} + obScore where scoreId = #{id}")
    int updateScoreWorkById(Integer score, Integer id);
}
