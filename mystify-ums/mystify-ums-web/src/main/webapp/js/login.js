$(document).ready(function() {
			$("#form1").validate({
				rules: {
					 
				},
				messages: {
					 
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					//alert(error);
				} 
			});
		});

/*$.validator.setDefaults({
    submitHandler: function() {
    	 login();
    }
});
$().ready(function() {
    $("#form1").validate();
});*/

$(function() {
	// Waves初始化
	Waves.displayEffect();
	// 输入框获取焦点后出现下划线
	$('.form-control').focus(function() {
		$(this).parent().addClass('fg-toggled');
	}).blur(function() {
		$(this).parent().removeClass('fg-toggled');
	});
	
	//生成验证码         
    $('#kaptchaImage').click(function () {  
	$(this).hide().attr('src', '${ROOT}/sso/code?' + Math.floor(Math.random()*100) ).fadeIn(); });      
    
    
    
});
Checkbix.init();
$(function() {
    $("#login-bt").on('click',function(event){
    	 login();
    });
	// 回车事件
	$('#username, #password').keypress(function (event) {
		if (13 == event.keyCode) {
			 login();
		}
	});
});
// 登录
var options = {
   url: BASE_PATH + '/sso/login',
   success: function(json) {
     if (json.httpCode == 200) {
		location.href = BASE_PATH+json.data;
     }else{
   	  alert(json.msg);
     }
} };
function login() {
	$('#form1').ajaxForm(options);
	$('#form1').submit();
}

window.onbeforeunload = function(){  
    //关闭窗口时自动退出  
    if(event.clientX>360&&event.clientY<0||event.altKey){     
        alert(parent.document.location);  
    }  
};  

function changeCode() {  //刷新
    $('#kaptchaImage').hide().attr('src', BASE_PATH+'/sso/code?' + Math.floor(Math.random()*100) ).fadeIn();  
    event.cancelBubble=true;  
}