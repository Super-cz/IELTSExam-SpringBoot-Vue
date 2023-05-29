package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.vo.AnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AnswerMapper {
//    @Select({"<script>",
//            "select question, subject, score, section,level, \"选择题\" as type from multi_question",
//            "where 1=1 <if test='question !=null'> and question like concat('%',#{question},'%') </if>",
//            "union select  question, subject, score, section,level, \"判断题\" as type  from judge_question ",
//            "where 1=1 <if test='question !=null'> and question like concat('%',#{question},'%') </if>",
//            "union select  question, subject, score, section,level, \"填空题\" as type from fill_question",
//            "where 1=1 <if test='question !=null'> and question like concat('%',#{question},'%') </if>",
//            "</script>"
//    })
@Select({"<script>",
        "select questionId, question, subject, score, section,level, \"选择题\" as type from multi_question",
        "where 1=1 <if test='question !=null'> and question like concat('%',#{question},'%') </if>",
        "union select  questionId, question, subject, score, section,level, \"判断题\" as type  from judge_question ",
        "where 1=1 <if test='question !=null'> and question like concat('%',#{question},'%') </if>",
        "union select  questionId, question, subject, score, section,level, \"填空题\" as type from fill_question",
        "where 1=1 <if test='question !=null'> and question like concat('%',#{question},'%') </if>",
        "</script>"
})
    IPage<AnswerVO> findAll(Page page,@Param("question") String question);
}
