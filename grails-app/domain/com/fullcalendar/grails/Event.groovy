package com.fullcalendar.grails

class Event implements Comparable{

    String summary
    Date startDate
    Date endDate
    Boolean allDay=Boolean.FALSE
    String location
    String description

    Calendar calendar

    static belongsTo = [calendar:Calendar]

    static constraints = {
        summary()
        startDate(attributes:[precision:"minute"])
        endDate(attributes:[precision:"minute"])
        allDay()
        location()
        description(maxSize:9092)
    }

    public int compareTo(Object that){
        startDate.compareTo(that.startDate)
    }

    public String toString(){
        summary
    }
}
