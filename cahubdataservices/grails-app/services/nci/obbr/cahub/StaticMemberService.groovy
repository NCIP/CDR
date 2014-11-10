package nci.obbr.cahub

import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.staticmembers.Fixative
import nci.obbr.cahub.staticmembers.ContainerType
import nci.obbr.cahub.staticmembers.AcquisitionType

class StaticMemberService {

    static scope = "request"
    static transactional = false

    def getAcquisitionTypeList(String studyCode) {
        def acquisitionTypes
        def filteredCodes = getAppSettingValue(studyCode+ "_ACTIVE_TISSUE_LIST")

        if(filteredCodes) {
            acquisitionTypes = AcquisitionType.findAllByCodeInList(filteredCodes).sort({it.name})
        } else {
            println "No acquisition type code found for study [" +studyCode+ "] in appSetting!"
        }

        return acquisitionTypes
    }

    def getFixativeList(String studyCode) {
        def fixatives
        def filteredCodes = getAppSettingValue(studyCode+ "_ACTIVE_FIXATIVE_LIST")

        if(filteredCodes) {
            fixatives = Fixative.findAllByCodeInList(filteredCodes).sort({it.name})
        } else {
            println "No fixative code found for study [" +studyCode+ "] in appSetting!"
        }

        return fixatives
    }

    def getContainerTypeList(String studyCode) {
        def containerTypes
        def filteredCodes = getAppSettingValue(studyCode+ "_ACTIVE_CONTAINER_LIST")

        if(filteredCodes) {
            containerTypes = ContainerType.findAllByCodeInList(filteredCodes).sort({it.name})
        } else {
            println "No container type code found for study [" +studyCode+ "] in appSetting!"
        }

        return containerTypes
    }

    def getAppSettingValue(String code) {
        def value = []
        def appSettingInstance = AppSetting.findByCode(code)

        if(appSettingInstance) {
            value = appSettingInstance.value?.tokenize(',')
            if(!value || "see big value".equals(value[0])) {
                value = appSettingInstance.bigValue?.tokenize(',')
            }
        }

        return value
    }
}
