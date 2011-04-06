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

            json+="title:\""+it.summary+"\","
            json+="start:'"+it.startDate+"',"
            json+="end:'"+it.endDate+"',"
            json+="allDay:"+it.allDay+","
            json+="url:\"${request.contextPath}/event/show/"+it.id+"\","
            json+="backgroundColor:'"+c.color+"',"
            json+="textColor:'"+c.textColor+"'"
            json+="}"
        }
        json+="]"
        return json
    }


    def ical={
        render createIcal(Long.parseLong(params.id))
    }

    private String createIcal(long id){
        def df=new java.text.SimpleDateFormat("yyyyMMdd'T'HHmmss")
        Calendar c=Calendar.get(id)
        def ical='''BEGIN:VCALENDAR
X-WR-CALNAME:'''+c.name+'''
X-WR-CALDESC:GRAILS Plugin Calendar
PRODID:-//Francois-Xavier Thoorens/NONSGML Bennu 0.1//EN
VERSION:2.0
'''
        c.events.each{
            ical+="BEGIN:VEVENT\n"
            ical+="UID:"+c.name+it.id+"@grails\n"
            ical+="DTSTAMP:"+df.format(new Date())+"Z\n"
            ical+="SUMMARY:"+it.summary+"\n"
            ical+="DTSTART:"+df.format(it.startDate)+"\n"
            ical+="DTEND:"+df.format(it.endDate)+"\n"
            ical+="DESCRIPTION:"+it.description+"\n"
            ical+="LOCATION:"+it.location+"\n"
            ical+="END:VEVENT\n"
        }
        ical+="END:VCALENDAR\n"
        return ical
    }

}
