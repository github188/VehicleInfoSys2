package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.UserUploadVideoDao;
import cn.jiuling.vehicleinfosys2.model.UserUploadVideo;
import cn.jiuling.vehicleinfosys2.service.UserUploadVideoService;
import cn.jiuling.vehicleinfosys2.util.FileUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.util.List;

@Service("userUploadVideoService")
public class UserUploadVideoServiceImpl implements UserUploadVideoService {
    private static final Logger log = Logger.getLogger(UserUploadVideoServiceImpl.class);

    @Resource
    private UserUploadVideoDao userUploadVideoDao;

    public UserUploadVideoServiceImpl() {
    }

    @Override
    public Pager queryUploadVideo(UserUploadVideo uuv, Integer page, Integer rows) {
        return userUploadVideoDao.queryUploadVideo(uuv, page, rows);
    }

    @Override
    public void delete(BigInteger[] userUploadVideoIds) {
        BigInteger[] arr$ = userUploadVideoIds;
        int len$ = userUploadVideoIds.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            BigInteger id = arr$[i$];
            UserUploadVideo uuv = (UserUploadVideo)this.userUploadVideoDao.find(id);
            deleteTransFailVideo(uuv);
            this.userUploadVideoDao.delete(uuv);
        }
    }

    /**
     * 删除上传成功、转码失败的视频
     */
    private void deleteTransFailVideo(UserUploadVideo uuv) {
        File file = FileUtils.transFileNameToGBK(uuv.getSrcURL());
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public Pager list(UserUploadVideo userUploadVideo, Integer page, Integer rows) {
        return this.userUploadVideoDao.list(userUploadVideo, page, rows);
    }

    @Override
    public List queryUploadVideo() {
        return userUploadVideoDao.queryUploadVideo();
    }

    @Override
    public void update(UserUploadVideo u) {
        userUploadVideoDao.update(u);
    }

}
