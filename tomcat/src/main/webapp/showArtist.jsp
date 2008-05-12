<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/header.jsp">
  <jsp:param name="subtitle" value="FFOOOOO" />
</jsp:include>
<h2><c:out value="${artist.name}"/></h2>
<script type="text/javascript" src="/static/coverflow.js"></script>
<canvas id="canvas" style="border: 1px solid black">
<jsp:include page="/include/footer.jsp"/>
