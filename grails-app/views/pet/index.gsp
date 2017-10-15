<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'pet.label', default: 'Pet')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-pet" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create" params="[ownerId: ownerId]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-pet" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" />: Owner: <f:display bean="owner" property="name"/> (<f:display bean="owner" property="email"/>)</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${petList}">
                <table class="table">
                    <thead><th>Name</th><th>Species</th><th>Breed</th><th>Birthdate</th></thead>
                    <tbody>

                    <g:each in="${petList}" var="thePetList">

                    <tr>
                        <td><g:link action="show" id="${thePetList.id}" params="[ownerId: ownerId]">${thePetList.name}</g:link></td>
                        <td><g:link action="show" id="${thePetList.id}" params="[ownerId: ownerId]">${thePetList.species}</g:link></td>
                        <td><g:link action="show" id="${thePetList.id}" params="[ownerId: ownerId]">${thePetList.breed}</g:link></td>
                        <td><g:link action="show" id="${thePetList.id}" params="[ownerId: ownerId]">${thePetList.birthdate}</g:link></td>
                    </tr>

                    </g:each>
                </tbody>
                </table>
                <div class="pagination">
                    <g:paginate total="${petCount ?: 0}" />
                </div>
            </g:if>
            <g:else>
                <div class="message" role="status">None at the moment! Please enter pets of the owner.</div>
            </g:else>
        </div>
    </body>
</html>