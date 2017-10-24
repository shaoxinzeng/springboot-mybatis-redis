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
<title>消息中心</title>
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
<body style="background:#fff;">
    <div class="ui-page ui-page-theme-a ui-page-active" data-role="page"  style="background:#fff;">
        <!-- main start -->
        <div class="wrapper" id="thetimebeing" style="display: none">
          <div class="no-news">
              <img src="${basePath}/images/b_08.png" alt="">
              <p>暂无消息通知哦~</p>
          </div>
        </div>
        <!-- main end -->
        <div class="wrapper">
          <div class="qb">
		        <ul id="Investors">
		        </ul>
      		</div>
        </div>
    </div>
</body>
</html>
<script type="text/javascript">
$(function(){
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
		data.numPerPage=20;
		data.pageNum=theHotIssue.pageNum;
		openAjax('${path}/help/findactivity', data, theHotIssue.theHotIssueHtml);
	},theHotIssueHtml:function(data){
		if(data.code ==$_SUCCESS_CODE){
			if(theHotIssue.pageNum==1&&data.list.length<=0){
				$("#help").show();
			}
			theHotIssue.totalPage=data.pageInfo.totalPage;
			var transHtml='';
			if(data.list.length > 0){
				$.each(data.list,function(i, account) {
					 transHtml+='<li><a href="${path}/content/details?id='+account.id+'" rel="external">'+
		            			'<h3><i class="yd"></i><em>'+account.channelTitle+'</em><span>'+account.createTime+'</span></h3>'+
		            			'<p>'+account.contentSummary+'</p>'+
		            			'</a> </li>';
				});
				$("#Investors").append(transHtml);
			}
			if(theHotIssue.pageNum==1&&data.list.length<=0){
				$("#thetimebeing").show();
			}
		}else{
			if(theHotIssue.pageNum==1){
				$("#thetimebeing").show();
			}
		}
	}
}
</script>


