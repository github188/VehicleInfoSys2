package cn.jiuling.vehicleinfosys2.util;

import java.util.HashMap;

/**
 * 用来存放服务器上传文件状态的缓存.
 *
 * key为一个唯一id,用于标识一次上传操作,由客户端传过来
 * value为用户已处理的文件数目
 * Created by Administrator on 2015/6/11 0011.
 */
public class UploadStatusCache {
    private static HashMap<String,Object> cache=new HashMap<String,Object>();

    public static void push(String uuid,Object o){
        cache.put(uuid,o);
    }
    public static Object get(String uuid){
        return cache.get(uuid);
    }
    public static void remove(String uuid){
        cache.remove(uuid);
    }
}
