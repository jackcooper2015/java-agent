package com.cooper.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName
 * @Description TODO
 * @createTime 2020年09月10日 14:20:00
 */
public class AgentMain {
    public static void premain(String agentOps, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.named("org.springframework.web.servlet.DispatcherServlet"))
                .transform((builder, type, classLoader, module) ->
                        builder.method(ElementMatchers.named("doDispatch"))
                                .intercept(MethodDelegation.to(DoDispatchInterceptor.class)))
                .installOn(instrumentation);
    }
}
