package events.calendar

class JQueryDatePickerTagLib {
	
	def jqDatePicker = {attrs, body ->
		def name=attrs.name
		def value=attrs.value
		def id= attrs.id?:name
		
		out.println "<input style=\"width=100px;\" type=\"text\" name=\"${name}\" id=\"${id}\" value=\"${attrs.value?.format('dd/MM/yyyy')}\" />"
		out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\"  value=\"${attrs.value?.format('dd')}\" />"
		out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\"  value=\"${attrs.value?.format('MM')}\" />"
		out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\"  value=\"${attrs.value?.format('yyyy')}\" />"
		out.println "<select style=\"width=30px;\" type=\"select\" name=\"${name}_hour\" id=\"${id}_hour\">"
		(0..23).each {
			out.println "<option value=\"${it}\"${value?.getHours()==it?' selected':''}>${it<10?'0':''}${it}</option>"
		}
		out.println "</select>:"
		
		out.println "<select style=\"width=30px;\" class=\"time\" type=\"select\" name=\"${name}_minute\" id=\"${id}_minute\"/>"
		[0,15,30,45].each {
			out.println "<option value=\"${it}\"${it==value?.getMinutes()?' selected':''}>${it<10?'0':''}${it}</option>"
		}
		out.println "</select>"
		
		out.println '''
		<script type="text/javascript">
		\$(document).ready(function(){
		   \$("#'''+name+'''").datepicker({
		   	  dateFormat:"dd/mm/yy",
			firstDay: 1,
			isRTL: false,
			showMonthAfterYear: false,
			yearSuffix: '',
		   	  onClose: function(dateText, inst) {
                 \$("#'''+name+'''_month").attr("value",dateText.split("/")[1]);
                 \$("#'''+name+'''_day").attr("value",dateText.split("/")[0]);
                 \$("#'''+name+'''_year").attr("value",dateText.split("/")[2]);
              }
		   });
		   
		})
		</script>
		'''
		
	}
	

}
