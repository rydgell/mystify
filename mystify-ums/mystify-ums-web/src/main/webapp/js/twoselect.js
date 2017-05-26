/*
 * Two select V1.0 Beta
 * Copyright(c) 2010 Alex Yang
 * pingyu007@163.com
 * ---------------------------------------------
 * 1.基于JQuery 的双选择框;
 * 2.任何人可以都可以免费使用，修改，复制，传播，但是请保留此段注释;
 * 3.与传统的双选择不同，右边的选择后不会删除左边下拉框的内容;
 * 4.支持多层树状结构，你只需在传入的参数中指定上级ID;
 * 5.支持排序;
 * 6.由于作者刚接触半天JQuery,对JQuery的特性也是完全不了解，因此在代码重用性、
 * 性能、以及美观度方面没有考虑太多,如果谁有时间欢迎改进;
 * 7.将左边的内容添加到右边时，会将选择的节点的父节点也会相应的添加到右边。
 * 相反，从右边删除一个节点时，会将他的所有子节点也删除;
 * ---------------------------------------------
 * 使用范例
 * 1,在你的页面中定义一个Element，可以是div,td等。选择框会将他作为容器显示。如<div id="test1"></div>
 * 2,在你需要显示的时候(如页面初始化或者别的时候)调用如下：
 * var a=[
 *			{value:'1',parent:'',display:'display1',index:1},
 *			{value:'2',parent:'1',display:'display2',index:2},
 *			{value:'3',parent:'1',display:'display3',index:3},
 *			{value:'4',parent:'',display:'display4',index:4},
 *			{value:'5',parent:'4',display:'display5',index:5}
 *        ];
 * var b=[
 *		   {value:'4',parent:'',display:'display4',index:4},
 *		   {value:'5',parent:'4',display:'display5',index:5}
 *       ];
 * generate2Select("test1",a,b);
 * 其中a 为左边下拉框供选择的列表，b为右边下拉框预置的数据，c为你第一步中定义的容器的ID
 * 3,在你提交表单后你就可以通过getParameter("returnValueText")来得到右边选择框的所有结果，
 * 他是一个JSON数据列表，其中排序字段为index
 */
