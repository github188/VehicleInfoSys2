package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprFirstIntoCityResult;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.TravelTogetherVehicleQuery;

import java.util.List;

public interface VlprFirstIntoCityResultDao extends BaseDao<VlprFirstIntoCityResult> {

    Pager query(ResultQuery rq, Integer page, Integer rows);

    Long countQuery(VlprFirstIntoCityResult vlprFirstIntoCityResult);
}
