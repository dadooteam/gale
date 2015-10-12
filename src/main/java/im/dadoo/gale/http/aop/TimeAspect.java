package im.dadoo.gale.http.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import im.dadoo.gale.http.request.GaleRequest;

@Aspect
@Order
@Component
public class TimeAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(TimeAspect.class);
  
  @Around("execution(* im.dadoo.gale.http.server.GaleProcessor.process(..))")
  public Object profile(ProceedingJoinPoint pjp) throws Throwable {
    long t1 = System.currentTimeMillis();
    Object ret = pjp.proceed();
    long t2 = System.currentTimeMillis();
    long delta = t2 - t1;
    GaleRequest request = (GaleRequest)pjp.getArgs()[0];
    LOGGER.info(String.format("{\"uri\":\"%s\",\"delta\":\"%d\"}", request.getUri(), delta));
    return ret;
  }
}
