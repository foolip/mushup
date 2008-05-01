<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/header.jsp"/>
<div class="login">
  <c:if test="${not empty error}" > 
    <div class="error">
      <b>OpenID Login failed</b><br/>
      <c:out value="${error}" escapeXml="true" />
    </div>
  </c:if> 
  <form action="login" method="post">
    <input type="text" size="40" name="identifier"
	   class="cleardefault" value="Enter your OpenID here..."/>
    <input type="submit" name="login" value="Login..." />
  </form>
  <p><a href="http://openid.net/get/">How do I get an OpenID?</a></p>
</div>
<jsp:include page="/include/footer.jsp"/>
