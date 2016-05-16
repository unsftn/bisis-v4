<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.gint.app.bisis4web.web.Messages" %>
<jsp:useBean id="user" class="com.gint.app.bisis4web.web.beans.WebUser" scope="session"/>
<jsp:useBean id="errorInfo" class="com.gint.app.bisis4web.web.beans.ErrorInfo" scope="session"/>
<jsp:include page="header.jsp" flush="true"/>

<h4><%=Messages.get("SEARCH_BY_ADVANCED", user.getLocale())%></h4>

<form name="searchform" action="MultiQuery" method="post" accept-charset="utf-8">
<select name="prefix1" id="prefix1" class="prefix">
	<option value="AU" selected><%=Messages.get("AU", user.getLocale())%></option>
	<option value="TI"><%=Messages.get("TI", user.getLocale())%></option>
	<option value="KW"><%=Messages.get("KW", user.getLocale())%></option>
	<option value="PU"><%=Messages.get("PU", user.getLocale())%></option>
	<option value="PY"><%=Messages.get("PY", user.getLocale())%></option>
	<option value="LA"><%=Messages.get("LA", user.getLocale())%></option>
	<option value="AB"><%=Messages.get("AB", user.getLocale())%></option>
	<option value="BN"><%=Messages.get("BN", user.getLocale())%></option>
	<option value="CC"><%=Messages.get("CC", user.getLocale())%></option>
	<option value="CD"><%=Messages.get("CD", user.getLocale())%></option>
	<option value="CL"><%=Messages.get("CL", user.getLocale())%></option>
	<option value="CN"><%=Messages.get("CN", user.getLocale())%></option>
	<option value="CO"><%=Messages.get("CO", user.getLocale())%></option>
	<option value="CS"><%=Messages.get("CS", user.getLocale())%></option>
	<option value="CY"><%=Messages.get("CY", user.getLocale())%></option>
	<option value="DC"><%=Messages.get("DC", user.getLocale())%></option>
	<option value="DT"><%=Messages.get("DT", user.getLocale())%></option>
	<option value="IN"><%=Messages.get("IN", user.getLocale())%></option>
	<option value="MR"><%=Messages.get("MR", user.getLocale())%></option>
	<option value="PD"><%=Messages.get("PD", user.getLocale())%></option>
	<option value="PP"><%=Messages.get("PP", user.getLocale())%></option>
	<option value="RN"><%=Messages.get("RN", user.getLocale())%></option>
	<option value="RT"><%=Messages.get("RT", user.getLocale())%></option>
	<option value="SG"><%=Messages.get("SG", user.getLocale())%></option>
	<option value="SB"><%=Messages.get("SB", user.getLocale())%></option>
	<option value="SD"><%=Messages.get("SD", user.getLocale())%></option>
	<option value="SP"><%=Messages.get("SP", user.getLocale())%></option>
	<option value="SR"><%=Messages.get("SR", user.getLocale())%></option>
	<option value="SU"><%=Messages.get("SU", user.getLocale())%></option>
	<option value="TA"><%=Messages.get("TA", user.getLocale())%></option>
	<option value="ID"><%=Messages.get("ID", user.getLocale())%></option>
</select>
<input type="text" name="content1" size="20" id="content1" class="textfield">
<select name="operator1" id="operator1" class="operator">
  <option value="AND" selected >AND</option>
  <option value="OR">OR</option>
  <option value="NOT">NOT</option>
