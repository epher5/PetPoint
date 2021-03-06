<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'pet.label', default: 'Pet')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-pet" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-pet" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" />: Owner: <f:display bean="owner" property="name"/> (<f:display bean="owner" property="email"/>)</h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <ol class="property-list pet">

                <li class="fieldcontain">
                    <span id="name-label" class="property-label">Name</span>
                    <span class="property-value" aria-labelledby="name-label"><f:display bean="pet" property="name"/></span>
                </li>

                <li class="fieldcontain">
                    <span id="species-label" class="property-label">Species</span>
                    <span class="property-value" aria-labelledby="species-label"><f:display bean="pet" property="species"/></span>
                </li>

                <li class="fieldcontain">
                    <span id="brees-label" class="property-label">Breed</span>
                    <span class="property-value" aria-labelledby="breed-label"><f:display bean="pet" property="breed"/></span>
                </li>

                <li class="fieldcontain">
                    <g:link controller="medicalRecord" params="[petId: petId]">MedicalRecords</g:link>
                </li>

            </ol>

            <g:form resource="${pet}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${pet}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>