package im.dadoo.gale.http.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import im.dadoo.gale.http.server.GaleRequest;

@Aspect
@Order(1)
@Component
public class MdcAspect {

  private static final String REQUEST_URI = "RequestUri";
  
  @Around("execution(* im.dadoo.gale.http.server.GaleProcessor.process(..))")
  public Object mdc(ProceedingJoinPoint pjp) throws Throwable {
    GaleRequest request = (GaleRequest)pjp.getArgs()[0];
    MDC.put(REQUEST_URI, request.getUri());
    Object result = pjp.proceed();
    MDC.remove(REQUEST_URI);
    return result;
  }
  
}
