
<%@ page import="com.fullcalendar.grails.Event" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'event.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="summary" title="${message(code: 'event.summary.label', default: 'Summary')}" />
                        
                            <g:sortableColumn property="startDate" title="${message(code: 'event.startDate.label', default: 'Start Date')}" />
                        
                            <g:sortableColumn property="endDate" title="${message(code: 'event.endDate.label', default: 'End Date')}" />
                        
                            <g:sortableColumn property="allDay" title="${message(code: 'event.allDay.label', default: 'All Day')}" />
                        
                            <g:sortableColumn property="location" title="${message(code: 'event.location.label', default: 'Location')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${eventInstanceList}" status="i" var="eventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${eventInstance.id}">${fieldValue(bean: eventInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "summary")}</td>
                        
                            <td><g:formatDate date="${eventInstance.startDate}" /></td>
                        
                            <td><g:formatDate date="${eventInstance.endDate}" /></td>
                        
                            <td><g:formatBoolean boolean="${eventInstance.allDay}" /></td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "location")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${eventInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
