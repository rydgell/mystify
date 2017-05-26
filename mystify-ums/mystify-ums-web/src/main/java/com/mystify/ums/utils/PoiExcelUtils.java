package com.mystify.ums.utils;

 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
 
import net.sf.json.JSONArray;
 
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

 
 
/**
 * Excel工具类
 */
@Deprecated
public class PoiExcelUtils {
    private static NumberFormat format              = NumberFormat.getInstance();
 
    /** 日志 */
    private static final Logger LOGGER              = Logger.getLogger(PoiExcelUtils.class);
    /** 列默认宽度 */
    private static final int    DEFAUL_COLUMN_WIDTH = 4000;
 
    /**
     * 1.创建 workbook
     * 
     * @return {@link HSSFWorkbook}
     */
    private HSSFWorkbook getHSSFWorkbook() {
        LOGGER.info("【创建 workbook】");
        return new HSSFWorkbook();
    }
 
    /**
     * 2.创建 sheet
     * 
     * @param hssfWorkbook {@link HSSFWorkbook}
     * @param sheetName sheet 名称
     * @return {@link HSSFSheet}
     */
    private HSSFSheet getHSSFSheet(HSSFWorkbook hssfWorkbook, String sheetName) {
        LOGGER.info("【创建 sheet】sheetName ： " + sheetName);
        return hssfWorkbook.createSheet(sheetName);
    }
 
    /**
     * 3.写入表头信息
     * 
     * @param hssfWorkbook {@link HSSFWorkbook}
     * @param hssfSheet {@link HSSFSheet}
     * @param headers 列标题，数组形式，
     *                 如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
     *                 其中参数@columnWidth可选，columnWidth为整型数值
     * @param title 标题
     */
    private void writeHeader(HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, String[] headers,
                             String title) {
        LOGGER.info("【写入表头信息】");
 
        // 头信息处理
        String[] newHeaders = headersHandler(headers);
 
        // 初始化标题和表头单元格样式
        HSSFCellStyle titleCellStyle = createTitleCellStyle(hssfWorkbook);
        // 标题栏
        //HSSFRow titleRow = hssfSheet.createRow(0);
       // titleRow.setHeight((short) 500);
       // HSSFCell titleCell = titleRow.createCell(0);
        // 设置标题文本
       // titleCell.setCellValue(new HSSFRichTextString(title));
        // 设置单元格样式
       // titleCell.setCellStyle(titleCellStyle);
 
        // 处理单元格合并，四个参数分别是：起始行，终止行，起始行，终止列
       // hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 0,
         //   (short) (newHeaders.length - 1)));
 
        // 设置合并后的单元格的样式
       // titleRow.createCell(newHeaders.length - 1).setCellStyle(titleCellStyle);
 
        // 表头
        HSSFRow headRow = hssfSheet.createRow(0);
        headRow.setHeight((short) 500);
        HSSFCell headCell = null;
        String[] headInfo = null;
        // 处理excel表头
        for (int i = 0, len = newHeaders.length; i < len; i++) {
            headInfo = newHeaders[i].split("@");
            headCell = headRow.createCell(i);
            headCell.setCellValue(headInfo[0]);
            headCell.setCellStyle(titleCellStyle);
            // 设置列宽度
            setColumnWidth(i, headInfo, hssfSheet);
        }
    }
 
    /**
     * 头信息校验和处理
     * @param headers
     */
    private String[] headersHandler(String[] headers) {
        List<String> newHeaders = new ArrayList<String>();
        for (String string : headers) {
            if (StringUtils.isNotBlank(string)) {
                newHeaders.add(string);
            }
        }
        int size = newHeaders.size();
 
        return newHeaders.toArray(new String[size]);
    }
 
    /**
     * 4.写入内容部分
     * 
     * @param hssfWorkbook {@link HSSFWorkbook}
     * @param hssfSheet {@link HSSFSheet}
     * @param headers 列标题，数组形式，
     *                 如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
     *                 其中参数@columnWidth可选，columnWidth为整型数值
     * @param dataList 要导出的数据集合
     * @throws Exception 
     */
    private void writeContent(HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, String[] headers,
                              List<?> dataList) throws Exception {
        LOGGER.info("【写入Excel内容部分】");
        // 2015-8-13 增加，当没有数据的时候，把原来抛异常的方式修改成返回一个只有头信息，没有数据的空Excel
        if (CollectionUtils.isEmpty(dataList)) {
            LOGGER.warn("【没有内容数据】");
            return;
        }
        HSSFRow row = null;
        HSSFCell cell = null;
        // 单元格的值
        Object cellValue = null;
        // 数据写入行索引
        int rownum = 1;
        // 单元格样式
        HSSFCellStyle cellStyle = createContentCellStyle(hssfWorkbook);
        // 遍历集合，处理数据
        for (int j = 0, size = dataList.size(); j < size; j++) {
            row = hssfSheet.createRow(rownum);
            for (int i = 0, len = headers.length; i < len; i++) {
                cell = row.createCell(i);
                cellValue = ReflectUtils.getCellValue(dataList.get(j), headers[i].split("@")[1]);
                cellValueHandler(cell, cellValue);
                cell.setCellStyle(cellStyle);
            }
            rownum++;
        }
    }
 
