package cn.jiuling.vehicleinfosys2.service;

/**
 * 识别数Service
 *
 * @author wangrb
 * @date 2015-12-1
 */
public interface RecognumService {

	/**
	 * 检查是否超过设定的数量
	 * @return  是否通过（true:没有超过限量  false:超过限量）
	 */
	public boolean checkRecognum();

}
