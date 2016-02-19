package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.TaskDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprTaskDao;
import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.model.VlprTask;
import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.DateUtils;
import cn.jiuling.vehicleinfosys2.vo.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Service("resultService")
public class ResultServiceImpl implements ResultService {

    @Resource
    private VlprResultDao vlprResultDao;
    @Resource
    private TaskDao taskDao;
    @Resource
    private VlprTaskDao vlprTaskDao;

    @Override
    public ResultVo findById(Long id) {
        ResultVo result = vlprResultDao.findResult(id);
        return result;
    }

    @Override
    public void addToChangHai(ResultVo r) {
        vlprResultDao.addToChangHai(r);
    }

    @Override
    public Pager list(ResultQuery rq, Integer page, Integer rows) {
        VlprResult v = new VlprResult();
        try {
            BeanUtils.copyProperties(v, rq);
        } catch (Exception e) {

        }
        return vlprResultDao.list(v, page, rows);
    }

    @Override
    public Pager listByTaskId(Integer taskId, Integer page, Integer rows) {

        PagerWithProgress pwp = new PagerWithProgress();
        Pager pager = vlprResultDao.findByTaskId(taskId, page, rows);
        Task t = taskDao.find(taskId);
        VlprTask vt = vlprTaskDao.find(t.getVlprTaskId());

        if (vt != null) {
            pwp.setProgress(vt.getProgress());
            pwp.setStatus(vt.getStatus());
        }
        pwp.setRows(pager.getRows());
        pwp.setTotal(pager.getTotal());

        return pwp;
    }

    @Override
    public Pager query(ResultQuery rq, Integer page, Integer rows) {
        return vlprResultDao.query(rq, page, rows);
    }

    @Override
    public Pager list(VlprResult vr, Integer page, Integer rows) {
        return vlprResultDao.list(vr, page, rows);
    }

    @Override
    public Pager queryMunityResult(final ResultQuery rq) {
        return vlprResultDao.queryMunityResult(rq);
    }

    @Override
    public String queryMunityResult1(final ResultQuery rq) {
        StringBuffer dataXML = new StringBuffer("<PassVehicleInfoList><License></License>");
        Pager pager = vlprResultDao.queryMunityResult(rq);
        List list = pager.getRows();
        for (Object o : list) {
            ResultVo resultVo = (ResultVo) o;
            String imageUrl = resultVo.getImageUrl();
            String imageUrl1 = "\\" + DateUtils.formateTime1(resultVo.getResultTime()) + "\\" + resultVo.getLicenseAttribution()
                    + "\\" + resultVo.getLocation() + "\\" + resultVo.getLocation()
                    + "\\" + imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            dataXML.append("<PassVehicleInfo> " +
                    "<TollgateID></TollgateID> " +
                    "<TollgateName></TollgateName> " +
                    "<PassTime>" + resultVo.getResultTime() + "</PassTime> " +
                    "<CameraID>" + resultVo.getCameraID() + "</CameraID> " +
                    "<CameraIP>" + resultVo.getCameraIP() + "</CameraIP> " +
                    "<DirectionName>" + resultVo.getDirection() + "</DirectionName> " +
                    "<LaneName></LaneName> " +
                    "<CarPlate>" + resultVo.getLicense() + "</CarPlate> " +
                    "<PlateColor>" + resultVo.getPlateColor() + "</PlateColor> " +
                    "<VehicleSpeed>" + resultVo.getCarspeed() + "</VehicleSpeed> " +
                    "<LimitSpeed></LimitSpeed> " +
                    "<PlateType>" + resultVo.getPlateType() + "</PlateType> " +
                    "<VehicleType>" + resultVo.getVehicleKind() + "</VehicleType> " +
                    "<VehicleColor>" + resultVo.getCarColor() + "</VehicleColor> " +
                    "<VehicleLogo>" + resultVo.getCarLogo() + "</VehicleLogo> " +
                    "<Longitude>" + resultVo.getLongitude() + "</Longitude> " +
                    "<Latitude>" + resultVo.getLatitude() + "</Latitude> " +
                    "<IllegalType></IllegalType> " +
                    "<ImageURL1>" + imageUrl1 + "</ImageURL1>" +
                    "<ImageURL2>图片地址2</ImageURL2>" +
                    "<ImageURL3>图片地址3</ImageURL3>" +
                    "</PassVehicleInfo> ");
        }
        return dataXML.append("</PassVehicleInfoList>").toString();
    }

