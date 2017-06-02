<%@page language="java" isThreadSafe="false" contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.net.*" %>
<%@page import="java.sql.*" %>
<%@page import="org.asynchttpclient.*" %>
<%@page import="com.edelivery.edeliveryserver.configuration.*" %>
<%@page import="com.edelivery.edeliveryserver.db.entityhandlers.*" %>
<%@page import="com.edelivery.edeliveryserver.db.models.*" %>
<%@page import="com.fasterxml.jackson.databind.*" %>
<%@page import="com.modus.edeliveryclient.*" %>
<%@page import="com.modus.edeliveryclient.consumer.*" %>
<%@page import="com.modus.edeliveryclient.models.*" %>
<%@page import="com.modus.edeliveryclient.serialize.*" %>
<%@page import="com.modus.edeliveryclient.serialize.*" %>
<%@page import="com.modus.edeliveryserver.db.factories.*" %>
<%@page import="com.modus.edeliveryserver.papyros.servers.*" %>
<%@page import="gr.modus.edelivery.adapter.messages.*" %>
<%@page import="gr.modus.edelivery.papyros.servers.exceptions.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
AsyncHttpClient httpClient;
httpClient = new DefaultAsyncHttpClient();
//Serializer serializer ;
//serializer =  new com.modus.edeliveryserver.serializer.JacksonSerializer(new ObjectMapper());
String basepath = "http://192.168.20.10:8080/APREST";
Authorization auth ;
String user="sp1";
String password="sp1";
auth = new Authorization(user,password);

EDeliveryClient deliveryClient = new EDeliveryClientImplementation(httpClient,
        null,
        new SmpParticipantConsumer(httpClient, null, basepath),
        new SbdConsumer(httpClient, null, basepath));

///fill sbdparams
//message params 
//auth object 
SBDParams sbdParams = new SBDParams(); 

MessageParams params = new MessageParams(); 
params.seteSensConfigFilename("C:\\eclipseProj\\edelivery\\GenericADAdapter-master\\main\\resource\\eSensConfig.xml");
params.setOriginatorName("panos");
params.setOriginatorEmail("panos@modus.gr");
params.setDestinatorName("anagnosg");
params.setDestinatorName("anagnosg@modus.gr");
params.setFilename("F:\\testDocument\\test_upload.pdf");
params.setMsgId("123");
params.setMsgIdentification("1234");
params.setNormalizedDocSubject("Esen");
params.setNormalizedDocComments("comments");
params.setSamSenderId("123");

deliveryClient.sendMessage(sbdParams, params, auth);


%>

</body>
</html>