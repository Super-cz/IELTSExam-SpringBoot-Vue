package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ExamManage;
import com.exam.entity.MultiQuestion;
import com.exam.mapper.MultiQuestionMapper;
import com.exam.service.MultiQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiQuestionServiceImpl implements MultiQuestionService {

    @Autowired
    private MultiQuestionMapper multiQuestionMapper;
    @Override
    public List<MultiQuestion> findByIdAndType(Integer PaperId) {
        return multiQuestionMapper.findByIdAndType(PaperId);
    }

    @Override
    public IPage<MultiQuestion> findAll(Page<MultiQuestion> page) {
        return multiQuestionMapper.findAll(page);
    }

    @Override
    public MultiQuestion findOnlyQuestionId() {
        return multiQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(MultiQuestion multiQuestion) {
        return multiQuestionMapper.add(multiQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return multiQuestionMapper.findBySubject(subject,pageNo);
    }

    @Override
    public MultiQuestion findById(Integer questionId) {
        return multiQuestionMapper.findById(questionId);
    }

    @Override
    public int delete(Integer questionId) {
        return multiQuestionMapper.delete(questionId);
    }

    @Override
    public int update(MultiQuestion multiquestion) {
        return multiQuestionMapper.update(multiquestion);
    }
}
