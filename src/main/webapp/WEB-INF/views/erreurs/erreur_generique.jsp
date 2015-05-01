<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>

	<c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
	<c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
    <title>ULavalTimeSheet - Erreur</title>
    
</head>
<body>
	<c:import url="/WEB-INF/views/masterFrame.jsp"/>
	<div id="page-wrapper">
	
		<div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Erreur non gérée</h1>
            </div>
        </div>
		
        <div class="row">
			Une erreur non gérée a été rencontrée.
        </div>
    </div>

</body>
</html>