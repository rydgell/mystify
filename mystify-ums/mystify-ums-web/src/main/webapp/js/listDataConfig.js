$(function(){
	listDataConfig();
});

 function listDataConfig(){
	 $('#dg').datagrid({
	        title: '系统配置',
	        singleSelect:false,
	        pagination: true,
	        remoteSort:false,
	        showFooter:false, 
	        fit:true,
	        fitColumns:false,
	        showFooter:false,
	        toolbar: '#toolbar',
	        url:'evaluationSystemConfigList.do',
	        pageSize:20,
	        queryParams:{
	        	 type:2
			},
	        columns: [
	            [  	
	             	{field: 'id',width:50, title: 'id' },
	                {field: 'name',width:200, title: '配置名称' },
	                {field: 'value',width:80, title: '配置值'},
	                {field: 'describ',width:350, title: '配置描述'},
	                {field: 'code',width:200, title: '配置类型'},
	                {field: 'operating',width:80, title: '操作',formatter:getOperating}
	            ]
	        ]

	    }); 
}

 function getOperating(value,row,rowIndex){
		var html ="";
		html += "<a href='javascript:void(0)' style='margin-right: 10px' class='easyui-linkbutton' onclick='editItem(\""+row.id+"\",\""+row.name+"\",\""+row.value+"\",\""+row.describ+"\",\""+row.code+"\")'>编辑</a>";
				/*"<a href='javascript:void(0)' style='margin-right: 10px' class='easyui-linkbutton' onclick='delItem(\""+row.id+"\")'>删除</a>";*/
		return html;
}
 
 
 
function resetCondition(){
			$('#name').val("");
}


function searchState(){
	$('#dg').datagrid('load',{  
		name: $('#name').val()
	});  
} 




function editItem(id,name,value,describ,code){	
	 $('#dlg').dialog('open').dialog('setTitle','修改配置');  
	 $('#dlg #fm').form('clear');
	 $('#fm #id').val(id);
	 $('#fm #name').textbox('setValue',name);
	 $('#fm #value').textbox('setValue',value);
	 $('#fm #describ').textbox('setValue',describ);
	 $('#fm #code').textbox('setValue',code);
	
}

function addItem(){	
	 $('#dlg').dialog('open').dialog('setTitle','新增配置');  
	 $('#fm').form('clear');
}

function saveItem(){  
    $('#fm').form('submit',{  
        url: 'saveEvaluationSystemConfig.do',              
        onSubmit: function(){  
            return $(this).form('validate');  
        },  
        success: function(data){
        	var result = eval('('+data+')'); 
        	if (result.success){  
        		alert("保存成功！");
        		$('#dlg').dialog('close');  
	            $('#dg').datagrid('reload'); 
            } else {  
            	alert(result.errorMsg);
            }        
        }  
    });  
}

 

