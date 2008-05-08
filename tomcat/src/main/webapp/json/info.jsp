<%@ page pageEncoding="UTF-8" contentType="text/plain; charset=UTF-8" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:array items="${artists}" var="artist">
  ${artist}
</json:array>
