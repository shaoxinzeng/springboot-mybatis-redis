<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/zmxy";
%>
<c:set var="path" value="<%=path%>"></c:set>
<c:set var="basePath" value="<%=basePath%>"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>帮助中心</title>
<meta http-equiv="Expires" content="-1">               
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache"> 
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.mobile-1.4.2.min.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/basic.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/common.css" />
 
<script type="text/javascript" src="${basePath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.mobile-1.4.2.min.js"></script>
<script type="text/javascript" src="${basePath}/js/base.js"></script>
<script src="${basePath }/js/global-1.1.0.min.js"></script>
</head>
<body>
    <div class="ui-page ui-page-theme-a ui-page-active" data-role="page" >
    	<div class="wrapper" id="thetimebeing" style="display: none">
          <div class="no-news">
              <img src="${basePath}/images/b_08.png" alt="">
              <p>暂无帮助信息哦~</p>
          </div>
        </div>
        <!-- main start -->
         <div class="wrapper pd40">
        <%--   <ul class="top22 clearfix">
       		 <li id="dhjing"><img src="${basePath }/images/dh1.png">
       		 	<a href="javascript:sobotService();">
       		 		<span>智能客服</span>
       		 	</a>
       		 </li>
        	 <li id="dh2" style="border-right:none;"><img style="width:2rem; height:1.6rem;" src="${basePath }/images/dh2.png">
        	 	<a href="javascript:iosPhoneService();">
        	 		<span>电话客服</span>
        	 		<input type="hidden" value="${tel}" data-role="none" disabled="disabled" id="tel">
        	 	</a>
        	 </li>
          </ul> --%>
          <ul class="help-list" id="Investors">
          </ul>
        </div> 
        <!-- main end -->
         <div id="ttc" style="display:none;"></div>
 		 <div class="wind" style="display:none;">
   			 <h2>电话客服</h2>
   			 <p>400-208-393</p>
    		 
   			 <ul>
   				 <li><a rel="external" href="javascript:;" id="close">取消</a></li>
   				 <li><a rel="external" href="javascript:androidPhoneService();">呼叫</a></li>
  			 </ul>
    	 </div>
    </div>
    <form action="" id="tempForm" method="get" data-ajax="false"></form>
</body>
</html>
<script type="text/javascript">
$(function(){
	 var u = navigator.userAgent, app = navigator.appVersion;
	 var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
	 var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	 if(isAndroid){
		$('#dh2').click(function(e) {
			$('.wind').show();
			$('#ttc').show();
		});		
	 } 
	 if(isIOS){
		 $('#dh2').click(function(e) {
			 iosPhoneService();
			});		
	}
	
	 $('#dhjing').click(function(e) {
		 sobotService();
		});		
	$('#close').click(function(e) {
	    $('.wind').hide();
		$('#ttc').hide();
	});	

	
	$(window).scroll(function () {
	  	//已经滚动到上面的页面高度
	 	var scrollTop = $(this).scrollTop();
	 	//页面高度
	 	var scrollHeight = $(document).height();
	 	//浏览器窗口高度
		var windowHeight = $(this).height();
		//此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
		if (scrollTop + windowHeight == scrollHeight) {
			theHotIssue.pageNum++;
			if(theHotIssue.pageNum<=theHotIssue.totalPage){
				theHotIssue.theHotIssueInfo();
			}
		 }
	 });
	theHotIssue.init();
})
var theHotIssue={
	pageNum:1,
	totalPage:1,
	init:function(){
		theHotIssue.theHotIssueInfo();
	},theHotIssueInfo:function(){
		var data = {};
		data.numPerPage=50;
		data.pageNum=theHotIssue.pageNum;
		openAjax('${path}/help/findTheHotIssueList', data, theHotIssue.theHotIssueHtml);
	},theHotIssueHtml:function(data){
		if(data.code ==$_SUCCESS_CODE){
			if(theHotIssue.pageNum==1&&data.list.length<=0){
				$("#help").show();
			}
			theHotIssue.totalPage=data.pageInfo.totalPage;
			var transHtml='';
			if(data.list.length > 0){
				$.each(data.list,function(i, account) {
					 transHtml+='<li>'+
		             '     <div class="help-title">'+
		             '       <img src="${basePath}/images/jt.png" alt="">'+
		             '       <span>'+account.channelTitle+'</span>'+
		             '     </div>'+
		             '     <div class="help-info">'+
		             '         <p>'+account.contentTxt+'</p>'+
		             '     </div>'+
		             ' </li>';
				});
				$("#Investors").append(transHtml);
				$('.help-list .help-title').unbind();
			 	$('.help-list .help-title').click(function(event) {
		            $(this).toggleClass('current');
		            $(this).next().slideToggle();
        		});
			}
			if(theHotIssue.pageNum==1&&data.list.length<=0){
				$("#thetimebeing").show();
			}
		}else{
			$("#thetimebeing").show();
		}
	}
}
//电话客服
function androidPhoneService() {
    var phone = $("#tel").val();
    var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid) {
        nativeMethod.callPhoneMethod(phone);
    } /* else if (isIOS) {
        $("#tempForm").attr("action", "/www.iosPhone.com&" + phone);
        $("#tempForm").submit();
    } */
}

function iosPhoneService(){
	var phone = $("#tel").val();
    var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    /* if (isAndroid) {
        nativeMethod.callPhoneMethod(phone);
    } else */ if (isIOS) {
        $("#tempForm").attr("action", "/www.iosPhone.com&" + phone);
        $("#tempForm").submit();
    }
}

//智能客服
function sobotService(){
	var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid) {
        nativeMethod.sobot();
    } else if (isIOS) {
        $("#tempForm").attr("action", "/www.iosSobot.com&");
        $("#tempForm").submit();
    }
}
</script>