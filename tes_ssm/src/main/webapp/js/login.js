$(function(){
	//每次加载login.html，都要从cookie中获取用户名，并赋值给用户名的框
	$("#inputName").val(getCookie("loginName"));
	$(".container form").submit(function(){
		return login();
	});
});
function login(){
	//获取数据
	var loginName=$("#inputName").val();
	var password=$("#inputPassword").val();
	var remember=$(".container form input[type=checkbox]").get(0).checked;
	//根据页面的数据做异步请求
	$.ajax({
		url:"user/login",
		data:{"username":loginName,"password":password},
		type:"post",
		dataType:"json",
		success:function(result){
			if(result.status==1){
				//登录成功,先判断是否记住密码，如果记住，保存到cookie中
				if(remember){
					//记住密码，打对勾
					addCookie("loginName",loginName,12);
				}
				window.location.href="index.html";
			}else if(result.status==0){
				//登录失败
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
	return false;
}