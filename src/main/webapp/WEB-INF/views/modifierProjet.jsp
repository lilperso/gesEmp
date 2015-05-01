<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <title>ULavalTimeSheet - Modifier un projet</title>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>
        <!-- Page Content -->
        <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Modifier un projet</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Modifier un projet
                    </div>
                    <form:form id="creerProjetForm" commandName="creerProjetForm" method="POST" role="form" action="modifierProjet">
                        <c:if test="${not empty errMsg}">
                            <div class="alert alert-danger">
                                ${errMsg}
                            </div>
                        </c:if>
                        <div class="panel-body">
                            <div class="form-group">
                                <label>Nom du projet</label>
                                <c:choose>
                                    <c:when test="${empty projetDTO.nomProjet}">
                                        <form:input path="nomProjet" class="form-control" placeholder="Nom de projet..."></form:input>
                                    </c:when>
                                    <c:otherwise>
                                        <form:input path="nomProjet" value="${projetDTO.nomProjet}" class="form-control" placeholder="Nom de projet..."></form:input>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:choose>
                                <c:when test="${empty projetDTO.descriptionProjet}">
                                    <form:input path="descriptionProjet" class="form-control" placeholder="Description de projet..."></form:input>
                                </c:when>
                                <c:otherwise>
                                    <form:input path="descriptionProjet" value="${projetDTO.descriptionProjet}" class="form-control" placeholder="Description de projet..."></form:input>
                                </c:otherwise>
                            </c:choose>
                    </form:form>
                    <div class="panel-footer" style="direction: rtl">
                    <button type="button" class="btn btn-primary" onclick="window.location='gererProjets'">Annuler</button>
                    <button id="creerProjet-btn" type="submit" class="btn btn-primary">Modifier le projet</button>
                    </div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
        <script type="text/javascript">
            $(document).ready(function() {
               $("#creerProjet-btn").click(function() {
                   $("#creerProjetForm").submit();
               });
            });
        </script>
    </body>
</html>