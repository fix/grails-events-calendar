package com.fullcalendar.grails

class Calendar {

    String name
    String color
    String textColor
    SortedSet events

    static hasMany = [events:Event]

    static constraints = {
        name()
        color()
        textColor()
    }

    public String toString(){
        name
    }
}
