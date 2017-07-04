package com.mystify.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import net.sf.json.JSONNull;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * 
 * @description json工具类
 * @date 2014-3-24 上午11:46:03
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class JsonUtils {
 	 
	private static JsonFactory jsonFactory = new JsonFactory();
	
	private static ObjectMapper mapper = null;
	
	private static ObjectMapper optimizedMapper = null;
	
	static {
		jsonFactory.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		jsonFactory.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper = new ObjectMapper(jsonFactory);
		mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		
		JsonFactory optFac = new JsonFactory();
		optFac.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		optFac.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); 
		optimizedMapper = new ObjectMapper(optFac);
		optimizedMapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		optimizedMapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
	}	
	
	public static JsonConfig getDefaultConfig(){
		return getDefaultConfig("yyyy-MM-dd HH:mm:ss");
	}
	
	public static JsonConfig getDefaultConfig(String datePattern) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{""});
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor(datePattern));
        jsonConfig.setJsonPropertyFilter(new IgnoreNullProcessorImpl());
        return jsonConfig;
    }
	
	
 
	/**
	 * 将json转成java bean
	 * @param <T> - 多态类型
	 * @param json - json字符串
	 * @param clazz - java bean类型(Class)
	 * @return - java bean对象
	 */
	public static <T> T toBean(String json, Class clazz) {
		
		T rtv = null;
		try { 
			rtv = (T) mapper.readValue(json, clazz); 
		} catch (Exception ex) {   
			String cause = String.format("json to bean error:[%s],json:[%s]", ex.getMessage(), json); 
			throw new IllegalArgumentException(cause, ex);
		}
		return (T)rtv;		
	}	
	 
		/**
		 * 将输入流转成java bean
		 * @param <T> - 多态类型
		 * @param reader - 读取流
		 * @param clazz - java bean类型(Class)
		 * @return - java bean对象
		 */
		public static <T> T toBean(Reader reader, Class clazz) {
			
			T rtv = null;
			try {  
				rtv = (T)mapper.readValue(reader, clazz); 
			} catch (Exception ex) {  
				String cause = String.format("json to bean error:[%s]", ex.getMessage()); 
				throw new IllegalArgumentException(cause, ex);
			}
			return (T)rtv;		
		}
 
	/**
	 * 将输入流转成java bean
	 * @param <T> - 多态类型
	 * @param in - 输入流
	 * @param clazz - java bean类型(Class)
	 * @return - java bean对象
	 */
	public static <T> T toBean(InputStream in, Class clazz) {
		
		T rtv = null;
		try {  
			rtv = (T)mapper.readValue(in, clazz); 
		} catch (Exception ex) {  
			throw new IllegalArgumentException("json-stream to bean error", ex);
		}
		return (T)rtv;		
	}
	
	/**
	 * 将java bean转成json
	 * @param bean - java bean
	 * @return - json 字符串
	 */
	public static String toJson(Object bean) {
		
		String rtv = null;
		try { 
			rtv = mapper.writeValueAsString(bean);
		} catch (Exception ex) {  
			String cause = String.format("bean to json error:[%s],bean-class:[%s]", ex.getMessage(), bean.getClass()); 
			throw new IllegalArgumentException(cause, ex);
		}
		return rtv;		
	}
	
	/**
	 * 将java bean转成json
	 * @param bean - java bean
	 * @param minimized - true,最小化的. 不对空值的属性进行序列化
	 * @return - json 字符串
	 */
	public static String toJson(Object bean, boolean minimized) {
		
		if (!minimized) {
			return toJson(bean);
		}
		String rtv = null;
		try { 
			rtv = optimizedMapper.writeValueAsString(bean);
		} catch (Exception ex) {  
			ex.printStackTrace();
			String cause = String.format("bean to minimized json error:[%s],bean-class:[%s]", ex.getMessage(), bean.getClass()); 
			throw new IllegalArgumentException(cause, ex);
		}
		return rtv;	
	}

	/**
	 * 将java bean转成xml, 根节点名称为'root'
	 * @param bean - java bean
	 * @return - xml字符串
	 */
	@Deprecated
	public static String toXml(Object bean) {
		String rt = "root";
		return toXml(rt, bean);
	}

	/**
	 * 将java bean转成xml
	 * @param rootTag - xml根节点名称
	 * @param bean - java bean
	 * @return - xml字符串
	 */
	@Deprecated
	public static String toXml(String rootTag, Object bean) {
		
		String rtv = null;
		try {
			XmlGenerator xgen = new XmlGenerator();  
			xgen.currentTag = rootTag;
			mapper.writeValue(xgen, bean);
			rtv = xgen.sb.toString();
		} catch (Exception ex) {  
			throw new IllegalArgumentException("bean to xml error", ex);
		}
		return rtv;				
	}
		
	/**
	 * 防止非法实例化
	 */
	private JsonUtils() { }
	
	/**
	 * jackson JSON bean to xml GENERATOR
	 * @creator yuyoo(zhandulin@aspirehld.com)
	 * @author www.aspirehld.com
	 * @date 2011-6-18 下午01:01:58
	 * @version 
	 * @since
	 */
	private static class XmlGenerator extends JsonGenerator {
		
		@SuppressWarnings("unused")
		private Feature f;
		
		private boolean enable;
		
		private ObjectCodec oc;
		
		private StringBuilder sb;
		
		private String currentTag = "root";
		
		private boolean inArray = false;
		
		@SuppressWarnings("unused")
		private boolean inObject = false;
				
		private Stack<String> at = new Stack<String>();
		
		private Stack<String> ot = new Stack<String>(); 
		
		XmlGenerator() {
			 sb = new StringBuilder(256);
			 //sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		}

		@Override
		public JsonGenerator enable(Feature paramFeature) { 
			
			enable = true;
			this.f = paramFeature;
			return this;
		}

		@Override
		public JsonGenerator disable(Feature paramFeature) { 
			
			enable = false;
			this.f = paramFeature;
			return this;
		}

		@Override
		public boolean isEnabled(Feature paramFeature) { 
			return enable;
		}

		@Override
		public JsonGenerator setCodec(ObjectCodec paramObjectCodec) { 
			
			this.oc = paramObjectCodec;
			return this;
		}

		@Override
		public ObjectCodec getCodec() { 
			return this.oc;
		}

		@Override
		public JsonGenerator useDefaultPrettyPrinter() { 
			return this;
		}

		@Override
		public void writeStartArray() throws IOException,
				JsonGenerationException { 
			
			inArray = true;
			this.at.push(this.currentTag);
		}

		@Override
		public void writeEndArray() throws IOException, JsonGenerationException {
			
			inArray = false;
			this.at.pop();
		}

		@Override
		public void writeStartObject() throws IOException,
				JsonGenerationException { 
			
			inObject = true;
			this.ot.push(this.currentTag);
			sb.append("<").append(this.currentTag).append(">");
		}

		@Override
		public void writeEndObject() throws IOException,
				JsonGenerationException {
			
			inObject = false; 
			String tag = this.ot.pop();
			sb.append("</").append(tag).append(">");		
			this.currentTag = tag;
		}

		@Override
		public void writeFieldName(String fn) throws IOException,
				JsonGenerationException {
			this.currentTag = fn;
		}

		@Override
		public void writeString(String ps) throws IOException,
				JsonGenerationException {
			
			String fn = null;
			if (inArray) {
				fn = this.at.peek(); 
			} else {
				fn = this.currentTag;
			}

			sb.append("<").append(fn).append(">");
			sb.append(ps);
			sb.append("</").append(fn).append(">");
		}

		@Override
		public void writeString(char[] arr, int start,
				int end) throws IOException, JsonGenerationException { 
			
			String str = new String(arr, start, end);
			writeString(str);
		}

		@Override
		public void writeRaw(String ps) throws IOException,
				JsonGenerationException {
			sb.append(ps);
		}

		@Override
		public void writeRaw(String ps, int start, int end)
				throws IOException, JsonGenerationException { 
			sb.append(ps.substring(start, end));
		}

		@Override
		public void writeRaw(char[] arr, int start,
				int end) throws IOException, JsonGenerationException { 	
			sb.append(arr, start, end);
		}

		@Override
		public void writeRaw(char c) throws IOException,
				JsonGenerationException {
			sb.append(c);
		}

		@Override
		public void writeRawValue(String v) throws IOException,
				JsonGenerationException { 
			
			String fn = null;
			if (inArray) {
				fn = this.at.peek();
			} else {
				fn = this.currentTag;
			}
			sb.append("<").append(fn).append(">");
			sb.append(v);
			sb.append("</").append(fn).append(">");
		}

		@Override
		public void writeRawValue(String v, int start,
				int end) throws IOException, JsonGenerationException {

			String fn = null;
			if (inArray) {
				fn = this.at.peek();
			} else {
				fn = this.currentTag;
			}
			sb.append("<").append(fn).append(">");
			sb.append(v, start, end);
			sb.append("</").append(fn).append(">");
		}

		@Override
		public void writeRawValue(char[] arr, int start,
				int end) throws IOException, JsonGenerationException { 
			
			String v = new String(arr, start, end);
			writeRawValue(v);
		}

		@Override
		public void writeBinary(Base64Variant var,
				byte[] bs, int start, int end)
				throws IOException, JsonGenerationException { 
			
			String v = new String(bs, start, end);
			writeRawValue(v);
		}

		@Override
		public void writeNumber(int v) throws IOException,
				JsonGenerationException {
			
			writeNumber((long)v);
		}

		@Override
		public void writeNumber(long v) throws IOException,
				JsonGenerationException {

			String fn = null;
			if (inArray) {
				fn = this.at.peek();
			} else {
				fn = this.currentTag;
			}
			sb.append("<").append(fn).append(">");
			sb.append(v);
			sb.append("</").append(fn).append(">");
		}

		@Override
		public void writeNumber(BigInteger v) throws IOException,
				JsonGenerationException {
			
			long lv = v.longValue();
			writeNumber(lv);
		}

		@Override
		public void writeNumber(double v) throws IOException,
				JsonGenerationException {

			String fn = null;
			if (inArray) {
				fn = this.at.peek();
			} else {
				fn = this.currentTag;
			}
			sb.append("<").append(fn).append(">");
			sb.append(v);
			sb.append("</").append(fn).append(">");
		}

		@Override
		public void writeNumber(float v) throws IOException,
				JsonGenerationException {

			writeNumber((double)v);
		}

		@Override
		public void writeNumber(BigDecimal v) throws IOException,
				JsonGenerationException {
			
			double dv = v.doubleValue();
			writeNumber(dv);
		}

		@Override
		public void writeNumber(String v) throws IOException,
				JsonGenerationException, UnsupportedOperationException {
			 
			long lv = Long.parseLong(v);
			writeNumber(lv);
		}

		@Override
		public void writeBoolean(boolean v) throws IOException,
				JsonGenerationException {

			String fn = null;
			if (inArray) {
				fn = this.at.peek();
			} else {
				fn = this.currentTag;
			}
			sb.append("<").append(fn).append(">");
			sb.append(Boolean.toString(v));
			sb.append("</").append(fn).append(">");
		}

		@Override
		public void writeNull() throws IOException, JsonGenerationException {

			String fn = null;
			if (inArray) {
				fn = this.at.peek();
			} else {
				fn = this.currentTag;
			}
			sb.append("<").append(fn).append(">"); 
			sb.append("</").append(fn).append(">");			
		}

		@Override
		public void writeObject(Object v) throws IOException,
				JsonProcessingException { 
			sb.append(v);
		}

		@Override
		public void writeTree(JsonNode v) throws IOException,
				JsonProcessingException { 
			throw new RuntimeException("writeTree v:" + v);
		}

		@Override
		public void copyCurrentEvent(JsonParser p)
				throws IOException, JsonProcessingException {
			
		}

		@Override
		public void copyCurrentStructure(JsonParser paramJsonParser)
				throws IOException, JsonProcessingException {
			
		}

		@Override
		public JsonStreamContext getOutputContext() {
			return null;
		}

		@Override
		public void flush() throws IOException {
			
		}

		@Override
		public boolean isClosed() {
			return false;
		}

		@Override
		public void close() throws IOException {
			
		}

		@Override
		public void writeRawUTF8String(byte[] arg0, int arg1, int arg2)
				throws IOException, JsonGenerationException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void writeUTF8String(byte[] arg0, int arg1, int arg2)
				throws IOException, JsonGenerationException {
			// TODO Auto-generated method stub
			
		}		 
	}
	
	static public class IgnoreNullProcessorImpl implements PropertyFilter {
		@Override
		public boolean apply(Object source, String name, Object value) {
			 if(value == null){
				 return true;  
			 }
			 return false;
		}  
	}
	
	static class JsonDateValueProcessor implements JsonValueProcessor{
		private String format = "yyyy-MM-dd HH:mm:ss";

		public JsonDateValueProcessor() {
		}

		public JsonDateValueProcessor(String format) {
			this.format = format;
		}

		@Override
		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			String[] obj = {};
			if (value instanceof Date[]) {
				SimpleDateFormat sf = new SimpleDateFormat(format);
				Date[] dates = (Date[]) value;
				obj = new String[dates.length];
				for (int i = 0; i < dates.length; i++) {
					obj[i] = sf.format(dates[i]);
				}
			}
			return obj;
		}

		@Override
		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			if(value==null){
				return JSONNull.getInstance(); 
			}
			if (value instanceof Date) {
				String str = new SimpleDateFormat(format).format((Date) value);
				return str;
			}
			return value == null ? null : value.toString();
		}
	}
}