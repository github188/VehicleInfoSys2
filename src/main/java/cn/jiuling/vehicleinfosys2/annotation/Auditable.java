package cn.jiuling.vehicleinfosys2.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2015/12/3.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {
    /**
     * 操作描述
     * @return
     */
    String remark() default "";

    /**
     * 操作类型
     * @return
     */
    String operType() default "0";

    /**
     * 操作对象
     * @return
     */
    String operObj() default "";
}