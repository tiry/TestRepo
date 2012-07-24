<@extends src="base.ftl">

<@block name="header_scripts">
  <script type="text/javascript" src="${skinPath}/scripts/loader.js"></script>
</@block>

<@block name="main">

<h1> Fix Me </h1>

<div class="body">Something should happen here ...
</div>

<script>
  window.setTimeout(function() {loadContent('${This.path}/hint/');}, wait * 1000);
</script>

<div id="hint" style="display:none"> ... but when ?</div>

</@block>
</@extends>