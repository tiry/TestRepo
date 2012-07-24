<@extends src="base.ftl">

<@block name="main">

<h1> Now, this is time for some questions </h1>

<div id="bodyContent">
<p>${msg}</p>
<br/>
<div id="message" style="color:red;white-space:pre;"></div>
<div id="errMessage" style="color:red;white-space:pre;"></div>
<form>
    <table>
  <#list qnas as qna>
    <tr> <td> ${qna.question} </td>
         <td>
         <#if qna.options?size=0>
      <input type="text" id="${qna.id}" name="${qna.id}" class="qna">
         <#else>
      <select id="${qna.id}" name="${qna.id}" class="qna">
         <option value="">-- choose one --</option>
        <#list qna.options?keys as optionId>
         <option value="${optionId}">${qna.options[optionId]}</option>
        </#list>
      </select>
         </#if>
  </#list>
  </table>
  <input type="button" onclick="doSubmit()" value="send answers to the server"/>
</form>
</div>

<script>

var qnaList=${qnasjson};

function doSubmit() {
  // update JSON object
  for (idx=0; idx <qnaList.length; idx++) {
    qnaList[idx].answer=jQuery("#"+qnaList[idx].id).val();
  }
  //console.log(JSON.stringify(qnaList));

  // send to server
  jQuery("#message").ajaxError( function(event, jqXHR, ajaxSettings, thrownError) {$(this).html('Error submiting the form : server returned an error ' +jqXHR.status); } );
  jQuery("#errMessage").ajaxError( function(event, jqXHR, ajaxSettings, thrownError) {$(this).html(jqXHR.responseText); } );

  jQuery.ajax({
        type: 'POST',
        contentType : 'text/json',
        data : JSON.stringify(qnaList),
        success : function(data, status, xhr) {jQuery("#bodyContent").html(data)}
        });

}

</script>

</@block>
</@extends>