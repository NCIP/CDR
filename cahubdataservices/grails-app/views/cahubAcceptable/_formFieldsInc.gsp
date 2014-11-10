<%@ page import="nci.obbr.cahub.datarecords.qm.CahubAcceptable" %>
<script type="text/javascript">
   $(document).ready(function() {
    
     $("#reasonselect").change(function() {
            if ($("#reasonselect").val() == '3' && $("#acceptableNo").prop('checked', true)) {
                $("#otherReasonRow").show()
            } else {
                $("#otherReasonRow").hide()
                $("#otherReason").val('')
            }
        });
        
        $("#acceptableYes").change(function() {
            if ($("#acceptableYes").val() == 'true') {
               $("#otherReason").val('')
               $("#reasonselect").val('')
                $("#otherReasonRow").hide()
            } 
        });
     });
  </script>

<div class="list">
  <div  class="div-t">
<div class="div-t-r clearfix">
      <div >

        <div class="left width-30"><label for="acceptable">Acceptable for Cahub Analysis:  
		
	</label>
        </div>

        <div class="left width-30 value ${hasErrors(bean: cahubAcceptableInstance, field: 'acceptable', 'errors')}">
          <div>
            <g:radio  id="acceptableYes" name="acceptable"  value="true" checked="${cahubAcceptableInstance?.acceptable  =='true'}" /><label for="acceptableYes">True</label> <br>
            <g:radio id="acceptableNo" name="acceptable"  value="false" checked="${cahubAcceptableInstance?.acceptable  =='false'}" /><label for="acceptableNo">False</label>
          </div>
        </div>
      </div>
    </div>

<div class="div-t-r clearfix depends-on" data-id="acceptableNo">
  
        <div class="left width-30"><label for="acceptable">Reason:</label>  </div>

        <div class="left width-30 ${hasErrors(bean: cahubAcceptableInstance, field: 'cahubUnacceptable', 'errors')}">
          
      
             <g:select id="reasonselect" name="cahubUnacceptable.id" from="${nci.obbr.cahub.staticmembers.CahubUnacceptable.list()}" optionKey="id" value="${cahubAcceptableInstance?.cahubUnacceptable?.id}" noSelection="['null': '']" />
          
        </div>
    
</div>
    
    
    <div class="div-t-r clearfix depends-on" data-id="reasonselect"  data-select="3" id="otherReasonRow" style="display:${cahubAcceptableInstance?.cahubUnacceptable?.code == 'OTHER'?'display':'none'}">
  
        <div class="left width-30"><label for="otherReason">Specify other reason:</label>  </div>

        <div class="left width-30 ${hasErrors(bean: cahubAcceptableInstance, field: 'otherReason', 'errors')}">
          
          <g:textField name="otherReason" value="${cahubAcceptableInstance?.otherReason}"/>
          
        </div>
    
</div>
    
    
 

<div class="div-t-r clearfix ">
    <div class="${hasErrors(bean: cahubAcceptableInstance, field: 'comments', 'errors')}"><label for="visit">Comments: </label><br>

      <g:textArea class="textwide" name="comments" cols="40" rows="5" value="${cahubAcceptableInstance?.comments}" />
    </div>
  </div>
    
    
    <g:if test="${cahubAcceptableInstance?.notAcceptableDate}">
      <div class="div-t-r clearfix ">
    <div class="left width-30 ${hasErrors(bean: cahubAcceptableInstance, field: 'notAcceptableDate', 'errors')}"><label for="visit">Date not Accepted: </label></div>

      <div class="left width-30"><g:formatDate date="${cahubAcceptableInstance?.notAcceptableDate}" /></div>
    </div>
    </g:if>
    
  </div>
</div>


