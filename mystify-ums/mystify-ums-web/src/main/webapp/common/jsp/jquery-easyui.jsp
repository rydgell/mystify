<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${ROOT}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ROOT}/css/common_style.css" rel="stylesheet" type="text/css" />
 
<script src="${ROOT}/js/comm.js" type="text/javascript"></script>	
<script src="${ROOT}/js/common.js" type="text/javascript"></script>
<script src="${ROOT}/js/isroll/iscroll.js" type="text/javascript"></script>  


<script type="text/javascript" src="${ROOT}/js/jquery-1.7.2.min.js"></script> 
<script type="text/javascript" src="${ROOT}/js/jquery.form.js"></script>
<link rel="stylesheet" type="text/css" href="${ROOT}/js/jquery-easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${ROOT}/js/jquery-easyui/themes/icon.css">
<script type="text/javascript"  src="${ROOT}/js/jquery-easyui/jquery.min.js"></script>	 
<script type="text/javascript" src="${ROOT}/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ROOT}/js/jquery-easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ROOT}/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>	
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {
    maxLength: {
    validator: function(value, param){
    	return value.length < param[0];
    },
    	message: '最多输入{0}个字 '
    }
});
$.extend($.fn.validatebox.defaults.rules, {
    isNumber: {
	   	validator: function(value,param){
	   		return $.isNumeric(value);
	   	},
	   	message: '必须输入数字.'
   	}
});
$.extend($.fn.numberbox.defaults.rules, {
	  priceRex: {
		    validator: function(value){
		    var length = value.toString().split(".")[1].length;  
		   
		    if(length>2){
		    	$.fn.numberbox.defaults.rules.priceRex.message = '不能输入超过2位小数!';
			       return false;
		    }else{
		    	return true;
		    }
		    
		    },
		    message: ''
		  }
	});
</script>
