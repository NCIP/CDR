<g:if test="${updatediv == true}">
<g:hasErrors bean="${bpvBloodFormInstance}">
  <div class="errors"><g:renderErrors bean="${bpvBloodFormInstance}" as="list" /></div>
 </g:hasErrors>
</g:if>

                                <table>
                                    <tbody>
                                      <tr>
                                        <th class="name">Whole Cell Pellet aliquot Specimen barcode ID</th>   
                                        <th class="name">Whole Cell Pellet aliquot volume</th>
                                        <th class="editOnly textcenter">Action</th>
                                      </tr>
                                          <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                                            <g:if test="${specimen.parentSpecimen != null && specimen.parentSpecimen.specimenId == bpvBloodFormInstance?.plasmaParBarcode}"> 
                                            <g:if test="${specimen.tissueType.code == 'BLOODCP'}">
                                              <g:if test="${specimen.containerType.code == 'CRYOV'}">
                                              <tr class="gen2-wcp gen2" id="gen2-${specimen.id}">
                                                <td class="ui-state-default ${warningMap?.get('specimenId_'+specimen.specimenId) ? 'warnings' : ''}"><span class="ui-icon ui-icon-arrowreturnthick-1-e"></span><span class="redtext hide">*</span><span class="specimenIdAl">${specimen.specimenId}</span></td>
                                                <td class="volumeAl textcenter">${specimen.chpBloodRecord?.volume} ml</td>
                                                <td class="editOnly textcenter" >
                                                  <a title="Edit" href="" onClick="editWcp('${specimen.id}','${specimen.specimenId.replace("'","\\'")}','${specimen.chpBloodRecord?.volume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button ui-state-default ui-corner-all removepadding" title="Delete" action="deleteSpecimen" value="Delete" update="WcpTubes"  params="'tube=Wcp'" id="${specimen.id}"  onComplete="uiDelAliquot('${specimen.id}',2)"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                                </td>                                                
                                              </tr>
                                              </g:if> 
                                            </g:if>
                                           </g:if>
                                          </g:each>
                                    </tbody>
                                  </table>
