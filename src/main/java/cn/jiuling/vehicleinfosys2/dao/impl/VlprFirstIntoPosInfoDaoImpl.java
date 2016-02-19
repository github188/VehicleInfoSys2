package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.VlprFirstIntoPosInfoDao;
import cn.jiuling.vehicleinfosys2.model.VlprFirstIntoPosInfo;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2015/11/20.
 */
@Repository("vlprFirstIntoPosInfoDao")
public class VlprFirstIntoPosInfoDaoImpl extends BaseDaoImpl<VlprFirstIntoPosInfo> implements VlprFirstIntoPosInfoDao {

    @Override
    public void updatePasInfo(VlprFirstIntoPosInfo vlprFirstIntoPosInfo) {
        String querSql = " update VlprFirstIntoPosInfo set threadPosition = ? ";
        super.getHibernateTemplate().bulkUpdate(querSql, vlprFirstIntoPosInfo.getThreadPosition());
    }
}
