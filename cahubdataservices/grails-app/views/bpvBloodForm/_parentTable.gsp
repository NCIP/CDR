<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<g:if test="${updatediv == true}">
<g:hasErrors bean="${bpvBloodFormInstance}">
  <div class="errors"><g:renderErrors bean="${bpvBloodFormInstance}" as="list" /></div>
 </g:hasErrors>
</g:if>
<table>
  <thead>
    <tr>
      <th></th><th class="name">Collection Tube Specimen Barcode ID</th>  
      <th valign="top" class="name">Specimen Tube Type</th>  
      <th valign="top" class="name">Processed for</th>  
      <th valign="top" class="name textcenter">Volume Collected</th>
      <th valign="top" class="editOnly textcenter">Action</th>                                        
    </tr>
  </thead>
  <tbody>
                    <g:each in="${bpvBloodFormInstance.caseRecord.specimens}" status="i" var="specimen">
                      <g:if test="${!specimen.parentSpecimen}">
                          <%  def gen1Type = ""%>
                          <g:if test="${specimen.tissueType.code == 'BLOODPLAS' || specimen.tissueType.code == 'BLOODSRM' || specimen.tissueType.code == 'BLOODRNA' || specimen.tissueType.code == 'BLOODDNA'}">
                            <g:if test="${specimen.containerType.code == 'SST' || specimen.containerType.code == 'LAVEDTA' || specimen.containerType.code == 'RNAPAX' || specimen.containerType.code == 'DNAPAX'}">
                              <g:if test="${specimen.tissueType.code == 'BLOODSRM'}"><%  gen1Type = "Serum" %></g:if>
                              <g:elseif test="${specimen.tissueType.code == 'BLOODPLAS'}"><%  gen1Type = "Plasma" %></g:elseif>                                            
                              <g:elseif test="${specimen.tissueType.code == 'BLOODRNA'}"><%  gen1Type = "RNA" %></g:elseif>
                              <g:elseif test="${specimen.tissueType.code == 'BLOODDNA'}"><%  gen1Type = "DNA" %></g:elseif>
                              <tr id="gen1row${i}" class="gen1 gen1-<%=  gen1Type.toLowerCase() %> ${specimen.bloodTimeDraw}-draw">
                                    <td class="drawTimeKey ${specimen.bloodTimeDraw}-draw">${specimen.bloodTimeDraw}</td>
                                    <td class="specimenIdName ${warningMap?.get('specimenId_'+specimen.specimenId) ? 'warnings' : ''}">${specimen.specimenId}</td>
                                    <td class="specimenName">${specimen.containerType?.name}</td>
                                    <td class="tissueType"><%=  gen1Type %></td>
                                    <g:if test="${specimen.tissueType?.code == 'BLOODPLAS'}">
                                      <td class="volumeP textcenter ${warningMap?.get('volume_' + specimen.tissueType?.code) && specimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode) ? 'warnings' : ''}">${specimen.chpBloodRecord?.volume} ml</td>
                                    </g:if>
                                    <g:else>
                                      <td class="volumeP textcenter ${warningMap?.get('volume_' + specimen.tissueType?.code) ? 'warnings' : ''}">${specimen.chpBloodRecord?.volume} ml</td>
                                    </g:else>
                                    <td class="editOnly textcenter">
                                      <a title="Edit" href="" onClick="editParent('${specimen.id}','${specimen.specimenId.replace("'","\\'")}','${specimen.containerType?.code}','${specimen.tissueType?.code}','${specimen.chpBloodRecord?.volume}','${specimen.bloodTimeDraw}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                      <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" title="Delete" action="deleteSpecimen" value="Delete" update='[success:"ParentTubes",failure:"imgDel_${specimen.id}"]' params="'tube=Parent'" before="if(!validateSpecDelete('gen1row${i}','${gen1Type}')) return false" onSuccess="deleteParentSpecimens('gen1row${i}',${bpvBloodFormInstance.caseRecord.id}, '${gen1Type}')" id="${specimen.id}" ><span id="imgDel_${specimen.id}" class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                    </td>
                              </tr>
                           <%  def gen2Type = ""%>
                           <g:set var="slist" value="${SpecimenRecord.findAllByParentSpecimen(specimen)}" />
                              <g:each in="${slist.sort{it.specimenId}}" status="j" var="u">
                                <g:if test="${u.tissueType.code == 'BLOODSRM'}"><%  gen2Type = "Serum" %></g:if>
                                <g:elseif test="${u.tissueType.code == 'BLOODCP'}"><%  gen2Type = "Whole Cell Pellet" %></g:elseif> 
                                <g:elseif test="${u.tissueType.code == 'BLOODPLAS'}"><%  gen2Type = "Plasma" %></g:elseif>                                            
                                <g:elseif test="${u.tissueType.code == 'BLOODRNA'}"><%  gen2Type = "RNA" %></g:elseif>
                                <g:elseif test="${u.tissueType.code == 'BLOODDNA'}"><%  gen2Type = "DNA" %></g:elseif>
                                 <g:if test="${!u.parentSpecimen.parentSpecimen}">
                                    <tr class="gen2 gen1row${i} gen2-<%=  gen2Type?.replace("Whole Cell Pellet","wcp")?.toLowerCase() %>-parent" id="gen2-${u.id}-parent">
                                        <td></td><td class="ui-state-default  ${warningMap?.get('specimenId_'+specimen.specimenId) ? 'warnings' : ''}"><span class="ui-icon ui-icon-arrowreturnthick-1-e"></span>${u.specimenId}</td>
                                        <td class="specimenName">${u.containerType?.name}</td>
                                        <td class="tissueType"><%=  gen2Type %></td>
                                        <td class="volumeP textcenter">${u.chpBloodRecord?.volume} ml</td>
                                        <td class="editOnly textcenter">
                                            <g:if test="${u.tissueType.code == 'BLOODSRM'}">
                                                  <a title="Edit" href="" onClick="editSerum('${u.id}','${u.specimenId.replace("'","\\'")}','${u.chpBloodRecord?.volume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" title="Delete"  action="deleteSpecimen" value="Delete" update="SerumTubes"  params="'tube=Serum'" id="${u.id}" before="showWait('Deleting Specimen')" onComplete="uiDelAliquot('${u.id}',2)"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                            </g:if>
                                          <g:elseif test="${u.tissueType.code == 'BLOODCP'}">
                                                  <a title="Edit" href="" onClick="editWcp('${u.id}','${u.specimenId.replace("'","\\'")}','${u.chpBloodRecord?.volume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button ui-state-default ui-corner-all removepadding" title="Delete" action="deleteSpecimen" value="Delete" update="WcpTubes"  params="'tube=Wcp'" id="${u.id}" before = "showWait('Deleting Whole Cell Pellet tube')" onComplete="uiDelAliquot('${u.id}',2)"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                          </g:elseif>
                                          <g:elseif test="${u.tissueType.code == 'BLOODPLAS'}">
                                                 <a title="Edit" href="" onClick="editPlasma('${u.id}','${u.specimenId.replace("'","\\'")}','${u.chpBloodRecord?.volume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" title="Delete"  action="deleteSpecimen" value="Delete" update="PlasmaTubes"  params="'tube=Plasma'" id="${u.id}" before = "showWait('Deleting conical centrifuge tube')" onComplete="plasmaCTBarcodeDel()"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                          </g:elseif>
                                        </td>                                      
                                    </tr>  
                          <%  def gen3Type = ""%>
                           <g:set var="ulist" value="${SpecimenRecord.findAllByParentSpecimen(u)}" />
                              <g:each in="${ulist.sort{it.specimenId}}" status="k" var="v">
                                <g:if test="${v.tissueType.code == 'BLOODSRM'}"><%  gen3Type = "Serum" %></g:if>
                                <g:elseif test="${v.tissueType.code == 'BLOODPLAS'}"><%  gen3Type = "Plasma" %></g:elseif>                                            
                                <g:elseif test="${v.tissueType.code == 'BLOODRNA'}"><%  gen3Type = "RNA" %></g:elseif>
                                <g:elseif test="${v.tissueType.code == 'BLOODDNA'}"><%  gen3Type = "DNA" %></g:elseif>
                                <tr class="gen3 gen1row${i} gen2-${u.id}-parent gen3-<%=  gen3Type?.toLowerCase() %>-parent" id="gen3-${v.id}-parent">
                                    <td></td><td class="ui-state-default ${warningMap?.get('specimenId_'+specimen.specimenId) ? 'warnings' : ''}"><span class="ui-icon ui-icon-arrowreturnthick-1-e"></span>${v.specimenId}</td>
                                    <td class="specimenName">${v.containerType?.name}</td>
                                    <td class="tissueType"><%=  gen3Type %></td>
                                    <td class="volumeP textcenter">${v.chpBloodRecord?.volume} ml</td>
                                    <td class="editOnly textcenter">
                                            <g:if test="${v.tissueType.code == 'BLOODSRM'}">
                                                  <a title="Edit" href="" onClick="editSerum('${v.id}','${v.specimenId.replace("'","\\'")}','${v.chpBloodRecord?.volume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" title="Delete"  action="deleteSpecimen" value="Delete" update="SerumTubes"  params="'tube=Serum'" id="${v.id}"  before="showWait('Deleting Specimen')" onComplete="uiDelAliquot('${v.id}',3)"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                            </g:if>
                                          <g:elseif test="${v.tissueType.code == 'BLOODPLAS'}">
                                                 <a title="Edit" href="" onClick="editPlasma('${v.id}','${v.specimenId.replace("'","\\'")}','${v.chpBloodRecord?.volume}');return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding"><span class="ui-icon ui-icon-pencil">Edit</span></a>
                                                  <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" title="Delete"  action="deleteSpecimen" value="Delete" update="PlasmaTubes"  params="'tube=Plasma'" id="${v.id}"  before="showWait('Deleting Specimen')" onComplete="uiDelAliquot('${v.id}',3)"><span class="ui-icon ui-icon-trash">Delete</span></g:remoteLink>
                                          </g:elseif>
                                    </td>
                                </tr>
                              </g:each>
                            </g:if>                        
                          </g:each>                  
                            </g:if> 
                          </g:if>
                      </g:if>                        
                    </g:each>                  
 </tbody>
</table>