    /**
     * 设置列宽度
     * @param i 列的索引号
     * @param headInfo 表头信息，其中包含了用户需要设置的列宽
     */
    private void setColumnWidth(int i, String[] headInfo, HSSFSheet hssfSheet) {
        if (headInfo.length < 3) {
            // 用户没有设置列宽，使用默认宽度
            hssfSheet.setColumnWidth(i, DEFAUL_COLUMN_WIDTH);
            return;
        }
        if (StringUtils.isBlank(headInfo[2])) {
            // 使用默认宽度
            hssfSheet.setColumnWidth(i, DEFAUL_COLUMN_WIDTH);
            return;
        }
        // 使用用户设置的列宽进行设置
        hssfSheet.setColumnWidth(i, Integer.parseInt(headInfo[2]));
    }
 
    /**
     * 单元格写值处理器
     * @param {{@link HSSFCell}
     * @param cellValue 单元格值
     */
    private void cellValueHandler(HSSFCell cell, Object cellValue) {
        // 2015-8-13 修改，判断cellValue是否为空，否则在cellValue.toString()会出现空指针异常
        if (cellValue == null) {
            cell.setCellValue("");
            return;
        }
        if (cellValue instanceof String) {
            cell.setCellValue((String) cellValue);
        } else if (cellValue instanceof Boolean) {
            cell.setCellValue((Boolean) cellValue);
        } else if (cellValue instanceof Calendar) {
            cell.setCellValue((Calendar) cellValue);
        } else if (cellValue instanceof Double) {
            cell.setCellValue((Double) cellValue);
        } else if (cellValue instanceof Integer || cellValue instanceof Long
                   || cellValue instanceof Short || cellValue instanceof Float) {
            cell.setCellValue((Double.parseDouble(cellValue.toString())));
        } else if (cellValue instanceof HSSFRichTextString) {
            cell.setCellValue((HSSFRichTextString) cellValue);
        }
        cell.setCellValue(cellValue.toString());
    }
 
    /**
     * 创建标题和表头单元格样式
     * @param hssfWorkbook {@link HSSFWorkbook}
     * @return {@link HSSFCellStyle}
     */
    private HSSFCellStyle createTitleCellStyle(HSSFWorkbook hssfWorkbook) {
        LOGGER.info("【创建标题和表头单元格样式】");
        // 单元格的样式
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        // 设置字体样式，改为不变粗
        HSSFFont font = hssfWorkbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        // 单元格垂直居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        // 设置通用的单元格属性
        setCommonCellStyle(cellStyle);
        return cellStyle;
    }
 
    /**
     * 创建内容单元格样式
     * @param hssfWorkbook {@link HSSFWorkbook}
     * @return {@link HSSFCellStyle}
     */
    private HSSFCellStyle createContentCellStyle(HSSFWorkbook hssfWorkbook) {
        LOGGER.info("【创建内容单元格样式】");
        // 单元格的样式
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        // 设置字体样式，改为不变粗
        HSSFFont font = hssfWorkbook.createFont();
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
        // 设置单元格自动换行
        cellStyle.setWrapText(true);
        // 单元格垂直居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        //水平居中
        //        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置通用的单元格属性
        setCommonCellStyle(cellStyle);
        return cellStyle;
    }
 
    /**
     * 设置通用的单元格属性
     * @param cellStyle 要设置属性的单元格
     */
    private void setCommonCellStyle(HSSFCellStyle cellStyle) {
        // 居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    }
 
    /**
     * 将生成的Excel输出到指定目录
     * @param hssfWorkbook {@link HSSFWorkbook}
     * @param filePath 文件输出目录，包括文件名（.xls）
     */
    private void write2FilePath(HSSFWorkbook hssfWorkbook, String filePath) {
        LOGGER.info("【将生成的Excel输出到指定目录】filePath ：" + filePath);
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
            hssfWorkbook.write(fileOut);
        } catch (Exception e) {
            LOGGER.error("【将生成的Excel输出到指定目录失败】", e);
            throw new RuntimeException("将生成的Excel输出到指定目录失败");
        } finally {
            IOUtils.closeQuietly(fileOut);
        }
    }
 
