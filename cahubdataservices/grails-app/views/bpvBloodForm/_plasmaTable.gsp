<g:if test="${updatediv == true}">
<g:hasErrors bean="${bpvBloodFormInstance}">
  <div class="errors"><g:renderErrors bean="${bpvBloodFormInstance}" as="list" /></div>
 </g:hasErrors>
</g:if>
                                  <div class="redtext hide">To remove the Conical Centrifuge tube, you must delete its child tubes.</div>
                                  <table>
                                    <tbody>
                                      <tr>
                                        <th class="name">Specimen barcode ID</th>   
                                        <th class="name textcenter">Volume</th>
                                    <g:if test="${bloodFormVersion > 1}">
                                        <th class="name">Time Placed on Dry Ice</th>   
                                        <th class="name">Time Transferred to Freezer</th>   
                                        <th class="name">Freezer Type Transferred to</th>                                      
                                    </g:if>
                                        <th class="editOnly textcenter">Action</th>
                                      </tr>
                                    <g:if test="${bloodFormVersion > 1}">
                                          <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                                            <g:if test="${specimen.parentSpecimen != null && specimen.parentSpecimen.specimenId == plasmaConicalTube}">
                                            <g:if test="${specimen.tissueType.code == 'BLOODPLAS'}">
                                              <g:if test="${specimen.containerType.code == 'CRYOV'}">
                                              <tr class="gen3-plasma gen3" id="gen3-${specimen.id}">
                                                <td class="ui-state-default ${warningMap?.get('specimenId_'+specimen.specimenId) ? 'warnings' : ''}"><span class="ui-icon ui-icon-arrowreturnthick-1-e"></span><span class="redtext hide">*</span><span class="specimenIdAl">${specimen.specimenId}</span></td>
                                                <td class="volumeAl textcenter">${specimen.chpBloodRecord?.volume} ml</td>
                                                <td>${updatediv == true || bodyclass?.find("create").equals("create") || bodyclass?.find("edit").equals("edit") || session.LDS == true ? specimen.chpBloodRecord?.bloodFrozen?.format("MM/dd/yyyy HH:mm") : "<span class=\"redactedMsg\">REDACTED (No LDS privilege)</span>"}</td>
                                                <td>${updatediv == true || bodyclass?.find("create").equals("create") || bodyclass?.find("edit").equals("edit") || session.LDS == true ? specimen.chpBloodRecord?.bloodStorage?.format("MM/dd/yyyy HH:mm") : "<span class=\"redactedMsg\">REDACTED (No LDS privilege)</span>"}</td>
                                                <td>${specimen.chpBloodRecord?.freezerType}</td>
                                                <td class="editOnly textcenter" >
                                                  <a title="Edit" href="" onClick="editPlasma('${specimen.id}','${specimen.specimenId.replace("'","\\'")}','${specimen.chpBloodRecord?.volume}','${specimen.chpBloodRecord?.bloodFrozen?.format("MM/dd/yyyy HH:mm")}','${specimen.chpBloodRecord?.bloodStorage?.format("MM/dd/yyyy HH:mm")}','${specimen.chpBloodRecord?.freezerType}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button ui-state-default ui-corner-all removepadding" title="Delete" action="deleteSpecimen" value="Delete" update="PlasmaTubes" before = "showWait('Deleting Specimen')" params="'tube=Plasma'" id="${specimen.id}"  onComplete="uiDelAliquot('${specimen.id}',3)"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                                </td>                                                
                                              </tr>
                                              </g:if> 
                                            </g:if>
                                           </g:if>
                                          </g:each>                                      
                                    </g:if>
                                    <g:else>
                                          <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                                            <g:if test="${specimen.parentSpecimen != null}">
                                            <g:if test="${specimen.tissueType.code == 'BLOODPLAS'}">
                                              <g:if test="${specimen.containerType.code == 'CRYOV'}">
                                              <tr class="gen3-plasma gen3" id="gen3-${specimen.id}">
                                                <td class="ui-state-default ${warningMap?.get('specimenId_'+specimen.specimenId) ? 'warnings' : ''}"><span class="ui-icon ui-icon-arrowreturnthick-1-e"></span><span class="redtext hide">*</span><span class="specimenIdAl">${specimen.specimenId}</span></td>
                                                <td class="volumeAl textcenter">${specimen.chpBloodRecord?.volume} ml</td>
                                                <td class="editOnly textcenter" >
                                                  <a title="Edit" href="" onClick="editPlasma('${specimen.id}','${specimen.specimenId.replace("'","\\'")}','${specimen.chpBloodRecord?.volume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button ui-state-default ui-corner-all removepadding" title="Delete" action="deleteSpecimen" value="Delete" update="PlasmaTubes" before = "showWait('Deleting Specimen')" params="'tube=Plasma'" id="${specimen.id}"  onComplete="uiDelAliquot('${specimen.id}',3)"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                                </td>                                                
                                              </tr>
                                              </g:if> 
                                            </g:if>
                                           </g:if>
                                          </g:each>                                      
                                    </g:else>  
                                    </tbody>
                                  </table>
