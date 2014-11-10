
        <style type="text/css">
          .blueRow{
            background-color:#B2D1FF;
          }
           .blueRow, td{
             font-weight:bold;
           }
        </style>           

<h3>Section D. Surgical Procedures</h3>
                  
                   <h3 align="center">Pre-Operative Medications</h3>
                  <table>
                    <tr><td colspan="2">Duration of administration of pre-operative medications to surgery  (hr:min)&nbsp;${surgicalProceduresInstance?.durationPreOpeMed}</td>
                    </tr>
                    <tr class="blueRow" ><td>Type of intravenous (IV) sedation administered</td><td colspan="1">Dosage / Unit</td></tr>
                    
                       <g:each in="${list1}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each>
                  
                    <tr class="blueRow" ><td>Type of IV Opiate administered</td><td colspan="1">Dosage / Unit</td></tr>
                       <g:each in="${list2}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each>
                     
                    <tr class="blueRow" ><td>Type of IV Antiemetic administered</td><td colspan="1">Dosage / Unit</td></tr>
                       <g:each in="${list3}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 
                    
                     
                    <tr class="blueRow" ><td>Type of IV Antacid administered</td><td colspan="1">Dosage / Unit</td></tr>
                       <g:each in="${list4}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 
                    
                     
                    <tr class="blueRow" ><td>List other IV pre-operative medications administered but not previously listed above</td><td colspan="1">Dosage / Unit</td></tr>
                   
                      <g:each in="${list12}" status="i" var="med">
                       
                       
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           
                           <td>${med.medicationName}</td>
                           <td>${med.dosage}</td>
                           
                         </tr>
                      
                     </g:each>
                  
                  
                  </table>   
                   
                   

                   

                  <br></br>
                   <h3 align="center">Pre-Operative Anesthesia</h3>
                  <table>
                    <tr> <td colspan="3">Duration of anesthesia induction  (hr:min)&nbsp;${surgicalProceduresInstance?.durationAnesthesia}</td>
                    </tr>
                    
                      <tr class="blueRow" >
                        <td>Type of local anesthesia agents administered</td><td colspan="1">Dosage / Unit</td></tr>
                        <g:each in="${list5}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 
                    
                      
                      
                      
                      <tr class="blueRow" ><td>Type of regional (spinal/epidural) anesthesia agents  administered</td><td colspan="1">Dosage / Unit</td></tr>
                          <g:each in="${list6}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 
                      
                      <tr class="blueRow" ><td>Type of IV anesthetic administered</td><td colspan="1">Dosage / Unit</td></tr>
                          <g:each in="${list7}" status="i" var="med">
                       <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 
                      
                    
                      <tr class="blueRow" ><td>Type of IV narcotic / opiate anesthetic administered</td><td colspan="1">Dosage / Unit</td></tr>
                           <g:each in="${list8}" status="i" var="med">
                          <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 

                      
                      <tr class="blueRow" ><td>Type of IV muscle relaxant administered</td><td colspan="1">Dosage / Unit</td></tr>
                          <g:each in="${list9}" status="i" var="med">
                          <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="2">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 
                       
                      <tr class="blueRow" ><td>Type of Inhalation anesthetic administered</td><td colspan="1">Dosage / Unit</td></tr>
                          <g:each in="${list10}" status="i" var="med">
                          <g:if test="${med.isOther1 || med.isOther2}">
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>Other (specify): &nbsp;${med.medicationName}</td>
                           <td colspan="1">${med.dosage}</td>
                         </tr>
                       </g:if>
                       <g:else>
                      
                       <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         <td>${med.medicationName}</td>
                         <td colspan="1">${med.dosage}</td>
                       </tr>
                         </g:else>
                       </g:each> 
                       
                         
                      <tr class="blueRow" ><td>Type of other anesthetics administered that were not previously listed</td><td colspan="1">Dosage / Unit</td></tr>
                       <g:each in="${list12}" status="i" var="med">
                       
                       
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           
                           <td>${med.medicationName}</td>
                           <td>${med.dosage}</td>
                           
                         </tr>
                      
                       </g:each>
                  
                      
                        
                  </table>
                  
                   
                   
                   
                   
                    <br></br>
                   <h3 align="center">Other medications administered during surgery</h3>
                   
                   <table>
                      <tr class="blueRow" >
                        <td>Type of local anesthesia agents administered</td><td colspan="1">Dosage / Unit</td></tr> 
                     
                       <g:each in="${list11}" status="i" var="med">
                       
                      
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                           <td>${med.subCatgory} (specify):&nbsp;${med.medicationName}</td>
                           <td >${med.dosage}</td>
                         </tr>
                      
                     
                     </g:each>  
                      
                      <tr class="blueRow" ><td>Any other medications (specify)</td><td colspan="1">Dosage / Unit</td></tr>
                        <g:each in="${list14}" status="i" var="med">
                       
                       
                         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                           <td>${med.medicationName}</td>
                           <td>${med.dosage}</td>
                         </tr>
                      
                      </g:each> 
                      
                   </table>
                   
                   
         
