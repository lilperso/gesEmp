<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp" />
        <title>ULavalTimeSheet - CreerEmploye</title>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp" />
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        Listes des approbations pour
                        <c:out value="${infoEmployeAvecPeriodes.prenom}" />
                    </h1>
                </div>
            </div>
            <c:forEach var="periode"
                items="${infoEmployeAvecPeriodes.listeDesPeriodes}"
                varStatus="countRowsParent">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Liste de la periode du
                                <c:out value="${periode.dateDebut}" />
                                au
                                <c:out value="${periode.dateFin}" />
                            </div>
                            <div class="panel-body">
                                <c:forEach var="quartDeTravail"
                                    items="${periode.listeQuartsTravailDTO}" varStatus="countRows">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Date début</th>
                                                    <th>Date fin</th>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        Quart de travail 
                                                        <c:out value="${countRows.index}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${quartDeTravail.heureEntree}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${quartDeTravail.heureSortie}" />
                                                    </td>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div>
                                    <c:choose>
                                        <c:when
                                            test="${quartDeTravail.estDepenseApprouve() != true}">
                                            <div class="well">
                                                <h4>Dépenses</h4>
                                                <p>
                                                    Notes :
                                                    <c:out value="${quartDeTravail.noteDepense}" />
                                                </p>
                                                <p>
                                                    Total :
                                                    <c:out value="${quartDeTravail.montantDepense}" />
                                                </p>
                                                <form action="/approuver-demande-depense/" method="POST">
                                                    <input type="hidden" name="adresseCourriel"
                                                    value="
                                                    <c:out value="${infoEmployeAvecPeriodes.adresseCourriel}"/>
                                                    " />
                                                    <input type="hidden" name="dateQuart"
                                                    value="
                                                    <c:out value='${quartDeTravail.dateQuartTravail}'/>
                                                    " />
                                                    <button type="submit"
                                                        class="btn btn-default btn-lg btn-block approuverDepense"
                                                        href="#">Approuver</button>
                                                </form>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                    <c:choose>
                                        <c:when
                                            test="${quartDeTravail.estDeplacementApprouve() != true}">
                                            <div class="well">
                                                <h4>Déplacements</h4>
                                                <p>
                                                    Notes :
                                                    <c:out value="${quartDeTravail.noteDeplacement}" />
                                                </p>
                                                <p>
                                                    Total :
                                                    <c:out value="${quartDeTravail.noteDeplacement}" />
                                                </p>
                                                <form action="/approuver-demande-deplacement/" method="POST">
                                                    <input type="hidden" name="adresseCourriel"
                                                    value="
                                                    <c:out value="${infoEmployeAvecPeriodes.adresseCourriel}"/>
                                                    " />
                                                    <input type="hidden" name="dateQuart"
                                                    value="
                                                    <c:out value='${quartDeTravail.dateQuartTravail}'/>
                                                    " />
                                                    <button type="submit"
                                                        class="btn btn-default btn-lg btn-block approuverDepense"
                                                        href="#">Approuver</button>
                                                </form>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp" />
        <script>
            $('.approuverDeplacement').click(function() {
            	$.post("/approuver-demande-deplacement", {
            		indexPeriode : $(this).attr('indexPeriode'),
            		indexDeplacement : $(this).attr('indexDeplacement')
            	}).done(function(data) {
            		alert("Success");
            	});
            });
            
            $('.approuverDepense').click(function() {
            	$.post("/approuver-demande-depense", {
            		indexPeriode : $(this).attr('indexPeriode'),
            		indexDepense : $(this).attr('indexDepense')
            	}).done(function(data) {
            		alert("Success");
            	});
            });
        </script>
    </body>
</html>