<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String username = (String) session.getAttribute("username");
%>


<header>
    <nav>
        <ul>
            <li>Home</li>
            <li>About</li>
            <li>Board</li>

            <%
                if(username != null){
            %>
            <li><%= username %>님 환영합니다.</li>
            <li><a href="logout">로그아웃</a></li>
            <%
                }else{
            %>
            <li><a href="login.jsp">로그인</a></li>
            <li><a href="register.jsp">회원가입</a></li>
            <%
                }
            %>
        </ul>
    </nav>


</header>