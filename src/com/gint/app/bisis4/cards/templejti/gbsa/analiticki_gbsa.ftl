<#include "_base_gbsa.ftl"
>
><#macro getAnalitika
><#assign la=""
><#assign out=""
><#assign predm=""
><#assign predm1=""
><#assign firstPO=true
><@odrednica/><@brojID/><@opisAnalitika/><@signatura/><@fieldAnalitika/><#--

--><#assign la=la+odred+"<BR>"+val+"<BR><BR>"+"&nbsp;&nbsp;&nbsp;\x0443"+"<BR><BR>"+opisA+"<BR><BR>"+sign+"<BR><BR>"+"&nbsp;&nbsp;&nbsp;U:&nbsp;"+recUtil.getMaticnaPublikacijaMR()+"&nbsp;&nbsp;&nbsp;"
></#macro

><@getAnalitika/><#assign la="<BISIS>"+la+"</BISIS>">${la}