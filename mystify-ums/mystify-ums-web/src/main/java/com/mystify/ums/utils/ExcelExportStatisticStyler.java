package com.mystify.ums.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.export.styler.ExcelExportStylerDefaultImpl;

 
public class ExcelExportStatisticStyler extends ExcelExportStylerDefaultImpl {

    private CellStyle myCellStyle;
    
    public static Set<String> customCellKey = new HashSet<String>(); 

    public ExcelExportStatisticStyler(Workbook workbook) {
        super(workbook);
        createMyCellStyler();
    }
    
    private void createMyCellStyler() {
    	myCellStyle = workbook.createCellStyle();
    	myCellStyle.setAlignment(CellStyle.ALIGN_LEFT);//左右居中 
    	myCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中 
    	myCellStyle.setWrapText(false); //自动换行
    }

    @Override
    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
    	if (entity != null&& customCellKey.contains(entity.getKey())) {
    		return myCellStyle;
    	}
    	return super.getStyles(noneStyler, entity);
    }

}
