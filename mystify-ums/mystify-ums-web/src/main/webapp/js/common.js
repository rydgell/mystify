//显示多个弹出层
function showDetail(bgDiv,msgDiv,msgShut) {    
//背景
  var bgObj=document.getElementById(bgDiv);
  bgObj.style.width = document.body.offsetWidth + "px";
  bgObj.style.height = screen.height + "px";

//定义窗口
  var msgObj=document.getElementById(msgDiv);
  msgObj.style.marginTop = -75 +  document.documentElement.scrollTop + "px";

//关闭
  document.getElementById(msgShut).onclick = function(){
  bgObj.style.display = msgObj.style.display = "none";
  }
  msgObj.style.display = bgObj.style.display = "block";
}

function openwindow(url,name,iWidth,iHeight)
{
	var url; //转向网页的地址;
	var name; //网页名称，可为空;
	var iWidth; //弹出窗口的宽度;
	var iHeight; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
}

/**
 * 获取省
 * @param dft_Value
 */
function getProvData(controlId,dft_Value){
	if(null==dft_Value||'undefine'==dft_Value){
		dft_Value=0;
	 }
	$.ajax({
		  async:false,
		  cache:false,
		  ifModified:false,
		  dataType:'json',
		  url: root_path+'/monitor/getAreaCommon.do',
		  data: 'entity.parentId=0',
		  success: function(data){
			var htmlSelect=[];
			var htmlStr=[];

			htmlSelect.push("<option value=''>--请选择--</option>");	
			$.each(data.areaList,function(index,value){		
				
				var selectValue="<option value='"+value.id+"' ";
				if(dft_Value==value.id){
					selectValue+=" selected";
				}
				selectValue+=">"+value.name+"</option>";
				htmlSelect.push(selectValue);
			});
			
			
			$("#"+controlId).empty();
			$("#"+controlId).append(htmlSelect.join(""));
		  },error:function(){
			 // location=location;
		  }
		});
	
}
/**
 * 获取市区
 * @param pId
 * @param dft_Value
 */
function getAreaData(controlId,pId,dft_Value){
	if(null==dft_Value||'undefine'==dft_Value){
		dft_Value=0;
	 }
	if(pId==''){
		var htmlSelect=[];
		htmlSelect.push("<option value=''>--请选择--</option>");	
		$("#"+controlId).empty();
		$("#"+controlId).append(htmlSelect.join(""));
	}else{
		$.ajax({
			  async:false,
			  cache:false,
			  ifModified:false,
			  dataType:'json',
			  url: root_path+'/monitor/getAreaCommon',
			  data: 'entity.parentId='+pId,
			  success: function(data){
				var htmlSelect=[];
				var htmlStr=[];
				
				htmlSelect.push("<option value=''>--请选择--</option>");	
				$.each(data.areaList,function(index,value){		
					
					var selectValue="<option value='"+value.id+"' ";
					if(dft_Value==value.id){
						selectValue+=" selected";
					}
					selectValue+=">"+value.name+"</option>";
					htmlSelect.push(selectValue);
				});			
				$("#"+controlId).empty();
				$("#"+controlId).append(htmlSelect.join(""));
			  },error:function(){
				 // location=location;
			  }
			});
	}
	
}

/**
 * 获取选中类型
 * @param dft_Value
 */
function getTypeData(controlId,dft_Value){
	if(null==dft_Value||'undefine'==dft_Value){
		dft_Value=0;
	 }
	$.ajax({
		  async:false,
		  cache:false,
		  ifModified:false,
		  dataType:'json',
		  url: root_path+'/monitor/groupTypeCommon.do',
		  success: function(data){
			var htmlSelect=[];
			var htmlStr=[];
			
			htmlSelect.push("<option value=''>--请选择--</option>");	
			$.each(data.typeList,function(index,value){		
				
				var selectValue="<option value='"+value.typeId+"' ";
				if(dft_Value==value.typeId){
					selectValue+=" selected";
				}
				selectValue+=">"+value.typeName+"</option>";
				htmlSelect.push(selectValue);
			});			
			$("#"+controlId).empty();
			$("#"+controlId).append(htmlSelect.join(""));
		  },error:function(){
			 // location=location;
		  }
		});
}
/**
 * 获取设备分组
 * @param provId 省id 
 * @param areaId 市id
 * @param typeid 类型id
 * @param defaultValue 默认选中项
 * @returns
 */
