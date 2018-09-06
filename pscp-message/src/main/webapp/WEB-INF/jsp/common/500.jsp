<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<%
	//记录日志
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("500.jsp");
	logger.error(exception.getMessage(), exception);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
       "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title>We're sorry, but something went wrong</title>
	<style type="text/css">
		body { background-color: #fff; color: #666; text-align: center; font-family: arial, sans-serif; }
		div.dialog {
			width: 25em;
			padding: 0 4em;
			margin: 4em auto 0 auto;
			border: 1px solid #ccc;
			border-right-color: #999;
			border-bottom-color: #999;
		}
		h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
	</style>
</head>

<body>
  <!-- This file lives in public/500.html -->
  <div class="dialog">
    <h1>We're sorry, but something went wrong.</h1>
    <p>We've been notified about this issue and we'll take a look at it shortly.</p>
  </div>
</body>
</html>