package com.hqq.ruleservice.controller;

import com.hqq.ruleservice.drools.loader.RuleLoader;
import com.hqq.ruleservice.drools.service.RuleInfoService;
import com.hqq.ruleservice.model.domain.Rule;
import com.hqq.ruleservice.model.dto.RuleDto;
import com.hqq.ruleservice.service.RuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiaoqian on 2020/11/18
 */
@RestController
@RequestMapping("rule")
@Api(value = "rule-service",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @Autowired
    private RuleLoader ruleLoader;

    @Autowired
    private RuleInfoService ruleInfoService;

    @PostMapping("add")
    @ApiOperation("新增一条规则")
    public Rule addRule(@RequestBody RuleDto ruleDto){
        Rule rule=ruleService.addRule(ruleDto);

        try{

        }catch (Exception e){
            System.out.print(e);
        }
        return rule;
    }

    /**********test***********/
    @PostMapping("test")
    @ApiOperation("test")
    public void test(){
        ruleInfoService.generateRuleInfoList();
    }
}
