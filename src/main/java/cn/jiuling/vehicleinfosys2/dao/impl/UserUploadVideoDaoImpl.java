package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.UserUploadVideoDao;
import cn.jiuling.vehicleinfosys2.model.UserUploadVideo;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userUploadVideoDao")
public class UserUploadVideoDaoImpl extends BaseDaoImpl<UserUploadVideo> implements UserUploadVideoDao {

    @Override
    public List queryUploadVideo() {
        StringBuilder sb = new StringBuilder("from UserUploadVideo u where u.status=" + Constant.TRANSTASKMGR_STATUS_COMPLETED + " and u.isAddedToDataSource=" + Constant.ISADDEDTODATASOURCE_NO);
        List list = super.find(sb.toString(), null, null, null);
        return list;
    }

    @Override
    public Pager queryUploadVideo(UserUploadVideo uuv, Integer page, Integer rows) {
        StringBuilder sb = new StringBuilder("from UserUploadVideo uuv where 1=1 ");
        if(uuv.getCameraId() != null){
            sb.append(" and uuv.cameraId="+uuv.getCameraId());
        }
        sb.append(" order by uuv.id desc");
        Long total = count("select count(*) " + sb.toString(), null);
        List list = super.find(sb.toString(), null, page, rows);
        Pager p = new Pager(total, list);
        return p;
    }

}
