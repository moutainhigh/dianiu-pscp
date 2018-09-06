<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<head>
<title>车辆控制</title>
</head>
<body>
	<div>
	车辆编号:<input type="text" name="terminalId" id="terminalId" value=""/><br/>
	指令类型:<input type="text" name="cmd" id="cmd" value=""/><br/>
	指令说明:<br/>
	C97:寻车,C98:锁车/解锁车,C05:通油电指令,C04:断油电指令,C01:冷启动指令,S01:查询终端制造商,S09:里程同步<br/>
	S14:获取软件版本<br/>
	扩展指令:<input type="text" name="cmd" id="cmd" value=""/>(S01,S02,S03,F01)<br/>
	<input type="button" name="bt1" value="模拟系统给电车辆发送指令" onclick="sendCmd()"/><br/>
	<div>控制台日志:<input type="button" name="btclear" value="清空日志" onclick="clearLogs()"/></div>
	<div id="resultMsgs">
	</div>
	</div>
	<script type="text/javascript">
	    function clearLogs(){
	    	$("#resultMsgs").html("");
	    }
		function sendCmd() {
			var terminalId=$("#terminalId").val();
			var extCmd=$("#exCmd").val();
			extCmd=extCmd.trim();
        	var cmd=$("#cmd").val();
			var param = {
				"terminalId" : terminalId,
				"cmd" : cmd,
				"exCmd":extCmd
			};
			$.ajax({
				type : "POST",
				url : "${contextPath}/data/carControl",
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