$(function(){
	listOrganization();
});

 function listOrganization(){
	 $('#dg').datagrid({
	        title: '组织列表',
	        singleSelect:false,
	        pagination: true,
	        remoteSort:false,
	        showFooter:false, 
	        fit:true,
	        fitColumns:false,
	        showFooter:false,
	        toolbar: '#toolbar',
	        url:root_path+'/manage/organization/list',
	        pageSize:20,
	        pageList:[20,30,40,50],
	        queryParams:{
			},
	        columns: [
	            [  	
	             	{field: 'id',width:50, title: 'id' },
	                {field: 'name',width:200, title: '组织名称' },
	                {field: 'description',width:230, title: '组织描述'},
	                {field: 'ctime',width:200, title: '添加时间',formatter:function(value,row,index){ 
	                	if(value==null||value==""){ 
	                		return "";
	                	}
	                	return getSmpFormatDateByLong(parseInt(value) ,true);
                        }},
	                {field: 'operating',width:180, title: '操作',formatter:getOperating}
	            ]
	        ]
	    }); 
}

 function getOperating(value,row,rowIndex){
		var html ="";
		html += "<a href='javascript:void(0)' style='margin-right: 10px' class='easyui-linkbutton' onclick='editItem(\""+row.id+"\",\""+row.name+"\",\""+row.description+"\")'>编辑</a>"+
				"<a href='javascript:void(0)' style='margin-right: 10px' class='easyui-linkbutton' onclick='delItem(\""+row.id+"\")'>删除</a>";
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


function delItem(id) {
	if (id == null) {
		$.messager.alert('系统提示', '请选择要删除的数据!');
		return;
	}
	if (id) {
		$.messager.confirm('确认','是否删除该项?',function(r) {
	     	if (r) {
					 $.getJSON(root_path+'/manage/organization/delete/'+id, function (result) { 
						 if (result.httpCode==200) {
								$.messager.alert('系统提示','操作成功');
								$('#dg').datagrid('reload'); 
							} else {
								$.messager.alert('系统提示',result.errorMsg);
							}
					});
					 
					}
			});
	  }

}



function editItem(id,name,description){	
	 $('#dlg').dialog('open').dialog('setTitle','修改组织');  
	/* $('#dlg #fm').form('clear');*/
	 $('#fm #id').val(id);
	 $('#fm #name').textbox('setValue',name);
	 $('#fm #description').textbox('setValue',description);
}

function addItem(){	
	 $('#dlg').dialog('open').dialog('setTitle','新增组织');  
	 $('#fm').form('clear');
	 $('#fm #id').val('');
	 $('#fm #name').textbox('setValue','');
	 $('#fm #description').textbox('setValue','');
}

function saveItem(){  
	var id = $('#fm #id').val();
	var url = '';
	if(id!=null&&id!=''){
		url = root_path+'/manage/organization/update/'+id;
	}else{
		url = root_path+'/manage/organization/create';
	}
    $('#fm').form('submit',{  
        url: url,              
        onSubmit: function(){  
            return $(this).form('validate');  
        },  
        success: function(data){
        	var result = eval('('+data+')'); 
        	if (result.httpCode==200){  
        		alert("保存成功！");
        		$('#dlg').dialog('close');  
	            $('#dg').datagrid('reload'); 
            } else {  
            	alert('保存失败:'+result.msg);
            }        
        }  
    });  
}

 

