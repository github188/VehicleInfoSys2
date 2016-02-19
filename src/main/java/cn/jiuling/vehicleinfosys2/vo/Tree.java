package cn.jiuling.vehicleinfosys2.vo;

import java.util.List;

public class Tree {

	private Integer id;
	private Integer pid;
	private String text;
	private Object attributes;
	private List<Tree> children;
	private Boolean checked;
	private String state;
	private String ancestorIds;
	private String iconCls;

	public Tree() {
		super();
	}

	public Tree(Integer id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public Tree(Integer id, String text, String ancestorIds) {
		super();
		this.id = id;
		this.text = text;
		this.ancestorIds = ancestorIds;
	}

	public Tree(Integer id, String text, Long Num) {
		super();
		this.id = id;
		this.text = text;
		this.state = Num > 0 ? "closed" : "open";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getChecked() {
		return checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setAncestorIds(String ancestorIds) {
		this.ancestorIds = ancestorIds;
	}

	public String getAncestorIds() {
		return ancestorIds;
	}

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
