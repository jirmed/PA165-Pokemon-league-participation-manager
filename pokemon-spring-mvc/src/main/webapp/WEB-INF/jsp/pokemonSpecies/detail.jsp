<%-- 
    Document   : Jsp page fog detailed information about a specific pokemon species.
    Author     : Tamás Rózsa 445653
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="pokemon.species"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <h1><fmt:message key="pokemon.species"/> ${pokemonSpecies.speciesName}</h1>
    <table class="table">
        <tbody>
        <tr>
            <th><fmt:message key="pokemon.species.species.name"/></th>
            <td><c:out value="${pokemonSpecies.speciesName}"/></td>
        </tr>

        <tr>
            <th><fmt:message key="pokemon.species.primary.type"/></th>
            <td><c:out value="${pokemonSpecies.primaryType}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="pokemon.species.secondary.type"/></th>
            <td><c:out value="${pokemonSpecies.secondaryType}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="pokemon.species.evolves.from"/></th>
            <td><c:out value="${pokemonSpecies.evolvesFrom}"/></td>
        </tr>
        </tbody>
    </table>
    <my:extraTag href="/pokemonSpecies/list" class="btn btn-default">
        <fmt:message key="all.pokemon.species"/>
    </my:extraTag>
</jsp:attribute>
</my:pagetemplate>
