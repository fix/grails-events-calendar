package com.fullcalendar.grails

class CalendarController {
	
	def scaffold=true 

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

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[calendarInstanceList: Calendar.list(params), calendarInstanceTotal: Calendar.count()]
	}

	def create = {
		def calendarInstance = new Calendar()
		calendarInstance.properties = params
		return [calendarInstance: calendarInstance]
	}

	def save = {
		def calendarInstance = new Calendar(params)
		if (calendarInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'calendar.label', default: 'Calendar'), calendarInstance.id])}"
			flash.calendarid=calendarInstance.id
			redirect(action: "show", id: calendarInstance.id)
		}
		else {
			render(view: "create", model: [calendarInstance: calendarInstance])
		}
	}

	def show = {
		def calendarInstance = Calendar.get(params.id)
		if (!calendarInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'calendar.label', default: 'Calendar'), params.id])}"
			redirect(action: "list")
		}
		else {
			[calendarInstance: calendarInstance]
		}
	}

	def edit = {
		def calendarInstance = Calendar.get(params.id)
		if (!calendarInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'calendar.label', default: 'Calendar'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [calendarInstance: calendarInstance]
		}
	}

	def update = {
		def calendarInstance = Calendar.get(params.id)
		if (calendarInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (calendarInstance.version > version) {

					calendarInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'calendar.label', default: 'Calendar')]
					as Object[], "Another user has updated this Calendar while you were editing")
					render(view: "edit", model: [calendarInstance: calendarInstance])
					return
				}
			}
			calendarInstance.properties = params
			if (!calendarInstance.hasErrors() && calendarInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'calendar.label', default: 'Calendar'), calendarInstance.id])}"
				flash.calendarid=calendarInstance.id
				redirect(action: "show", id: calendarInstance.id)
			}
			else {
				render(view: "edit", model: [calendarInstance: calendarInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'calendar.label', default: 'Calendar'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def calendarInstance = Calendar.get(params.id)
		if (calendarInstance) {
			try {
				def cid=calendarInstance.id
				calendarInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'calendar.label', default: 'Calendar'), params.id])}"
				flash.calendarid=cid
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'calendar.label', default: 'Calendar'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'calendar.label', default: 'Calendar'), params.id])}"
			redirect(action: "list")
		}
	}
}