function getGroupData(controlId,provId,areaId,typeId,defaultValue){
	if(null==defaultValue||'undefine'==defaultValue){
		 defaultValue=0;
	 }
	//alert(provId+"--"+areaId+"--"+typeId);
	if(provId=="" && areaId=="" &&typeId==""){
		var htmlSelect=[];
		
		htmlSelect.push("<option value=''>--请选择--</option>");	
		$("#"+controlId).empty();
		$("#"+controlId).append(htmlSelect.join(""));
	}else{
		var condition="";
		if(provId!=""&&null!=provId){
			condition="groupEntity.provId="+provId;
		}
		if(areaId!=""&&null!=areaId){
			condition+="&groupEntity.areaId="+areaId;
		}
		if(typeId!="" && null!=typeId){
			if(condition.length>0){
				condition+="&groupEntity.typeId="+typeId;
			}else{
				condition+="groupEntity.typeId="+typeId;
			}
		}
		$.ajax({
			  async:false,
			  cache:false,
			  ifModified:false,
			  dataType:'json',
			  url: root_path+'/monitor/groupCommon.do',
			  data: condition,
			  success: function(data){
				var htmlSelect=[];
				var htmlStr=[];
				
				htmlSelect.push("<option value=''>--请选择--</option>");	
				$.each(data.groupList,function(index,value){		
					var selectValue="<option value='"+value.groupId+"' ";
					if(defaultValue==value.groupId){
						selectValue+=" selected";
					}
					selectValue+=">"+value.groupName+"</option>";
					htmlSelect.push(selectValue);
				});
				$("#"+controlId).empty();
				$("#"+controlId).append(htmlSelect.join(""));
			  },error:function(){
				 // location=location;
			  }
			});
	}
	
}
/**
 * 获取设备
 * @param zid 省id 
 * @param pid 市id
 * @param typeid 类型id
 * @param groupId 分组id
 * @param defaultValue 默认选中项
 * @returns
 */
function getDeviceData(zid,pid,typeId,groupId,defaultValue){
	if(null==defaultValue||'undefine'==defaultValue){
		 defaultValue=0;
	 }

	var strData='device.area='+zid;
	strData+='&device.type='+pid;	
	strData+='&device.address='+typeId;
	strData+='&device.groupName='+groupId;
	
	$.ajax({
		  async:false,
		  cache:false,
		  ifModified:false,
		  dataType:'json',
		  url: root_path+'/monitor/deviceCommon.do',
		  data: strData,
		  success: function(data){
			var htmlSelect=[];
			var htmlStr=[];
			
			htmlSelect.push("<option value=''>--请选择--</option>");	
			$.each(data.deviceList,function(index,value){		
				var selectValue="<option value='"+value.uid+"' ";
				if(defaultValue==value.uid){
					selectValue+=" selected";
				}
				selectValue+=">"+value.name+"</option>";
				htmlSelect.push(selectValue);
			});
			$("#uid").empty();
			$("#uid").append(htmlSelect.join(""));
		  },error:function(){
			 // location=location;
		  }
		});
}
/**
 * 检查是否唯一
 * @param url	检查的URL
 * @param id	页面中的ID
 * @param name	最后提示的名称
 */
function checkUnique(url,dataName,id)
{
	var inputVal = $('#' + id).val();
	if(inputVal!=null && inputVal!='')
		{
			var reqdata = dataName + '=' + inputVal;
			$.ajax({
				url:url,
				data:reqdata,
				type:'POST',
				dataType:'text',
				success:function(msg)
				{
					if(msg=='0')
						{
							alert(inputVal + ' 已存在,请重新输入!');
							 $('#' + id).val("");	//清空
						}
				}
			});
		}
	
}



