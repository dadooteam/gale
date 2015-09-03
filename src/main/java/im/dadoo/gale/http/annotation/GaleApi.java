package im.dadoo.gale.http.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 定义api类，被本注解修饰的类中的，被GaleMapping修饰的方法，可以响应http实践
 * 
 * @author codekitten
 * @see GaleMapping
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GaleApi {

  /** bean名称 */
  String value() default "";
  
}
