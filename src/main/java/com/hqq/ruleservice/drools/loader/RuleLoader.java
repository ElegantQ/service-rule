package com.hqq.ruleservice.drools.loader;

import com.hqq.ruleservice.drools.service.RuleInfoService;
import com.hqq.ruleservice.model.domain.Rule;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.phreak.RuleExecutor;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.KieResources;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by huqiaoqian on 2020/11/18
 */
@Component
@Slf4j
public class RuleLoader implements ApplicationRunner {
    @Autowired
    RuleInfoService ruleInfoService;
    /**
     * key:kcontainerName,value:KieContainer，每个场景对应一个KieContainer
     */
    private final ConcurrentMap<String, KieContainer> kieContainerMap = new ConcurrentHashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
    /**
     * 构造kcontainerName
     *
     * @param sceneId 场景ID
     * @return kcontainerName
     */
    private String buildKcontainerName(long sceneId) {
        return "kcontainer_" + sceneId;
    }

    /**
     * 构造kbaseName
     *
     * @param sceneId 场景ID
     * @return kbaseName
     */
    private String buildKbaseName(long sceneId) {
        return "kbase_" + sceneId;
    }

    /**
     * 构造ksessionName
     *
     * @param sceneId 场景ID
     * @return ksessionName
     */
    private String buildKsessionName(long sceneId) {
        return "ksession_" + sceneId;
    }

    public KieContainer getKieContainerBySceneId(long sceneId) {
        return kieContainerMap.get(buildKcontainerName(sceneId));
    }

    public void loadRule(long sceneId, List<Rule> rules){
        try{
            KieServices kieServices = KieServices.get();
            KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
            KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(buildKbaseName(sceneId));
            kieBaseModel.setDefault(true);
            kieBaseModel.addPackage("rules");
            kieBaseModel.newKieSessionModel(buildKsessionName(sceneId));
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            String xml = kieModuleModel.toXML();
            System.out.println(xml);
            KieHelper helper = new KieHelper();
            for (Rule rule : rules) {
                helper.addContent(rule.getContent(), ResourceType.DRL);
                String fullPath = MessageFormat.format("src/main/resources/rules/rule_{0}.drl", String.valueOf(rule.getRuleId()));
                kieFileSystem.write(fullPath, rule.getContent());
            }
            kieFileSystem.writeKModuleXML(xml);
            KieSession kSession = helper.build().newKieSession();


            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                System.out.println(results.getMessages());
                throw new IllegalStateException("rule error");
            }

            KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            kieContainerMap.put(buildKcontainerName(sceneId), kieContainer);
            kSession.fireAllRules();
            kSession.dispose();
            log.info("执行规则引擎 end ....");

        }catch (Exception e){
            log.info("加载规则失败，{}",e);
        }
    }



}
