import com.fullcalendar.grails.Calendar
import com.fullcalendar.grails.Event

class BootStrap {
    def init = { servletContext ->
        Calendar home=new Calendar(name:"Home",color:'red',textColor:'white')
        Calendar work=new Calendar(name:"Work",color:'green',textColor:'white').save()

        home.addToEvents(new Event(summary:"something to do at home",location:"Paris",allDay:false,startDate:new Date(), endDate:new Date(new Date().time+3600000),description:"This is all good there"))
        home.save()
    }

    def destroy = {
    }
}