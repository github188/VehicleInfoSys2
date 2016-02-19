package cn.jiuling.vehicleinfosys2.vo;

public class CameraQuery {
	private Double minLat;
	private Double minLng;
	private Double maxLat;
	private Double maxLng;

	public Double getMinLat() {
		return minLat;
	}

	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}

	public Double getMinLng() {
		return minLng;
	}

	public void setMinLng(Double minLng) {
		this.minLng = minLng;
	}

	public Double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}

	public Double getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(Double maxLng) {
		this.maxLng = maxLng;
	}

	@Override
	public String toString() {
		return "CameraQuery [maxLat=" + maxLat + ", maxLng=" + maxLng + ", minLat=" + minLat + ", minLng=" + minLng + "]";
	}

}
