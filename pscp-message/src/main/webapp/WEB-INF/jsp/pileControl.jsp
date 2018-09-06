<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<head>
<title>电桩接口-仅供调试</title>
</head>
<body>
	<div>
	<input type="button" name="bt1" value="模拟电桩(3702129000801)停止充电" onclick="stopCharge(this,'3702129000801')"/><br/>
	<input type="button" name="bt2" value="模拟电桩(3702129000802)停止充电" onclick="stopCharge(this,'3702129000802')"/><br/>
	<input type="button" name="bt3" value="模拟电桩(3702129000803)停止充电" onclick="stopCharge(this,'3702129000803')"/><br/>
	<input type="button" name="bt4" value="模拟电桩(3702129000804)停止充电" onclick="stopCharge(this,'3702129000804')"/><br/>
	<div>控制台日志:<input type="button" name="btclear" value="清空日志" onclick="clearLogs()"/></div>
	<div id="resultMsgs">
	</div>
	</div>
	<script type="text/javascript">
	    function clearLogs(){
	    	$("#resultMsgs").html("");
	    }
		function stopCharge(obj,pileCode) {
			obj.disabled=true;
			var param = {
				"pileCode" : pileCode,
				"cmd":stopCharge
			};
			$.ajax({
				type : "POST",
				url : "${contextPath}/data/pileControl",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(param),
				success : function(data) {
					console.log("发送成功");
					obj.disabled=false;
					$("#resultMsgs").append("<div>"+pileCode+":"+JSON.stringify(data)+"</div>");
				},
				error : function(error) {
					console.log("error");
					console.log(error);
					obj.disabled=false;
				}
			});
		}
	</script>
</body>