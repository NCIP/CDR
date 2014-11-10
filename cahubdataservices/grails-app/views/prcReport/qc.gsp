<%@ page import="nci.obbr.cahub.prc.PrcReport" %>
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="prcreport edit wide" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${prcReportInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.edit.label.prcreport" args="[entityName]" /></title>
       <g:javascript>
           $(document).ready(function(){
           
               $(".do").click(function(){
                     var paid =(jQuery(this).get(0).id).substring(2)
                     //alert("psid: " + psid)
                     document.getElementById("paid").value = paid
                   
                      
                });
              
           
                 $("#a1").click(function(){
                     // alert("what???")
                    //  document.getElementById("d1").style.display = 'block';
                    
                      $("#d1").show()
                      $("#l1").hide()

                   return false;
                      
                });  
                
                
                 $("#c1").click(function(){
                
                    
                   document.getElementById("new_pi_specimen_id").value='';
                   document.getElementById("new_pi_issue_description").value=''
                   document.getElementById("d1").style.display = 'none';
                    $("#l1").show()
                    
                    return false;
                  
                  
               
                });    
                
                
               
                
                
                 $("#qq1").click(function(){
                    
                      $("#issue").show()

                      
                }); 
                
                 $("#qq2").click(function(){
                     $("#issue").hide()

                });  
                
                
                 $(":input").change(function(){
                  document.getElementById("changed").value = "Y"
                  //alert("Changed!")
                });
                
           });
           
          function openImageWin(image_id){
             //var w2=window.open('https://microscopy.vai.org/imageserver/@@/@'+image_id + '/view.apml', 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
              var w2=window.open('${AppSetting.findByCode("APERIO_URL").value}'+image_id, 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
              w2.focus();
           }
           
           function check_id(){
          // alert("start checking...")
            
            var sid = document.getElementById("new_pi_specimen_id").value
            //alert("sid: " + sid.length)
        
            var desc = document.getElementById("new_pi_issue_description").value
             
            if((sid==null || sid.length==0) && (desc== null || desc.length == 0)){
             alert("Please specify specimen id and/or issue description!")
             return false
             }
           
          
           }
           
           
             function del_pi(pi_id){
            
              document.getElementById("delete_pi").value=pi_id;
              
  
                
              return confirm("Are you sure you want to delete the issue record?")
           }
           
  
  
          function sub(){
            var changed = document.getElementById("changed").value
            if(changed == "Y"){
               alert("Please save the change!")
               return false
               }
            
          }
        </g:javascript>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
        <div id="container" class="clearfix">
          
          
          
       <g:render template="/prcReport/viewInc1" bean="${prcReportInstance}" />
        <g:render template="/prcReport/viewInc3" bean="${prcReportInstance}" />
        </div>
     </body>
</html>