    /**
     * 生成Excel，存放到指定目录
     * @param sheetName sheet名称
     * @param title 标题
     * @param filePath 要导出的Excel存放的文件路径
     * @param headers 列标题，数组形式，
     *                 如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
     *                 其中参数@columnWidth可选，columnWidth为整型数值
     * @param dataList 要导出数据的集合
     * @throws Exception
     */
    public static void createExcel2FilePath(String sheetName, String title, String filePath,
                                            String[] headers, List<?> dataList) throws Exception {
        LOGGER
            .info("【生成Excel,并存放到指定文件夹目录下】sheetName : " + sheetName + " , title : " + title
                  + " , filePath : " + filePath + " , headers : " + JSONArray.fromObject(headers));
        if (ArrayUtils.isEmpty(headers)) {
            LOGGER.warn("【表头为空】");
            throw new RuntimeException("表头不能为空");
        }
 
        PoiExcelUtils poiExcelUtil = new PoiExcelUtils();
        // 1.创建 Workbook
        HSSFWorkbook hssfWorkbook = poiExcelUtil.getHSSFWorkbook();
        // 2.创建 Sheet
        HSSFSheet hssfSheet = poiExcelUtil.getHSSFSheet(hssfWorkbook, sheetName);
        // 3.写入 head
        poiExcelUtil.writeHeader(hssfWorkbook, hssfSheet, headers, title);
        // 4.写入内容
        poiExcelUtil.writeContent(hssfWorkbook, hssfSheet, headers, dataList);
        // 5.保存文件到filePath中
        poiExcelUtil.write2FilePath(hssfWorkbook, filePath);
    }
 
    /**
     * 生成Excel的WorkBook，用于导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param headers 列标题，数组形式，
     *                 如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
     *                 其中参数@columnWidth可选，columnWidth为整型数值
     * @param dataList 要导出数据的集合
     * @throws Exception
     */
    public static HSSFWorkbook createExcel2Export(String sheetName, String title, String[] headers,
                                                  List<?> dataList) throws Exception {
 
        LOGGER.info("【生成Excel的WorkBook，用于导出Excel】sheetName : " + sheetName + " , title : " + title
                    + "  , headers : " + JSONArray.fromObject(headers));
        
        if (ArrayUtils.isEmpty(headers)) {
            LOGGER.warn("【表头为空】");
            throw new RuntimeException("表头不能为空");
        }
        PoiExcelUtils poiExcelUtil = new PoiExcelUtils();
        // 1.创建 Workbook
        HSSFWorkbook hssfWorkbook = poiExcelUtil.getHSSFWorkbook();
        // 2.创建 Sheet
        HSSFSheet hssfSheet = poiExcelUtil.getHSSFSheet(hssfWorkbook, sheetName);
        // 3.写入 head
        poiExcelUtil.writeHeader(hssfWorkbook, hssfSheet, headers, title);
        // 4.写入内容
        poiExcelUtil.writeContent(hssfWorkbook, hssfSheet, headers, dataList);
 
        return hssfWorkbook;
    }
 
    /**
     * 创建知识库TOP3系统渠道统计的Excel数据
     * @param sheetName sheet名称
     * @param title 标题
     * @param headers 列标题，数组形式，
     *                 如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
     *                 其中参数@columnWidth可选，columnWidth为整型数值
     * @param dataList 要导出数据的集合
     */
    public static HSSFWorkbook createknowledgeTop3Excel(String sheetName, String title,
                                                        String[] headers, List<List<?>> dataList) {
        LOGGER.info("【生成Excel的WorkBook，用于导出Excel】sheetName : " + sheetName + " , title : " + title
                    + "  , headers : " + JSONArray.fromObject(headers));
        if (ArrayUtils.isEmpty(headers)) {
            LOGGER.warn("【表头为空】");
            throw new RuntimeException("表头不能为空");
        }
        PoiExcelUtils poiExcelUtil = new PoiExcelUtils();
        // 1.创建 Workbook
        HSSFWorkbook hssfWorkbook = poiExcelUtil.getHSSFWorkbook();
        // 2.创建 Sheet
        HSSFSheet hssfSheet = poiExcelUtil.getHSSFSheet(hssfWorkbook, sheetName);
        // 3.写入 head
        poiExcelUtil.writeHeader(hssfWorkbook, hssfSheet, headers, title);
        // 4.写入内容
        try {
            poiExcelUtil.writeKnowledgeContent(hssfWorkbook, hssfSheet, headers, dataList);
        } catch (Exception e) {
            LOGGER.error("【写入内容部分失败】", e);
            throw new RuntimeException("写入内容部分失败");
        }
        return hssfWorkbook;
    }
 
