package com.hqq.ruleservice.drools;

import com.hqq.ruleservice.drools.loader.RuleLoader;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huqiaoqian on 2020/11/18
 */
@Component
public class KieSessionHelper {
    @Autowired
    private RuleLoader ruleLoader;

    /**
     * 获取KieSession
     *
     * @param sceneId 场景ID
     * @return KieSession
     */
    public KieSession getKieSessionBySceneId(long sceneId) {
        return ruleLoader.getKieContainerBySceneId(sceneId).getKieBase().newKieSession();
    }
}
