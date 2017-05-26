function killErrors() {
return true;
}
window.onerror = killErrors;
//鼠标移动到上面的时候，变背景颜色
function overmouse(obj){
    obj.style.background = "#edecf5";	
}
//鼠标移开上面的时候，恢复背景颜色
function outmouse(obj){
    obj.style.background = "#ffffff";
}

//文本框获得焦点
function getfocus(obj){
    obj.style.background = "#FFFBD5";
}
//文本框失去焦点
function losefocus(obj){
    obj.style.background = "#f4f9fc";
}


//复选框 全选/全不选 切换
function checkAll(e, itemName)
{
  var aa = document.getElementsByName(itemName);
  for (var i=0; i<aa.length; i++)
   aa[i].checked = e.checked;
}


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


//用图片代替提交按钮submit和重置按钮reset
function fsubmit(obj){
	obj.submit();
	}
function freset(obj){
	obj.reset();
	}	
	
//对话框按钮图片切换
/*
img1=new Array(1);
img1[0]=new Image; img1[0].src="images/dhkok_on.jpg";
img1[1]=new Image; img1[1].src="images/dhkcancle_on.jpg";
//img1[2]=new Image; img1[2].src="images/group/close_off.jpg";
//
//img1[3]=new Image; img1[3].src="images/group/share_off.jpg";
//img1[4]=new Image; img1[4].src="images/group/setup_off.jpg";
//img1[5]=new Image; img1[5].src="images/group/group_off.jpg";
//
//img1[6]=new Image; img1[6].src="images/group/font_off.jpg";
//img1[7]=new Image; img1[7].src="images/group/biaoqing_off.jpg";
//img1[8]=new Image; img1[8].src="images/group/message_off.jpg";
//
//img1[9]=new Image; img1[9].src="images/group/c_off.jpg";
//img1[10]=new Image; img1[10].src="images/group/s_off.jpg";

img2=new Array(1);
img2[0]=new Image; img2[0].src="images/dhkok_off.jpg";
img2[1]=new Image; img2[1].src="images/dhkcancle_off.jpg";
//img2[2]=new Image; img2[2].src="images/group/close_on.jpg";
//
//img2[3]=new Image; img2[3].src="images/group/share_on.jpg";
//img2[4]=new Image; img2[4].src="images/group/setup_on.jpg";
//img2[5]=new Image; img2[5].src="images/group/group_on.jpg";
//
//img2[6]=new Image; img2[6].src="images/group/font_on.jpg";
//img2[7]=new Image; img2[7].src="images/group/biaoqing_on.jpg";
//img2[8]=new Image; img2[8].src="images/group/message_on.jpg";
//
//img2[9]=new Image; img2[9].src="images/group/c_on.jpg";
//img2[10]=new Image; img2[10].src="images/group/s_on.jpg";
*/

function show(n)
{
if (n==0) document.P0.src=img2[0].src;
if (n==1) document.P1.src=img2[1].src;
//if (n==2) document.P2.src=img2[2].src;
//if (n==3) document.P3.src=img2[3].src;
//if (n==4) document.P4.src=img2[4].src;
//if (n==5) document.P5.src=img2[5].src;
//if (n==6) document.P6.src=img2[6].src;
//if (n==7) document.P7.src=img2[7].src;
//if (n==8) document.P8.src=img2[8].src;
//if (n==9) document.P9.src=img2[9].src;
//if (n==10) document.P10.src=img2[10].src;
}

function hide(n)
{
if (n==0) document.P0.src=img1[0].src;
if (n==1) document.P1.src=img1[1].src;
//if (n==2) document.P2.src=img1[2].src;
//if (n==3) document.P3.src=img1[3].src;
//if (n==4) document.P4.src=img1[4].src;
//if (n==5) document.P5.src=img1[5].src;
//if (n==6) document.P6.src=img1[6].src;
//if (n==7) document.P7.src=img1[7].src;
//if (n==8) document.P8.src=img1[8].src;
//if (n==9) document.P9.src=img1[9].src;
//if (n==10) document.P10.src=img1[10].src;
}


function Tab_Index(name,cursel,n){
	
	for (i=1; i<=n; i++){ 
        var menu=document.getElementById(name+i);
		var con=document.getElementById(name+"Con"+i);
		var more = document.getElementById('more'+i);
		if (i==cursel){
		    menu.style.backgroundImage='url(images/tab_on.jpg)';
		    menu.style.fontSize='12px';
			menu.style.color='#6e7375';
			con.style.display='block';
			more.style.display='block';
		} 
		else{
		    menu.style.backgroundImage='url(images/tab_off.jpg)';
		    menu.style.fontSize='12px';
			menu.style.color='#6e7375';
			con.style.display='none';
			more.style.display='none';
		} 
	}
} 

Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1, // month  
        "d+": this.getDate(), // day  
        "h+": this.getHours(), // hour  
        "m+": this.getMinutes(), // minute  
        "s+": this.getSeconds(), // second  
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S": this.getMilliseconds()  
        // millisecond  
    }  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
            .substr(4 - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
    return format;  
}  
function formatDatebox(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {  
        dt = new Date(value);  
    }  
  
    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)  
}  

function formatDateTimebox(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {  
        dt = new Date(value);  
    }  
  
    return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)  
}  
