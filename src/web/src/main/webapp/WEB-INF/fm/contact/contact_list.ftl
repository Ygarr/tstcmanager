<#import "../common.ftl" as c />
<#include "../page.ftl"/>


<#-- PAGE BODY NESTED CONTENT  -->  <#t/>
<#macro page_body>  <#t/>
    <a href="<@spring.url "/contact_edit" />">
        <img src="<@spring.url "/img/add.png" />" size="16" />&nbsp;<@c.msg "contact_list.page.button.add_contact"/></a>
    <br/>
    <br/>
    <#local contacts = (contactList)![] >  <#t/>
    <div class="table_title">
       <@c.msg "contact_list.page.table.title" />  <#t/>
       &nbsp;(<#if ((pager.recordCount)??)>${pager.recordCount?c}<#else>${contacts?size?c}</#if>)  <#t/>
    </div>  <#t/>
    <#if error?? && error?has_content>
      <@c.handleError errorDetails=error id="contact_list_error_"/>  <#t/>
    </#if>  <#t/>
    
    <form method="POST" action="<@spring.url ("/contact_search") />" >
      <span class="label"><@c.msg "contact_list.page.search.input.label" /></span>&nbsp;  <#t/>
      <input id="input_search_name" name="name" type="text" maxlength="32" value="${(searchCriteria!"")?html}" />&nbsp;&nbsp;  <#t/>
      <input name="search_submit" type="submit" value="<@c.msg "contact_list.page.search.button.label" />" />
    </form>
    <br/>

    <#if contacts?has_content>
      <#if pager??>
       <#local pageSizes = [10, 20, 50, 100] />  <#t/>
       <@c.msg "contact_list.page.page_size"/>&nbsp;  <#t/>
       <#local pageSizeUrl><@spring.url "/contact_list?page=1&page_size="/></#local>  <#t/>
       <#list pageSizes as pageSize>  <#t/>
         &nbsp;  <#t/>
         <#if (pager?? && pager.pageSize = pageSize)>  <#t/>
           <span>${pageSize?c}</span>  <#t/>
         <#else>  <#t/>
           <a href="${pageSizeUrl}${pageSize?c}">${pageSize?c}</a>  <#t/>
         </#if>  <#t/>
       </#list>  <#t/>
     </#if>

      <table class="contact_list">  <#t/>
        <tr>  <#t/>
          <th>  <#t/>
            <@c.msg "contact_list.page.table.header.name"/>
          </th>  <#t/>
          <th>  <#t/>
            <@c.msg "contact_list.page.table.header.phone_number"/>
          </th>  <#t/>
          <th>&nbsp;</th>
        </tr>  <#t/>
        <#list contacts as contact>  <#t/>
          <tr class="data_row">  <#t/>
            <td>${((contact.name)!"")?html}</td>  <#t/>
            <td>${((contact.phoneNumber)!"")?html}</td>  <#t/>
            <td class="td_center">
                <a href="<@spring.url ("/contact_edit?id=" + contact.id?c) />" 
                   title="<@c.msg "contact_list.page.table.edit.button.title"/>">
                   <img src="<@spring.url "/img/edit.png"/>" size="16"/></a>  <#t/>
                &nbsp;
                <a href="<@spring.url ("/contact_delete?id=" + contact.id?c) />" 
                   title="<@c.msg "contact_list.page.table.delete.button.title"/>">
                   <img src="<@spring.url "/img/delete.png"/>" size="16"/></a>  <#t/>
            </td>  <#t/>
          </tr>  <#t/>
        </#list>  <#t/>
      </table>  <#t/>
    <#else>
      <#if (searchCriteria!"")?has_content>
        <@c.msg "contact_list.page.table.nothing_found"/>
      <#else>
        <@c.msg "contact_list.page.table.no_data"/>
      </#if>
    </#if>
    <#if pager?has_content && (pager.pageCount > 1)>
        <div class="pager_container"><#t/>
          <nobr>  <#t/>
            <#list 1..pager.pageCount as p><#t/>
                <#if p=pager.pageNum>  <#t/>
                  <span>${p?c}</span>  <#t/>
                <#else>  <#t/>
                  <#local href><@spring.url ("/contact_list?page=" + p?c) /></#local>  <#t/>
                  <a href="${href}">${p?c}</a>  <#t/>
                </#if>  <#t/>
            </#list>  <#t/>
          </nobr>  <#t/>
        </div><#t/>
    </#if>
    <script type="text/javascript">
      var focusElt = document.getElementById("input_search_name");
      focusElt.focus();
    </script>
</#macro>

<#-- Drawing page layout -->
<@pageLayout pageTitle="contact_list.page.title"/>
