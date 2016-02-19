package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.VlprFirstIntoCityResultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprFirstIntoPosInfoDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.model.VlprFirstIntoCityResult;
import cn.jiuling.vehicleinfosys2.model.VlprFirstIntoPosInfo;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.service.VlprFirstIntoCityResultService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.DateUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("vlprFirstIntoCityResultService")
public class VlprFirstIntoCityResultServiceImpl implements VlprFirstIntoCityResultService {

    private Logger log = Logger.getLogger(VlprFirstIntoCityResultServiceImpl.class);


    @Resource
    private VlprFirstIntoCityResultDao vlprFirstIntoCityResultDao;
    @Resource
    private VlprFirstIntoPosInfoDao vlprFirstIntoPosInfoDao;

    @Resource
    private VlprResultDao vlprResultDao;

    @Override
    public Pager query(ResultQuery rq, Integer page, Integer rows) {
        return vlprFirstIntoCityResultDao.query(rq, page, rows);
    }

    @Override
    public List queryNonLocalPlateByCurrentTime(String localPlatePre) {

        /**
         *首次入城分析业务处理
         */
        List positionInfoPage = vlprFirstIntoPosInfoDao.getAll();
        VlprFirstIntoPosInfo vlprFirstIntoPosInfo = (VlprFirstIntoPosInfo) positionInfoPage.get(0);
        Long minSeriNumber = vlprFirstIntoPosInfo.getThreadPosition();
        //查询数据库中最大的seriNumber
        Long maxSeriNumber = vlprResultDao.queryMaxSeriNumber(minSeriNumber,10);

        //#首次入城要查询的时间范围
        Long withinMonths = Long.parseLong(PropertiesUtils.get("withinMonths"));
        //时间范围毫秒数
        long intervalTime = withinMonths * Constant.MILLISECONDS_PER_MONTH;

        VlprResult vlprResult = new VlprResult();

        //查询识别结果
        List<VlprResult> result = (List<VlprResult>) vlprResultDao.queryResultBetweenBySeriNumber(maxSeriNumber, minSeriNumber);
        //异地车list
        List nonLocalList = new ArrayList();
        int size = result.size();
        if (result != null && size > 0) {

            for (int i = 0;i < size;i++) {
                VlprResult r = result.get(i);

                //处理到最后一个元素时把位置信息写入位置表
                if( i== size - 1){
                    Long minsn = r.getSerialNumber();
                    vlprFirstIntoPosInfo.setThreadPosition(minsn);
                    vlprFirstIntoPosInfoDao.update(vlprFirstIntoPosInfo);
                }

                //剔除异地车、无牌车、未识别
                String license = r.getLicense();
                if (!license.startsWith(localPlatePre) && !license.equals("无车牌") && !license.equals("未识别")) {
                    //按车牌查找最近3个月的记录
                    Timestamp interval = new Timestamp(r.getResultTime().getTime() - intervalTime);
                    StringBuilder queryString = new StringBuilder(" and license='").append(license)
                                                    .append("' and resultTime >='").append(DateUtils.formateTime(interval))
                                                    .append("' and resultTime<'").append(DateUtils.formateTime(r.getResultTime())).append("'");
                    Pager recentlyPager = vlprResultDao.list(vlprResult, null, null, queryString.toString());
                    List recentLyList = recentlyPager.getRows();
                    if (recentLyList != null && recentLyList.size() > 0) {
                        continue;
                    }
                    nonLocalList.add(r);
                }
            }
        }
        return nonLocalList;
    }

    @Override
    public void save(List list) {

        for (Object o : list) {
            VlprResult r = (VlprResult) o;
            VlprFirstIntoCityResult vlprFirstIntoCityResult = new VlprFirstIntoCityResult();
            try {
                BeanUtils.copyProperties(vlprFirstIntoCityResult, r);
            } catch (Exception e) {
                log.info(e);
            }
            //判断首次入城表相同时间是否有相同车牌，若有则不执行插入
            Long count = vlprFirstIntoCityResultDao.countQuery(vlprFirstIntoCityResult);
            int i = count.intValue();
            if(i > 0) {
                continue;
            }
            log.info("保存异地车！");
            vlprFirstIntoCityResultDao.save(vlprFirstIntoCityResult);
            log.info("保存完成！");
        }
    }
}
