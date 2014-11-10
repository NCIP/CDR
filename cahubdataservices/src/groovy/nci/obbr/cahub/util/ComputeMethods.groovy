
package nci.obbr.cahub.util
import groovy.time.TimeCategory
import static java.util.Calendar.*
/**
 *
 * @author hariharanp
 * 04/01/13
 */
class ComputeMethods {
    
    static compareCDRVersion(String v1, String v2) throws Exception{
        if(!v1 || ! v2)
           throw new Exception("Wrong CDR version format")
        int result=100
        def a1 = v1.split("\\.")
        def a2 = v2.split("\\.")
       
        
        def len1 = a1.size()
        def len2 = a2.size()
        
        def len
        if(len1 <= len2)
          len = len1
        else
          len = len2
       
        
       try{
        for(int i = 0; i < len; i++){
           int i1 = Integer.parseInt(a1[i])
           int i2 = Integer.parseInt(a2[i])
           if(i1 > i2){
             result = 1
           }else if (i1 == i2){
             result = 0
           }else{
              result = -1
           }  
           if(result == 1 || result == -1)
           break
        }
       }catch(Exception e){
            throw new Exception("Wrong CDR version format")
       } 
       
       if(result == 0){
           if(len1 < len2)
             result = -1
           else if(len1 > len2)
             result = 1
           else
             result = 0
       }
       
       return result
        
    }
    static getSixMonthPrior(){
        def sixMonthPriorDate
        use(TimeCategory){
             sixMonthPriorDate = new Date() - 6.month
            //println "six month prior to today is "+sixMonthPriorDate.toString()
           
        }
        return sixMonthPriorDate
    }
	
}

