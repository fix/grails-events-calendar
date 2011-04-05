import com.fullcalendar.grails.*


class BootStrap {
    def init = { servletContext ->
        new Calendar(name:"Home",color:'red',fontColor:'white').save()
        new Calendar(name:"Work",color:'green',fontColor:'white').save()
    }

    def destroy = {
    }
}