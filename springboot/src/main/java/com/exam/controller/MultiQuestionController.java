package com.exam.controller;

import com.exam.entity.ApiResult;
import com.exam.entity.ExamManage;
import com.exam.entity.MultiQuestion;
import com.exam.serviceimpl.MultiQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultiQuestionController {

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @GetMapping("/multiQuestionId")
    public ApiResult findOnlyQuestion() {
        MultiQuestion res = multiQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200,"查询成功",res);
    }

    @PostMapping("/MultiQuestion")
    public ApiResult add(@RequestBody MultiQuestion multiQuestion) {
        int res = multiQuestionService.add(multiQuestion);
        if (res != 0) {

            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @GetMapping("/multiQuestion/{questionId}")
    public ApiResult findById(@PathVariable("questionId") Integer questionId){
        System.out.println("根据ID查找");
        MultiQuestion res = multiQuestionService.findById(questionId);
        if(res == null) {
            return ApiResultHandler.buildApiResult(10000,"考题编号不存在",null);
        }
        return ApiResultHandler.buildApiResult(200,"请求成功！",res);
    }

    @DeleteMapping("/multiQuestion/{questionId}")
    public ApiResult deleteById(@PathVariable("questionId") Integer questionId){
        int res = multiQuestionService.delete(questionId);
        return ApiResultHandler.buildApiResult(200,"删除成功",res);
    }

    @PutMapping("/multiQuestion")
    public ApiResult update(@RequestBody MultiQuestion multiquestion){
        int res = multiQuestionService.update(multiquestion);
//        if (res == 0) {
//            return ApiResultHandler.buildApiResult(20000,"请求参数错误");
//        }
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }
}
