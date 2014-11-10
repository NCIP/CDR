package nci.obbr.cahubdataservices

import grails.converters.JSON


class UserController extends grails.plugins.springsecurity.ui.UserController {

    //overriding plugin classes in GRAILS_HOME/projects/cahubdataservices/plugins/spring-security-ui-0.2/grails-app/controllers/grails/plugins/springsecurity/ui

    def save = {
        def user = lookupUserClass().newInstance(params)
//        if (params.password) {
//            String salt = saltSource instanceof NullSaltSource ? null : params.username
//            user.password = springSecurityUiService.encodePassword(params.password, salt)
//        }

        if(!user.save(flush: true)) {
            render view: 'create', model: [user: user, authorityList: sortedRoles()]
            return
        }

        addRoles(user)
        flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])}"
        redirect action: edit, id: user.id
    }

    def userSearch = {
        boolean useOffset = params.containsKey('offset')
        setIfMissing 'max', 10, 100
        setIfMissing 'offset', 0

        def hql = new StringBuilder('FROM ' + lookupUserClassName() + ' u WHERE 1=1 ')
        def queryParams = [:]

        for(name in ['username', 'email', 'fname', 'lname']) {
            if(params[name]) {
                hql.append " AND LOWER(u.$name) LIKE :$name"
                queryParams[name] = params[name].toLowerCase() + '%'
            }
        }

        for(name in ['enabled', 'accountExpired', 'accountLocked', 'passwordExpired']) {
            int value = params.int(name)
            if(value) {
                hql.append " AND u.$name=:$name"
                queryParams[name] = value == 1
            }
        }

        int totalCount = lookupUserClass().executeQuery("SELECT COUNT(DISTINCT u) $hql", queryParams)[0]

        int max = params.int('max')
        int offset = params.int('offset')

        String orderBy = ''
        if(params.sort) {
            orderBy = " ORDER BY u.$params.sort ${params.order ?: 'ASC'}"
        }

        def results = lookupUserClass().executeQuery(
            "SELECT DISTINCT u $hql $orderBy",
                queryParams, [max: max, offset: offset])
        def model = [results: results, totalCount: totalCount, searched: true]

        // add query params to model for paging
        for(name in ['username', 'email', 'fname', 'lname', 'enabled', 'accountExpired', 'accountLocked',
            'passwordExpired', 'sort', 'order']) {
            model[name] = params[name]
        }

        render view: 'search', model: model
    }

    /**
      * Ajax call used by autocomplete textfield.
    */
    def ajaxUsernameSearch = {
        def jsonData = []

            if (params.term?.length() > 2) {
                    String username = params.term

                    setIfMissing 'max', 10, 100

                    def results = lookupUserClass().executeQuery(
                                    "SELECT DISTINCT u.username " +
                                    "FROM ${lookupUserClassName()} u " +
                                    "WHERE LOWER(u.username) LIKE :name " +
                                    "ORDER BY u.username",
                                    [name: "${username.toLowerCase()}%"],
                                    [max: params.max])

                    for (result in results) {
                            jsonData << [value: result]
                    }
            }

            render text: jsonData as JSON, contentType: 'text/plain'
    }

    def ajaxEmailSearch = {
        def jsonData = []

        if(params.term?.length() > 2) {
            String email = params.term
            setIfMissing 'max', 10, 100

            def results = lookupUserClass().executeQuery(
                            "SELECT DISTINCT u.email " +
                            "FROM ${lookupUserClassName()} u " +
                            "WHERE LOWER(u.email) LIKE :name " +
                            "ORDER BY u.email",
                            [name: "${email}%"],
                            [max: params.max])

            for(result in results) {
                jsonData << [value: result]
            }
        }

        render text: jsonData as JSON, contentType: 'text/plain'
    }

    def ajaxFnameSearch = {
        def jsonData = []

        if(params.term?.length() > 2) {
            String fname = params.term
            setIfMissing 'max', 10, 100

            def results = lookupUserClass().executeQuery(
                            "SELECT DISTINCT u.fname " +
                            "FROM ${lookupUserClassName()} u " +
                            "WHERE LOWER(u.fname) LIKE :name " +
                            "ORDER BY u.fname",
                            [name: "${fname}%"],
                            [max: params.max])

            for(result in results) {
                jsonData << [value: result]
            }
        }

        render text: jsonData as JSON, contentType: 'text/plain'
    }

    def ajaxLnameSearch = {
        def jsonData = []

        if(params.term?.length() > 2) {
            String lname = params.term
            setIfMissing 'max', 10, 100

            def results = lookupUserClass().executeQuery(
                            "SELECT DISTINCT u.lname " +
                            "FROM ${lookupUserClassName()} u " +
                            "WHERE LOWER(u.lname) LIKE :name " +
                            "ORDER BY u.lname",
                            [name: "${lname}%"],
                            [max: params.max])

            for(result in results) {
                    jsonData << [value: result]
            }
        }

        render text: jsonData as JSON, contentType: 'text/plain'
    }
}
