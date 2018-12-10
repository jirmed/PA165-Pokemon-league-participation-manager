<%-- 
    Document   : Jsp page for creating new pokemon species.
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
    <h1><fmt:message key="pokemon.species.create.new"/></h1>
    
    <form:form method="post" action="${pageContext.request.contextPath}/pokemonSpecies/create"
               modelAttribute="pokemonSpeciesCreate" cssClass="form-horizontal">
    
        <div class="form-group ${speciesName_error?'has-error':''}">
            <form:label path="speciesName" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="speciesName" cssClass="form-control"/>
                <form:errors path="speciesName" cssClass="help-block"/>
            </div>
        </div>
            
        <div class="form-group">
            <form:label path="primaryType" cssClass="col-sm-2 control-label">
                        <fmt:message key="pokemon.species.primary.type"/>
            </form:label>
            <div class="col-sm-10">
                <form:select path="primaryType" cssClass="form-control">
                    <c:forEach items="${allTypes}" var="types">
                        <form:option value="${types}"><c:out value="${types}"/></form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="primaryType" cssClass="error"/>
            </div>
        </div>
        
        <div class="form-group">
            <form:label path="secondaryType" cssClass="col-sm-2 control-label">
                        <fmt:message key="pokemon.species.secondary.type"/>
            </form:label>
            <div class="col-sm-10">
                <form:select path="secondaryType" cssClass="form-control">
                    <c:forEach items="${allTypes}" var="types">
                        <form:option value="${types}"><c:out value="${types}"/></form:option>
                    </c:forEach>
                    <form:option value="${types}"><c:out value="${none}"/></form:option>
                </form:select>
                <form:errors path="secondaryType" cssClass="error"/>
            </div>
        </div>
            
        <div class="form-group">
            <form:label path="evolvesFromId" cssClass="col-sm-2 control-label">
                        <fmt:message key="pokemon.species.evolves.from"/>
            </form:label>
            <div class="col-sm-10">
                <form:select path="evolvesFromId" cssClass="form-control">
                    <c:forEach items="${allSpecies}" var="species">
                        <form:option value="${species.id}"><c:out value="${species.speciesName}"/></form:option>
                    </c:forEach>
                    <form:option value="${types}"><c:out value="${none}"/></form:option>
                </form:select>
                <form:errors path="evolvesFromId" cssClass="error"/>
            </div>
        </div>
            
        <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
            <span class="glyphicon glyphicon-edit"></span> 
            <fmt:message key="confirm"/>
        </button>
    </form:form>
            
</jsp:attribute> 
</my:pagetemplate>