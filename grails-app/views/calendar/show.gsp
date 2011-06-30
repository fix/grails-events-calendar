<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${calendarInstance.name}</title>
  <fullcal:resources/>
</head>
<body>
  <script>
	$(function() {
		$("#edit").click(function() { window.location="${createLink(controller:'calendar',action:'edit', id:calendarInstance.id)}"; });
	});
	</script>
<div id='loading' style='position:absolute; top: 8px; left: 200px; display:none'>Loading...</div> 
  
  <h1>${calendarInstance.name} </h1><button class="ui-button ui-button-text-only ui-widget ui-state-default ui-corner-all" id="edit"><span class="ui-button-text"><g:message code="default.button.edit.label" default="Edit"/></span></button>
<fullcal:calendar id="cal">
  theme: true,
  header: {
  left: 'prev,next today',
  center: 'title',
  right: 'month,agendaWeek,agendaDay',
  },
  columnFormat: { week: 'ddd d/M' },
  timeFormat: 'HH:mm{ - HH:mm}',
  selectable: true,
  selectHelper: true,
  select: function(start, end, allDay) {
  javascript:window.location="${createLink(controller:'event',action:'create', params:['calendar.id':calendarInstance.id])}&allDay="+allDay+"&startDate_year="+start.getFullYear()+"&startDate_month="+(start.getMonth()+1)+"&startDate_day="+start.getDate()+"&startDate_hour="+start.getHours()+"&startDate_minute="+start.getMinutes()+"&endDate_year="+end.getFullYear()+"&endDate_month="+(end.getMonth()+1)+"&endDate_day="+end.getDate()+"&endDate_hour="+end.getHours()+"&endDate_minute="+end.getMinutes()
  },
  editable: true,
  eventResize: function(event,dayDelta,minuteDelta,revertFunc) {
       jQuery.ajax({type:'POST',data:{'dayDelta': dayDelta,'minuteDelta': minuteDelta}, url:'${createLink(controller:'event', action:'updateEndDate')}'+'/'+event.id,success:function(data,textStatus){},error:function(XMLHttpRequest,textStatus,errorThrown){revertFunc()}});
  },
  eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) {
  	jQuery.ajax({type:'POST',data:{'allDay': allDay, 'dayDelta': dayDelta,'minuteDelta': minuteDelta}, url:'${createLink(controller:'event', action:'updateMoveDate')}'+'/'+event.id,success:function(data,textStatus){},error:function(XMLHttpRequest,textStatus,errorThrown){revertFunc()}}); 
  },
  loading: function(bool) {
	if (bool) $('#loading').show();
	else {
		$('#loading').hide();
		$('#cal').fullCalendar( 'rerenderEvents' );
	}
  },
  events:'${createLink(controller:"calendar", action:"json", id:calendarInstance.id)}'
</fullcal:calendar>

</body>
</html>
