package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;

import java.util.List;

public interface VlprFirstIntoCityResultService {

    /**
     * 查找异地车牌首次入城
     * 按当前时间检查最新结果
     * @param localPlatePre
     */
    List queryNonLocalPlateByCurrentTime(String localPlatePre);

    void save(List list);

    Pager query(ResultQuery rq, Integer page, Integer rows);
}
