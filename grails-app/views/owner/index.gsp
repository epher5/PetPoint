<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'owner.label', default: 'Owner')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-owner" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-owner" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${owners}">
            <table class="table">
                <thead><th>Name</th><th>Email</th><th>Phone</th></thead>
                <tbody>
                <g:each in="${owners}" var="theOwner">

                    <tr>
                        <td><g:link action="show" id="${theOwner.id}">${theOwner.name}</g:link></td>
                        <td><g:link action="show" id="${theOwner.id}">${theOwner.email}</g:link></td>
                        <td><g:link action="show" id="${theOwner.id}">${theOwner.phone}</g:link></td>
                    </tr>

                </g:each>
                </tbody>
            </table>
            <div class="pagination">
                <g:paginate total="${ownerCount ?: 0}" />
            </div>
            </g:if>
        </div>
    </body>
</html>