</select>
<br/>
<select name="prefix2" id="prefix2" class="prefix">
	<option value="AU"><%=Messages.get("AU", user.getLocale())%></option>
	<option value="TI" selected><%=Messages.get("TI", user.getLocale())%></option>
	<option value="KW"><%=Messages.get("KW", user.getLocale())%></option>
	<option value="PU"><%=Messages.get("PU", user.getLocale())%></option>
	<option value="PY"><%=Messages.get("PY", user.getLocale())%></option>
	<option value="LA"><%=Messages.get("LA", user.getLocale())%></option>
	<option value="AB"><%=Messages.get("AB", user.getLocale())%></option>
	<option value="BN"><%=Messages.get("BN", user.getLocale())%></option>
	<option value="CC"><%=Messages.get("CC", user.getLocale())%></option>
	<option value="CD"><%=Messages.get("CD", user.getLocale())%></option>
	<option value="CL"><%=Messages.get("CL", user.getLocale())%></option>
	<option value="CN"><%=Messages.get("CN", user.getLocale())%></option>
	<option value="CO"><%=Messages.get("CO", user.getLocale())%></option>
	<option value="CS"><%=Messages.get("CS", user.getLocale())%></option>
	<option value="CY"><%=Messages.get("CY", user.getLocale())%></option>
	<option value="DC"><%=Messages.get("DC", user.getLocale())%></option>
	<option value="DT"><%=Messages.get("DT", user.getLocale())%></option>
	<option value="IN"><%=Messages.get("IN", user.getLocale())%></option>
	<option value="MR"><%=Messages.get("MR", user.getLocale())%></option>
	<option value="PD"><%=Messages.get("PD", user.getLocale())%></option>
	<option value="PP"><%=Messages.get("PP", user.getLocale())%></option>
	<option value="RN"><%=Messages.get("RN", user.getLocale())%></option>
	<option value="RT"><%=Messages.get("RT", user.getLocale())%></option>
	<option value="SG"><%=Messages.get("SG", user.getLocale())%></option>
	<option value="SB"><%=Messages.get("SB", user.getLocale())%></option>
	<option value="SD"><%=Messages.get("SD", user.getLocale())%></option>
	<option value="SP"><%=Messages.get("SP", user.getLocale())%></option>
	<option value="SR"><%=Messages.get("SR", user.getLocale())%></option>
	<option value="SU"><%=Messages.get("SU", user.getLocale())%></option>
	<option value="TA"><%=Messages.get("TA", user.getLocale())%></option>
	<option value="ID"><%=Messages.get("ID", user.getLocale())%></option>
</select>
<input type="text" name="content2" size="20" id="content2" class="textfield">
<select name="operator2" id="operator2" class="operator">
  <option value="AND" selected >AND</option>
  <option value="OR">OR</option>
  <option value="NOT">NOT</option>
</select>
<br/>
<select name="prefix3" id="prefix3" class="prefix">
	<option value="AU" ><%=Messages.get("AU", user.getLocale())%></option>
	<option value="TI"><%=Messages.get("TI", user.getLocale())%></option>
	<option value="KW" selected><%=Messages.get("KW", user.getLocale())%></option>
	<option value="PU"><%=Messages.get("PU", user.getLocale())%></option>
	<option value="PY"><%=Messages.get("PY", user.getLocale())%></option>
	<option value="LA"><%=Messages.get("LA", user.getLocale())%></option>
	<option value="AB"><%=Messages.get("AB", user.getLocale())%></option>
	<option value="BN"><%=Messages.get("BN", user.getLocale())%></option>
	<option value="CC"><%=Messages.get("CC", user.getLocale())%></option>
	<option value="CD"><%=Messages.get("CD", user.getLocale())%></option>
	<option value="CL"><%=Messages.get("CL", user.getLocale())%></option>
	<option value="CN"><%=Messages.get("CN", user.getLocale())%></option>
	<option value="CO"><%=Messages.get("CO", user.getLocale())%></option>
	<option value="CS"><%=Messages.get("CS", user.getLocale())%></option>
	<option value="CY"><%=Messages.get("CY", user.getLocale())%></option>
	<option value="DC"><%=Messages.get("DC", user.getLocale())%></option>
	<option value="DT"><%=Messages.get("DT", user.getLocale())%></option>
	<option value="IN"><%=Messages.get("IN", user.getLocale())%></option>
	<option value="MR"><%=Messages.get("MR", user.getLocale())%></option>
	<option value="PD"><%=Messages.get("PD", user.getLocale())%></option>
	<option value="PP"><%=Messages.get("PP", user.getLocale())%></option>
	<option value="RN"><%=Messages.get("RN", user.getLocale())%></option>
	<option value="RT"><%=Messages.get("RT", user.getLocale())%></option>
	<option value="SG"><%=Messages.get("SG", user.getLocale())%></option>
	<option value="SB"><%=Messages.get("SB", user.getLocale())%></option>
	<option value="SD"><%=Messages.get("SD", user.getLocale())%></option>
	<option value="SP"><%=Messages.get("SP", user.getLocale())%></option>
	<option value="SR"><%=Messages.get("SR", user.getLocale())%></option>
	<option value="SU"><%=Messages.get("SU", user.getLocale())%></option>
	<option value="TA"><%=Messages.get("TA", user.getLocale())%></option>
	<option value="ID"><%=Messages.get("ID", user.getLocale())%></option>
