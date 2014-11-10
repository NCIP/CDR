package nci.obbr.cahub.util

import grails.plugins.springsecurity.Secured

class AppSettingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = AppSetting
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [appSettingInstanceList: AppSetting.list(params), appSettingInstanceTotal: AppSetting.count()]
    }

    def create = {
        def appSettingInstance = new AppSetting()
        appSettingInstance.properties = params
        return [appSettingInstance: appSettingInstance]
    }

    def save = {
        def appSettingInstance = new AppSetting(params)
        if (appSettingInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), appSettingInstance.id])}"
            redirect(action: "show", id: appSettingInstance.id)
        }
        else {
            render(view: "create", model: [appSettingInstance: appSettingInstance])
        }
    }

    def show = {
        def appSettingInstance = AppSetting.get(params.id)
        if (!appSettingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), params.id])}"
            redirect(action: "list")
        }
        else {
            [appSettingInstance: appSettingInstance]
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def appSettingInstance = AppSetting.get(params.id)
        if (!appSettingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [appSettingInstance: appSettingInstance]
        }
    }

    def update = {
        def appSettingInstance = AppSetting.get(params.id)
        if (appSettingInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (appSettingInstance.version > version) {
                    
                    appSettingInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'appSetting.label', default: 'AppSetting')] as Object[], "Another user has updated this AppSetting while you were editing")
                    render(view: "edit", model: [appSettingInstance: appSettingInstance])
                    return
                }
            }
            appSettingInstance.properties = params
            if (!appSettingInstance.hasErrors() && appSettingInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), appSettingInstance.id])}"
                redirect(action: "show", id: appSettingInstance.id)
            }
            else {
                render(view: "edit", model: [appSettingInstance: appSettingInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def appSettingInstance = AppSetting.get(params.id)
        if (appSettingInstance) {
            try {
                appSettingInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appSetting.label', default: 'AppSetting'), params.id])}"
            redirect(action: "list")
        }
    }
}
