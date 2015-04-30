package cn.jiuling.vehicleinfosys2.util;

import org.codehaus.jackson.map.ObjectMapper;

import cn.jiuling.vehicleinfosys2.model.User;

public class JsonUtils {
	/**
	 * 对象转String
	 * 
	 * @param o
	 * @return
	 */
	public static String toJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * String 转对象
	 * 
	 * @param <T>
	 * @param str
	 * @param valueType
	 * @return
	 */
	public static <T> T toObj(String str, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(str, valueType);
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		User o = new User();
		o.setLoginName("张三");
		String str = JsonUtils.toJson(o);
		System.out.println(str);
		User u = JsonUtils.toObj(str, User.class);
		System.out.println(u.getLoginName());
	}
}
