<#assign autor = data.getSubfieldContent("700a")!?upper_case>
<#if (autor != "" && data.getSubfieldContent("700b")??)>
	<#assign autor = autor + ",&nbsp;" + data.getSubfieldContent("700b")>
</#if>

<#assign autor2 = data.getSubfieldContent("701a")!?upper_case>
<#if autor2 != "" && data.getSubfieldContent("701b")??>
	<#assign autor2 = autor2 + ",&nbsp;" + data.getSubfieldContent("701b")>
</#if>
<#if autor != "" && autor2 != "">
	<#assign autor = autor + "<br>">
</#if>
<#assign autor = autor + autor2>

<#assign naslov = data.getSubfieldContent("200a")!>

<#assign naslov2 = data.getSubfieldContent("200c")!>

<#assign deo = data.getSubfieldContent("200h")!>

<#assign naslovDeo = data.getSubfieldContent("200i")!>

<#assign mesto = data.getSubfieldContent("210a")!>

<#assign izdavac = data.getSubfieldContent("210c")!>

<#assign godina = data.getSubfieldContent("210d")!>

<#assign brStr = data.getSubfieldContent("215a")!>

<#assign dimenzije = data.getSubfieldContent("215d")!>

<#assign zbirka = data.getSubfieldContent("225a")!>

<#assign napomene = "">
<#list data.getSubfieldsContent("300a") as item>
	<#if napomene != "">
		<#if napomene?ends_with(".")>
			<#assign napomene = napomene + "&nbsp;-&nbsp;">
		<#else>
			<#assign napomene = napomene + ".&nbsp;-&nbsp;">
		</#if>
		<#assign napomene = napomene + item>
	</#if>
</#list>
<#assign napomene2 = "">
<#list data.getSubfieldsContent("320a") as item>
	<#if napomene2 != "">
		<#if napomene2?ends_with(".")>
			<#assign napomene2 = napomene2 + "&nbsp;-&nbsp;">
		<#else>
			<#assign napomene2 = napomene2 + ".&nbsp;-&nbsp;">
		</#if>
		<#assign napomene2 = napomene2 + item>
	</#if>
</#list>
<#if napomene2 != "">
	<#assign napomene = napomene + "<br>" + napomene2>
</#if>

<#assign isbnBroj = data.getSubfieldContent("010a")!>

<#assign udc = "">
<#list data.getSubfieldsContent("675a") as item>
	<#if udc != "">
		<#assign udc = udc + "<br>">
	</#if>
	<#assign udc = udc + item>
</#list>

<#assign signatura = utils.getSignatura()>

<#assign invbrojevi = utils.getInvBrojevi()>


<table cellpadding="0" cellspacing="0" width="100%">

<#if autor != "">
	<tr>
	<td valign=top width="18%"><i>${labels.autorR}:</i></td>
	<td>${autor}</td>
	</tr>
</#if>

<#if autor != "">
	<tr><td valign=top width=\"18%\"><br></td><td></td></tr>
</#if>

<#if naslov!="">
	<tr>
	<td valign=top width="18%"><i>${labels.naslovR}:</i></td>
	<td>${naslov}</td>
	</tr>
</#if>

<#if naslov2!="">
	<tr>
	<td valign=top width="18%"><i>${labels.naslovDrugogR}:</i></td>
	<td>${naslov2}</td>
	</tr>
</#if>

<#if deo!="">
	<tr>
	<td valign=top width="18%"><i>${labels.deoR}:</i></td>
	<td>${deo}</td>
	</tr>
</#if>

<#if naslovDeo!="">
	<tr>
	<td valign=top width="18%"><i>${labels.naslovDelaR}:</i></td>
	<td>${naslovDeo}</td>
	</tr>
</#if>

<#if naslov!="" || naslov2!="" || deo!="" || naslovDeo!="">
	<tr><td><br></td><td></td></tr>
</#if>

<#if mesto!="">
	<tr>
	<td valign=top width="18%"><i>${labels.mestoR}:</i></td>
	<td>${mesto}</td>
	</tr>
</#if>

<#if izdavac!="">
	<tr>
	<td valign=top width="18%"><i>${labels.izdavacR}:</i></td>
	<td>${izdavac}</td>
	</tr>
</#if>

<#if godina!="">
	<tr>
	<td valign=top width="18%"><i>${labels.godIzdR}:</i></td>
	<td>${godina}</td>
	</tr>
</#if>

<#if brStr!="">
	<tr>
	<td valign=top width="18%"><i>${labels.brStrR}:</i></td>
	<td>${brStr}</td>
	</tr>
</#if>

<#if dimenzije!="">
	<tr>
	<td valign=top width="18%"><i>${labels.dimenzijeR}:</i></td>
	<td>${dimenzije}</td>
	</tr>
</#if>

<#if zbirka!="">
	<tr>
	<td valign=top width="18%"><i>${labels.zbirkaR}:</i></td>
	<td>${zbirka}</td>
	</tr>
</#if>

<#if mesto!="" || izdavac!="" || godina!="" || brStr!="" || dimenzije!="" || zbirka!="">
	<tr><td valign=top width="18%"><br></td><td></td></tr>
</#if>

<#if napomene!="">
	<tr>
	<td valign=top width="18%"><i>${labels.napomeneR}:</i></td>
	<td>${napomene}</td>
	</tr>
</#if>

<#if isbnBroj!="">
	<tr>
	<td valign=top width="18%"><i>ISBN:</i></td>
	<td>${isbnBroj}</td>
	</tr>
</#if>

<#if udc!="">
	<tr>
	<td valign=top width="18%"><i>${labels.udkR}:</i></td>
	<td>${udc}</td>
	</tr>
</#if>

<#if napomene!="" || isbnBroj!="" || udc!="">
	<tr><td valign=top width="18%"><br></td><td></td></tr>
</#if>

<#if signatura!="">
	<tr>
	<td valign=top width="18%"><i>${labels.signat}:</i></td>
	<td>${signatura}</td>
	</tr>
</#if>

<#if signatura!="">
	<tr><td valign=top width="18%"><br></td><td></td></tr>
</#if>

<#if invbrojevi?? && invbrojevi != "">
	<tr>
	<td valign=top width="18%"><i>${labels.invbrojR}:</i></td>
	<td>${invbrojevi}</td>
	</tr>
</#if>

<#if invbrojevi?? && invbrojevi != "">
	<tr><td valign=top width="18%"><br></td><td></td></tr>
</#if>

</table>
