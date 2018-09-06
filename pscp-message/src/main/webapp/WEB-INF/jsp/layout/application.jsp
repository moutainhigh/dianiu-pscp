<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="request_uri"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><sitemesh:write property='title' />-iot-web</title>
<link href="${contextPath}/stylesheets/public.css" rel="stylesheet">
<link href="${contextPath}/stylesheets/index.css" rel="stylesheet">
<link href="${contextPath}/stylesheets/search.css" rel="stylesheet">
<script src="${contextPath}/javascripts/jquery-1.7.2.min.js"></script>
<script src="${contextPath}/javascripts/unslider.js"></script>
<%--  <script src="${contextPath}/javascripts/bootstrap.js"></script> --%>
<script src="${contextPath}/javascripts/application.js"></script>
<sitemesh:write property='head' />
<script>
function log(that,action,from,type){
	   var url;
	   var resourceId;
	   var resourceVer;
	   var resourceName;
	   var appPackage;
	   	if(type == 1){
	   		url = $(that).attr("href");
	   		resourceId = $(that).next().val();
	   		resourceVer = $(that).next().next().val();
	   		resourceName = $(that).next().next().next().val();
	   		appPackage = $(that).next().next().next().next().val();
	   		
	   		var param={"action":action,"from":from,"url":url,"resourceId":resourceId,"resourceVer":resourceVer,"resourceName":resourceName,"appPackage":appPackage};
	   		$.ajax({ 
	   	       type:"POST", 
	   	       url:"${contextPath}/log/clientLog", 
	   	       dataType:"json",      
	   	       contentType:"application/json",               
	   	       data:JSON.stringify(param), 
	   	       success:function(data){ 
	   	      		console.log("download log成功");     
	   	       },
	   	       error:function(error){
	   	    	   console.log("error");
	   	    	   console.log(error);
	   	       }
	   	    }); 
	   	}	
	}
