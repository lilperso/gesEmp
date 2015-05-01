<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
        <title>ULavalTimeSheet - Gérer les projets</title>
    </head>
    <body>
        <div id="wrapper">
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Gestion de projets</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-10">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Mes Projets
                            <form action="creerNouveauProjet" method="GET" align="right">
                                <button type="submit" class="btn btn-primary">Ajouter un projet</button>
                            </form>
                        </div>
                        <div class="panel-body">
                            <c:forEach var="projet" items="${listeProjets}" varStatus="countRows">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr bgcolor="#c3c3c3">
                                                <th>#</th>
                                                <th>Nom du projet</th>
                                                <th>Description du projet</th>
                                                <th></th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td>
                                                <c:out value="${countRows.index}"/>
                                            </td>
                                            <td>
                                                <c:out value="${projet.nomProjet}"/>
                                            </td>
                                            <td>
                                                <c:out value="${projet.descriptionProjet}"/>
                                            </td>
                                            <td align="right">
                                                <form action="creerNouvelleTache" method="GET">
                                                    <button type="submit" class="btn btn-primary" name="indexProjet" value="${countRows.index}">Ajouter une tache</button>
                                                </form>
                                            </td>
                                            <td align="right">
                                                <form action="modifierProjet" method="GET">
                                                    <button type="submit" class="btn btn-primary" name="nomProjet" value="${projet.nomProjet}">Modifier le projet</button>
                                                </form>
                                            </td>
                                            <td align="right">
                                                <form action="supprimerProjet" method="POST">
                                                    <button type="submit" class="btn btn-primary" name="nomProjet" value="${projet.nomProjet}">Supprimer le projet</button>
                                                </form>
                                            </td>
                                        </tr>
                                        <c:if test="${not empty projet.listeTachesDTO}">
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th>#</th>
                                                        <th>Nom Tache</th>
                                                        <th>Description Tache</th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                </thead>
                                                <c:forEach  var="tache" items="${projet.listeTachesDTO}" varStatus="countRowsTache">
                                                    <tr>
                                                        <td></td>
                                                        <td>
                                                            <c:out value="${countRowsTache.index}"/>
                                                        </td>
                                                        <td>
                                                            <c:out value="${tache.nomTache}"/>
                                                        </td>
                                                        <td>
                                                            <c:out value="${tache.descriptionTache}"/>
                                                        </td>
                                                        <td align="right">
                                                            <form action="modifierTache" method="GET">
                                                                <input type="hidden" name="indexTache" value="${countRowsTache.index}">
                                                                <button type="submit" class="btn btn-primary" name="nomProjet" value="${projet.nomProjet}">Modifier</button>
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <form action="supprimerTache" method="POST">
                                                                <input type="hidden" name="nomProjet" value="${projet.nomProjet}">
                                                                <button type="submit" class="btn btn-primary" name="nomTache" value="${tache.nomTache}">Supprimer</button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </c:if>
                                    </table>
                            </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>