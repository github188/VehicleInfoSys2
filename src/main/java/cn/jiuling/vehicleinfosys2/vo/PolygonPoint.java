package cn.jiuling.vehicleinfosys2.vo;

/**
 * 包含x,y坐标的封装类
 * Created by phq on 2015/5/21 0021.
 */
public class PolygonPoint {
    private int x;
    private int y;

    public PolygonPoint() {
    }
    public PolygonPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public PolygonPoint(String x, String y) {
        this.x = Integer.valueOf(x);
        this.y = Integer.valueOf(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
