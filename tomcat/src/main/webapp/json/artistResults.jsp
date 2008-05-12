<%@ page pageEncoding="UTF-8" contentType="text/plain; charset=UTF-8" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:array items="${result}" var="artistResult">
  <json:object>
    <json:property name="score" value="${artistResult.score}"/>
    <json:property name="id" value="${artistResult.artist.id}"/>
    <json:property name="name" value="${artistResult.artist.name}"/>
    <json:property name="sortName" value="${artistResult.artist.sortName}"/>
    
      <json:property name="disambiguation" value="${artistResult.artist.disambiguation}"/>
    
  </json:object>
</json:array>
