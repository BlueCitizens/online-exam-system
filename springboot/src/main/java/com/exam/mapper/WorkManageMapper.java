package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ExamManage;
import com.exam.entity.ScoreWork;
import com.exam.entity.WorkManage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WorkManageMapper {
    @Select("SELECT * FROM work_manage")
    List<WorkManage> findAll();

    @Select("SELECT work_manage.*,score_work.answerDate,score_work.score FROM work_manage LEFT JOIN score_work ON work_manage.workCode = score_work.workCode AND score_work.studentId = #{studentId} WHERE work_manage.status = 1")
    List<WorkManage> find(Integer studentId);

    @Select("SELECT * FROM work_manage")
    IPage<WorkManage> findAll(Page page);

    @Select("SELECT work_manage.*,score_work.answerDate,score_work.score FROM work_manage LEFT JOIN score_work ON work_manage.workCode = score_work.workCode AND score_work.studentId = #{studentId} WHERE work_manage.status = 1")
    IPage<WorkManage> find(Page page, Integer studentId);

    @Select("select * from work_manage where workCode = #{workCode}")
    WorkManage findById(Integer workCode);

    @Delete("delete from work_manage where workCode = #{workCode}")
    int delete(Integer workCode);

    @Update("update work_manage set description = #{description},source = #{source},paperId = #{paperId}," +
            "examDate = #{examDate},totalTime = #{totalTime},grade = #{grade},term = #{term}," +
            "major = #{major},institute = #{institute},totalScore = #{totalScore}," +
            "type = #{type},tips = #{tips} where workCode = #{workCode}")
    int update(WorkManage workManage);

    @Options(useGeneratedKeys = true, keyProperty = "workCode")
    @Insert("insert into work_manage(description,source,paperId,createTime,deadline,clazz,totalScore,type,tips)" +
            " values(#{description},#{source},#{paperId},#{createTime},#{deadline},#{clazz},#{totalScore},#{type},#{tips})")
    int add(WorkManage workManage);

    @Update("update work_manage set status = #{status} where workCode = #{workCode}")
    int changeStatus(Integer status, Integer workCode);

    /**
     * 查询最后一条记录的paperId,返回给前端达到自增效果
     *
     * @return paperId
     */
    @Select("select paperId from work_manage order by paperId desc limit 1")
    WorkManage findOnlyPaperId();

    @Select("select sw.scoreId,sw.workCode,sw.subject,sw.suScore,sw.obScore,sw.score,sw.answerDate,s.studentId,s.studentName from score_work sw left join student s on sw.studentId = s.studentId where workCode = (select work_manage.workCode from work_manage where work_manage.workCode = #{workCode})")
    List<ScoreWork> findUnmarkedByWork(Integer workCode);
}
