package cn.jiuling.vehicleinfosys2.vo;

/**
 * 感兴趣区域
 * @author Administrator
 *
 */
public class FollowArea {
	
	private short x0 = 0;
	private short y0 = 0;
	private short x1 = 0;
	private short y1 = 0;
	private short width = 0;
	private short height = 0;
	
	public FollowArea() {
		super();
	}
	
	
	public FollowArea(short x0, short y0, short x1, short y1, short width, short height) {
	    super();
	    this.x0 = x0;
	    this.y0 = y0;
	    this.x1 = x1;
	    this.y1 = y1;
	    this.width = width;
	    this.height = height;
    }


	public short getX0() {
		return x0;
	}

	public void setX0(short x0) {
		this.x0 = x0;
	}

	public short getY0() {
		return y0;
	}

	public void setY0(short y0) {
		this.y0 = y0;
	}

	public short getX1() {
		return x1;
	}

	public void setX1(short x1) {
		this.x1 = x1;
	}

	public short getY1() {
		return y1;
	}

	public void setY1(short y1) {
		this.y1 = y1;
	}

	public short getWidth() {
		return width;
	}

	public void setWidth(short width) {
		this.width = width;
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}
}
