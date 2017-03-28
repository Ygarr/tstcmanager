<#import "/spring.ftl" as spring />

<#setting url_escaping_charset="UTF-8" />
<#setting number_format="0.00"/>
<#setting date_format="dd.MM.yyyy"/>
<#setting time_format="hh:mm:ss"/>
<#setting datetime_format="dd.MM.yyyy HH:mm"/>


<#macro msg messageCode="", defaultMessage="" suffix="" prefix="">
    <#assign defaultMessageCode = defaultMessage><#t/>
    <#if defaultMessageCode == ""><#t/>
        <#assign defaultMessageCode = messageCode><#t/>
    </#if><#t/>
    ${prefix}<@spring.messageText messageCode, defaultMessageCode />${suffix}<#t/>
</#macro>

<#macro handleError errorDetails={} id="error_">
  <#if errorDetails?has_content>  <#t/>
    <div id="${id}_container" class="error_container">  <#t/>
      <div class="error_header_container">  <#t/>
        <div class="error_header">  <#t/>
          <@c.msg "error.error_occured"/>  <#t/>
        </div>  <#t/>
        <div class="error_message">  <#t/>
          ${(errorDetails.descr)!""}  <#t/>
        </div>  <#t/>
      </div>  <#t/>

      <#if errorDetails.stackTraceAsString?has_content>  <#t/>
        <a onclick="showErrorStack(this, '${id}_details', '<@c.msg "error.show_details"/>', '<@c.msg "error.hide_details"/>');return false;" href="#"><@c.msg "error.show_details"/></a>  <#t/>

        <div id="${id}_details" class="error_details" style="display:none;">  <#t/>
          <b>${(errorDetails.getMessage())!errorDetails.cause.getClass().getName()}</b><br/>
          ${errorDetails.stackTraceAsString}  <#t/>
        </div> 
			   <#t/>
      </#if>  <#t/>
    </div>   <#t/>
  </#if>  <#t/>
</#macro>

