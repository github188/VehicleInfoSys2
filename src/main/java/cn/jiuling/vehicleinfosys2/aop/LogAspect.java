package cn.jiuling.vehicleinfosys2.aop;

import cn.jiuling.vehicleinfosys2.annotation.Auditable;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.model.VehicleLogger;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.service.VehicleLoggerService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.TCPIPUtil;
import cn.jiuling.vehicleinfosys2.vo.AspectjDTO;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日志拦截器
 */

@Component
@Aspect
public class LogAspect {

    private static Logger logger = Logger.getLogger(LogAspect.class);

    @Resource
    private VehicleLoggerService vehicleLoggerService;
    @Resource
    private CameraService cameraService;

    User user = null;
   private static HttpSession session = null;

    @Pointcut("@annotation(cn.jiuling.vehicleinfosys2.annotation.Auditable)")
    public void methodCachePointcut() {
    }

    @Before("methodCachePointcut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        session = request.getSession();
        if(session != null) {
            user = (User) session.getAttribute("user");
        }
    }

    @AfterReturning(value = "methodCachePointcut()",returning = "returnValue")
    public void afterReturn(JoinPoint joinPoint,Object returnValue)  throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        session = request.getSession();
        if(session != null && session.getAttribute("user") != null) {
            user = (User)session.getAttribute("user");
        }

        Calendar ca = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(ca.getTimeInMillis());
        String ip = TCPIPUtil.getIpAddr(request);

        String objStr = "";

        if(user != null) {
            VehicleLogger vehicleLogger = new VehicleLogger();

            vehicleLogger.setAccount(user.getLoginName());
            vehicleLogger.setGuessIP(ip);
            vehicleLogger.setCreatedate(timestamp);

            AspectjDTO aspectjDTO = getControllerMethodDescription(joinPoint);

            vehicleLogger.setOperation(aspectjDTO.getOperType());
            vehicleLogger.setLogInfo(aspectjDTO.getRemark());

            String operObj = joinPoint.getTarget().getClass().getName().toString().toLowerCase();

            if(operObj.contains("user")) {
                objStr = Constant.OPRE_OBJ_USER;
            } else if(operObj.contains("camera")) {
                objStr = Constant.OPRE_OBJ_CAMERA;
            } else if(operObj.contains("task")) {
                objStr = Constant.OPRE_OBJ_VLPRTASK;
            } else if(operObj.contains("resource")) {
                objStr = Constant.OPRE_OBJ_RES;
            }
            vehicleLogger.setOperObj(objStr);
            if(returnValue != null) {
                vehicleLogger.setOperResult(Constant.OPRE_RESULT_SUCCESS);
            } else {
                vehicleLogger.setOperResult(Constant.OPRE_RESULT_FAIL);
            }
            vehicleLoggerService.add(vehicleLogger);
        }
    }

    @AfterThrowing(pointcut = "methodCachePointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        logger.info(e);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        session = request.getSession();
        if(session != null && session.getAttribute("user") != null) {
            user = (User)session.getAttribute("user");
        }

        Calendar ca = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(ca.getTimeInMillis());
        String ip = TCPIPUtil.getIpAddr(request);

        String objStr = "";

        if(user != null) {
            VehicleLogger vehicleLogger = new VehicleLogger();

            vehicleLogger.setAccount(user.getLoginName());
            vehicleLogger.setGuessIP(ip);
            vehicleLogger.setCreatedate(timestamp);

            AspectjDTO aspectjDTO = getControllerMethodDescription(joinPoint);

            vehicleLogger.setOperation(aspectjDTO.getOperType());
            vehicleLogger.setLogInfo(aspectjDTO.getRemark());

            String operObj = joinPoint.getTarget().getClass().getName().toString().toLowerCase();

            if(operObj.contains("user")) {
                objStr = Constant.OPRE_OBJ_USER;
            } else if(operObj.contains("camera")) {
                objStr = Constant.OPRE_OBJ_CAMERA;
            } else if(operObj.contains("task")) {
                objStr = Constant.OPRE_OBJ_VLPRTASK;
            } else if(operObj.contains("resource")) {
                objStr = Constant.OPRE_OBJ_RES;
            }
            vehicleLogger.setOperObj(objStr);
            vehicleLogger.setOperResult(Constant.OPRE_RESULT_FAIL);

            vehicleLoggerService.add(vehicleLogger);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public  static AspectjDTO getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        AspectjDTO aspectjDTO = new AspectjDTO();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    Auditable annotation = method.getAnnotation(Auditable.class);
                    aspectjDTO.setOperType(annotation.operType());
                    aspectjDTO.setOperObj(annotation.operObj());
                    aspectjDTO.setRemark(annotation.remark());
                    break;
                }
            }
        }
        return aspectjDTO;
    }

}
