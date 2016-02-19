package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.CollectPicturesDao;
import cn.jiuling.vehicleinfosys2.model.VlprCollectPictures;
import cn.jiuling.vehicleinfosys2.service.CollectPicturesService;
import cn.jiuling.vehicleinfosys2.vo.CollectPicturesVo;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service("collectPicturesService")
public class CollectPicturesServiceImpl implements CollectPicturesService {

    private static final Logger log = Logger.getLogger(CollectPicturesServiceImpl.class);
    @Resource
    private CollectPicturesDao collectPicturesDao;

    @Override
    public boolean check(String cameraNames) {
        List list = list(new Short[]{0, 1, 3});// 获取未结束的采集任务信息
        boolean flag = true; //标记是否通过检测（如果该任务监控点有其他任务在进行采集，则为标记为false）
        if (list != null && list.size() > 0) {
            String checkCameraNames[] = cameraNames.split(",");
            for (int i = 0; i < list.size(); i++) {
                VlprCollectPictures cp = (VlprCollectPictures) list.get(i);
                for (int j = 0; j < checkCameraNames.length; j++) {
                    String oldCameraNameStr = cp.getCameraName(); //该任务所选的监控点
                    if (oldCameraNameStr.equals(checkCameraNames[j])) {
                        flag = false;
                    }
                    if (!flag) {
                        break;
                    }
                }
                if (!flag) {
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public void save(CollectPicturesVo collectPicturesVo) {
        String cameraNames[] = collectPicturesVo.getCameraNames().split(",");
        for (int i = 0; i < cameraNames.length; i ++){
            VlprCollectPictures vcp = new VlprCollectPictures();
            vcp.setStatus((short) 0);
            vcp.setDownloadCount(0);
            vcp.setCameraName(cameraNames[i]);
            if (collectPicturesVo.getStartTime() == null) {
                vcp.setStartTime(new Timestamp(System.currentTimeMillis()));
            }else{
                vcp.setStartTime(collectPicturesVo.getStartTime());
            }
            if(collectPicturesVo.getEndTime() != null){
                vcp.setEndTime(collectPicturesVo.getEndTime());
            }
            vcp.setCreateTime(new Timestamp(System.currentTimeMillis()));
            collectPicturesDao.save(vcp);
        }
    }

    @Override
    public Pager list(CollectPicturesVo cpv, Integer page, Integer rows) {
        return collectPicturesDao.list(cpv, page, rows);
    }

    @Override
    public List list(Short status) {
        return collectPicturesDao.list(status);
    }

    @Override
    public List list(Short status[]) {
        return collectPicturesDao.list(status);
    }

    @Override
    public void update(VlprCollectPictures vcp) {
        collectPicturesDao.update(vcp);
    }

    @Override
    public void updateRealCollectPictrue(VlprCollectPictures vcp){
        collectPicturesDao.updateRealCollectPictrue(vcp);
    }

    @Override
    public void delete(Long[] ids) {
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Long id = ids[i];
                VlprCollectPictures vcp = (VlprCollectPictures) this.collectPicturesDao.find(id);
                if(vcp.getStatus() == 2 || vcp.getStatus() == 4){ //如果该任务状态为已完成或已停止，则删除
                    this.collectPicturesDao.delete(vcp);
                }
            }
        }
    }

    @Override
    public void start(Long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            VlprCollectPictures vcp = collectPicturesDao.find(ids[i]);
            if (vcp.getStatus() == 3) {
                vcp.setStatus((short) 0); //设置为等待中状态
                collectPicturesDao.updateRealCollectPictrue(vcp);
            }
        }
    }

    @Override
    public void stopOrPause(Long[] ids,Integer oper) {
        for (int i = 0; i < ids.length; i++) {
            VlprCollectPictures vcp = collectPicturesDao.find(ids[i]);
            if (oper == 4 && vcp.getStatus() != 4 && vcp.getStatus() != 2) {
                vcp.setStatus((short) 4); //设置为终止状态
                collectPicturesDao.updateRealCollectPictrue(vcp);
            }
            if (oper == 3 && vcp.getStatus() != 4 && vcp.getStatus() != 2) {
                vcp.setStatus((short) 3); //设置为暂停状态
                collectPicturesDao.updateRealCollectPictrue(vcp);
            }
        }
    }

    @Override
    public VlprCollectPictures findById(Long id) {
        return collectPicturesDao.find(id);
    }
}
