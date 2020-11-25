package com.hqq.ruleservice.service;

import com.hqq.ruleservice.model.domain.Rule;
import com.hqq.ruleservice.model.dto.RuleDto;

/**
 * Created by huqiaoqian on 2020/11/18
 */
public interface RuleService {

    public Rule addRule(RuleDto ruleDto);
}
