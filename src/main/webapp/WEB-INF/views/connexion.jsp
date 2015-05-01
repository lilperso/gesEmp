<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <title>ULavalTimeSheet - Connexion</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Connexion</h3>
                        </div>
                        <form:form commandName="utilisateurForm" method="POST" action="/login-action" role="form">
                            <div class="panel-body">
                                <c:out value="${messageErreur}"  />
                                <div class="form-group">
                                    <form:input path="adresseCourriel" class="form-control" placeholder="Courriel"/>
                                </div>
                                <div class="form-group">
                                    <form:password path="motDePasse" class="form-control" placeholder="Mot de passe"/>
                                </div>
                                <div class="checkbox">
                                    <label>
                                    <input name="remember" type="checkbox" value="Remember Me">Se souvenir de moi
                                    </label>
                                </div>
                                <button type="submit" class="btn btn-lg btn-success btn-block">Connexion</button>                 
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
    </body>
</html>