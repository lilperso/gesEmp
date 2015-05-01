<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
        <title>ULavalTimeSheet - Assigner projet</title>
    </head>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Assigner des tâches</h1>
                </div>
            </div>
            <div class="row">
                <form:form commandName="assignerProjetForm" method="POST" action="/assignerProjet-action">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Mes employés
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Email</th>
                                            </tr>
                                            <c:forEach var="email" items="${listeAdresseCourrielEmployeDTO.listeEmploye}" varStatus="countRows">
                                                <tr>
                                                    <td>
                                                        <c:out value="${countRows.index}"/>
                                                    </td>
                                                    <td>
                                                        <form:checkbox path="employeSelectionne" value="${email}"/>
                                                        ${email}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Mes projets
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Projet</th>
                                                <th>Tâches</th>
                                            </tr>
                                            <c:forEach var="projet" items="${listeProjetDTO}">
                                                <tr>
                                                    <td>
                                                        <form:checkbox path="projetSelectionne" value="${projet.nomProjet}"/>
                                                        ${projet.nomProjet} 
                                                    </td>
                                                    <td> </td>
                                                </tr>
                                                <c:forEach  var="tache" items="${projet.listeTachesDTO}">
                                                    <tr>
                                                        <td> </td>
                                                        <td>
                                                            <form:checkbox path="tacheSelectionnee" value="${projet.nomProjet}/${tache.nomTache}"/>
                                                            ${tache.nomTache} 
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:forEach>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12">
                        <button type="submit" class="btn btn-lg btn-success btn-block">Enregistrer les assignations</button>
                    </div>
                </form:form>
            </div>
        </div>
    </body>
</html>