lightHttpServerUnit
===================
<link href='http://alexgorbatchev.com/pub/sh/current/styles/shCore.css' rel='stylesheet' type='text/css'/>
<link href='http://alexgorbatchev.com/pub/sh/current/styles/shThemeDefault.css' rel='stylesheet' type='text/css'/>
<script src='http://alexgorbatchev.com/pub/sh/current/scripts/shCore.js' type='text/javascript'></script>
<script src='http://alexgorbatchev.com/pub/sh/current/scripts/shBrushPlain.js' type='text/javascript'></script>
<script src='http://alexgorbatchev.com/pub/sh/current/scripts/shBrushJava.js' type='text/javascript'></script>
<script language="javascript" type="text/javascript">
  SyntaxHighlighter.config.bloggerMode = true;
	SyntaxHighlighter.all();
</script>
LightHttpServerUnit is Light, light http server.  
Basic usage is for Unit test like follow.
<pre class='brush: java'>
HttpServer server = new HttpServer(1080);  
server.getDefaultHandler()  
.addResponse("/", "&lt;html&gt;&lt;body&gt;テスト&lt;/body&gt;&lt;html&gt;")  
.addResponse("/json", "{\"request\":\"success\"}", "application/json")  
.addResponse("/text", "value", "text", "Shift_JIS");  
server.start();  
//some test;  
server.stop();  
</pre>


or start command line like follow.  
<pre class='brush: plain'>
./bin/lightHttpServer  
</pre>
and put response files into ./responseContents directory

This component under Apache License 2.0.
