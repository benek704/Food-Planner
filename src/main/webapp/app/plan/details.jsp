<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kuba
  Date: 20.07.2022
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>

<body>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-between">
        <a href="/" class="navbar-brand main-logo main-logo-smaller">
            Zaplanuj <span>Jedzonko</span>
        </a>
        <div class="d-flex justify-content-around">
            <h4 class="text-light mr-3">Imię</h4>
            <div class="circle-div text-center"><i class="fas fa-user icon-user"></i></div>
        </div>
    </nav>
</header>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%@include file="/app/dashboard/dashboardSection.jsp" %>

        <div class="m-4 p-3 width-medium ">
            <div class="dashboard-content border-dashed p-3 m-4">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">SZCZEGÓŁY PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="/app/plan/list" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <div class="schedules-content-header">
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Nazwa planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">${Plan.name}</p>
                            </div>
                        </div>
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Opis planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">${Plan.description}</p>
                            </div>
                        </div>
                    </div>

                    <table class="table">
                        <c:if test="${not empty DaysMap}">
                            <c:forEach items="${DaysMap}" var="dayMapItem">
                                <thead>
                                <tr class="d-flex">
                                    <th class="col-2">${dayMapItem.key.name}</th>
                                    <th class="col-7"></th>
                                    <th class="col-1"></th>
                                    <th class="col-2"></th>
                                </tr>
                                </thead>
                                <tbody class="text-color-lighter">
                                <c:forEach items="${dayMapItem.value}" var="item">
                                    <tr class="d-flex">
                                        <td class="col-2">${item.name}</td>
                                        <td class="col-7">${item.recipe.name}</td>
                                        <td class="col-1 center">
                                            <a href="/add/plan/remove?id=${item.id}&planId=${Plan.id}" id="delete"
                                               class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                                        </td>
                                        <td class="col-2 center">
                                            <a href="/app/recipe/details?id=${item.recipe.id}"
                                               class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </c:if>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="../../scripts/deleteConfirmation.js" type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
