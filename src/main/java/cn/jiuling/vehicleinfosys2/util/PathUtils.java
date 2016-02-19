package cn.jiuling.vehicleinfosys2.util;

import org.springframework.web.context.ContextLoader;

public class PathUtils {

	public static String getUploadPath() {
		return PropertiesUtils.get("uploadPath");
	}

	public static String getResultPath() {
		return PropertiesUtils.get("resultPath");
	}

	public static String getVlprDataSrcPath() {
		return PropertiesUtils.get("vlprDataSrcPath");
	}

	public static String getVlprDataSrcHisPath() {
		return PropertiesUtils.get("vlprDataSrcHisPath");
	}

	public static String getRealPath(String arg) {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(arg);
	}

	/**
	 * xxx.jpg变成xxx+suffix.jpg
	 * 
	 * @param name
	 * @param suffix
	 * @return
	 */
	public static String rename(String name, String suffix) {
		int index = name.lastIndexOf(".");
		String s1 = name.substring(0, index);
		String s2 = name.substring(index);
		return s1 + suffix + s2;
	}

	/**
	 * 返回path中的文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		return path.substring(path.lastIndexOf("/") + 1);
	}

	/**
	 * 返回项目中的临时文件目录名
	 * 
	 * @return
	 */
	public static String getTempPath() {
		return PropertiesUtils.get("tempPath");
	}

	public static void main(String[] args) {
		String n = rename("aaa.jpg", "-small");
		System.out.println(n);
	}

	/**
	 * 取得png目录
	 * @return
	 */
	public static String getCoverPngPath() {
		return PropertiesUtils.get("coverPngPath");
	}
}
