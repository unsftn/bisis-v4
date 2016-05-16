<%@ page contentType="text/html; charset=utf-8" %>
<jsp:useBean id="user" class="com.gint.app.bisis4web.web.beans.WebUser" scope="session"/>
<jsp:useBean id="errorInfo" class="com.gint.app.bisis4web.web.beans.ErrorInfo" scope="session"/>
<%!
  String sr = "Srpski";
  String sx = "\u0421\u0440\u043f\u0441\u043a\u0438";
  String hu = "Magyar";
  String sk = "Slovensk\u00fd";
  String ro = "Rom\u00e2n\u0103";
  String rx = "\u0420\u0443\u0441\u043a\u0438";
  String en = "English";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="Expires" content="-1"/>
  <title>BISIS Pretraživanje</title>
  <link rel="stylesheet" type="text/css" href="main.css">
  <script src="js/jquery-1.11.3.min.js"></script>
</head>
<body>

<%
  String locale = request.getParameter("locale");
  if (locale != null)
    user.setLocale(locale);

  String callerPage = request.getRequestURI();
  int slashPos = callerPage.lastIndexOf('/');
  callerPage = callerPage.substring(slashPos + 1);
%>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
  <td width="245"><img src="images/<%=user.getLocale()%>/logo.gif" height="33" width="245" border="0"></td>
  <td width="*"><img src="images/<%=user.getLocale()%>/logo-gradient.gif" height="33" width="245" border="0"></td>
  <td align="right" valign="center">
    <!--
    <a href="<%=callerPage%>?locale=sr"><img src="images/flag_sr.gif" height="20" width="32" border="0"></a>
    <a href="<%=callerPage%>?locale=en"><img src="images/flag_en.gif" height="20" width="32" border="0"></a>
    <a href="<%=callerPage%>?locale=hu"><img src="images/flag_hu.gif" height="20" width="32" border="0"></a>
    -->
    <a href="<%=callerPage%>?locale=sr" class="mainlink"><%=sr%></a> &nbsp;&nbsp;&nbsp;
    <a href="<%=callerPage%>?locale=sx" class="mainlink"><%=sx%></a> &nbsp;&nbsp;&nbsp;
    <a href="<%=callerPage%>?locale=hu" class="mainlink"><%=hu%></a> &nbsp;&nbsp;&nbsp;
    <a href="<%=callerPage%>?locale=sk" class="mainlink"><%=sk%></a> &nbsp;&nbsp;&nbsp;
    <a href="<%=callerPage%>?locale=ro" class="mainlink"><%=ro%></a> &nbsp;&nbsp;&nbsp;
    <a href="<%=callerPage%>?locale=rx" class="mainlink"><%=rx%></a> &nbsp;&nbsp;&nbsp;
    <a href="<%=callerPage%>?locale=en" class="mainlink"><%=en%></a> &nbsp;&nbsp;&nbsp;
  </td>
</tr>
</table>

<table class="margintable" cellpadding="10" cellspacing="0" border="0" width="100%"><tr><td>