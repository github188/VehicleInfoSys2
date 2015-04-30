package cn.jiuling.vehicleinfosys2.vo;

import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2015/4/29 0029.
 */
public class MapSettings {
    private String centerLat;
    private String centerLng;
    private static MapSettings instance;

    public String getCenterLng() {
        return centerLng;
    }

    public void setCenterLng(String centerLng) {
        this.centerLng = centerLng;
    }

    public String getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(String centerLat) {
        this.centerLat = centerLat;
    }

    private MapSettings() {

    }

    public static MapSettings getInstance() {
        if (instance == null) {
            instance = new MapSettings();
        }
        return instance;
    }

    @Override
    public String toString() {
        if (StringUtils.isEmpty(centerLat) || StringUtils.isEmpty(centerLng)) {
            return "";
        }
        return "mapSettings.centerLat=" + centerLat + ";mapSettings.centerLng=" + centerLng + ";";
    }
}
