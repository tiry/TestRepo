<@extends src="base.ftl">

<@block name="header_scripts">
  <script>
  function doAction() {
    window.location.href= '${This.path}/start/' + document.forms[0].hint.value;
    return false;
  }
  </script>
</@block>

<@block name="main">

  <h1> Welcome </h1>

  <span class="messageBlock">${This.getMessage("welcome",k)}</span>

  <form method="POST" action="${This.path}/continue" onsubmit="return doAction()">
    Next step :
    <input type="text" name="hint">
    <input type="submit" value="Go!">
  </form>

</@block>
</@extends>
