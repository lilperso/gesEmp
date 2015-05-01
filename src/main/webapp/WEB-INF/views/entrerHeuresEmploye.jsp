<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>UlavalTimeSheet - Entrer heure</title>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
        <script type="text/javascript">
            $(window).load(function(){
            	if (${modifierPeriode}){
            		 $('#entrerHeures').modal('show');
            	}
            });
            
            $(function() {
            $('#heureDebut').timepicker();
            $('#heureFin').timepicker();
            });
        </script>
        
        <style>
        	.error
        	{
        		border: 1px solid red;
        	}
        </style>
    </head>
    <body>
        <div id="wrapper">
            <c:import url="/WEB-INF/views/masterFrame.jsp"/>
            <!-- Page Content -->
            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Entrer mes heures</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Calendrier
                            </div>
                            <div class="panel-heading">
                                Période du : ${periodeDTO.dateDebut}
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="calendrier">
                                        <thead>
                                            <tr>
                                                <th>Info</th>
                                                <th>Lundi</th>
                                                <th>Mardi</th>
                                                <th>Mercredi</th>
                                                <th>Jeudi</th>
                                                <th>Vendredi</th>
                                                <th>Samedi</th>
                                                <th>Dimanche</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td></td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}" varStatus="countRows">
                                                    <c:choose>
                                                        <c:when test="${empty quartTravail.heureEntree}">
                                                            <td>
                                                                <form method="POST" action="/entrerTemps">
                                                                    <button class="btn btn-primary" name="indexPeriode" value="${countRows.index}">Entrer temps</button>
                                                                </form>
                                                            </td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>
                                                                <form method="POST" action="/entrerTemps">
                                                                    <button class="btn btn-primary" name="indexPeriode" value="${countRows.index}">Modifier</button>
                                                                </form>
                                                            </td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Date</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.dateQuartTravail}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Heure entrée</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.heureEntree}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Heure sortie</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.heureSortie}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Note quart</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.noteQuart}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Depense ($)</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.montantDepense}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Note depense</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.noteDepense}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Deplacement (km)</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.longueurDeplacement}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Note deplacement</td>
                                                <c:forEach var="quartTravail" items="${periodeDTO.listeQuartsTravailDTO}">
                                                    <td>
                                                        <c:out value="${quartTravail.noteDeplacement}"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <form method="POST" action="/periode-suivante-action">
                                    <button type="submit" class="btn btn-default" style="float: right">Période suivante</button>
                                </form>
                                <form method="POST" action="/periode-precedente-action">
                                    <button type="submit" class="btn btn-default" style="float: left">Période précédente</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /#page-wrapper -->
            <div class="modal fade" id="entrerHeures" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form:form commandName="QuartTravailForm" method="POST" action="/entrer-mes-heures-employe-action">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">${optionQuart} un quart de travail</h4>
                            </div>
                            <c:if test="${not empty listeErreur}">
                                <div class="alert alert-danger">
                                    ${listeErreur}
                                </div>
                            </c:if>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Date du quart : </label>
                                    <label>${QuartTravailForm.dateQuart}</label>     
                                    <form:input path="dateQuart" type="hidden" value="${QuartTravailForm.dateQuart}" />
                                </div>
                                <div class="form-group">
                                    <form:input id="heureDebut" path="heureEntree" class="form-control required"  placeholder="Heures d'entrée"/>
                                </div>
                                <div class="form-group">
                                    <form:input id="heureFin" path="heureSortie" class="form-control required" placeholder="Heures de sortie"/>
                                </div>
                                <div class="form-group">
                                    <label>Note sur les heures</label>
                                    <form:textarea path="noteHeure" class="form-control" rows="3"/>
                                </div>
                                <div class="form-group">
                                    <label>Compte de dépenses</label> 
                                    <form:input path="depense" type="number" class="form-control" cplaceholder="Ex. 200$"/>
                                </div>
                                <div class="form-group">
                                    <label>Note sur les dépenses</label>
                                    <form:textarea path="noteDepense" class="form-control" rows="3"/>
                                </div>
                                <div class="form-group">
                                    <label>Kilometrage (0.52$ / Km)</label> 
                                    <form:input  path="deplacement" type="number" class="form-control" placeholder="Ex. 50 Km"/>
                                </div>
                                <div class="form-group">
                                    <label>Note sur les déplacements</label>
                                    <form:textarea  path="noteDeplacement" class="form-control" rows="3"/>
                                    <form:input class="required" path="appelService" type="hidden"/>
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
        </div>
        <script>
            $('#QuartTravailForm').submit(function(){
            	var isValid = true;
            	// Reset des erreurs antérieures
            	$('input').each(function(i, elem) {
            		$(elem).removeClass("error");
            	});
            	
            	// Valide les champs obligatoires
            	$('.required').each(function(i, elem) {
            		if($(elem).val() == "") {
            			$(elem).addClass("error");
            			isValid = false;
            		}
            	});
            	
            	// Valide les champs numériques
            	$('input[type="number"]').each(function(i, elem) {
            		if($(elem).val() != "" && !$.isNumeric($(elem).val())) {
            			$(elem).addClass("error");
            			isValid = false;
            		}
            	});
            	
            	// Note depense validation
            	if($('#noteDepense').val() != "" && $('#depense').val() == "")
           		{
            		$('#depense').addClass("error");
            		isValid = false;
           		}

            	
            	// Note deplacement validation
            	if($('#noteDeplacement').val() != "" && $('#deplacement').val() == "")
           		{
            		$('#deplacement').addClass("error");
            		isValid = false;
           		}
            	
            	return isValid;
            });
        </script>
    </body>
</html>
