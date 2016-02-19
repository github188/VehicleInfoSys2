package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprFirstIntoPosInfo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface VlprFirstIntoPosInfoDao extends BaseDao<VlprFirstIntoPosInfo> {

    void updatePasInfo(VlprFirstIntoPosInfo vlprFirstIntoPosInfo);
}
