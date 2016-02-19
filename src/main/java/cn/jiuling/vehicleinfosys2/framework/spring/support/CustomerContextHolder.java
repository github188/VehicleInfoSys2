package cn.jiuling.vehicleinfosys2.framework.spring.support;

/**
 * 多数据源
 * Created by Administrator on 2015/7/25.
 */
public abstract class CustomerContextHolder {
    public final static String DATA_SOURCE_MYSQL = "mysql";
    public final static String  DATA_SOURCEY_ORACLE = "oracle";
    public final static String DATA_SOURCE_POSTGRESQL = "postgresql";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    //设置数据源类型
    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    //获取数据源类型
    public static String getCustomerType() {
        return contextHolder.get();
    }


    //清除数据源类型
    public static void clearCustomerType() {
        contextHolder.remove();
    }
}
