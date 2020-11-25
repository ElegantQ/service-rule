package com.hqq.ruleservice.drools.service;

import com.hqq.ruleservice.drools.loader.RuleLoader;
import com.hqq.ruleservice.model.domain.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huqiaoqian on 2020/11/18
 */
@Service
public class RuleInfoService {
    @Autowired
    private RuleLoader ruleLoader;
    /**
     * 生成规则信息列表，注意场景id和规则id的对应关系
     *
     * @return 规则信息列表
     */
    public List<Rule> generateRuleInfoList() {
        int sceneNum = 5;
        int ruleNumPerScene = 3;
        int sceneFactor = 10000;

        List<Rule> rules = new ArrayList<>(sceneNum * ruleNumPerScene);
            long sceneId = sceneFactor ;
            long id = sceneId + 1;
            rules.add(generateRuleInfo(sceneId, id));
            ruleLoader.loadRule(sceneId,rules);

        return rules;
    }

    /**
     * 生成规则信息
     *
     * @param sceneId 场景ID
     * @param id      规则ID
     * @return 规则信息
     */
    private Rule generateRuleInfo(long sceneId, long id) {
        Rule ruleInfo = new Rule();
        ruleInfo.setRuleId(id);
        ruleInfo.setContent(generateRuleContent(sceneId, id));
        return ruleInfo;
    }

    /**
     * 生成规则内容，每个场景id对应一个package，每个规则对应一个唯一的规则名
     *
     * 每次生成规则时记录时间戳，用来验证动态加载效果
     *
     * @param sceneId 场景ID
     * @param id      规则ID
     * @return 规则内容
     */
    public String generateRuleContent(long sceneId, long id) {
        String sceneIdStr = String.valueOf(sceneId);
        String idStr = String.valueOf(id);
        String nowStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String content = "package rules;\n" +
                "\n" +
                "rule \"rule_{1}\"\n" +
                "    when\n" +
                "        eval(true);\n" +
                "    then\n" +
                "        System.out.println(\"{2} [{3}, {4}]\");\n" +
                "end\n";
        return MessageFormat.format(content, sceneIdStr, idStr, nowStr, sceneIdStr, idStr);
    }
}
