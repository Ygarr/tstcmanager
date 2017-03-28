<#import "../common.ftl" as c />
<#include "../page.ftl"/>


<#-- PAGE BODY NESTED CONTENT  -->  <#t/>
<#assign titleMsgCode = "contact_edit.page.title.create"/>
<#if (commandObject.id)?? && (commandObject.id)?has_content>
    <#assign titleMsgCode = "contact_edit.page.title.edit"/>
</#if> 
<#macro page_body>  <#t/>
    <div class="table_title"><@c.msg titleMsgCode/></div>  <#t/>
    <#if ((error!{})?has_content)>  <#t/>
      <@c.handleError errorDetails=error id="contact_edit_error_"/>  <#t/>
    </#if>  <#t/>
    <form method="POST" action="<@spring.url "/contact_edit"/>" >
        
      <table class="form_input_table">
        <#if commandObject??>
          <@spring.bind "commandObject.id" />
          <input name="${spring.status.expression}" type="hidden" value="<#if (commandObject.id)?? && (commandObject.id)?has_content>${commandObject.id?c}</#if>"/>
          <tr>
            <td class="cell_label">
               <span class="label_required">*</span><span class="label"><@c.msg "contact_edit.page.label.name"/></span>
            </td>
            <td class="cell_input">
              <@spring.bind "commandObject.name" />
              <#local nameValue = spring.status.value?default("")>
              <input type="text" id="input_name" name="${spring.status.expression}" value="${nameValue?html}"  maxlength="32" size="32"/>
              <@spring.showErrors "<br/>", "form_error" />
            </td>
          </tr>
          <tr>
           <td class="cell_label">
             <span class="label_required">*</span><span class="label"><@c.msg "contact_edit.page.label.phone_number"/></span>
           </td>
           <td class="cell_input">
             <@spring.bind "commandObject.phoneNumber" />
             <#local phoneNumberValue = spring.status.value?default("")>
             <input type="text" name="${spring.status.expression}" value="${phoneNumberValue?html}" maxlength="32" size="32"/>
             <@spring.showErrors "<br/>", "form_error" />
           </td>
         </tr>
       </#if>
         <tr>
            <td class="cell_buton" colspan="2">
              <#if commandObject??>
                <input name="save" type="submit" value="<@c.msg "contact_edit.page.button.save" />" />
                &nbsp;
              </#if>
                <#local cancelURL><@spring.url "/contact_list"/></#local>
                <input name="save" type="button" onClick="javascript:location.href='${cancelURL}';" value="<@c.msg "contact_edit.page.button.cancel" />" />
            </td>
         </tr>
       </table>
       <script type="text/javascript">
         var focusElt = document.getElementById("input_name");
         focusElt.focus();
       </script>
    </form>
</#macro>

<#-- Drawing page layout -->
<@pageLayout pageTitle=titleMsgCode/>
