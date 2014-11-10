
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<%@ page import="nci.obbr.cahub.staticmembers.Study" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  
  <title>GTEX Dashboard - caHUB CDR</title>
</head>
<body>
  
  <g:set var="cases" value="${CaseRecord.findAllByStudy(Study.findByCode('GTEX'))}" />
  
  <div class="body">
    <h1>POC Dashboard</h1>
    <div class="list">

      <table>
        <thead>
          <tr>
            <th>Total Case Count</th>
            <th class="specimencount">Total Specimen Count</th>
        </tr>
        </thead>
        <tbody>
          <tr>
            <td>${cases.size()}</td>
            <td></td>
          </tr>
        </tbody>
      </table>
         
    </div>
  </div>
</body>
