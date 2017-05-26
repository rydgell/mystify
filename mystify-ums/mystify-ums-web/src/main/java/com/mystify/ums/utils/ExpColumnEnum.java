package com.mystify.ums.utils;


/**
 * 经验分类项
 * @author Administrator
 *
 */
public enum ExpColumnEnum {
	HEALTH("健康安全"),
	VERBAL("语言学能"),
	KINEMATIC("运动学能"),
	EMOTION("情绪控制"),
	INTERACTION("社会交往"),
	BEHAVIOR("行为控制");
	
	private final String name;
	
	ExpColumnEnum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static ExpColumnEnum getByName(String name ){
		for(ExpColumnEnum rt:ExpColumnEnum.values()){
			if(rt.getName().equalsIgnoreCase(name)){
				return rt;
			}
		}
		return null;
	} 
}
