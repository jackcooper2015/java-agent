package com.cooper.agent;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;


public class DoDispatchInterceptor {

    @RuntimeType
    public static Object intercept(@Argument(0) HttpServletRequest request, @SuperCall Callable<?> callable) throws Exception {
        final Logger logger = LoggerFactory.getLogger(callable.getClass());
        long agentStart = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            logger.info("===> path:{} [clientIp]:{} [入参]:{} [耗时]:{}ms"
                    ,request.getRequestURI()
                    ,ServletUtil.getClientIPByHeader(request)
                    ,JSONUtil.toJsonStr(ServletUtil.getParams(request))
                    ,(System.currentTimeMillis() - agentStart));
        }
    }
}
