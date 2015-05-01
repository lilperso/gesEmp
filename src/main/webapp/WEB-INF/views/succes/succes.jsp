<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>

	<c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
	<c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
    <title>ULavalTimeSheet - Succes</title>
    
</head>
<body>
	<c:import url="/WEB-INF/views/masterFrame.jsp"/>
	<div id="page-wrapper">
	
		<div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Succès</h1>
            </div>
        </div>
		
        <div class="row">
			<c:if test="${not empty succesMessage}">
				<h4>${succesMessage}</h4>
			</c:if>
        </div>
    </div>

</body>
</html>