jQuery.extend({   
evalJSON: function(strJson) {  
return eval("(" + strJson + ")");  
}  
});  
jQuery.extend({  
toJSON: function(object) {  
var type = typeof object;  
if ('object' == type) {  
if (Array == object.constructor) type = 'array';  
else if (RegExp == object.constructor) type = 'regexp';  
else type = 'object';  
}  
switch (type) {  
case 'undefined':  
case 'unknown':  
return;  
break;  
case 'function':  
case 'boolean':  
case 'regexp':  
return object.toString();  
break;  
case 'number':  
return isFinite(object) ? object.toString() : 'null';  
break;  
case 'string':  
return object.toString();
break;  
case 'object':  
if (object === null) return 'null';  
var results = [];  
for (var property in object) {  
var value = jQuery.toJSON(object[property]);  
if (value !== undefined) results.push(jQuery.toJSON(property) + ':' + value);  
}  
return '{' + results.join(',') + '}';  
break;  
case 'array':  
var results = [];  
for (var i = 0; i < object.length; i++) {  
var value = jQuery.toJSON(object[i]);  
if (value !== undefined) results.push(value);  
}  
return '[' + results.join(',') + ']';  
break;  
}  
}  
});
		this.opts={};
		this.returnOpts=[];
		function get(arr,objPropery,objValue)
		{
			return $.grep(arr, function(cur,i){
				return cur[objPropery]==objValue;
			});
		}
		function remove(arr,objPropery,objValue)
		{
		   return $.grep(arr, function(cur,i){
				  return cur[objPropery]!=objValue;
			   });
		}
		function setValueToForm(){
			$("#returnValueText")[0].value=$.toJSON(this.returnOpts);
		}
		function mergeOptions(obj,rmv,oThis){
			$.each(obj,function(){
						oThis.returnOpts[oThis.returnOpts.length]=get(oThis.opts,"value",this.value)[0];
						if(rmv)
						oThis.opts=remove(oThis.opts,"value",this.value);
				})
			adjustData(oThis);
		}
		function adjustData(oThis)
		{
			oThis.returnOpts=$.unique(oThis.returnOpts);
			oThis.opts=$.unique(oThis.opts);
			oThis.returnOpts.sort(function(a, b){
				return a.index - b.index;
			});

		}
		function adjustUI(oThis)
		{
			var tmp2=$.unique($('#select2 option'));
			$.each($('#select2 option'),function(){
					$(this).remove();
				})
			$.each(this.returnOpts,function(){
					$(get(tmp2,"value",$(this)[0].value)[0]).appendTo($('#select2'));
				})
		}
		function deepGenerateOptions(str,arr,parent,strip){
			var tmpRes="";
			var tmpOpts=get(arr,"parent",parent);
			if(tmpOpts&&tmpOpts.length>0){
				for(var i=0;i<tmpOpts.length;i++){
					tmpRes='<option value="'+tmpOpts[i].value+'">'+strip+((strip&&strip.length>0)?"∟":"")+tmpOpts[i].display+'</option>';
					str=tmpRes+deepGenerateOptions(str,arr,tmpOpts[i].value,strip+"&nbsp;&nbsp;&nbsp;&nbsp;");
				}
			}
			return str;
		}
		function deepMoveToLeft(to,obj){
			//debugger;
			if(obj&&obj.length>0){
				for(var i=0;i<obj.length;i++){
					var value=obj[i].value;
					var parentId=get(this.opts,"value",value)[0].parent;
					var tmp2=$.unique($('#select1 option'));
					if(parentId){
						deepMoveToLeft(to,$(get(tmp2,"value",parentId)[0]));
					}
					var oThis=this;
					mergeOptions(obj,false,oThis);
						obj.clone().appendTo(to);
					adjustUI(oThis);
				}
			}
			
		}
		function deepMoveToRight(to,obj){
			//debugger;
			if(obj&&obj.length>0){
				for(var i=0;i<obj.length;i++){
					var value=obj[i].value;
					var childs=get(this.returnOpts,"parent",value);
					if(childs&&childs.length>0){
						var tmp2=$.unique($('#select2 option'));
						for(var j=0;j<childs.length;j++){
						//debugger;
							var childOp=get(tmp2,"value",childs[j].value)[0];
							if(childOp){
								var childObj=$(childOp);
								deepMoveToRight(to,childObj);
							}
						}
					}
					var oThis=this;
					oThis.returnOpts=remove(oThis.returnOpts,"value",value);
					obj.remove();
				}
			}
		}
		function getSelectVal()
		{
			var options = $('#select2 option');
			return options;
		}
		
		function removeSame(arr1,arr2)  
		{  
		  if(arr1.length==0||arr1==null) return [];  
		  if(arr2.length==0||arr2==null) return arr1;  
		    
		  var obj=new Object();  
		  //首先将arr2这个数组里面的值赋给obj这个对象  
		  for(var i=0;i<arr2.length;i++)  
		  {  
		    eval("obj.pro"+$(arr2[i]).val()+"='"+$(arr2[i]).val()+"';");  
		  }  
		    
		  var arr3=new Array();  
		  //根据arr1[j]里面的值遍历obj如果obj.proarr1[j]里面不存在值的话  
		  //就把该值添加到arr3这个数组里面,例如removeSame([1,2,3],[2])调用的是这2个数组的话  
		  //此时obj.pro2=2;在下面这个循环里面当j=0的时候arr1[0]=1那么obj.pro1在执行eval()的时候不存在所以会返回一个undefined  
		  for(var j=0;j<=arr1.length;j++)  
		  {  
		    if(eval("typeof obj.pro"+$(arr1[j]).val()+"=='undefined'"))  
		    {  
		    	if(arr1[j]!=null && arr1[j]!='undefined')
		    		{
		    			arr3.push(arr1[j]);  
		    		}
		    }  
		  }  
		  return arr3;  
		}  
       /**
				options格式:
					[{value:'1',parent:'',display:'display1',index:1},
					{value:'2',parent:'1',display:'display2',index:2},
					{value:'3',parent:'1',display:'display3',index:3},
					{value:'4',parent:'',display:'display4',index:4}]
	   **/
	   
		function generate2Select(parentId,options,preSelectOptions,isDeepMove){
			if(isDeepMove==undefined){
				isDeepMove=true;
			}
			this.opts=options;
			this.returnOpts=preSelectOptions;
			var oThis=this;
			var leftSelectHtml='<select multiple id="select1" style="min-height:350px;height:auto;overflow:visible; width:300px">';
			var optionStr="";
			optionStr=deepGenerateOptions(optionStr,options,'','');
			var preOptionStr="";
			preOptionStr=deepGenerateOptions(preOptionStr,preSelectOptions,'','');
			leftSelectHtml=leftSelectHtml+optionStr+'</select>';
			var htmlStr='<div style="float:left;text-align: center;margin:10px;"><div>所有角色:</div>'+
						leftSelectHtml+
						'</div>'+
						'<div id="middleBtnWrap" style="float:left;text-align: center;margin:10px;">'+
						'<button id="add" style="display: block;text-decoration: none;width:90px;">添加&gt;</button>'+
						'<button id="addAll" style="display: block;text-decoration: none;width:90px;">添加所有&gt;&gt;</button>'+
						'<br>'+
						'<button id="remove" style="display: block;text-decoration: none;width:90px;">&lt;移除</button>'+
						'<button id="removeAll" style="display: block;text-decoration: none;width:90px;">&lt;&lt;移除所有</button>'+
						'<br>'+
					
						'</div>'+
						'<div style="float:left;text-align: center;margin:10px;"> <div>已有角色:</div>'+
						'<select multiple id="select2">'+preOptionStr+
						'</select>'+
						'</div>';
				htmlStr=htmlStr+'<input type="hidden" id="returnValueText" value="value"></input>'		
			$("#"+parentId).html(htmlStr);
			//移到右边
		   $('#add').click(function() {
			   /*
				if(isDeepMove){
					deepMoveToLeft('#select2',$('#select1 option:selected'));
				}else{
					deepMoveToLeft('#select2',$('#select1 option:selected'));
				}*/
			   var obj = $('#select1 option:selected');
			   var selectedoption = $("#select2 option");
			   if(selectedoption.length==1 && $(selectedoption[0]).val()=='0')
				   {
				   	//当没有任何角色默认的时候，添加角色时，要把默认的空的去掉
				   $('#select2 option').remove();
				   }
			   var toAddOption = removeSame(obj,selectedoption);
			   $(toAddOption).clone().appendTo("#select2");
				setValueToForm();
				return false;
		   });
		   $('#addAll').click(function() {
				
					$('#select2 option').remove();
					$('#select1 option').clone().appendTo('#select2');
				
				oThis.returnOpts=oThis.opts;
				setValueToForm();
				return false;
		   });
		   //移到左边
		   $('#remove').click(function() {
				$('#select2 option:selected').remove();
				setValueToForm();
				return false;
		   });
		   $('#removeAll').click(function() {
				if(isDeepMove){
					if($('#select2 option').length>0)
						$('#select1 option').remove();
					$('#select2 option').remove().appendTo('#select1');
				}else{
					$('#select2 option').remove();
				}
				oThis.returnOpts={};
				setValueToForm();
				return false;
		   });
		   //双击选项
		   $('#select1').dblclick(function(){
			   var obj = $('#select1 option:selected');
			   var selectedoption = $("#select2 option");
			   if(selectedoption.length==1 && $(selectedoption[0]).val()=='0')
			   {
			   	//当没有任何角色默认的时候，添加角色时，要把默认的空的去掉
			   $('#select2 option').remove();
			   }
			   
			   var toAddOption = removeSame(obj,selectedoption);
			   $(toAddOption).clone().appendTo("#select2");
				setValueToForm();
		   });
		   //双击选项
		   $('#select2').dblclick(function(){
			
				$('#select2 option:selected').remove();
				
				setValueToForm();
		   });
		   //向上
		   $('#right_up').click(function(){
				if(isDeepMove){
					var index = $('#select2 option').index($('#select2 option:selected:first'));
					if(index>0){
						var $recent ;
						var vernier=1;
						while(true){
							var tmpRecent=$('#select2 option:eq('+(index-vernier)+')');
							if(tmpRecent.length==0||index-vernier<0){
								break;
							}
							if(get(oThis.returnOpts,"value",tmpRecent[0].value)[0].parent==get(oThis.returnOpts,"value",$('#select2 option:selected:first')[0].value)[0].parent){
								$recent=tmpRecent;
								break;
							}
							vernier++;
						}
						if($recent){
							 var $options = $('#select2 option:selected:first');
							 var recentData=get(oThis.returnOpts,"value",$recent[0].value)[0];
							 var thisData=get(oThis.returnOpts,"value",$options[0].value)[0];
							 var childData=get(oThis.returnOpts,"parent",$options[0].value);
							 var childLength=childData.length;
							 for(var i=0;i<childLength;i++){
								$options=$options.add($('#select2 option:eq('+(index+i+1)+')'));
							 }
							 if(thisData.parent!=$recent[0].value){
								$options.remove();
								setTimeout(function(){
									$recent.before($options );
									for(var i=0;i<$('#select2 option').length;i++){
										var val=$('#select2 option:eq('+(i)+')')[0].value;
										get(oThis.returnOpts,"value",val)[0].index=i+1;
										//debugger;
									}
									//debugger;
								},10);
							}
							
						}
						setValueToForm();
					}
				}else{
					var index = $('#select2 option').index($('#select2 option:selected:first'));
					var $recent = $('#select2 option:eq('+(index-1)+')');
					if(index>0){
						 var $options = $('#select2 option:selected').remove();
						setTimeout(function(){
							$recent.before($options )
						},10);
						setValueToForm();
					}
				}
			return false;
			//debugger;
		   });
		   //向下
		   $('#right_down').click(function(){
			   if(isDeepMove){
					var index = $('#select2 option').index($('#select2 option:selected:last'));
					var len = $('#select2 option').length-1;
					if(index<len){
						var $recent ;
						var vernier=1;
						while(true){
							var tmpRecent= $('#select2 option:eq('+(index+vernier)+')');;
							if(tmpRecent.length==0||index+vernier>len){
								break;
							}
							if(get(oThis.returnOpts,"value",tmpRecent[0].value)[0].parent==get(oThis.returnOpts,"value",$('#select2 option:selected:first')[0].value)[0].parent){
								var childData=get(oThis.returnOpts,"parent",tmpRecent[0].value);
								var childLength=childData.length;
								$recent=$('#select2 option:eq('+(index+vernier+childLength)+')');;;
								break;
							}
							vernier++;
						}
						if($recent){
							 var $options = $('#select2 option:selected:first');
							 var recentData=get(oThis.returnOpts,"value",$recent[0].value)[0];
							 var thisData=get(oThis.returnOpts,"value",$options[0].value)[0];
							 var childData=get(oThis.returnOpts,"parent",$options[0].value);
							 var childLength=childData.length;
							 for(var i=0;i<childLength;i++){
								$options=$options.add($('#select2 option:eq('+(index+i+1)+')'));
							 }
							 if(thisData.parent!=$recent[0].value){
								$options.remove();
								setTimeout(function(){
									$recent.after($options );
									for(var i=0;i<$('#select2 option').length;i++){
										var val=$('#select2 option:eq('+(i)+')')[0].value;
										get(oThis.returnOpts,"value",val)[0].index=i+1;
										//debugger;
									}
									//debugger;
								},10);
							}
							
						}
						setValueToForm();
					}
			   }else{
					var index = $('#select2 option').index($('#select2 option:selected:last'));
					var len = $('#select2 option').length-1;
					var $recent = $('#select2 option:eq('+(index+1)+')');
					if(index<len ){
						var $options = $('#select2 option:selected').remove();
						setTimeout(function(){
							$recent.after( $options )
						},10);
					}
				}
				return false;
		   });
		   var tmpHeight=$('#select1').height();
			if($('#select1').height()<$('#select1').children().size()*18){
				$('#select1').height($('#select1').children().size()*18);
				tmpHeight=$('#select1').children().size()*18;
			}
			$('#select2').height($('#select1').outerHeight());
			$('#select2').width($('#select1').width());
			if($('#middleBtnWrap')[0])
			$('#middleBtnWrap')[0].style.marginTop=tmpHeight/2-$('#middleBtnWrap').children().length*10+"px";
		}