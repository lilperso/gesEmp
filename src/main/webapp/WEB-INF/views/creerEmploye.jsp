<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <title>ULavalTimeSheet - CreerEmploye</title>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Creer Employe</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            Créer employé
                        </div>
                        <form:form commandName="creerEmployeForm" method="POST" action="/creerEmploye-action" role="form">
                            <c:if test="${not empty listeErreur}">
                                <div class="alert alert-danger">
                                    ${listeErreur}
                                </div>
                            </c:if>
                            <div class="panel-body">
                                <div class="form-group">
                                    <form:input path="adresseCourriel" class="form-control" placeholder="Adresse Courriel"  type="email"/>
                                </div>
                                <div class="form-group">
                                    <form:input path="motDePasse" class="form-control" placeholder="Mot de passe (temporaire)"/>
                                </div>
                                <div class="form-group">
                                    <label>Role</label>
                                    <select class="form-control" name="role">
                                        <option>Employé</option>
                                        <option>Gestionnaire</option>
                                    </select>
                                </div>
                            </div>
                            <div class="panel-body" id="informationPersonnelle">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a type="checkbox" data-toggle="collapse" data-parent="#informationPersonnelle" href="#collapseOne">Informations personnelles</a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse">
                                        <div class="form-group">
                                            <form:input path="prenom" class="form-control" placeholder="Prénom"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input path="nom" class="form-control" placeholder="Nom"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input path="adresseResidence" class="form-control" placeholder="Adresse de résidence"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="numTelephone" class="form-control" placeholder="Numero de téléphone"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-primary">Créer Employé</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
    </body>
</html>