<#import "/spring.ftl" as spring />
<#import "common.ftl" as c />
<#-- 
###############################
# Page layout macro
################################
-->
<#macro pageLayout pageTitle="" pageTitleSuffix="" error={}>
<html>
<#-- PAGE HEADER  -->
  <head>
    <title><@c.msg "page.title"/>&nbsp;${((projectConfig.version)!"")?html}:&nbsp;<@c.msg pageTitle />${pageTitleSuffix}</title>
        <#local resourceSuffix>?v=${(projectConfig.version)!"1"}</#local>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="icon" href="<@spring.url "/img/favicon.ico"/>" />
        <link rel="stylesheet" type="text/css" href='<@spring.url "/css/main.css" + resourceSuffix/>' />
        <script type="text/javascript" src='<@spring.url "/script/main.js" + resourceSuffix/>'></script>
  </head>
  
  <body>
    <div class="page_container">
    <#-- HEADER  -->  <#t/>
      <div class="page_header">
          <table class="blank_table" width="100%" height="100%">
            <tr>
              <td style="width: 80%; padding: 0 0 0 5px;"><#t/>
                <a class="page_title" href="<@spring.url "/contact_list" />"><#t/>
                    <img src="<@spring.url "/img/title.png" />" size="16" /><#t/>
                    <@c.msg "page.title" /></a><#t/>
              </td><#t/>
              <td style="width: 20%; text-align: right; vertical-align: middle; padding: 0 5px 0 0;">
                <#local langURL = "" />
                <#if ((reqParams!"")?has_content)>  <#t/>
                  <#local langURL = "?" + reqParams + "&lang=" /><#t/>
                <#else>  <#t/>
                  <#local langURL = "?lang=" /><#t/>
                </#if><#t/>
                <a href="${langURL}en"><#t/>
                  <img size="16" src="<@spring.url "/img/lang_en.png" />" title="<@c.msg "lang.en" />"/><#t/>
                </a><#t/>
                &nbsp;
                <a href="${langURL}ru"><#t/>
                  <img size="16" src="<@spring.url "/img/lang_ru.png"/>" title="<@c.msg "lang.ru" />"/><#t/>
                </a><#t/>
              </td>
            </tr>
          </table>
      </div>
    <#-- BODY  -->  <#t/>
      <div class="page_body">
          <@page_body/>  <#t/>
      </div>
      <#t/>
    <#-- FOOTER  -->  <#t/>
      <#t/>
      <div class="page_footer">
          <@c.msg "page.footer"/>
      </div>
    </div>
    <#t/>
  </body>
</html>
</#macro>


