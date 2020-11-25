package com.hqq.ruleservice.service.impl;

import com.hqq.ruleservice.mapper.RuleMapper;
import com.hqq.ruleservice.model.domain.Rule;
import com.hqq.ruleservice.model.dto.RuleDto;
import com.hqq.ruleservice.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by huqiaoqian on 2020/11/18
 */
@Service
public class RuleServiceImpl implements RuleService {
    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public Rule addRule(RuleDto ruleDto) {
        Rule rule=new Rule();
        rule.setRuleId(new Random().nextLong());
        rule.setContent(ruleDto.getContent());
        rule.setName(ruleDto.getName());
        rule.setStatus(ruleDto.getStatus());
        rule.setUserId(ruleDto.getUserId());
        if(ruleMapper.insert(rule)<0){
            System.out.print("插入记录失败");
        }
        return rule;
    }
}
