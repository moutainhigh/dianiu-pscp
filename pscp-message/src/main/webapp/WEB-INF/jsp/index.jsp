<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<head>
<title>首页</title>
</head>
<body>
	<div>
	电桩编号:<input type="text" name="chargeId" id="chargeId" value=""/><br/>
	指令类型:<input type="text" name="cmd" id="cmd" value=""/>(open,close,finish,order)<br/>
	<input type="button" name="bt1" value="模拟系统给电桩发送指令" onclick="sendCmd()"/><br/>
	<div>控制台日志:<input type="button" name="btclear" value="清空日志" onclick="clearLogs()"/></div>
	<div id="resultMsgs">
	</div>
	
	
	</div>
	<script type="text/javascript">
	    function clearLogs(){
	    	$("#resultMsgs").html("");
	    }
		function sendCmd() {
			var chargeId=$("#chargeId").val();
        	var cmd=$("#cmd").val();
			var param = {
				"chargeId" : chargeId,
				"cmd" : cmd
			};
			$.ajax({
				type : "POST",
				url : "${contextPath}/data/send_cmd",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(param),
				success : function(data) {
					console.log("发送成功");
					$("#resultMsgs").append("<div>"+JSON.stringify(data)+"</div>");
				},
				error : function(error) {
					console.log("error");
					console.log(error);
				}
			});
		}
	</script>
</body>