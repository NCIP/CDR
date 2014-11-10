package nci.obbr.cahub

import nci.obbr.cahub.surveyrecords.*
import nci.obbr.cahub.surveycomponents.*
import nci.obbr.cahub.staticmembers.*

class BpvElsiService {
    
        static transactional = true

    def getSurvey (surveyRecordInstance, session) {
        
            SurveyAnswer surveyAnswer
            SurveyQuestion surveyQuestion
            def survey = [:]
            def questions = []
            def question
            def i = 1
            def questionNo
            def sections = []
            def section
            def options = []
            
            surveyRecordInstance.surveyTemplate.sections.each {
                section = [:]
                section.put("id",it.id)
                section.put("typ","section")
                section.put("title",it.sectionHeader)
                section.put("desc",it.sectionDescription)
                section.put("vers",it.version)
                section.put("sect_id",it.id)
                section.put("sect_name","")
                section.put("sect_numbering","numeric")
                section.put("options",[])
                section.put("options_questions",[])
                section.put("options_ques_numbering","")
                questions = []
                
                def subQuestionList = []
                
                it.questions.each {
                    if (it.parentQuestion!=null) {
                        subQuestionList.add(it)
                    }
                }
                
                    it.questions.each {
                        question = [:]
                        def subQuestions = []
                        if (it.parentQuestion==null) {
                        def questionId = it.id
                        question.put("id",it.id)
                        def qType = (it.answerType == null ? "" : it.answerType)
                        
                        //all of type radio in json
                        qType = qType.replace("radioIncomeGrp","radio")
                        qType = qType.replace("radioEducation","radio")
                        qType = qType.replace("radioCommunity","radio")
                        qType = qType.replace("radioYesNo","radio")
                        qType = qType.replace("radioGender","radio")
                        qType = qType.replace("radioCADate","radio")
                        
                        //all of type checkbox in json
                        qType = qType.replace("checkboxEthnicity","checkbox")
                        qType = qType.replace("checkboxEmployment","checkbox")
                        
                        //qType = qType.replace("scale1","scale")
                        qType = qType.replace("scale2","scale")
                        qType = qType.replace("scale3","scale")
                        qType = qType.replace("scale4","scale")
                        qType = qType.replace("scale5","scale")
                        qType = qType.replace("scale6","scale")
                        qType = qType.replace("scale7","scale")
                        qType = qType.replace("scale8","scale")
                        qType = qType.replace("scale9","scale")
                        qType = qType.replace("scale10","scale")
                        qType = qType.replace("scale11","scale")
                        qType = qType.replace("scale12","scale")
                        

                        question.put("typ",qType)
                        question.put("title",it.questionText)
                        def answerType = it.answerType
                        question.put("desc","")
                        question.put("tip","")
                        question.put("sect_name","")
                        question.put("sect_numbering","")
                        subQuestionList.each {
                            if (it.parentQuestion.id==questionId){
                                def subQuestion1 = [:]
                                subQuestion1.put("id",it.id)
                                def subQuestionId = it.id
                                def questType = (it.answerType == null ? "" : it.answerType)
                                //questType = questType.replace("scale1","scale")
                                questType = questType.replace("scale2","scale")
                                questType = questType.replace("scale3","scale")
                                questType = questType.replace("scale4","scale")
                                questType = questType.replace("scale5","scale")
                                questType = questType.replace("scale6","scale")
                                questType = questType.replace("scale7","scale")
                                questType = questType.replace("scale8","scale")
                                questType = questType.replace("scale9","scale")
                                questType = questType.replace("scale10","scale")
                                questType = questType.replace("scale11","scale")
                                questType = questType.replace("scale12","scale")
                                
                                questType = questType.replace("radioIncomeGrp","radio")
                                questType = questType.replace("radioEducation","radio")
                                questType = questType.replace("radioCommunity","radio")
                                questType = questType.replace("radioYesNo","radio")
                                questType = questType.replace("radioGender","radio")
                                questType = questType.replace("radioCADate","radio")

                                //all of type checkbox in json
                                questType = questType.replace("checkboxEthnicity","checkbox")
                                questType = questType.replace("checkboxEmployment","checkbox")                                
                                
                                subQuestion1.put("typ",questType)
                                def subQuestionAnswerType = it.answerType
                                subQuestion1.put("title",it.questionText)
                                subQuestion1.put("desc","")
                                subQuestion1.put("tip","")
                                subQuestion1.put("sect_name","")
                                subQuestion1.put("sect_numbering","")
                                def subQoption
                                def subQoptions = []
                                if (subQuestionAnswerType=="scale1" || subQuestionAnswerType=="scale2" || subQuestionAnswerType=="scale3" || subQuestionAnswerType=="scale4" || 
                                    subQuestionAnswerType=="scale5" || subQuestionAnswerType=="scale6" || subQuestionAnswerType=="scale7" || subQuestionAnswerType=="scale8" || 
                                    subQuestionAnswerType=="scale9" || subQuestionAnswerType=="scale10" || subQuestionAnswerType=="scale11" || subQuestionAnswerType=="scale12" ||
                                    subQuestionAnswerType=="radioIncomeGrp" || subQuestionAnswerType=="radioEducation" || subQuestionAnswerType=="radioCommunity" || subQuestionAnswerType=="radioYesNo" ||
                                    subQuestionAnswerType=="radioGender" || subQuestionAnswerType=="radioCADate" || subQuestionAnswerType=="checkboxEthnicity" || subQuestionAnswerType=="checkboxEmployment" ) {

                                    nci.obbr.cahub.surveycomponents.SurveyQuestionOption.list().each {
                                        if (it.optionType.equalsIgnoreCase(subQuestionAnswerType)){
                                            subQoption = [:]
                                            subQoption.put("title",it.optionText)
                                            if (it.optionLabel!=null){
                                                subQoption.put("desc",it.optionLabel)
                                            } else {
                                                subQoption.put("desc","")    
                                            }
                                            subQoptions.add(subQoption)
                                        }
                                    }
                                    subQuestion1.put("options",subQoptions)
                                } else {
                                    subQuestion1.put("options",[])
                                }
                                subQuestion1.put("options_questions",[])
                                subQuestion1.put("options_ques_numbering","")
                                subQuestion1.put("questions","")
                                subQuestion1.put("questions_name","")
                                subQuestion1.put("questions_numbering","")
                                subQuestion1.put("answer",[])
                                surveyRecordInstance.answers.each() {
                                    def answer = [:]
                                    if (it.question.id==subQuestionId) {
                                        if (it.question.answerType=="radioYesNo" || (it.question.parentQuestion!=null && it.question.parentQuestion.answerType=="tabled radio")) {
                                                if (it.response=="-1" || it.response.equalsIgnoreCase("IDK")) {
                                                    answer.put("response",it.response)
                                                } else {
                                                    answer.put("response",(nci.obbr.cahub.surveycomponents.SurveyQuestionOption.findByOptionLabel(it.response)).optionText)
                                                }
                                        } else {
                                            answer.put("response",it.response)
                                        }
                                        subQuestion1.put("answer",answer)
                                    }
                                }
                                if (it.uiClass!=null) {
                                    subQuestion1.put("ui_class",it.uiClass)
                                } else {
                                    subQuestion1.put("ui_class","")                         
                                }
                                subQuestions.add(subQuestion1)
                            }
                        }
                        question.put("answer",[])
                        surveyRecordInstance.answers.each() {
                            def answer = [:]
                            if (it.question.id==questionId) {
                                if (it.question.answerType=="radioYesNo") {
                                    if (it.response=="-1" || it.response.equalsIgnoreCase("IDK")) {
                                        answer.put("response",it.response)
                                    } else {
                                        answer.put("response",(nci.obbr.cahub.surveycomponents.SurveyQuestionOption.findByOptionLabel(it.response)).optionText)
                                    }

                                } else {
                                    answer.put("response",it.response)
                                }
                                question.put("answer",answer)
                            }
                        }

                        def option
                        options = []

                        if (it.answerType=="tabled radio"){
                            nci.obbr.cahub.surveycomponents.SurveyQuestionOption.list().each {
                                if (it.optionType.equalsIgnoreCase("radioYesNo")) {
                                option = [:]
                                option.put("title",it.optionText)
                                option.put("desc","")
                                options.add(option)
                                }
                            }

                            question.put("options",options)
                            question.put("options_questions",subQuestions)    
                            question.put("options_ques_numbering","")
                            question.put("questions",[])
                            question.put("questions_numbering","")
                        } else if (answerType=="scale1" || answerType=="scale2" || answerType=="scale3" || answerType=="scale4" || 
                                    answerType=="scale5" || answerType=="scale6" || answerType=="scale7" || answerType=="scale8" || 
                                    answerType=="scale9" || answerType=="scale10" || answerType=="scale11" || answerType=="scale12" ||
                                    answerType=="radioIncomeGrp" || answerType=="radioEducation" || answerType=="radioCommunity" || answerType=="radioYesNo" ||
                                    answerType=="radioGender" || answerType=="radioCADate" || answerType=="checkboxEthnicity" || answerType=="checkboxEmployment" ) {                            
                            nci.obbr.cahub.surveycomponents.SurveyQuestionOption.list().each {
                                    if (it.optionType.equalsIgnoreCase(answerType)){
                                        option = [:]
                                        option.put("title",it.optionText)
                                        if (it.optionLabel!=null){
                                            option.put("desc",it.optionLabel)
                                        } else {
                                            option.put("desc","")    
                                        }
                                        options.add(option)
                                    }
                            }

                            question.put("options",options)
                            question.put("options_questions",[])    
                            question.put("options_ques_numbering","")
                            question.put("questions",subQuestions)
                            question.put("questions_numbering","")
                        } else if (it.answerType=="tabled scale"){
                            nci.obbr.cahub.surveycomponents.SurveyQuestionOption.list().each {
                                if (it.optionType.equalsIgnoreCase("scale4")) {
                                option = [:]
                                option.put("title",it.optionText)
                                if (it.optionLabel!=null){
                                    option.put("desc",it.optionLabel)
                                } else {
                                    option.put("desc","")    
                                }
                                options.add(option)                                    
                                }
                            }

                            question.put("options",options)
                            question.put("options_questions",subQuestions)
                            question.put("options_ques_numbering","")
                            question.put("questions",[])
                            question.put("questions_numbering","")                  
                            
                        } else {
                            question.put("options",[])
                            question.put("options_questions",[]) 
                            question.put("options_ques_numbering","")
                            question.put("questions",subQuestions)
                            question.put("questions_numbering","")                            
                        }
                        question.put("questions_name","")
                        if (it.uiClass!=null) {
                            question.put("ui_class",it.uiClass)
                        } else {
                            question.put("ui_class","")                            
                        }
                        questions.add(question)
                    }
                }
                section.put("questions",questions)
                section.put("questions_name","")
                section.put("questions_numbering","")
                sections.add(section)
            }
            survey.put("id",surveyRecordInstance.id)
            survey.put("typ",surveyRecordInstance.surveyTemplate.surveyCode)            
            survey.put("title",surveyRecordInstance.surveyTemplate.surveyName)
            survey.put("desc",surveyRecordInstance.surveyTemplate.surveyDesc)
            survey.put("sections",sections)
            survey.put("sect_name","Part")
            survey.put("sect_numbering","numeric")
            survey.put("options",[])
            survey.put("options_questions",[])
            survey.put("options_ques_numbering","")
            survey.put("questions",[])
            survey.put("questions_numbering","")
            survey.put("interview_record_id",surveyRecordInstance.interviewRecord.id)
            survey.put("interview_id",surveyRecordInstance.interviewRecord.interviewId)
            survey.put("organization",BSS.findByCode(surveyRecordInstance.interviewRecord.orgCode)?.name)
            survey.put("interview_status",surveyRecordInstance.interviewRecord.interviewStatus?.value)
            def surveyComplete = false
            survey.put("survey_complete",surveyComplete)
            def dateSubmitted = surveyRecordInstance.dateSubmitted
            survey.put("date_submitted", dateSubmitted == null?"":dateSubmitted)
            def resEditFlg = false
            if ((((session.authorities?.contains("ROLE_BPV_ELSI_DA") && surveyRecordInstance.interviewRecord.orgCode.equals(session.org?.code)) || 
                surveyRecordInstance.interviewRecord.orgCode.equals(session.org?.code)) && surveyRecordInstance.interviewRecord.interviewStatus?.key in ['DATA', 'REMED']) 
                || (session.org?.code == 'OBBR' && session.DM == true && surveyRecordInstance.interviewRecord.interviewStatus?.key in ['REMED', 'QA'])) {
                resEditFlg = true
            }
            survey.put("resume_editing", resEditFlg)        
            return survey
        
    }
    
}
