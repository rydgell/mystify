package com.mystify.ums.model;

import java.util.List;

public class ParentMenu {
	
	private String menuid;
	
	private String icon = "icon-sys"; 
	
	private String menuname;
	
	private Integer tree = 0;
	
	private List<SubMenu> menus;

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Integer getTree() {
		return tree;
	}

	public void setTree(Integer tree) {
		this.tree = tree;
	}

	public List<SubMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<SubMenu> menus) {
		this.menus = menus;
	}
	
	
}
