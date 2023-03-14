package com.exam.paper.mapper;

import com.exam.paper.entity.Marking;
import com.exam.paper.entity.PaperManage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaperMapper {
    @Select("select paperId, questionType,questionId from paper_manage")
    List<PaperManage> findAll();

    @Select("select paperId, questionType,questionId from paper_manage where paperId = #{paperId}")
    List<PaperManage> findById(Integer paperId);

    @Insert("insert into paper_manage(paperId,questionType,questionId) values " +
            "(#{paperId},#{questionType},#{questionId})")
    int add(PaperManage paperManage);

    @Insert("insert into answer_sheet(paperId,studentId,questionType,questionId,answer,score) values (#{paperId},#{studentId},#{questionType},#{questionId},#{answer},#{score})")
    int insertAnswerSheet(Marking marking);

    @Delete("DELETE FROM paper_manage WHERE paperId = #{paperId} AND questionId = #{questionId} AND questionType = #{questionType}")
    int delete(PaperManage paperManage);
}
