package cn.jiuling.vehicleinfosys2.util;

import java.util.Random;

/**
 * 随机字符串
 * @author wsy 2015/4/15
 *
 */
public class RandomString {
	
	 private static final char[] symbols = new char[36];

	  static {
	    for (int idx = 0; idx < 10; ++idx)
	      symbols[idx] = (char) ('0' + idx);
	    for (int idx = 10; idx < 36; ++idx)
	      symbols[idx] = (char) ('a' + idx - 10);
	  }

	  private final Random random = new Random();

	  private final char[] buf;

	  public RandomString(int length)
	  {
	    if (length < 1)
	      throw new IllegalArgumentException("length < 1: " + length);
	    buf = new char[length];
	  }

	  public String nextString()
	  {
	    for (int idx = 0; idx < buf.length; ++idx) 
	      buf[idx] = symbols[random.nextInt(symbols.length)];
	    return new String(buf);
	  }

	public static void main(String[] args) {
		RandomString randomString=new RandomString(6);
		System.out.println(randomString.nextString());
	}

}
