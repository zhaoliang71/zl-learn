package com.bj.zl.learn.flow.rule;

import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class FlowRuleUtil {


    public static void initRule(){
        FlowRule rule = new FlowRule();
        //rule.setResource("testAnnotation");
        rule.setResource("testAnnotation2");
        //rule.setResource("castExceptionMethod");
        //rule.setResource("booleanMethod");
        //rule.setResource("testAnnotationHandler");

        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//按QPS限流
        //rule.setGrade(RuleConstant.FLOW_GRADE_THREAD);//按线程限流
        rule.setCount(9);//QPS 或 线程的限制数


        //调用方限流
        //默认值 "default" 表示不区分调用者，来自任何调用着的请求都将进行限流统计。
        //{some_origin_name}：表示针对特定的调用者，只有来自这个调用者的请求才会进行流量控制。
        //other：表示针对除 {some_origin_name} 以外的其余调用方的流量进行流量控制
        //同一个资源名可以配置多条规则，规则的生效顺序为：{some_origin_name} > other > default
        //rule.setLimitApp()

        //rule.setStrategy()流量策略
        //rule.setControlBehavior() 流量控制效果（直接拒绝、Warm Up、匀速排队）
        List<FlowRule> ruleList = new ArrayList<>();
        ruleList.add(rule);//同一个资源可以创建多条限流规则。FlowSlot 会对该资源的所有限流规则依次遍历，直到有规则触发限流或者所有规则遍历完毕
        FlowRuleManager.loadRules(ruleList);
    }



    public static void initRule1(){
        FlowRule rule = new FlowRule();
        rule.setResource("annotationTestFall");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//按QPS限流
        rule.setCount(9);//QPS 或 线程的限制数
        List<FlowRule> ruleList = new ArrayList<>();
        ruleList.add(rule);//同一个资源可以创建多条限流规则。FlowSlot 会对该资源的所有限流规则依次遍历，直到有规则触发限流或者所有规则遍历完毕
        FlowRuleManager.loadRules(ruleList);
    }

    public static void initRule2(){
        FlowRule rule = new FlowRule();
        rule.setResource("testAnnotationHandlerLimitApp");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//按QPS限流
        rule.setCount(2);//QPS 或 线程的限制数
        rule.setLimitApp("caller1");

        FlowRule rule1 = new FlowRule();
        rule1.setResource("testAnnotationHandlerLimitApp");
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);//按QPS限流
        rule1.setCount(100);//QPS 或 线程的限制数
        //rule.setLimitApp("limitApp");

        List<FlowRule> ruleList = new ArrayList<>();
        ruleList.add(rule);//同一个资源可以创建多条限流规则。FlowSlot 会对该资源的所有限流规则依次遍历，直到有规则触发限流或者所有规则遍历完毕
        ruleList.add(rule1);//同一个资源可以创建多条限流规则。FlowSlot 会对该资源的所有限流规则依次遍历，直到有规则触发限流或者所有规则遍历完毕
        FlowRuleManager.loadRules(ruleList);
    }
}
