package com.fullcalendar.grails

class Event implements Comparable{

    String title
    Date startDate
    Date endDate
    Boolean allDay=Boolean.FALSE
    String summary
    Calendar calendar

    static belongsTo = [calendar:Calendar]

    static constraints = {
        title()
        startDate(attributes:[precision:"minute"])
        endDate(attributes:[precision:"minute"])
        allDay()
        summary(maxSize:9092)
    }

    public int compareTo(Object that){
        startDate.compareTo(that.startDate)
    }

    public String toString(){
        title
    }
}
