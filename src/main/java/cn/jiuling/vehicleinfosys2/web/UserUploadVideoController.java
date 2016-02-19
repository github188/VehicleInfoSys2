package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.UserUploadVideo;
import cn.jiuling.vehicleinfosys2.service.UserUploadVideoService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 *  上传视频转码Controller
 *
 * @author wangrb
 * @version 1.001, 2015-10-09
 */
@Controller
@RequestMapping("userUploadVideo")
public class UserUploadVideoController extends BaseController {

	@Resource
	private UserUploadVideoService userUploadVideoService;

	@RequestMapping("index.action")
	public void index() {
	}

	@RequestMapping("delete.action")
	@ResponseBody
	public Object delete(BigInteger[] ids) {
		userUploadVideoService.delete(ids);
		return SUCCESS;
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(UserUploadVideo userUploadVideo,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return userUploadVideoService.queryUploadVideo(userUploadVideo, page, rows);
	}
}
