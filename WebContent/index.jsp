<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
<%
if (loginMember == null) {
%>
<a href="login_form.jsp">로그인</a>
<%
} else { %>
<%=loginMember.getMlid() %>(<%=loginMember.getMlname() %>)님 환영합니다.<br />
<a href="logout">로그아웃</a>
<%
}
%>

<a href="pdt_list.pdta">상품목록-백엔드</a>
<hr />
<a href="pdt_list.pdt">상품목록-프론트</a>
<hr />
<a href="pdt_list.pdt?bcata=1">여성한복</a><br />
<a href="pdt_list.pdt?bcata=2">남성한복</a><br />
<a href="pdt_list.pdt?bcata=3">장신구</a><br />
</body>
</html>