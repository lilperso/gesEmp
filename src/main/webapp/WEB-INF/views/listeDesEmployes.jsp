<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>ULavalTimeSheet - Liste des employés</title>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
        <script type="text/javascript">
            $(window).load(function(){
            	if (${modifierEmploye}){
            		 $('#modifierEmploye').modal('show');
            	}
            });
        </script>
    </head>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Liste des employés</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
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
                                            <th>Prénom</th>
                                            <th>Nom</th>
                                            <th>Adresse courriel</th>
                                            <th>Taux Horaire</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        <c:forEach var="employe" items="${listeEmploye}" varStatus="countRows">
                                            <tr>
                                                <td>
                                                    <c:out value="${countRows.index}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${employe.prenom}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${employe.nom}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${employe.adresseCourriel}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${employe.tauxHoraire}/hrs"/>
                                                </td>
                                                <td>
                                                    <form method="POST" action="/listes-demandes-approbations-pour-employe">
                                                        <a class="link-to-approval" href="#" >
                                                        <button type="submit" class="btn btn-primary" name="indexPeriode" value="0">Approuver</button>
                                                        </a>
                                                        <input type="hidden" name="adresseCourriel" value="
                                                        <c:out value="${employe.adresseCourriel}"/>
                                                        "/>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form method="POST" action="/redirect-modifierEmploye">
                                                        <button class="btn btn-primary" name="indexEmploye" value="${countRows.index}">Modifier employé</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->
        <div class="modal fade" id="modifierEmploye" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form:form commandName="InformationEmployeForm" method="POST" action="/modifier-employe-action">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Modifier un employé</h4>
                        </div>
                        <c:if test="${not empty listeErreur}">
                            <div class="alert alert-danger">
                                ${listeErreur}
                            </div>
                        </c:if>
                        <div class="modal-body">
                            <div class="form-group">
                                <label>Taux horaire</label> 
                                <form:input path = "tauxHoraire" type="text" class="form-control" placeholder="Ex. 8.50"/>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                            <button type="submit" class="btn btn-primary">Enregistrer</button>
                        </div>
                    </form:form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
    </body>
</html>