    @Override
    public Pager queryByTravelTogetherVehicle(ResultVo[] resultVos, Long timeInterval, Integer locations, Integer page, Integer rows) {
        //统计同一车牌出现的次数
        HashMap<String, MutableInteger> counter = new HashMap<String, MutableInteger>();

        ResultQuery rq = new ResultQuery();

        //要返回的对象list
        List<TravelTogetherVO> listVO = new ArrayList<TravelTogetherVO>();

        for (int i = 0; i < resultVos.length; i++) {
            ResultVo resultVo = resultVos[i];

            Timestamp resultTime = resultVo.getResultTime();
            long time = resultTime.getTime();

            long startTime = time - timeInterval * 1000;
            long endTime = time + timeInterval * 1000;

            Timestamp startTime1 = new Timestamp(startTime);
            Timestamp endTime1 = new Timestamp(endTime);
            Integer cameraID = resultVo.getCameraID();
            if (cameraID != null) {

                String cameraIds = cameraID.toString();

                rq.setStartTime(startTime1);
                rq.setEndTime(endTime1);
                rq.setCameraIds(cameraIds);
                rq.setPlate(resultVo.getLicense());

                List pagerByMovent = vlprResultDao.queryByTravelTogetherVehicle(rq);
                for (Object o : pagerByMovent) {
                    String r = (String) o;
                    MutableInteger initValue = new MutableInteger(1);
                    // 利用 HashMap 的put方法弹出旧值的特性
                    MutableInteger oldValue = counter.put(r, initValue);
                    if (oldValue != null) {
                        initValue.set(oldValue.get() + 1);
                    }
                }
            }
        }

        Set<String> set = counter.keySet();
        for (String s : set) {
            int count = Integer.parseInt(counter.get(s).toString());
            if (count >= locations) {
                TravelTogetherVO t = new TravelTogetherVO();
                t.setCount(count);
                t.setPlate(s);
                listVO.add(t);
            }
        }

        //按count对listVO中的对象进行排序
        Collections.sort(listVO, new Comparator<TravelTogetherVO>() {
            @Override
            public int compare(TravelTogetherVO t1, TravelTogetherVO t2) {
                return t1.getCount().compareTo(t2.getCount());
            }
        });
        //逆序
        Collections.reverse(listVO);
        Pager pager = new Pager();
        pager.setRows(listVO);
        pager.setTotal((long) listVO.size());
        return pager;
    }

    @Override
    public Pager queryMunityResult2(ResultQuery rq) {
        Pager pager = vlprResultDao.queryMunityResult1(rq);
        List list = pager.getRows();
        List<ResultVo> resultVoList = new ArrayList<ResultVo>();
        int size = list.size();
        if (list != null && size > 0) {
            for (int i = 0; i < size; i++) {
                ResultVo r = (ResultVo) list.get(i);
                if (r.getCameraID() != null) {
                    resultVoList.add(r);
                }
            }

            pager.setRows(resultVoList);
            int rvListLen = resultVoList.size();
            pager.setTotal((long) rvListLen);
        }
        return pager;
    }

    @Override
    public Pager queryByTravelTogetherVehicle(TravelTogetherVehicleQuery travelTogetherVehicleQuery) {

        ResultQuery rq = new ResultQuery();

        rq.setPlate(travelTogetherVehicleQuery.getPlate());
        rq.setStartTime(travelTogetherVehicleQuery.getStartTime());
        rq.setEndTime(travelTogetherVehicleQuery.getEndTime());

        //同行车详细列表
        Pager pager = queryMunityResult2(rq);

        return pager;
    }

    @Override
    public List queryByNow() {
        return vlprResultDao.queryByNow();
    }

    // 可变Integer内部类
    public static final class MutableInteger {
        private int val;

        public MutableInteger(int val) {
            this.val = val;
        }

        public int get() {
            return this.val;
        }

        public void set(int val) {
            this.val = val;
        }

        public String toString() {
            return Integer.toString(val);
        }
    }
}
