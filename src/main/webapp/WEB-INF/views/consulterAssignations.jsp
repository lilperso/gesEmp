<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
        <title>ULavalTimeSheet - Consulter mes assignations</title>
    </head>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Mes projets et tâches assignés</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Mes assignations
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Projet</th>
                                            <th>Tâches</th>
                                        </tr>
                                        <c:forEach var="projet" items="${listeProjetsDTO}">
                                            <tr>
                                                <td> ${projet.nomProjet} </td>
                                                <td> </td>
                                            </tr>
                                            <c:forEach  var="tache" items="${projet.listeTachesDTO}">
                                                <tr>
                                                    <td> </td>
                                                    <td> ${tache.nomTache} </td>
                                                </tr>
                                            </c:forEach>
                                        </c:forEach>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>