</select>
<input type="text" name="content3" size="20" id="content3" class="textfield">
<select name="operator3" id="operator3" class="operator">
  <option value="AND" selected >AND</option>
  <option value="OR">OR</option>
  <option value="NOT">NOT</option>
</select>
<br/>
<select name="prefix4" id="prefix4" class="prefix">
	<option value="AU"><%=Messages.get("AU", user.getLocale())%></option>
	<option value="TI"><%=Messages.get("TI", user.getLocale())%></option>
	<option value="KW"><%=Messages.get("KW", user.getLocale())%></option>
	<option value="PU"><%=Messages.get("PU", user.getLocale())%></option>
	<option value="PY"><%=Messages.get("PY", user.getLocale())%></option>
	<option value="LA"><%=Messages.get("LA", user.getLocale())%></option>
	<option value="AB"><%=Messages.get("AB", user.getLocale())%></option>
	<option value="BN"><%=Messages.get("BN", user.getLocale())%></option>
	<option value="CC"><%=Messages.get("CC", user.getLocale())%></option>
	<option value="CD"><%=Messages.get("CD", user.getLocale())%></option>
	<option value="CL"><%=Messages.get("CL", user.getLocale())%></option>
	<option value="CN"><%=Messages.get("CN", user.getLocale())%></option>
	<option value="CO"><%=Messages.get("CO", user.getLocale())%></option>
	<option value="CS"><%=Messages.get("CS", user.getLocale())%></option>
	<option value="CY"><%=Messages.get("CY", user.getLocale())%></option>
	<option value="DC"><%=Messages.get("DC", user.getLocale())%></option>
	<option value="DT" selected><%=Messages.get("DT", user.getLocale())%></option>
	<option value="IN"><%=Messages.get("IN", user.getLocale())%></option>
	<option value="MR"><%=Messages.get("MR", user.getLocale())%></option>
	<option value="PD"><%=Messages.get("PD", user.getLocale())%></option>
	<option value="PP"><%=Messages.get("PP", user.getLocale())%></option>
	<option value="RN"><%=Messages.get("RN", user.getLocale())%></option>
	<option value="RT"><%=Messages.get("RT", user.getLocale())%></option>
	<option value="SG"><%=Messages.get("SG", user.getLocale())%></option>
	<option value="SB"><%=Messages.get("SB", user.getLocale())%></option>
	<option value="SD"><%=Messages.get("SD", user.getLocale())%></option>
	<option value="SP"><%=Messages.get("SP", user.getLocale())%></option>
	<option value="SR"><%=Messages.get("SR", user.getLocale())%></option>
	<option value="SU"><%=Messages.get("SU", user.getLocale())%></option>
	<option value="TA"><%=Messages.get("TA", user.getLocale())%></option>
	<option value="ID"><%=Messages.get("ID", user.getLocale())%></option>
</select>
<input type="text" name="content4" size="20" id="content4" class="textfield">
<select name="operator4" id="operator4" class="operator">
  <option value="AND" selected >AND</option>
  <option value="OR">OR</option>
  <option value="NOT">NOT</option>
