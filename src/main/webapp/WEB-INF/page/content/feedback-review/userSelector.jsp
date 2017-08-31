<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户列表</title>
    <link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
    <script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user-selector.css">
    <script src="${ctx}/js/dw/user-selector.js"></script>
</head>
<body>
<div class="user-selector">
    <table>
        <tr><th>姓名</th><th>账号</th></tr>
        <c:forEach items="${userList}" var="user">
            <tr class="userTr" data-user-id="${user.id}">
                <td class="userTd">${user.name}</td><td>${user.email}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<input type="hidden" id="selectDimension" value="${dimensionId}">
</body>
</html>
