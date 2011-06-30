package com.fullcalendar.grails


class EventController {

	def scaffold = true


	def updateEndDate={
		Event e=Event.get(params.id)
		e.endDate.time+=24*3600000*Long.parseLong(params.dayDelta)+60000*Long.parseLong(params.minuteDelta)
		println e.endDate
		e.save(flush:true)
		render 'update OK'
	}

	def updateMoveDate={
		Event e=Event.get(params.id)
		e.allDay=params.allDay=='true'
		e.startDate.time+=24*3600000*Long.parseLong(params.dayDelta)+60000*Long.parseLong(params.minuteDelta)
		e.endDate.time+=24*3600000*Long.parseLong(params.dayDelta)+60000*Long.parseLong(params.minuteDelta)
		e.save(flush:true)
		render 'update OK'
	}
}
