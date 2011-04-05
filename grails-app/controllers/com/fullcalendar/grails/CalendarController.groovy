package com.fullcalendar.grails

class CalendarController {

    def scaffold = true

    def json={
        render createJSON(Long.parseLong(params.id))
    }

    private String createJSON(long id){
        def json="["
        boolean first=true
        Calendar c=Calendar.get(id)
        c.events.each{
            if(first){
                first=false
                json+="{"
            }
            else {
                json+=",{"
            }

            json+="title:\""+it.title+"\","
            json+="start:'"+it.startDate+"',"
            json+="end:'"+it.endDate+"',"
            json+="allDay:"+it.allDay+","
            json+="url:\"${request.contextPath}/event/show/"+it.id+"\","
            json+="backgroundColor:'"+c.color+"',"
            json+="textColor:'"+c.fontColor+"'"
            json+="}"
        }
        json+="]"
        return json
    }
}
