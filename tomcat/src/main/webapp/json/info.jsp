<%@ page pageEncoding="UTF-8" contentType="text/plain; charset=UTF-8" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<json:array items="${artists}" var="artist">
  <json:object>
    <json:property name="id" value="${artist.id}"/>
    <c:if test="${not empty artist.wikipediaUrl}">
      <json:object name="wikipedia">
      	<json:property name="url" value="${artist.wikipediaUrl}"/>
	<c:if test="${not empty artist.wikipediaBlurb}">
	  <json:property name="blurb" value="${artist.wikipediaBlurb}"/>
	</c:if>
      </json:object>
    </c:if>
  </json:object>
</json:array>