    /**
     * 生成内容部分(该方法无扩展性，仅支持知识库TOP3的导出)
     * @param hssfWorkbook {@link HSSFWorkbook}
     * @param hssfSheet {@link HSSFSheet}
     * @param headers 列标题，数组形式，
     *                 如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
     *                 其中参数@columnWidth可选，columnWidth为整型数值
     * @param dataList 要导出数据的集合
     * @throws Exception 
     */
    private void writeKnowledgeContent(HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet,
                                       String[] headers, List<List<?>> dataList) throws Exception {
        LOGGER.info("【写入Excel内容部分】");
        // 2015-8-13 增加，当没有数据的时候，把原来抛异常的方式修改成返回一个只有头信息，没有数据的空Excel
        if (CollectionUtils.isEmpty(dataList)) {
            LOGGER.warn("【没有内容数据】");
            return;
        }
        HSSFRow row = null;
        HSSFCell cell = null;
        // 单元格的值
        Object cellValue = null;
        // 数据写入行索引
        int rownum = 2;
        // 单元格样式
        HSSFCellStyle cellStyle = createContentCellStyle(hssfWorkbook);
        // 遍历集合，处理数据
 
        // 要写的内容的行数
        int rows = dataList.get(0).size();
        // 除去固定列“日期”外，还需要写的列数
        int columns = dataList.size();
 
        /**
         * 思路说明
         * 如果是top3，那么dataList的长度应该是3（即除去固定列“日期”剩下的列数）
         * 其次索引依次是top1的数据、top2的数据、top3的数据
         * top1/top2/top3的数据长度表示行数
         * 第一层循环，肯定以行数为基准来进行
         * 然后第一层循环的第一步是完成固定列的数据填充
         * 第二步是填充对应行每个列的数据，而每个列的数据应该分别从top1、top2、top3中取
         */
        for (int j = 0; j < rows; j++) {
            row = hssfSheet.createRow(rownum);
            // 写固定列“日期”
            cell = row.createCell(0);
            cellValue = ReflectUtils.getCellValue(dataList.get(0).get(j), headers[0].split("@")[1]);
            cellValueHandler(cell, cellValue);
            cell.setCellStyle(cellStyle);
 
            for (int i = 1; i <= columns; i++) {
                cell = row.createCell(i);
                cellValue = ReflectUtils.getCellValue(dataList.get(i - 1).get(j),
                    headers[i].split("@")[1]);
                cellValueHandler(cell, cellValue);
                cell.setCellStyle(cellStyle);
            }
            rownum++;
        }
    }
 
    /**
     * 根据文件路径读取excel文件
     * @param excelPath excel的路径
     * @param skipRows 需要跳过的行数
     * @return List<String[]> 集合中每一个元素是一个数组，按单元格索引存储每个单元格的值，一个元素可以封装成一个需要的java bean
     * @throws Exception
     */
    public static List<String[]> readExcel(String excelPath, int skipRows, int columCount) throws Exception {
        LOGGER.info("【读取Excel】excelPath : " + excelPath + " , skipRows : " + skipRows);
        FileInputStream is = new FileInputStream(new File(excelPath));
        POIFSFileSystem fs = new POIFSFileSystem(is);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        List<String[]> list = new ArrayList<String[]>();
        HSSFSheet sheet = wb.getSheetAt(0);
        // 得到总共的行数
        int rowNum = sheet.getPhysicalNumberOfRows();
        try {
            for (int i = skipRows; i < rowNum; i++) {
                String[] vals = new String[columCount];
                HSSFRow row = sheet.getRow(i);
                if (null == row) {
                    continue;
                }
                for (int j = 0; j < columCount; j++) {
                    HSSFCell cell = row.getCell(j);
                    String val = getStringCellValue(cell);
                    vals[j] = val;
                }
                list.add(vals);
            }
        } catch (Exception e) {
            LOGGER.error("【Excel解析失败】", e);
            throw new RuntimeException("Excel解析失败");
        } finally {
        	is.close();
        }
        return list;
    }
 
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(HSSFCell cell) {
        if (cell == null)
            return "";
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(format.format(cell.getNumericCellValue()))
                    .replace(",", "");
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
 
        return strCell;
    }
 
    
    public  static void main(String[] dsa){
    	String[] headers = {"列标题1@id","列标题2@name"};
    	 
    }
}