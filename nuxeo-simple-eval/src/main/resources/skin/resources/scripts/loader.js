var wait = 3000;
var rx=/NXT\-([0-9]+)/;

function nextScreen() {
  var url = window.location.href + "/next?key=";
  var hint = rx.exec($('.messageBlock').html())[0]
  url+= hint;
  $.get(url,function(data) { window.location.href += "/" + data + "?key=" + hint;});
}

function loadContent(path) {
  $('.body').html("... loading content ...");
  var url = path;
  var k = url.replace(/\//g,"|") + $('#hint').html();
  k = escape(k);
  url = url+k;
  $.get(url,function(data) { $('.body').html('data');});
}
