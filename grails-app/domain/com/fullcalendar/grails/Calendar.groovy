package com.fullcalendar.grails

class Calendar {

    String name
    String color
    String fontColor
    SortedSet events

    static hasMany = [events:Event]

    static constraints = {
        name()
        color()
        fontColor()
    }

    public String toString(){
        name
    }
}