</select>
<br/>
<select name="prefix5" id="prefix5" class="prefix">
	<option value="AU"><%=Messages.get("AU", user.getLocale())%></option>
	<option value="TI"><%=Messages.get("TI", user.getLocale())%></option>
	<option value="KW"><%=Messages.get("KW", user.getLocale())%></option>
	<option value="PU"><%=Messages.get("PU", user.getLocale())%></option>
	<option value="PY"><%=Messages.get("PY", user.getLocale())%></option>
	<option value="LA"><%=Messages.get("LA", user.getLocale())%></option>
	<option value="AB"><%=Messages.get("AB", user.getLocale())%></option>
	<option value="BN"><%=Messages.get("BN", user.getLocale())%></option>
	<option value="CC" selected><%=Messages.get("CC", user.getLocale())%></option>
	<option value="CD"><%=Messages.get("CD", user.getLocale())%></option>
	<option value="CL"><%=Messages.get("CL", user.getLocale())%></option>
	<option value="CN"><%=Messages.get("CN", user.getLocale())%></option>
	<option value="CO"><%=Messages.get("CO", user.getLocale())%></option>
	<option value="CS"><%=Messages.get("CS", user.getLocale())%></option>
	<option value="CY"><%=Messages.get("CY", user.getLocale())%></option>
	<option value="DC"><%=Messages.get("DC", user.getLocale())%></option>
	<option value="DT"><%=Messages.get("DT", user.getLocale())%></option>
	<option value="IN"><%=Messages.get("IN", user.getLocale())%></option>
	<option value="MR"><%=Messages.get("MR", user.getLocale())%></option>
	<option value="PD"><%=Messages.get("PD", user.getLocale())%></option>
	<option value="PP"><%=Messages.get("PP", user.getLocale())%></option>
	<option value="RN"><%=Messages.get("RN", user.getLocale())%></option>
	<option value="RT"><%=Messages.get("RT", user.getLocale())%></option>
	<option value="SG"><%=Messages.get("SG", user.getLocale())%></option>
	<option value="SB"><%=Messages.get("SB", user.getLocale())%></option>
	<option value="SD"><%=Messages.get("SD", user.getLocale())%></option>
	<option value="SP"><%=Messages.get("SP", user.getLocale())%></option>
	<option value="SR"><%=Messages.get("SR", user.getLocale())%></option>
	<option value="SU"><%=Messages.get("SU", user.getLocale())%></option>
	<option value="TA"><%=Messages.get("TA", user.getLocale())%></option>
	<option value="ID"><%=Messages.get("ID", user.getLocale())%></option>
</select>
<input type="text" name="content5" size="20" id="content5" class="textfield">
<br/>
<input type="submit" value=" <%=Messages.get("SUBMIT_BUTTON", user.getLocale())%> " name="submit">
</form>

<script type="text/javascript">
$(function() {
	$(".prefix").change(function(event) {
		var prefixName = $(this).find("option:selected").val();
		var prefixid = $(this).attr('id').slice(-1);
		$.ajax({
			url: "Coders",
			data: "prefix=" + prefixName,
			dataType: "json",
			method: "GET",
			error: function() { alert('error'); },
			success: function(data, status, jqxhr) {
        var myid = 'content'+prefixid;
				if (data.length == 0) {
					var textField = $("<input>");
					textField.attr('class', 'textfield').attr('name', myid).attr('type', 'text').attr('size', '20');
          $("#"+myid).replaceWith(textField);
          textField.attr('id', myid);
				} else {
	        var sel = $("<select>").attr('style', 'width: 150px');
	        sel.append($("<option>").attr('value', '').text(''));
	        $(data).each(function() {
	          sel.append($("<option>").attr('value', this.code).text(this.value));
	        });
	        sel.attr('name', myid).attr('class', 'combobox');
	        $("#"+myid).replaceWith(sel);
	        sel.attr('id', myid);
				}
			}
		});
	});
	$("#prefix4").change();
	$("#prefix5").change();
});
</script>
<jsp:include page="footer.jsp" flush="true"/>
