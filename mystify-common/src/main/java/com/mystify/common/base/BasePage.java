package com.mystify.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.plugins.Page;

public class BasePage<T>  extends Page<T>{
 
	private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
	
	private static final long serialVersionUID = 1L;

	/**
	 * offset 当前页包括之前页的数据总数,如果传入的是页码 直接用Page,现在要把数据条数转化成页码
	 * @param offset
	 * @param size
	 */
	public BasePage(int offset, int size) {
		super(getCurrent(offset,size), size);
    }
	

    public BasePage(int offset, int size, String orderByField) {
        super(getCurrent(offset,size), size,orderByField);
    }
    
    private static int getCurrent(int offset, int size) {
    	int current = 1;
    	if(offset>0){
    		current = offset/size+1;
    	}
    	logger.info("current ---------------> "+current);
    	return current;
    }
}
