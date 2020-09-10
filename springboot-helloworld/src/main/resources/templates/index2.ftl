<html>
<head lang="zh-CN">
    <meta charset="UTF-8" />
    <title>hello</title>
</head>
<body>
    <p>hello这是模板代码生成的我叫${name}</p>

    <#if sex == "0"> 男<#elseif sex == "1"> 女<#else> 其他 </#if>

    <#list userList as u>
        ${u}
    </#list>
</body>
</html>