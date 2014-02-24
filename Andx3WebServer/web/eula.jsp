<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();//项目名称
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";// 获得项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量
    pageContext.setAttribute("basePath",basePath);// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。
%>

<!DOCTYPE html>
<html lang="zh_CN" class="no-js">
<head>
    <base href="<%=basePath %>">
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Andx3 Web Server</title>
    <meta name="description" content="Andx3 Web Server" />
    <meta name="keywords" content="Andx3 Web Server" />
    <meta name="author" content="Winnid" />
    <link rel="shortcut icon" href="img/favicon.ico">
</head>
<body>

<div style='layout-grid:15.6pt'>

    <p align=center style='text-align:center'><b style='mso-bidi-font-weight:
normal'><span style='font-family:SimHei'>用户协议<span lang=EN-US><o:p></o:p></span></span></b></p>

    <p align=left style='text-align:left'><b style='mso-bidi-font-weight:
normal'><span lang=EN-US style='font-family:SimHei'><o:p>&nbsp;</o:p></span></b></p>

    <p align=left style='text-align:left;text-indent:21.0pt'><span
            style='font-size:9.0pt;font-family:SimHei'>欢迎使用贴吧看图神器，请您仔细阅读以下条款，如果您对本协议的任何条款表示异议，您可以选择不使用本应用；进入本应用则意味着您将同意遵守本协议下全部规定，并完全服从于贴吧看图神器的统一管理。<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='text-align:left;text-indent:21.0pt'><span
            style='font-size:9.0pt;font-family:SimHei'>凡是注册用户和浏览用户均为贴吧看图神器用户（以下统称<span
            lang=EN-US>&quot;</span>用户<span lang=EN-US>&quot;</span>）。<span lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='text-align:left;text-indent:21.0pt'><span
            style='font-size:9.0pt;font-family:SimHei'>第<span lang=EN-US>1</span>条 用户的个人信息受到保护，不接受任何个人或单位的查询。国家机关依法查询除外，用户的个人设置公开除外。<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='text-align:left;text-indent:21.0pt'><span
            style='font-size:9.0pt;font-family:SimHei'>第<span lang=EN-US>2</span>条 用户的言行不得违反《计算机信息网络国际联网安全保护管理办法》、《互联网信息服务管理办法》、《互联网电子公告服务管理规定》、《维护互联网安全的决定》、《互联网新闻信息服务管理规定》等相关法律规定，不得在本应用发布、传播或以其它方式传送含有下列内容之一的信息：<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>1.</span><span style='font-size:9.0pt;font-family:SimHei'>反对宪法所确定的基本原则的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>2.</span><span style='font-size:9.0pt;font-family:SimHei'>危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>3.</span><span style='font-size:9.0pt;font-family:SimHei'>损害国家荣誉和利益的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>4.</span><span style='font-size:9.0pt;font-family:SimHei'>煽动民族仇恨、民族歧视、破坏民族团结的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>5.</span><span style='font-size:9.0pt;font-family:SimHei'>破坏国家宗教政策，宣扬邪教和封建迷信的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>6.</span><span style='font-size:9.0pt;font-family:SimHei'>散布谣言，扰乱社会秩序，破坏社会稳定的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>7.</span><span style='font-size:9.0pt;font-family:SimHei'>散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>8.</span><span style='font-size:9.0pt;font-family:SimHei'>侮辱或者诽谤他人，侵害他人合法权利的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>9.</span><span style='font-size:9.0pt;font-family:SimHei'>煽动非法集会、结社、游行、示威、聚众扰乱社会秩序的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:10.5pt;mso-para-margin-left:
1.0gd;text-align:left;text-indent:21.0pt'><span lang=EN-US style='font-size:
9.0pt;font-family:SimHei'>10.</span><span style='font-size:9.0pt;font-family:SimHei'>以非法民间组织名义活动的；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:31.5pt;mso-para-margin-left:
3.0gd;text-align:left'><span lang=EN-US style='font-size:9.0pt;font-family:
SimHei'>11.</span><span style='font-size:9.0pt;font-family:SimHei'>含有虚假、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、猥亵、或其它道德上令人反感的内容；<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='margin-left:31.5pt;mso-para-margin-left:
3.0gd;text-align:left'><span lang=EN-US style='font-size:9.0pt;font-family:
SimHei'>12.</span><span style='font-size:9.0pt;font-family:SimHei'>含有中国法律、法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其它内容的。<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='text-align:left;text-indent:21.0pt'><span
            style='font-size:9.0pt;font-family:SimHei'>第<span lang=EN-US>3</span>条 贴吧由用户搜索获取，是用户对该吧名称所指事物、现象介绍、评论的平台。贴吧文章仅代表作者观点，与本应用无关。对于用户言论的真实性引发的全部责任，由用户自行承担，本应用不承担任何责任。<span
            lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='text-align:left;text-indent:21.0pt'><span
            style='font-size:9.0pt;font-family:SimHei'>第<span lang=EN-US>4</span>条 用户之间因使用而产生或可能产生的任何纠纷和<span
            lang=EN-US>/</span>或损失，由用户自行解决并承担相应的责任，与本应用无关。<span lang=EN-US><o:p></o:p></span></span></p>

    <p align=left style='text-align:left;text-indent:21.0pt'><span
            style='font-size:9.0pt;font-family:SimHei'>第<span lang=EN-US>5</span>条 如因系统维护或升级等而需暂停服务时，将事先公告。若因硬件故障或其它不可抗力而导致暂停服务，于暂停服务期间造成的一切不便与损失，本应用不负任何责任。
由于调整导致信息丢失和<span lang=EN-US>/</span>或其他结果的，本应用不承担任何责任。<span lang=EN-US><o:p></o:p></span></span></p>

</div>

</body>
</html>