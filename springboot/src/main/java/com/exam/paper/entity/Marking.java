package com.exam.paper.entity;

import lombok.Data;

/**
 * @Author: BlueCitizens
 * @Date: 2021/6/12 11:17
 * @Description:
 */
@Data
public class Marking {
    private Integer id;

    private Integer paperId;

    private Integer studentId;

    private String studentName;

    private Integer questionType;

    private Integer questionId;

    private String question;

    private String analysis;

    private String answer;

    private String rightAnswer;

    private Integer score;

    private Integer fullScore;
}
