<#assign autor = data.getSubfieldContent("700a")!?upper_case>
<#if autor != "" && data.getSubfieldContent("700b")??>
<#assign autor = autor + ",&nbsp;" + data.getSubfieldContent("700b")>
</#if>

<#assign naslov = data.getSubfieldContent("200a")!>

<#assign deo = data.getSubfieldContent("200h")!>

<#assign naslovDeo = data.getSubfieldContent("200i")!>

<#assign mesto = data.getSubfieldContent("210a")!>

<#assign izdavac = data.getSubfieldContent("210c")!>

<#assign godina = data.getSubfieldContent("210d")!>

<#assign signatura = utils.getSignatura()>

<#assign invbrojevi = utils.getInvBrojevi()>

<#assign text = autor>

<#if text != "">
	<#if naslov!="" || deo!="" || naslovDeo!="" || mesto!="" || izdavac !="" || godina !="" >
		<#assign text = text + ":&nbsp;">
	</#if>
</#if>

<#if naslov != "">
	<#assign text = text + naslov>
	<#if deo!="" || naslovDeo!="" || mesto!="" || izdavac!="" ||godina!="" >
     <#assign text = text + ",&nbsp;">	
	</#if>
</#if>

<#if deo != "">
	<#assign text = text + deo>
	<#if naslovDeo!="" || mesto!="" || izdavac!="" ||godina!="" >
	<#assign text = text + ".&nbsp;">
	</#if>
</#if>

<#if naslovDeo != "">
	<#assign text = text + naslovDeo>
	<#if mesto!="" || izdavac!="" || godina!="">
	<#assign text = text + ".&nbsp;-&nbsp;">
	</#if>
</#if>
	
<#if mesto != "">
	<#assign text = text + mesto>
	<#if izdavac!="" || godina!="" >
	<#assign text = text + ":&nbsp;">
	</#if>
</#if>

<#if izdavac != "">
<#assign text = text + izdavac>
	<#if godina!="">
	<#assign text = text + ",&nbsp;">
	</#if>
</#if>

<#if godina != "">
<#assign text = text + godina>
</#if>

<#if signatura != "">
	<#assign text = text + "<BR>">
	<#assign text = text + signatura>
</#if>

<#if invbrojevi?? && invbrojevi != "">
	<#assign text = text + "<BR>">
	<#assign text = text + invbrojevi>
</#if>

${text}
