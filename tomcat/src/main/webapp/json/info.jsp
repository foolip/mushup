<%@ page pageEncoding="UTF-8" contentType="text/plain; charset=UTF-8" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:array items="${artists}" var="artist">
  <json:object>
    <json:property name="id" value="${artist.id}"/>
    <json:property name="wikipediaUrl" value="${artist.wikipediaUrl}"/>
    <json:property name="wikipediaBlurb" value="${artist.wikipediaBlurb}"/>
  </json:object>
</json:array>
