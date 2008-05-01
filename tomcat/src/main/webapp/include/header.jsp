<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Mushup! <c:out value="${param.subtitle}"/></title>
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.5.1/build/reset/reset-min.css"/>
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.5.1/build/fonts/fonts-min.css"/> 
<link rel="stylesheet" type="text/css" href="http://static.mushup.foolip.org/style.css"/>
<script type="text/javascript" src="http://yui.yahooapis.com/2.5.1/build/yahoo-dom-event/yahoo-dom-event.js"></script>  
<script type="text/javascript" src="http://yui.yahooapis.com/2.5.1/build/connection/connection-min.js"></script> 
<script type="text/javascript">ROOT="<c:url value='/'/>"</script>
<script type="text/javascript" src="http://static.mushup.foolip.org/input.js"></script>
<script type="text/javascript" src="http://static.mushup.foolip.org/search.js"></script>
</head>
<body>
<div id="header">
  <h1><a href="<c:url value='/'/>">Mushup!</a></h1>

  <form action="<c:url value='/search'/>" method="POST"
	id="search" onsubmit="return false;">
    <input type="text" size="40" name="query"
	   class="cleardefault" value="Search for..."/>
    <select name="type">
      <option value="all" selected="selected">All</option>
      <option value="artist">Artist</option>
      <option value="release">Release</option>
      <option value="track">Track</option>
    </select>
  </form>

  <div id="user">
    <c:choose>
      <c:when test="${not empty sessionScope.openid}">
	Logged in as <b><c:out value="${sessionScope.openid}" escapeXml="true" /></b> &bull;
	<a href="<c:url value='/settings'/>">Settings</a> &bull;
	<a href="<c:url value='/logout'/>">Logout</a>
      </c:when>
      <c:otherwise>
	<a href="<c:url value='/login'/>">Login</a>
      </c:otherwise>
    </c:choose>
  </div>
</div>
