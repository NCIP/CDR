<%@ page import="nci.obbr.cahub.util.AppSetting" %>

<script>
    function emailSentSuccess() {
        setEmailSentMessage('Case Record email sent successfully.');
    }

    function emailSentFailure() {
        setEmailSentMessage('Failure sending Case Record email.');
    }

    function setEmailSentMessage(msg) {
        if($('.message').html() != undefined) {
            $('.message').html(msg);
            changeToInfoBox();
        } else if($('.infobox').html() != undefined) {
            $('.infobox').html(msg);
        }
    }
</script>

<span class="${emailSent?'yes':'no'}">${emailSent?'Sent':'Not Sent'}</span>
<g:if test="${caseStatus in AppSetting.findByCode('EMAIL_CASE_STATUS_LIST')?.value?.split(',') &&
    ('ROLE_ADMIN' in session?.authorities || 'ROLE_NCI-FREDERICK_CAHUB_DM' in session?.authorities || 'ROLE_NCI-FREDERICK_CAHUB_SUPER' in session?.authorities)}">
    <g:remoteLink action="emailCase" update="emailCaseBtn" id="${id}" onSuccess="emailSentSuccess()" onFailure="emailSentFailure()" title="${emailSent?'Resend':'Send'}" class="button ui-button ui-state-default ui-corner-all removepadding">
        <span class="ui-icon ${emailSent?'ui-icon-mail-open':'ui-icon-mail-closed'}"></span>
    </g:remoteLink>
</g:if>
