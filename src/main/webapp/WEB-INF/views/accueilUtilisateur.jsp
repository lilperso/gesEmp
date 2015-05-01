<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/WEB-INF/views/balisesHeadCommunes.jsp"/>
        <c:import url="/WEB-INF/views/includeScriptCommun.jsp"/>
        <title>ULavalTimeSheet - Accueil</title>
    </head>
    <body>
        <c:import url="/WEB-INF/views/masterFrame.jsp"/>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Accueil</h1>
                    <p>
                        <c:out value="${messageErreur}"  />
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>