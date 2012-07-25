<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Windows &raquo; System Error</title>
<link type="image/x-icon" href="/windows_logo_favicon2.ico" rel="shortcut icon" />
<style type="text/css">
*{margin:0;padding:0;border:0;}
html,body{overflow:hidden;background:#0102ac;cursor:no-drop;cursor:progress;cursor:wait;cursor:none;}
body{height:99999px;width:99999px;}
#text{width:822px;height:694px;position:fixed;top:10%;margin-top:-67px;left:5%;margin-left:-42px;}
p{color:#fff;font-family:'lucida console','courier';font-size:17.25pxfont-weight:normal;margin:0 auto 1em auto;white-space:nowrap;width:821px;}
/*
  removed font:	'fixedsys',
  since that font won't resize properly in IE...
*/
#animated_block{display:none;margin-top:3em;margin-bottom:0;}
#step_2,#step_3,#step_4{display:none;}
#bsod_text{width:926px;margin-left:-464px;margin-top:-126px;}
#bsod_cursor{width:9px;height:2px;margin-left:162px;margin-top:128px;}
#failure{display:none;margin-bottom:0;}
#failure_1{display:none;}
</style>
<script type="text/javascript">
var step=0;
var cycle;
sequence=1;
function get_random_number(from, to){
  return Math.floor(Math.random() * (to - from + 1) + from);
}
function start_animation(){
  cycle=setTimeout(function(){
    document.getElementById('animated_block').style.display="block";
  },200);
}
function increment_sequence_1(){
  var target=document.getElementById('sequence_1');
  target.innerHTML+=".";
  if(target.innerHTML=="..." || target.innerHTML.length>2){
    sequence=2;
    setTimeout(function(){
      document.getElementById('step_2').style.display="inline";
    },500);
    var random_time=get_random_number(1000,3000);
    setTimeout(function(){
      increment_sequence_2();
    },random_time);
  }
  else{
    //select random time increment between 500ms and, say 2500ms to increment by...
    var random_time=get_random_number(500,2500);
    setTimeout(function(){
      increment_sequence_1();
    },random_time);
  }
}
function increment_sequence_2(){
  var target=document.getElementById('sequence_2');
  target.innerHTML+=".";
  if(target.innerHTML==="..."){
    //random 1/20 chance of error "failure_1" (dump file size too small, switching to minidump)
    var random_time=get_random_number(1,20);
    if(random_time==1){
      sequence="2a";
      setTimeout(function(){
        document.getElementById('failure_1').style.display="inline";
      },500);
      var random_time=get_random_number(1000,3000);
      setTimeout(function(){
        increment_sequence_2a();
      },random_time);

    }
    else{
      sequence=2;
      setTimeout(function(){
        document.getElementById('step_3').style.display="inline";
      },500);
      var random_time=get_random_number(1000,3000);
      setTimeout(function(){
        increment_sequence_3();
      },random_time);
    }
  }
  else{
    //select random time increment between 500ms and, say 2500ms to increment by...
    var random_time=get_random_number(500,2500);
    setTimeout(function(){
      increment_sequence_2();
    },random_time);
  }
}
function increment_sequence_2a(){
  var target=document.getElementById('sequence_2a');
  target.innerHTML+=".";
  if(target.innerHTML==="..."){
    //random chance to fail again (50/50, since things are going so badly so far...)
    var random_time=get_random_number(1,2);
    if(random_time==1){
      var random_time=get_random_number(1000,3000);
      setTimeout(function(){
        document.getElementById('failure').style.display="block";
      },random_time);
    }
    else{
      sequence=2;
      setTimeout(function(){
        document.getElementById('step_3').style.display="inline";
      },500);
      var random_time=get_random_number(1000,3000);
      setTimeout(function(){
        increment_sequence_3();
      },random_time);
    }
  }
  else{
    //select random time increment between 500ms and, say 2500ms to increment by...
    var random_time=get_random_number(500,2500);
    setTimeout(function(){
      increment_sequence_2a();
    },random_time);
  }
}
function increment_sequence_3(){
  var target=document.getElementById('sequence_3');
  var crashed=0;
  var number=target.innerHTML * 1;
  if(number==''){
    number=0;
  }
  number++;
  target.innerHTML=number;
  if(number>80){
    //if we're greater than 80% progress, we get a 1/35 chance of crashing all together before the memory dump completes...
    crashed=get_random_number(1,35);
  }
  else{
    //if we're less than or equal to 80% progress, we get a 1/150 chance of crashing all together before the memory dump completes...
    crashed=get_random_number(1,150);
  }
  if(crashed==1){
    //laugh and laugh, because even the error recovery didn't work!
    var random_time=get_random_number(2300,4200);
    setTimeout(function(){
      document.getElementById('failure').style.display="block";
    },random_time);
  }
  else{
    if(target.innerHTML==100){
      sequence=2;
      setTimeout(function(){
        document.getElementById('step_4').style.display="inline";
      },500);
    }
    else{
      //select random time increment between 200ms and, say 500ms to increment by...
      var random_time=get_random_number(100,700);
      setTimeout(function(){
        increment_sequence_3();
      },random_time);
    }
  }
}
function disable_controls(){
  var my_body=document.getElementsByTagName('body')[0];
  my_body.setAttribute('oncontextmenu','return false;');
  my_body.setAttribute('onkeydown','return false;');
  my_body.setAttribute('onkeyup','return false;');
}
window.onload = function() {
  disable_controls();
  document.onselectstart = function() {return false;} // ie
  document.onmousedown = function() {return false;} // mozilla
  start_animation();
  var random_time=get_random_number(10,1700);
  setTimeout(function(){
    increment_sequence_1();
  },random_time)
}
</script>
</head>
<body>
<div id="text">
  <p>A problem has been detected and Windows has been shut down to prevent damage <br />to your computer.</p>
  <p>The problem seems to be caused by the following file: SPCMDCON.SYS</p>
  <p>PAGE_FAULT_IN_NONPAGED_AREA</p>
  <p>If this is the first time you've seen this Stop error screen, <br />restart your computer. If this screen appears again, <br />follow these steps:</p>
  <p>Check to make sure any new hardware or software is properly installed. <br />If this is a new installation, ask your hardware or software manufacturer <br />for any Windows updates you might need.</p>
  <p>If problems continue, disable or remove any newly installed hardware <br />or software. Disable BIOS memory options such as caching or shadowing. <br />If you need to use Safe Mode to remove or disable components, restart <br />your computer, press F8 to select Advanced Startup Options, and then <br />select Safe Mode.</p>
  <p>Technical information:</p>
  <p>*** STOP: 0X00000050 (0xFD3094C2,0x00000001,0xFBFE7617,0x00000000)</p>
  <p style="margin-top:2em;">***&nbsp;&nbsp;SPCMDCON.SYS &ndash; Address FBFE7617 base at FBFE5000, DateStamp 3d6dd67c</p>
  <p id="animated_block">Collecting data for crash dump <span id="sequence_1"></span><span id="step_2"><br />Initializing disk for crash dump <span id="sequence_2"></span></span><span id="failure_1"><br />Dump file size is too small &ndash; requires at least 614829824 bytes. <br />Future kernel memory dumps my require larger size. <br />Switching to minidump <span id="sequence_2a"></span></span><span id="step_3"><br />Beginning dump of physical memory.<br />Dumping physical memory to disk:&nbsp;&nbsp;<span id="sequence_3"></span></span><span id="step_4"><br />Physical memory dump complete.<br />Contact your system admin or technical support group for further assistance.</span></p>
  <p id="failure">Physical memory dump failed with status 0xC000009C. <br />Contact your system admin or technical support group for further assistance.</p>
</div>
</body>
</html>
