package com.fullcalendar.grails

import java.util.Date;

class CalendarController {

    def scaffold = true

    def json={
		if(!params.start){
			params.start=(long)((System.currentTimeMillis()-13600000*24*30)/1000)
		}
		if(!params.end){
			params.end=(long)((System.currentTimeMillis()+13600000*24*30)/1000)
		}
		render(text:createJSON(new Date(Long.parseLong(params.start+"000")),new Date(Long.parseLong(params.end+"000")), Calendar.get(params.id)),contentType:"text/html")
	}
	
	private String createJSON(Date start, Date end, Calendar cal){
		def json="["
		boolean first=true
		def c = Event.createCriteria()
		def results=c.list([order:'desc', sort:'startDate']){
			and{
				eq('calendar',cal)
				between('startDate', start, end)
			}
		}
		results.each{
			if(first){
				first=false
				json+="{"
			}
			else {
				json+=",{"
			}
			json+="\"id\":"+it.id+","
			json+="\"title\":\""+it.summary.replaceAll("\"","'")+"\","
			json+="\"start\":\""+it.startDate+"\","
			json+="\"end\":\""+it.endDate+"\","
			json+="\"allDay\":"+it.allDay+","
			json+="\"url\":\"../../event/show/"+it.id+"\","
			json+="\"textColor\": \""+cal.textColor+"\","
			json+="\"backgroundColor\": \""+cal.color+"\""
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
