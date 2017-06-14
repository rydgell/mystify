var _menus = null;
$(function(){
	
	loadMenuData();	//先查询所有的菜单
	
	InitLeftMenu();
	tabClose();
	tabCloseEven();
})

function loadMenuData()
 {
    		$.ajax({
    			 async:false,
				 //dataType:'json',
				 url:  window.ROOT + '/manage/permission/userPermissions',
				 success:function(data)
				 {	
					 
    				_menus = eval("(" + data + ")");
				 },
        		error:function() 
        		{
						 alert("数据错误！" +arguments[1]);
        		}
    		});
   
 }

function addNav(obj){
	var url = $(obj).attr("rel");
	var tabTitle = $(obj).html();
	addTab(tabTitle,url,'icon icon-log');//增加tab
	$('.easyui-accordion li div').removeClass("selected");
}
//初始化左侧
function InitLeftMenu() {
	$("#nav").accordion({animate:false});//为id为nav的div增加手风琴效果，并去除动态滑动效果
    $.each(_menus.menus, function(i, n) {//$.each 遍历_menu中的元素
		var menulist ='';
		menulist +='<ul>';
        $.each(n.menus, function(j, o) {
			menulist += '<li><div><a ref="'+o.menuid+'" href="#" rel="' +window.ROOT + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li> ';
        })
		menulist += '</ul>';
        
		$('#nav').accordion('add', {
            title: n.menuname,
            content: menulist,
            iconCls: 'icon ' + n.icon
        });
    });
    
    
    
	$('.easyui-accordion li a').click(function(){//当单击菜单某个选项时，在右边出现对用的内容
		var tabTitle = $(this).children('.nav').text();//获取超链里span中的内容作为新打开tab的标题
		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");//获取超链接属性中ref中的内容
		var icon = getIcon(menuid,icon);

		addTab(tabTitle,url,icon);//增加tab
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//选中第一个
	var panels = $('#nav').accordion('panels');
	var t = panels[0].panel('options').title;
    $('#nav').accordion('select', t);
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('close',subtitle);
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		//$('#mm-tabcloseright').click();
		//$('#mm-tabcloseleft').click();
		var currtab_title = $('#mm').data("currtab");
         $('.tabs-inner span').each(function(i,n){
             var t = $(n).text();
             if(t!=currtab_title)
                 $('#tabs').tabs('close',t);
         }); 
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

function findMenu()
{
	//查找菜单
	var menuname = $("#queryMenumenuname").val();

	if(menuname==null || menuname=="")
	{
		alert("请输入菜单名称进行查询");
		return false ;
	}
	else
	{
			var url = "queryMenuByName.do";
			var data= "menuname=" + menuname;
		
			var $table = $("#queryMenudataTable tr");//获取table的tr集合  
			var len = $table.length;//获取table的行数
		
			if(len>1)
			{
				for(var i =1 ;i<=len+1;i++)
				{
					$("#queryMenudataTable tr:eq("+i+")").remove();
				}
			}
			$.ajax({
					url:url,
					type:'POST',
					data:data,
					dataType:'json',
					success:function(msg)
					{
						if(msg==null || msg=="")
						{
							$("#queryMenudataTable").append("<tr class='tabTextMain'  onmouseout='this.style.background='#FFFFFF';'  onmouseover='this.style.background='#F4F7FA';'><td colspan='5' align='center'>没有您要查找的数据</td></tr>")
						}
						else
						{
							for(var i=0;i<msg.length;i++)
							{
								var menu = msg[i];
								$("#queryMenudataTable").append("<tr class='tabTextMain'  onmouseout='this.style.background='#FFFFFF';'  onmouseover='this.style.background='#F4F7FA';'><td align='center' class='table_bottom1' id='menuname'>"+menu.name+"</td><td align='center' name='showname' class='table_bottom1' id='modulename'>"+menu.type+"</td><td align='center' class='table_bottom1' id='status'>"+menu.url+" </td><td align='center' class='table_bottom1'><a onclick=opentab('"+menu.id+"','"+menu.name+"','"+menu.url+"','icon-log') href='#'>go</a> </td><td align='center' class='table_bottom1'><a onclick=insertToUsalLink('"+menu.id+"') href='#'>  +  </a> </td></tr>")
							}
						}
					}
				});
	}
}

function opentab(menuid,menuname,menuurl,icon)
{
	addTab(menuname,menuurl,'icon icon-log');
}
 

 


