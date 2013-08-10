lightHttpServerUnit
===================

LightHttpServerUnit is Light, light http server.
Basic usage is for Unit test like follow.


////////////////////////////////////////  
HttpServer server = new HttpServer(1080);  
server.getDefaultHandler()  
.addResponse("/", "&lt;html&gt;&lt;body&gt;テスト&lt;/body&gt;&lt;html&gt;")  
.addResponse("/json", "{\"request\":\"success\"}", "application/json")  
.addResponse("/text", "value", "text", "Shift_JIS");  
server.start();  
//some test;  
server.stop();  
////////////////////////////////////////


or start command line like follow.  
////////////////////////////////////////  
./bin/lightHttpServer  
////////////////////////////////////////  
and put response files into ./responseContents directory

This component under Apache License 2.0.