function log_sort(action,from,sectionId){
	   var url;
	   var resourceId;
	   var resourceVer;
	   var resourceName;
	   var appPackage;
	   		url = "/sort/detail.html";
	   		resourceId = sectionId;
	   		resourceVer = 0;
	   		resourceName = 0;
	   		appPackage = 0;
	   		
	   		var param={"action":action,"from":from,"url":url,"resourceId":resourceId,"resourceVer":resourceVer,"resourceName":resourceName,"appPackage":appPackage};
	   		$.ajax({ 
	   	       type:"POST", 
	   	       url:"${contextPath}/log/clientLog", 
	   	       dataType:"json",      
	   	       contentType:"application/json",               
	   	       data:JSON.stringify(param), 
	   	       success:function(data){ 
	   	      		console.log("download log成功");     
	   	       },
	   	       error:function(error){
	   	    	   console.log("error");
	   	    	   console.log(error);
	   	       }
	   	    }); 
	   	}	
        $(function () {
            var target = $("li > a[href='${contextPath}${request_uri}']").parent();
            target.addClass("active");
            target.parents("li.dropdown").addClass("active");
            $("li[class~='dropdown']").hover(
                    function () {
                        $(this).addClass("open");
                    },
                    function () {
                        $(this).removeClass("open");
                    });
        });
        var search_input = 0;
        function entersearch(){
           var event = window.event || arguments.callee.caller.arguments[0];
           if (event.keyCode == 13)
           {
               search();
           }
       }
       function onfocuss(){
    	   $("#searchKey").attr("value","");
    	   $("#searchKey").css("color","black");
    	   search_input =1;
       }
        var page=5;//分页 0-5,6个
        function search(){
        	var key=$("#searchKey").val();
        	if(search_input == 1){
        		if(key == ""){
            		
            	}
            	else{
            		$("#curSearchKey").val(key)
                	$("#container").html("");
                	$(".nav-content").eq(0).removeClass("nav-checked");
                	$(".nav-content").eq(1).removeClass("nav-checked");
                	$(".nav-content").eq(2).removeClass("nav-checked");
                	$(".nav-content").eq(3).removeClass("nav-checked");
                	
                	$("#container").append('<div class="content-box"><div class="search-content-box" id="searchAppList"><p class="search-title"><span class="search-span">以下是</span><span class="search-word">'+key+'</span><span>的搜索结果：</span></p></div></div>');
                	
                	   var url;
                	   var resourceId;
                	   var resourceVer;
                	   var resourceName;
                	   var appPackage;
                	   	
                	   		url = 0;
                	   		resourceId = 0;
                	   		resourceVer = 0;
                	   		resourceName = $("#searchKey").val();
                	   		appPackage = 0;
                	   
                	   	var param={"action":'search',"from":'0',"url":url,"resourceId":resourceId,"resourceVer":resourceVer,"resourceName":resourceName,"appPackage":appPackage};
                		$.ajax({ 
                	       type:"POST", 
                	       url:"${contextPath}/log/clientLog", 
                	       dataType:"json",      
                	       contentType:"application/json",               
                	       data:JSON.stringify(param), 
                	       success:function(data){ 
                	       	    console.log("search log success");               
                	       } 
                	    }); 
                		
                	searchApp(key,0,page,3,'');
            	}
        	}	
        }
        function searchMore(){
        	var key=$("#curSearchKey").val();
        	var searchId=$("#curSearchId").val();
        	var begin=$("#nextBeginOffset").val();
        	var begin=parseInt(begin)+1;
        	var end=parseInt(begin)+page;
        	searchApp(key,begin,end,3,searchId);
        }
        function searchApp(key,beginOffset,endOffset,dpi,searchId){
        	$("#nextBeginOffset").val(endOffset+1);
        	$(document).ready(function(){  
        		var param={"key":key,"beginOffset":beginOffset,"endOffset":endOffset,"dpi":dpi,"searchId":searchId}; 
                $.ajax({ 
                    type:"POST", 
                    url:"${contextPath}/data/searchApp", 
                    dataType:"json",      
                    contentType:"application/json",               
                    data:JSON.stringify(param), 
                    beforeSend:function(XMLHttpRequest){
                        $("#searchAppList").append('<div class="load"><img src="${contextPath}/img/loading.gif" alt="" /><span>加载中。。。</span></div>');
                        $(".getmore").hide();
                    },
                    success:function(data){ 
                    	initSearchResult(data);                    
                    } 
                 }); 
            });  
        }
        var num=1;
        var btn=0;
        function initSearchResult(data){	
        	if(data!=null){
        		if(data.bizCode==2000){
        			$(".load").hide();
                	$(".getmore").show();
        			if(btn == 0){
        				$("#container").append('<div class="getmore" onclick="searchMore();"></div>')
                    	$("#container").append('<div class="nomore"></div>');
        				btn =1;
        			}
        			if(data!=null&&data.searchApps!=null&&data.searchApps.length>0){
                		$("#curSearchId").val(data.searchId)
                		for(var i=0;i<data.searchApps.length;i++){
                			var appId=data.searchApps[i].appId;
                			var appVer=data.searchApps[i].appVer;
                			var appName=data.searchApps[i].appName;
                			var apppkg=data.searchApps[i].appPackage;
                			var description=data.searchApps[i].description;
                			var iconFileId=data.searchApps[i].iconFileId;
                			var downCount=data.searchApps[i].currentDc;
            				downCount=downCount/10000;
            				downCount=downCount.toFixed(0);
            				var appSize=data.searchApps[i].apkSize/1024/1024;
            				appSize=appSize.toFixed(1);

                			var key=$("#curSearchKey").val();

							if((num % 2)==0){
								var content='<div class="search-app-box search-app-box-right">';
							}
							else{
								var content='<div class="search-app-box">';
							}
							
							
								
							}
							
            				
            				num++;
            				$("#searchAppList").append(content);
            				}

				}
			} else if (data.bizCode == 5001) {
				var len = $(".search-app-box").length;
				if (len == 0) {
					var key=$("#searchKey").val();
					$("#searchAppList").html('<p class="search-title"><span class="search-span">以下是</span><span class="search-word">'+key+'</span><span>的搜索结果：</span></p><div class="nosearch-content"><img src="${contextPath}/img/animation.gif" alt="" class="nosearch-img"/><div class="nosearch-text-content"><p class="nosearch-text">没有找到与 <span class="search-word">'+key+'</span> 匹配的结果o(╯□╰)o</p><p class="nosearch-text nosearch-text-top">或许你可以试下：</p><p class="nosearch-text">*核对一下搜索词</p><p class="nosearch-text">*搜索该应用另外的中文名或英文名</p></div></div>');
					$("#container").append('<div class="nomore" style="display:block"></div>');
				
				} else {
					$(".getmore").hide();
					$(".nomore").show();
				}

			}
			else if(data.bizCode == 5000){
				$("#searchAppList").html('<p class="search-title"><span class="search-span">以下是</span><span class="search-word">'+key+'</span><span>的搜索结果：</span></p><div class="nosearch-content"><img src="${contextPath}/img/animation.gif" alt="" class="nosearch-img"/><div class="nosearch-text-content"><p class="nosearch-text">没有找到与 <span class="search-word">'+key+'</span> 匹配的结果o(╯□╰)o</p><p class="nosearch-text nosearch-text-top">或许你可以试下：</p><p class="nosearch-text">*核对一下搜索词</p><p class="nosearch-text">*搜索该应用另外的中文名或英文名</p></div></div>');
				$("#container").append('<div class="nomore" style="display:block"></div>');
			}

		}

	}
</script>
</head>
<body>
	<!-- Navbar
    ================================================== -->
	<header class="navbar navbar-default navbar-fixed-top">
		<div class="head-box">
			<%-- <div class="head">
				<a href="${contextPath}/index.html"><img src="${contextPath}/img/logo.png" alt="" class="logo"/></a>
				<div class="search-box">
					<input type="text" name="searchKey" class="search" id="searchKey"
						value="搜索应用或游戏..." onkeydown="entersearch()" onfocus="onfocuss()"/>
					<input type="button" name="searchBt" class="search-btn" value="搜索" onclick="search();">
					<input type="hidden" name="curSearchKey" id="curSearchKey" value="12">
                	<input type="hidden" name="curSearchId" id="curSearchId" value="d31a227218b442f0a67dc62f20bb4f20">
                	<input type="hidden" name="nextBeginOffset" id="nextBeginOffset" value="20">
				</div>
			</div> --%>
		</div>
		<div class="nav">
			<%-- <ul>
				<a href='${contextPath}/index.html'><li class="nav-content">首页</li></a>
				<a href='${contextPath}/game.html'><li class="nav-content">找游戏</li></a>
				<a href='${contextPath}/app.html'><li class="nav-content">找应用</li></a>
				<a href='${contextPath}/topic.html'><li class="nav-content">专题</li></a>
			</ul> --%>
		</div>
	</header>

                
	<div id="container" class="container" style="min-height: 500px">
		<sitemesh:write property='body' />
	</div>
	<div id="ajax-indicator" style="display: none;">
		<span>加载中...</span>
	</div>
	<footer class="footer">
		<div class="footer-box">

		</div>
	</footer>
</body>
<script>