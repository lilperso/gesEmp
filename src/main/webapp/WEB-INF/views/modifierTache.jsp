<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <title>ULavalTimeSheet - Modifier une tâche</title>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>

        <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Modifier une tâche</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Détails de la tâche
                    </div>
                    <form:form name="creerTacheForm" id="creer-tache-form" commandName="creerTacheForm" method="POST" role="form" action="modifierTache">
                        <c:if test="${not empty errMsg}">
                            <div class="alert alert-danger">
                                ${errMsg}
                            </div>
                        </c:if>
                        <div class="panel-body">
                            <div class="form-group">
                                <label>Nom de la tâche</label>
                                <c:choose>
                                    <c:when test="${empty tacheDTO.nomTache}">
                                        <form:input path="nomTache" class="form-control" placeholder="Nom de la tâche..."></form:input>
                                    </c:when>
                                    <c:otherwise>
                                        <form:input path="nomTache" value="${tacheDTO.nomTache}" class="form-control" placeholder="Nom de la tâche..."></form:input>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="form-group">
                                <label>Description de la tache</label>
                                <c:choose>
                                    <c:when test="${empty tacheDTO.descriptionTache}">
                                        <form:input path="descriptionTache" class="form-control" placeholder="Description de la tâche..."></form:input>
                                    </c:when>
                                    <c:otherwise>
                                        <form:input path="descriptionTache" value="${tacheDTO.descriptionTache}" class="form-control" placeholder="Description de la tâche..."></form:input>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                    </form:form>
                    <div class="panel-footer" style="direction: rtl">
                    	<button type="button" class="btn btn-primary" onclick="window.location='gererProjets'">Annuler</button>
                    	<button id="creer-tache-btn" type="sumbit" class="btn btn-primary">Modifier la tâche</button>
                    </div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
    </body>
</html>