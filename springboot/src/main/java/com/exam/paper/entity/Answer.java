package com.exam.paper.entity;

import lombok.Data;

/**
 * @Author: BlueCitizens
 * @Date: 2021/6/8 16:51
 * @Description:
 */
@Data
public class Answer {
    private Integer scoreId;

    private Integer paperId;

    private Integer studentId;

    private Integer questionType;

    private Integer questionId;

    private String answer;

    private String rightAnswer;

    private Integer score;
}
