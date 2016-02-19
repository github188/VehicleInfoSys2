package cn.jiuling.vehicleinfosys2.util;

import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

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

    /**
     * String 转对象数组
     *
     * @param <T>
     * @param json
     * @param cls
     * @return
     */
    public static <T> T[] toObjArr(String json, Class<T[]> cls) {
        List<T> list = null;  //目标list
        T[] arr = null; //ObjectMapper无法将json直接解析成对象的list，
        //因此需要先将其解析成对象数组，
        //再通过Arrays.asList转换成对象List
        try {
            ObjectMapper mapper = new ObjectMapper();
            arr = mapper.readValue(json, cls); //执行转换
        }catch (JsonParseException e){
            e.printStackTrace();
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return arr;
    }

	public static void main(String[] args) {
		User o = new User();
        User u = new User();
		o.setLoginName("张三");
        u.setLoginName("张三");
        String str = "[{\"license\":\"粤B100EL\",\"plateType\":\"蓝牌\",\"plateColor\":\"蓝色\",\"confidence\":89,\"licenseAttribution\":\"深圳市\",\"imageUrl\":\"vlpr_result/4/粤B100EL_20150825143049133.jpg\",\"carColor\":\"黑\",\"resultTime\":1440484249000,\"location\":\"高塘岭镇郭亮路与宝粮路南向北\",\"direction\":\"未知\",\"longitude\":113.52104187011719,\"latitude\":23.044352663791813,\"path\":\"file://D:/upload\\\\29e15010-63a5-4496-8e8b-b4f5a5f8d2c6\",\"frame_index\":443,\"taskType\":3,\"resourceId\":null,\"resourceType\":3,\"serialNumber\":2424,\"vehicleKind\":\"轿车\",\"vehicleBrand\":\"雷克萨斯\",\"vehicleSeries\":\"ES\",\"vehicleStyle\":\"2010\",\"locationLeft\":495,\"locationTop\":691,\"locationRight\":617,\"locationBottom\":717,\"tag\":1,\"paper\":0,\"drop\":0,\"sun\":0,\"tagRectAndScore\":\"{\\\"rect\\\":[{\\\"x1\\\":407,\\\"y1\\\":323,\\\"x2\\\":429,\\\"y2\\\":345},{\\\"x1\\\":437,\\\"y1\\\":310,\\\"x2\\\":461,\\\"y2\\\":334}],\\\"score\\\":[65,41]}\",\"paperRectAndScore\":\"{\\\"rect\\\":[],\\\"score\\\":[]}\",\"dropRectAndScore\":\"{\\\"rect\\\":[],\\\"score\\\":[]}\",\"sunRectAndScore\":\"{\\\"rect\\\":[],\\\"score\\\":[]}\",\"windowRectAndScore\":\"{\\\"rect\\\":{\\\"x1\\\":368,\\\"y1\\\":253,\\\"x2\\\":811,\\\"y2\\\":474},\\\"score\\\":[0]}\",\"cameraID\":482,\"cameraIP\":\"\",\"carspeed\":0,\"carLogo\":\"福田\",\"imagePath\":\"D:\\\\vlpr_result\\\\4\\\\粤B100EL_20150825143049133.jpg\",\"vehicleLeft\":295,\"vehicleTop\":179,\"vehicleRight\":854,\"vehicleBootom\":816" +
                "},{\"license\":\"粤B100EL\",\"plateType\":\"蓝牌\",\"plateColor\":\"蓝色\",\"confidence\":89,\"licenseAttribution\":\"深圳市\",\"imageUrl\":\"vlpr_result/3/粤B100EL_20150825140803241.jpg\",\"carColor\":\"黑\",\"resultTime\":1440482883000,\"location\":\"高塘岭镇郭亮路与宝粮路南向北\",\"direction\":\"未知\",\"longitude\":113.52104187011719,\"latitude\":23.044352663791813,\"path\":\"file://D:/upload\\\\29e15010-63a5-4496-8e8b-b4f5a5f8d2c6\",\"frame_index\":443,\"taskType\":3,\"resourceId\":null,\"resourceType\":3,\"serialNumber\":1818,\"vehicleKind\":\"轿车\",\"vehicleBrand\":\"雷克萨斯\",\"vehicleSeries\":\"ES\",\"vehicleStyle\":\"2010\",\"locationLeft\":495,\"locationTop\":691,\"locationRight\":617,\"locationBottom\":717,\"tag\":1,\"paper\":0,\"drop\":0,\"sun\":0,\"tagRectAndScore\":\"{\\\"rect\\\":[{\\\"x1\\\":407,\\\"y1\\\":323,\\\"x2\\\":429,\\\"y2\\\":345},{\\\"x1\\\":437,\\\"y1\\\":310,\\\"x2\\\":461,\\\"y2\\\":334}],\\\"score\\\":[65,41]}\",\"paperRectAndScore\":\"{\\\"rect\\\":[],\\\"score\\\":[]}\",\"dropRectAndScore\":\"{\\\"rect\\\":[],\\\"score\\\":[]}\",\"sunRectAndScore\":\"{\\\"rect\\\":[],\\\"score\\\":[]}\",\"windowRectAndScore\":\"{\\\"rect\\\":{\\\"x1\\\":368,\\\"y1\\\":253,\\\"x2\\\":811,\\\"y2\\\":474},\\\"score\\\":[0]}\",\"cameraID\":482,\"cameraIP\":\"\",\"carspeed\":0,\"carLogo\":\"福田\",\"imagePath\":\"D:\\\\vlpr_result\\\\3\\\\粤B100EL_20150825140803241.jpg\",\"vehicleLeft\":295,\"vehicleTop\":179,\"vehicleRight\":854,\"vehicleBootom\":816}]";
		System.out.println(str);
		JsonUtils.toObj(str, User[].class);
		System.out.println(JsonUtils.toObjArr(str, ResultVo[].class).length);
	}
}
