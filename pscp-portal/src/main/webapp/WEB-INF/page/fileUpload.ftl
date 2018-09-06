<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>电力服务云平台</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="icon" href="../../statics/img/favicon.ico">
</head>

<body>
<h1>springMVC字节流输入上传文件</h1>
<form name="userForm1" action="/file/fileupload" enctype="multipart/form-data" method="post">
    <div id="newUpload1">
        <input type="file" name="myfile">
    </div>

    <input type="submit" value="上传">
</form>

<br>
<br>
<hr align="left" width="60%" color="#FF0000" size="3">
<#if fid??>
<h1>${fid}</h1>
</#if>
<#if msg??>
<h1>${msg}</h1>
</#if>
</body>
</html>