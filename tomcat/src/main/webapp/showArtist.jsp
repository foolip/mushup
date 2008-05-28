<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/include/header.jsp">
  <c:param name="subtitle" value="${artist.name}"/>
</c:import>
<h2><c:out value="${artist.name}"/></h2>
<script type="text/javascript" src="/static/coverflow.js"></script>
<canvas id="coverflow"></canvas>
<h2>Albums</h2>
<ul>
<c:forEach var="release" items="${artist.releases}">
  <li class="release">
    <c:if test="${not empty release.asin}">
      <a href="http://www.amazon.com/gp/product/${release.asin}">
	<img class="coverart" src="http://ec1.images-amazon.com/images/P/${release.asin}"/>
      </a>
    </c:if>
    <a href="http://musicbrainz.org/release/${release.id}.html">${release.title}</a>
  </li>
</c:forEach>
</ul>
<c:import url="/include/footer.jsp"/>
