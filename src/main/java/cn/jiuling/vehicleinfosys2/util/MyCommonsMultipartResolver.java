package cn.jiuling.vehicleinfosys2.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MyCommonsMultipartResolver extends CommonsMultipartResolver {

	private static final Logger log = Logger.getLogger(MyCommonsMultipartResolver.class);

	@Override
	protected FileUpload prepareFileUpload(String encoding) {
		FileUpload f = super.prepareFileUpload(encoding);
		// 设置监控进度条
		f.setProgressListener(new ProgressListener() {
			private long hasRead = 0;
			// 50k记录一次
			private long step = 50 * 1024l;

			@Override
			public void update(long pBytesRead, long pContentLength, int pItems) {
				if (pBytesRead - hasRead >= step || pBytesRead == pContentLength) {
					hasRead = pBytesRead;
					long progress = pBytesRead * 100 / pContentLength;
					// log.info("上传了" + pBytesRead + "字节,总共有" + pContentLength +
					// "字节,进度" + progress + "%");
					storeProgressInSession(progress);
				}
			}

			private void storeProgressInSession(long progress) {
				HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				HttpSession s = req.getSession(false);
				if (null != s) {
					s.setAttribute("progress", progress);
				}
			}
		});
		return f;
	}
}
