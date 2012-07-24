<@extends src="base.ftl">

<@block name="header_scripts">
</@block>

<@block name="main">

  <h1> Jax-RS </h1>

  ${msg}

  <ul>
    <li> <A href="${This.path}/resource/Mario">Simple resource example (Mario) </A></li>
    <li> <A href="${This.path}/resource/Jacky">Simple resource example (Jacky) </A></li>
    <li> <A href="${This.path}/simpleResource">Simple resource example (No name) </A></li>
    <li> <A href="${This.path}/simpleView">Simple view on this current resource </A></li>
    <li> <A href="${This.path}/qna?key=${This.getInitkey()}">Some simple questions for you</A></li>
  </ul>


</@block>
</@extends>
