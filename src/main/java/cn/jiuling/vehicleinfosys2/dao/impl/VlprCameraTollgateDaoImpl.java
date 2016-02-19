package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.VlprCameraTollgateDao;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.VlprCameraTollgate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("vlprCameraTollgateDao")
public class VlprCameraTollgateDaoImpl extends BaseDaoImpl<VlprCameraTollgate> implements VlprCameraTollgateDao{

    @Override
    public VlprCameraTollgate findByCameraId(Integer cameraId) {
        List list = getHibernateTemplate().find("from VlprCameraTollgate vct where vct.cameraId =?", cameraId);
        if(list != null && list.size() >0){
            return (VlprCameraTollgate)list.get(0);
        }
        return null;
    }

    @Override
    public List queryTollgateCodesByCameraIds(Integer[] cameraIds) {
        List list = new ArrayList();
        if (cameraIds==null || cameraIds.length==0){
            return list;
        }else{
            StringBuilder sb = new StringBuilder("select vct.tollgateId from VlprCameraTollgate vct where vct.cameraId in(");
            for (int i = 0; i < cameraIds.length; i ++){
                sb.append(cameraIds[i]);
                sb.append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append(")");
            list = getHibernateTemplate().find(sb.toString());
            return list;
        }
    }
}
