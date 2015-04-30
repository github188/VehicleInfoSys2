package cn.jiuling.vehicleinfosys2.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 格式化日期
	 * 
	 * @param d
	 *            日期
	 * @return
	 */
	public static String formateTime(Date d) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(d);

		return str;
	}

}
