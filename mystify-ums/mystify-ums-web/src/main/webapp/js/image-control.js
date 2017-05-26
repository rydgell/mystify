
	/**
	 * 临时文件上传
	 * @param obj 上传对象
	 * @param type 文件类型 1：图片 2：视频
	 * @param title 控件标题
	 * @param multi 是否多文件 true 是  false 否
	 * @param jsessionid jsessionid 
	 * @param previewControlId 预览区域id
	 * @param controlIds 控件id(保存资源id集合) 
	 */
	function addUploadify(obj, type, title, multi,jsessionid,previewControlId,controlIds,width) {
		var fileTypeExts='';
		if(type==1){
			fileTypeExts='*.gif;*.jpg;*.jpeg;*.png';
		}else if(type==2){
			fileTypeExts='*.mp4;*.mov';
		}
		obj.uploadify({
					'formData' : {
						'clientType' : type,
						'JSESSIONID' : jsessionid
					},
					'swf' : root_path+'/js/uploadify/uploadify.swf',
					'uploader' : root_path+'/saveUploadFile.do',
					'cancelImage' : 'http://www.static-xxx.nu/uploadify-cancel.png',
					"fileObjName" : "up",
					'auto' : true,
					'fileDesc' : 'php压缩包',
					'fileObjectName' : 'up',
					'removeCompleted' : true,
					'sizeLimit' : 1024 * 1024 * 300,
					'buttonText' : title,
					'fileTypeExts' : fileTypeExts,
					'multi' : multi,
					'height' : 20,//上传按钮的高和宽
					'width' : width==null?100:width,

					//'width':50,
					//'height':20,
					// 'buttonImg ':'${ROOT}/images/button_notext.png',
					'simUploadLimit' : 10,
					'onSelect' : function(event, queueID, fileObj) {
						
					},
					'onUploadSuccess' : function(file, data, response) {
						
						if (response) {
							
							var dataJson = eval('(' + data + ')');						
																					
							
							if(dataJson.status==0){
								return;
							}else if(dataJson.status==1){
								if(type==1){//图片
								
									if(multi==false){//单张图片
										document.getElementById(previewControlId).innerHTML ="";
										//$("#"+previewControlId).empty()  ;//预览清空	
										document.getElementById(controlIds).value="";
										
										
									}				
									
									
									
									addNode(dataJson.data,type,previewControlId,controlIds);
									
								
								}else if(type==2){//视频
									$("#"+previewControlId).append("视频名称："+dataJson.data.videoName);
								}
								
							}											

						
						}
						if (response) {
							parent.upload_succ = true;
						} else {

						}

						
					},
					'queueSizeLimit' : 10
				});
	}
	/**
	 * 
	 * @param resoureData 图片对象
	 * @param type 文件类型 1：图片 2：视频
	 * @param previewControlId 预览区域id
	 * @param controlIds 控件id(保存资源id集合) 
	 */
	function addNode(resoureData,type,previewControlId,controlIds) {
		var id="";
		var url="";
		
		if(type==1){//图片
			
			id = resoureData.pictureId;
			
			url=previewPath+"/"+resoureData.fileUrl;

			url=url.replace(/\\+/g,"/");
			
			var str ='<table id="'+id+'" width="200" height="140" border="0" cellspacing="0" cellpadding="0" style="float: left;">'+
	        '<tr>'+
	          '<td width="50%">'+'<div class="divbj">'+
	            '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	              '<tr>'+
	                '<td align="center">'+'<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	                    '<tr>'+
	                      '<td width="8%" height="50" align="right">'+'</td>'+
	                      '<td width="92%">'+'<img src="'+url+'" onerror="defaultImg(this)"  style="width: 200px; height: 133px; background-color: #ccc; border: 1px solid #333"/>'+'</td>'+
	                    '</tr>'+
	                '</table>'+'</td>'+
	              '</tr>'+
	            '</table>'+
	          '</div>'+'</td>'+         
	          '<td width="6%" align="center">'+'<img src="'+root_path+'/images/trip_32.jpg" onerror="defaultImg(this)" onclick="deleleNode(\'TABLE\',this,\''+id+'\',\''+controlIds+'\');"   onmouseover="this.style.cursor=\'pointer\'" onmouseout="this.style.cursor=\'\'"  width="98" height="62" />'+'</td>'+
	        '</tr>'+
	      '</table>';
			
			//$("#"+previewControlId).append(str);//预览清空		
			document.getElementById(previewControlId).innerHTML =document.getElementById(previewControlId).innerHTML+str;
		}else if(type==2){//视频
			
			
		} 
		
		
		var idsData=$("#"+controlIds).val();//id集合
		
		if(idsData==undefined){			
			$("#"+controlIds).val(id);
			//alert($("#"+controlIds).val());
		}else{
			if(idsData.length==0){
				$("#"+controlIds).val(id);
			}else{
				idsData=idsData+','+id;
				$("#"+controlIds).val(idsData);
			}
			
		}
		
		
		
		
		//alert();
	}

	
	

	/**
	 * 
	 * @param ElementType
	 * @param obj
	 * @param id 删除id
	 * @param controlIds 控件id(保存资源id集合) 
	 */
	function deleleNode(ElementType, obj,id, controlIds) {
		$.messager.confirm('系统提示', '真的要删除这张图片？', function(r) {
			if (r) {
				//alert($("#"+controlIds).val());
				var idArray=$("#"+controlIds).val().split(",");
				
				
				var tempData=new Array();
				for(var i=0;i<idArray.length;i++){
					if(id!=idArray[i]){
						tempData.push(idArray[i]);
					}
				}
				
				var idsData=[];
				for(var k=0;k<tempData.length;k++){
					idsData.push(tempData[k]);
				}
				$("#"+controlIds).val(idsData);
				//alert($("#"+controlIds).val());
				
				$("#"+id).remove();
			}
		});	
		
	}
	function defaultImg(obj){
		 obj.onerror= null;
		 obj.src=root_path+"/images/image.jpg";
	}

	