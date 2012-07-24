<@extends src="base.ftl">

<@block name="header_scripts">
</@block>
<@block name="stylesheets" >
<style>
.chatRoom {
  border: 1px solid black ;
  height:400px;
  vertical-align:text-bottom;
  font-size:8pt;
  width:200px;
  }
.adm {
  color:red;
  }
.usr {
  color:blue;
  }

#chatRoom1 {
  border: 2px solid green ;
  width:250px;
}
</style>
</@block>

<@block name="main">

  <h1> Chat Server </h1>

<p>
This is a very simple Chat simulator.<br/>
You will have to use chat room1 and talk with Nono (a stupid bot) that will ask you questions.<br/>
But it looks like the ChatServer is bugged and will crash sooner or later, so unless you are very fast, you will need to fix it so that Nono can give you the key to the next level ...
</p>

<table>
<tr>
<#list 1..5 as idx>
 <td> Chat Room ${idx} </td>
</#list>
</tr>

<tr>
<#list 1..5 as idx>
 <td>
 <div id="chatRoom${idx}" class="chatRoom"></div>
 <#if idx==1>
 say : <input type="text" id="chatPrompt"><input type="button" onclick="interactiveMessage()"/>
 </#if>
 </td>
</#list>
</tr>

</table>

<script>

var continueChat=true;

function refreshAllRooms() {
  var rooms = jQuery(".chatRoom");
  for (idx = 0; idx < rooms.length; idx++) {
    var room = jQuery(rooms[idx]);
    jQuery.get('${This.path}/room/' + room.attr('id'),refreshRoomFunction(room));
  }
}

function refreshRoom(roomName) {
  var room = jQuery("#" + roomName);
  if (room) {
    jQuery.get('${This.path}/room/' + room.attr('id'),refreshRoomFunction(room));
  } else {
    continueChat = false;
    alert("no room with id " + roomName);
  }

}

function refreshRoomFunction(room) {
  return function(data) { room.html(data)};
}

var quotes = ["Anybody there ?",":clear","yo","how are you ?", "don't care", ":kick",":activeUsers",":moderate","It does not work, can you help me ?","Restart your windows !","Aaaahhhhhhhh!","I go to sleep, good night ...","We'll have to refactor this code.",];

function serverError(jqXHR, textStatus, errorThrown) {
  continueChat = false;
  alert("error!");
}

function saySomething() {
  var user='user' + Math.floor(Math.random()*11);
  var roomName='chatRoom' + (Math.floor(Math.random()*4)+2);
  var message = quotes[Math.floor(Math.random()*(quotes.length-1))];
  sendMessage(user, roomName,message);
  return roomName;
}

function interactiveMessage() {
  var user='me';
  var roomName='chatRoom1';
  var message = "chat:" + jQuery("#chatPrompt").val();
  sendMessage(user, roomName,message);
  jQuery("#chatPrompt").val("");
  refreshRoom(roomName);
}

function sendMessage(user, roomName, message) {
 jQuery.ajax({ url : '${This.path}/' + roomName + '/' + user, contentType : "multipart/form-data", type : "POST",data : message, "error" : serverError});
}

window.setInterval(function() {if (continueChat) {refreshRoom(saySomething());}}, 20);
window.setInterval(function() {if (continueChat) {refreshRoom(saySomething());}}, 20);
window.setInterval(function() {if (continueChat) {refreshRoom(saySomething());}}, 20);
window.setInterval(function() {if (continueChat) {refreshAllRooms();}}, 1000);
window.setTimeout(function() {if (continueChat) {sendMessage("Nono", "chatRoom1",":close");sendMessage("Nono", "chatRoom1","chat:welcome");refreshRoom("chatRoom1");}}, 1000);
window.setTimeout(function() {if (continueChat) {sendMessage("Nono", "chatRoom1","chat:warn");refreshRoom("chatRoom1");}}, 2000);
window.setTimeout(function() {if (continueChat) {sendMessage("Nono", "chatRoom1","chat:firstQuestion");refreshRoom("chatRoom1");}}, 3000);
</script>

</@block>
</@extends>
