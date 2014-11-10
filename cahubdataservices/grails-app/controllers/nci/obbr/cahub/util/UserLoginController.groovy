package nci.obbr.cahub.util
import java.text.SimpleDateFormat
import grails.plugins.springsecurity.Secured
    
class UserLoginController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
    
    def list = {
        //println 'CCCC 22 params=' + params 
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
        def start = params.start
        def end = params.end

        def startD = parseDateTime(start, true)
        def endD = parseDateTime(end, false)
        
        def userName = params.userName
        def organization = params.organization
        
        if (userName?.trim().equals("")) userName = null
        if (organization?.trim().equals("")) organization = null
        
        if ((userName)&&(organization))
        {
            organization = null
        }
        if (!startD)
        {
            def today = df2.format(new Date())
            startD = df.parse( today + " 00:00:00.000")
            start = null
        }
        if (!endD)
        {
            def today = df2.format(new Date())
            endD = df.parse( today + " 23:59:59.999")
            end = null
        }
        else if (((params.end_hour?.equals('0'))||(params.end_hour?.equals('00')))&&
                 ((params.end_minute?.equals('0'))||(params.end_minute?.equals('00'))))
        {
            if (df2.format(endD).equals(df2.format(new Date()))) endD = new Date()
            else endD = df.parse( df2.format(endD) + " 23:59:59.999")
        }
        
        
        def startF 
        def endF 
        def nowF 
        
        try
        {
            startF  = df.format(startD).substring(0, 16)
            endF  = df.format(endD).substring(0, 16)
            nowF  = df.format(new Date()).substring(0, 16)
        }
        catch(java.text.ParseException pe)
        {
            println("UserLoginController Date value parsing failure(2): '" + format + "' - " + pe.getMessage())
            return null
        }
        
        String errorMessage 
        if (startF.compareTo(endF) >= 0) errorMessage = 'Start date is later than End date. [' +startF +'-'+endF+']'
        else if (getDiffTime(startD, endD, 300000l)) errorMessage = 'Searching time range is less than 5 minutes. [' +startF +'-'+endF+']'
        if ((end)&&(!start))
        {
            startD = df.parse( "2000/01/01 00:00:00.000")
            startF = '*'
        }
                        
        if (params.max)
        {
            params.max = params.int('max')
            if (params.max > 100) params.max = 100
        }
        else params.max = 25
        
        if (params.offset) params.offset = params.int('offset')
        else params.offset = 0
        
        if (!params.sort)
        {
            params.sort = 'loginDate'
            params.bsort = '';
            params.offset = 0;
        }
        else if ((params.bsort)&&(!params.sort.equals(params.bsort)))
        {
            params.offset = 0;
        }
        
        if (!params.order)
        {
            params.order = 'desc'
            params.border = '';
            params.offset = 0;
        }
        else if ((params.border)&&(!params.order.equals(params.border)))
        {
            params.offset = 0;
        }
        
        def results
        def search_range
        int count = 0
        if (!errorMessage)
        {
            Date d = getNowTime(endD)
            
            if (d)
            {
                endF = 'Now'
                endD = d
            }
            if (userName)
            {
                count = UserLogin.countByUsernameAndLoginDateBetween(userName, startD, endD)
                results = UserLogin.findAllByUsernameAndLoginDateBetween(userName, startD, endD, params)
            }
            else if (organization)
            {
                count = UserLogin.countByOrganizationAndLoginDateBetween(organization, startD, endD)
                results = UserLogin.findAllByOrganizationAndLoginDateBetween(organization, startD, endD, params)
            }
            else
            {
                count = UserLogin.countByLoginDateBetween(startD, endD)
                results = UserLogin.findAllByLoginDateBetween(startD, endD, params)
                //search_range = "[" + df.format(startD) + ", " + df.format(endD) + "]"
            }
            search_range = "[" + startF + ", " + endF + "]"
        }
     
        //println 'CCCC 33 c=' + count + ', sz=' + results.size()
        params.bsort = params.sort
        params.border = params.order
        if (endF.equals('Now'))
        {
            if (startF.equals('*')) endD = new Date()
            else if (end)
            {
                if ((df2.format(endD).equals(df2.format(new Date())))&&
                    ((params.end_hour?.equals('0'))||(params.end_hour?.equals('00')))&&
                    ((params.end_minute?.equals('0'))||(params.end_minute?.equals('00'))))
                {
                    endD = new Date()
                }
                else endD = parseDateTime(end, false)
            }
            else endD = null
        }
        if (startF.equals('*')) startD = null
        if ((!results)||(results.size() == 0))
        {
            results = null
            count = 0
        }
        [userLoginInstanceList:results, userLoginInstanceTotal:count, start:startD, end:endD, search_range:search_range,userName:userName, organization:organization, errorMessage:errorMessage]

    }
    
    def create = {
        def userLoginInstance = new UserLogin()
        userLoginInstance.properties = params
        return [userLoginInstance: userLoginInstance]
    }

    def save = {
        def userLoginInstance = new UserLogin(params)
        if (userLoginInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), userLoginInstance.id])}"
            redirect(action: "show", id: userLoginInstance.id)
        }
        else {
            render(view: "create", model: [userLoginInstance: userLoginInstance])
        }
    }

    def show = {
        def userLoginInstance = UserLogin.get(params.id)
        if (!userLoginInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userLoginInstance: userLoginInstance]
        }
    }

    def edit = {
        def userLoginInstance = UserLogin.get(params.id)
        if (!userLoginInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userLoginInstance: userLoginInstance]
        }
    }

    def update = {
        def userLoginInstance = UserLogin.get(params.id)
        if (userLoginInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userLoginInstance.version > version) {
                    
                    userLoginInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userLogin.label', default: 'UserLogin')] as Object[], "Another user has updated this UserLogin while you were editing")
                    render(view: "edit", model: [userLoginInstance: userLoginInstance])
                    return
                }
            }
            userLoginInstance.properties = params
            if (!userLoginInstance.hasErrors() && userLoginInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), userLoginInstance.id])}"
                redirect(action: "show", id: userLoginInstance.id)
            }
            else {
                render(view: "edit", model: [userLoginInstance: userLoginInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def userLoginInstance = UserLogin.get(params.id)
        if (userLoginInstance) {
            try {
                userLoginInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userLogin.label', default: 'UserLogin'), params.id])}"
            redirect(action: "list")
        }
    }
    private Date getNowTime(Date date)
    {
        return getDiffTime(date, new Date(), 180000l)
    }
    private Date getDiffTime(Date date1, Date date2, long diff)
    {
        if (!date1) return null
        if (!date2) return null
        Calendar cal = Calendar.getInstance()
        cal.setTime(date1)
        long m1 = cal.getTimeInMillis()
        cal.setTime(date2)
        long m2 = cal.getTimeInMillis()
        cal.setTimeInMillis(m2 + diff)
        Date newNow = cal.getTime()
        long dist = m1 - m2
        if (dist >= 0l) return newNow
        else dist = m2 - m1
        if (dist < diff) return newNow
               
        return null
    }
    private Date parseDateTime(String date, boolean isStart)
    {
        if (!date) return null
        String dateStr = date.trim()
        if (dateStr.equals("")) return null
        if (dateStr.equalsIgnoreCase('select date')) return null
        
        int n = 0
        
        def format
        SimpleDateFormat df 
        Date value
        while(true)
        {
            if (n == 0)
            {
                format = "MM/dd/yyyy HH:mm"
            }
            else if (n == 1)
            {
                format = "MM/dd/yyyy"
            }
            else if (n == 2)
            {
                format = "EEE MMM dd HH:mm:ss z yyyy"
            }
            df = new SimpleDateFormat(format);
            try
            {
                value = df.parse(dateStr)
                break
            }
            catch(java.text.ParseException pe)
            {
                if (n == 2)
                {
                    println("UserLoginController Date value parsing failure: '" + date + "' - " + pe.getMessage())
                    return null
                }
            }
            n++
        }
        return value
        /*
        if (!value) return null
        format = "MM/dd/yyyy"
        df = new SimpleDateFormat(format);
        String d 
        
        try
        {
            d = df.format(value)
        }
        catch(java.text.ParseException pe)
        {
            println("UserLoginController Date value parsing failure(2): '" + format + "' - " + pe.getMessage())
            return null
        }
        
        format = "MM/dd/yyyy HH:mm:ss.SSS"
        df = new SimpleDateFormat(format);
        String dS = d + " 00:00:00.000"
        if (!isStart) dS = d + " 23:59:59.999"
        try
        {
            value = df.parse(dS)
        }
        catch(java.text.ParseException pe)
        {
            println("UserLoginController Date value parsing failure(3): '" + dS + "' - " + pe.getMessage())
        }
        
        return value
        */
    }
    
}
