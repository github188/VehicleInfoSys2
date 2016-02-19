package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.TSSupportVideoTypeDao;
import cn.jiuling.vehicleinfosys2.model.TSSupportVideoType;
import cn.jiuling.vehicleinfosys2.service.TSSupportVideoTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/9.
 */
@Service("tsSupportVideoTypeService")
public class TSSupportVideoTypeServiceImpl implements TSSupportVideoTypeService {

    @Resource
    private TSSupportVideoTypeDao tsSupportVideoTypeDao;

    @Override
    public List list(TSSupportVideoType tv) {
        List<TSSupportVideoType> encodeTypeNameAll = new ArrayList<TSSupportVideoType>();
        List<TSSupportVideoType> all = tsSupportVideoTypeDao.getAll();

        for (TSSupportVideoType tsSupportVideoType : all) {
            String typeName = null;
            try {
                typeName = new String(tsSupportVideoType.getTypeName().getBytes("ISO8859-1"),"gb2312");
                tsSupportVideoType.setTypeName(typeName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            encodeTypeNameAll.add(tsSupportVideoType);
        }
        return encodeTypeNameAll;
    }
}
