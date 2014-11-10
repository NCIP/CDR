package nci.obbr.cahub

import nci.obbr.cahub.datarecords.*

class GtexTrfService {

    static transactional = true
    
    
    def getBrainFlag(caseRecord) {
        //def caseRecord = CaseRecord.findByCaseId(caseid)
        def specimenList = caseRecord.specimens
        specimenList.any() {
            if (it.gtexSequenceNum() in ['0011','0014']){
                return true
            }
        }
    }
    
    def getFrozenCaseNum(caseRecord) 
    {
        def specimenList = caseRecord?.specimens
            
        if (specimenList)
        {
            def filteredList = []
            specimenList.each{
                if (it?.fixative?.code == 'DICE')
                {
                    if (!isFrozenSpecimenFound(filteredList, it))
                    {
                        filteredList.add(it)
                        //println "getFrozenCaseNum() CaseSize=" + filteredList.size + ", name=" + it.tissueType.toString()
                    }
                }
            }
            //println "getFrozenCaseNum Size=" + filteredList.size()
            return filteredList.size
        }
        else
        {
            println "getFrozenCaseNum caseRecord.specimens is NULL"  
            return 0
        }
    }
    
    def isFrozenSpecimenFound(filteredList, specimen) 
    {
        def found = false
        filteredList.each{
            if (it.tissueType.toString().equals(specimen.tissueType.toString()))
            {
                found = true
            }
        }
        return found
    }
    
//        def getFrozenCaseNum(caseRecord) {
//            def specimenList = caseRecord.specimens
//            
//            //println specimenList.size()
//            if (specimenList)
//            {
//                java.util.List<String> filteredList = new java.util.ArrayList<String>()
//            
//                for (SpecimenRecord specimen:specimenList) 
//                {  
//                    if (specimen.fixative?.code == 'DICE')
//                    {
//                        boolean found = false
//                        for(String str:filteredList)
//                        {
//                            if (str.equals(specimen.tissueType.toString()))
//                            {
//                                found = true
//                                break
//                            }
//                        }
//                        if (!found)
//                        {
//                            filteredList.add(specimen.tissueType.toString())
//                            //println "getFrozenCaseNum() CaseSize=" + filteredList.size() + ", name=" + specimen.tissueType.toString()
//                        }
//                    }
//                }
//                //println "getFrozenCaseNum Size=" + filteredList.size()
//                return filteredList.size()  
//            }
//            else
//            {
//                println "getFrozenCaseNum caseRecord.specimens is NULL"  
//                return 0  
//            }
//            
//        }
    }
