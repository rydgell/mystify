package com.mystify.ums.utils;

import java.util.ArrayList;
import java.util.List;

public class SqlUtil<T> {
	private List<Condition> conditionList =  new ArrayList<Condition>();
	private List<Object> paramList = new ArrayList<Object>();
	private String orderStr = "";
	
	@SuppressWarnings("unchecked")
	public void putCondition(SqlUtil.Symbol symbol , String name, String con){
		@SuppressWarnings("rawtypes")
		SqlUtil.Condition temp = new SqlUtil.Condition();
		temp.setCondition(con);
		temp.setKey(name);
		temp.setSymbol(symbol.getValue());
		temp.setNoValue(true);
		//paramList.add(temp.getValue());
		conditionList.add(temp);
	}
	
	@SuppressWarnings("unchecked")
	public void putCondition(SqlUtil.Symbol symbol , String name, SqlUtil.Conditions condition){
		@SuppressWarnings("rawtypes")
		SqlUtil.Condition temp = new SqlUtil.Condition();
		temp.setCondition(condition.getValue());
		temp.setKey(name);
		temp.setSymbol(symbol.getValue());
		temp.setNoValue(true);
		//paramList.add(temp.getValue());
		conditionList.add(temp);
	}
	
	@SuppressWarnings("unchecked")
	public void putCondition(SqlUtil.Symbol symbol , String name, SqlUtil.Conditions condition ,  Object object){
		@SuppressWarnings("rawtypes")
		SqlUtil.Condition temp = new SqlUtil.Condition();
		temp.setCondition(condition.getValue());
		temp.setKey(name);
		temp.setSymbol(symbol.getValue());
		temp.setValue(object);
		temp.setNoValue(condition.isNoValue());
		paramList.add(temp.getValue());
		conditionList.add(temp);
	}

	public void setOrder(String order){
		orderStr = order;
	}
	
	public void addOrder(String order){
		if(!"".equals(orderStr)){
			orderStr += " , " + order;
		}else{
			orderStr = order;
		}
	}
	
	public Object[] getValues(){
		return paramList.toArray();
	}
	
	public String toSql(){
		StringBuffer buffer = new StringBuffer();
		if(!conditionList.isEmpty())buffer.append(" WHERE ");
		for(int i = 0 , len = conditionList.size() ; i < len ; i++){
			SqlUtil.Condition temp = conditionList.get(i);
			if(i == 0){
				buffer.append(temp.getKey() + " " + temp.getCondition() + (!temp.isNoValue() ? " ? " : " "));
			}else{
				buffer.append(" " + temp.getSymbol()+ " " + temp.getKey() + " " + temp.getCondition() + (!temp.isNoValue() ? " ? " : " "));
			}
		}
		if(orderStr.equals("")){
			return buffer.toString();
		}
		return buffer.toString() + " order by " + orderStr;
	}
	
	public String toNoWhereSql(){
		StringBuffer buffer = new StringBuffer();
		if(!conditionList.isEmpty())buffer.append(" AND ");
		
		for(int i = 0 , len = conditionList.size() ; i < len ; i++){
			SqlUtil.Condition temp = conditionList.get(i);
			if(i == 0){
				buffer.append(temp.getKey() + " " + temp.getCondition() + (!temp.isNoValue() ? " ? " : " "));
			}else{
				buffer.append(" " + temp.getSymbol()+ " " + temp.getKey() + " "  + " " + temp.getCondition() + (!temp.isNoValue() ? " ? " : " "));
			}
		}
		if(orderStr.equals("")){
			return buffer.toString();
		}
		return buffer.toString() + " order by " + orderStr;
	}	
	class Condition{
		private String key;
		private String condition;
		private String symbol;
		private Object value;
		private boolean noValue = false;
		
		public boolean isNoValue() {
			return noValue;
		}
		public void setNoValue(boolean noValue) {
			this.noValue = noValue;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		
	}
	
	public enum Symbol{
		AND("and"),
		OR("or");
		private String value;
		private Symbol(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public enum Conditions{
		eq("="),
		gt(">"),
		egt(">="),
		lt("<"),
		elt("<="),
		like("like"),
		isNUll("is null",true);
		
		private String value;
		private boolean noValue = false;
		private Conditions(String value){
			this.value = value;
		}
		private Conditions(String value ,boolean noValue){
			this.value = value;
			this.noValue = noValue;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public boolean isNoValue() {
			return noValue;
		}
		public void setNoValue(boolean noValue) {
			this.noValue = noValue;
		}
		
	}
}
