package cn.jiuling.vehicleinfosys2.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtils {
	/**
	 * 替换文件中内容
	 * 
	 * @param filePath
	 *            文件path
	 * @param str
	 *            用于定位要替换的行,如果行中包含str,则替换
	 * @param toReplace
	 *            把找到的行整行替换成toReplace
	 */
	public static void replaceFileContent(String filePath, String str, String toReplace) {
		try {
			FileReader f = new FileReader(filePath);
			BufferedReader r = new BufferedReader(f);
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = r.readLine()) != null) {
				if (line.contains(str)) {
					sb.append(toReplace + "\r\n");
				} else {
					sb.append(line + "\r\n");
				}
			}
			FileWriter w = new FileWriter(filePath);
			w.write(sb.toString());
			w.flush();
			w.close();
			f.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String filePath = "C:\\Users\\Administrator\\Desktop\\GMap2-Config.js";
		replaceFileContent(filePath, "centerLng:", "centerLng:20,");
		replaceFileContent(filePath, "centerLat:", "centerLat:30,");
	}
}
