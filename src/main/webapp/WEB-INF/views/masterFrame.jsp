<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-default navbar-static-top" role="navigation"
    style="margin-bottom: 0">
    <div class="navbar-header">
        <a class="navbar-brand" href="/">Bonjour
        ${authentificationDTO.adresseCourriel}, bienvenue sur la page
        ${authentificationDTO.role}</a>
    </div>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i><i class="fa fa-caret-down"></i></a>
            <ul class="dropdown-menu dropdown-user">
                <li class="divider"></li>
                <li><a href="/logout-action"><i class="fa fa-sign-out fa-fw"></i> Déconnexion</a></li>
            </ul>
        </li>
    </ul>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li><a class="active" href="/"><i class="fa fa-dashboard fa-fw"></i> Accueil</a></li>
                <c:if test="${authentificationDTO.role == 'Gestionnaire'}">
                    <li><a href="/redirect-listeEmploye"><i class="fa fa-table fa-fw"></i> Liste de mes employés</a></li>
                </c:if>
                <li>
                    <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Mes options<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <c:if test="${authentificationDTO.role == 'Employe' || authentificationDTO.role == 'Gestionnaire'}" >
                            <li>
                                <a href="/redirect-entrerTemps">Entrer du temps</a>
                                <a href="/consulterAssignations">Consulter mes assignations</a>
                            </li>
                        </c:if>
                        <c:if test="${authentificationDTO.role == 'Gestionnaire'}">
                            <li><a href="/redirect-creerEmploye">Créer un employé </a></li>
                            <li><a href="/gererProjets">Gérer mes projets</a></li>
                            <li><a href="/afficherListeEmployeEtListeProjet">Assigner des tâches</a>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>