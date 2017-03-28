<#import "common.ftl" as c />
<#include "page.ftl"/>


<#-- PAGE BODY NESTED CONTENT  -->  <#t/>
<#macro page_body>  <#t/>
    <a href="<@spring.url "/contact_list" />"><@c.msg "back_to_main_page" /></a>  <#t/>
    <br/>
    <#if error?? && error?has_content>  <#t/>
      <@c.handleError errorDetails=error id="error_"/>  <#t/>
    </#if>  <#t/>
</#macro>

<#-- Drawing page layout -->
<@pageLayout pageTitle="error.page.title"/>
