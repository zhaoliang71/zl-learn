package com.bj.zl.learn.degrade.rule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;

import java.util.Arrays;

public class DegradeRuleUtil {



    public static void initRtRule(){
        DegradeRule rule = new DegradeRule();

        rule.setResource("RT");
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);//RT 以平均的RT时间熔断
        //如果接下来1s内持续进入5个请求，他们的RT都持续超过count值，即降级。
        // sentinel默认统计的RT上限为4900ms,超出此阈值都会算作4900ms，通过启动配置项 -Dcsp.sentinel.statistic.max.rt=xxx 来配置。
        rule.setCount(10);
        rule.setTimeWindow(10);//在接下来的这个时间都会降级，抛出DegradeRule 异常
        //加载规则
        DegradeRuleManager.loadRules(Arrays.asList(rule));

    }
}
