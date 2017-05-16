<#include "_polja_gbsa.ftl">
<#assign odr="">

<#macro toRoman

     ><#assign roman=""
     ><#assign ost10=br % 10
     ><#if ost10=1
              ><#assign roman=roman+"I"
     ><#elseif ost10=2
              ><#assign roman=roman+"II" 
     ><#elseif ost10=3
              ><#assign roman=roman+"III" 
     ><#elseif ost10=4
              ><#assign roman=roman+"IV" 
     ><#elseif ost10=5
              ><#assign roman=roman+"V" 
     ><#elseif ost10=6
              ><#assign roman=roman+"VI" 
     ><#elseif ost10=7
              ><#assign roman=roman+"VII" 
     ><#elseif ost10=8
              ><#assign roman=roman+"VIII" 
     ><#elseif ost10=9
              ><#assign roman=roman+"IX" 
     ></#if


     ><#assign ost100=br % 100-ost10
     ><#if ost100=10
              ><#assign roman="X"+roman
     ><#elseif ost100=20
              ><#assign roman="XX" +roman
     ><#elseif ost100=30
              ><#assign roman="XXX" +roman
     ><#elseif ost100=40
              ><#assign roman="XL" +roman
     ><#elseif ost100=50
              ><#assign roman="L"+roman 
     ><#elseif ost100=60
              ><#assign roman="LX" +roman
     ><#elseif ost100=70
              ><#assign roman="LXX"+roman 
     ><#elseif ost100=80
              ><#assign roman="LXXX" +roman
     ><#elseif ost100=90
              ><#assign roman="XC"+roman 
     ></#if

     ><#assign ost1000=br % 1000-ost10-ost100
     ><#if ost1000=100
              ><#assign roman="C"+roman
     ><#elseif ost1000=200
              ><#assign roman="CC" +roman
     ><#elseif ost1000=300
              ><#assign roman="CCC" +roman
     ><#elseif ost1000=400
              ><#assign roman="CD" +roman
     ><#elseif ost1000=500
              ><#assign roman="D"+roman 
     ><#elseif ost1000=600
              ><#assign roman="DC" +roman
     ><#elseif ost1000=700
             ><#assign roman="DCC"+roman 
     ><#elseif ost1000=800
             ><#assign roman="DCCC" +roman
     ><#elseif ost1000=900
             ><#assign roman="CM"+roman 
     ></#if
 
     ><#assign ost10000=br % 10000-ost10-ost100-ost1000
     ><#if ost10000=1000
             ><#assign roman="M"+roman
     ><#elseif ost10000=2000
             ><#assign roman="MM" +roman
     ><#elseif ost10000=3000
             ><#assign roman="MMM" +roman
     ><#elseif ost10000=4000
             ><#assign roman="MMMM" +roman
     ><#elseif ost10000=5000
             ><#assign roman="MMMMM"+roman 
     ><#elseif ost10000=6000
             ><#assign roman="MMMMMM" +roman
     ><#elseif ost10000=7000
             ><#assign roman="MMMMMMM"+roman 
     ><#elseif ost10000=8000
             ><#assign roman="MMMMMMMM" +roman
     ><#elseif ost10000=9000
             ><#assign roman="MMMMMMMMM"+roman 
     ></#if
></#macro


><#macro cutFirstN  
 ><#assign i=1
 ><#assign pom=" "
 ><#list val?split(" ") as x
        ><#if i!=1
             ><#assign pom=pom+" "
        ></#if
        ><#assign pom=pom+x
        
             ><#if i==number+1
                  ><#break
             ></#if
             ><#assign i=i+1
 ></#list
 ><#assign val=pom
></#macro

><#macro upperFirstN 
 ><#assign i=0
 ><#assign pom=""
 
 ><#list val?split(" ") as x
 	><#if x?index_of("&nbsp;")!=-1
 	><#list x?split("&nbsp;") as y
	 	><#if i<number
	 	><#assign pom=pom+"&nbsp;"+y?upper_case
	 	><#assign i=i+1 
		><#else
		  ><#assign pom=pom+"&nbsp;"+y
	 	></#if
	></#list
	><#else
		><#if i<number
	 	><#assign pom=pom+"&nbsp;"+x?upper_case
	 	><#assign i=i+1 
	 	><#else
		  ><#assign pom=pom+"&nbsp;"+x
	 	></#if
	 ></#if 
	></#list
	><#assign val=pom
></#macro


><#macro glavniOpis
><#assign opis=""
><#assign start532=false
><#assign abc=""
><#if f101?exists
   ><#list f101 as field
         ><#assign val=""
         ><#assign allSF="a" 
         ><@field010 field/><#--
         --><#assign abc=val
   ></#list
></#if             
><#if f532?exists 
            ><#list f532 as field 
                    ><#assign val=""
                    ><#assign allSF="a" 
                    ><@field532 field/><#--
                    --><#if val?index_of("[")=0
                        ><#assign start532=true
                    ></#if
                    ><#break
            ></#list
      ></#if
><#if f200?exists 
     ><#list f200 as field            
            ><#if field.ind1="1" 
                    ><#assign val="" 
                    ><#assign allSF="@"   
                    ><@field200 field/><#--
                    --><#if (abc="scc" || abc="scr") && !start532                               
                                ><#assign number=1
                                ><@upperFirstN/><#--
                   --></#if
                   ><#assign opis=opis+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+val 
           ><#elseif field.ind1="0" && start532
                    ><#assign val="" 
                    ><#assign allSF="@"   
                    ><@field200 field/><#--
                    --><#assign opis=opis+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+val
                   ><#if f532?exists
                        ><#list f532 as field
                                          ><#assign i532=0
                                          ><#assign pom532=""
                                          ><#assign odr="f532" 
                                          ><#assign val=""
                                          ><#assign allSF="a" 
                                          ><@field532 field/><#--
                                          --><#list val?split("]") as x
                                              ><#if i532=0
                                                 ><#assign pom532=pom532+x+"]"
                                                 ><#assign i532=i532+1
                                              ></#if
                
                                          ></#list
                                          ><#assign val=pom532
                                          ><#break 
                   
                       ></#list 
                       ><#assign opis="&nbsp;&nbsp;&nbsp;"+val+"<BR>"+opis
                   ></#if                   
                          
           ><#else
                    ><#assign val=""
                    ><#assign allSF="@"
                    ><@field200 field/><#--
                    --><#if val!=""
                            ><#assign val=""
                            ><#assign allSF="abcdefghiv"
                            ><@field200 field/><#--
                            --><#assign opis=opis+"&nbsp;&nbsp;&nbsp;"+val
                    ></#if
           ></#if
           ><#break
    ></#list
></#if
><#if f205?exists  
  ><#list f205 as field
      ><#assign val=""
      ><#assign allSF="adfgb"
      ><@field205 field/><#--
      --><#if val!=""
                ><#if opis?ends_with(".") 
                      ><#assign opis=opis+"&nbsp;-&nbsp;"
                ><#else
                      ><#assign opis=opis+".&nbsp;-&nbsp;"
                ></#if
                ><#assign opis=opis+val
      ></#if    
      ><#break
  ></#list
></#if
><#if f207?exists    
  ><#list f207 as field
           ><#assign val=""
           ><#assign allSF="a"
  
           ><@field207 field/><#-- 
           --><#if val!=""
                 ><#if opis?ends_with(".")
                          ><#assign opis=opis+"&nbsp;-&nbsp;"
                 ><#else
                          ><#assign opis=opis+".&nbsp;-&nbsp;"
                 ></#if
                 ><#assign opis=opis+val 
           ></#if
           ><#break
  ></#list
></#if
><#if f210?exists 
  ><#list f210 as field
       ><#assign val=""
       ><#assign allSF="acdegh"
       ><@field210 field/><#--
       --><#if val!=""
                 ><#if opis?ends_with(".")
                          ><#assign opis=opis+"&nbsp;-&nbsp;"
                 ><#else
                          ><#assign opis=opis+".&nbsp;-&nbsp;"
                 ></#if
                 ><#assign opis=opis+val 
       ></#if 
       ><#break
  ></#list 
></#if
><#if f215?exists
  ><#list f215 as field
          ><#assign val=""
          ><#assign allSF="acde"  
          ><@field215 field/><#--
          --><#if val!=""
                 ><#if opis?ends_with(".")
                          ><#assign opis=opis+"&nbsp;-&nbsp;"
                 ><#else
                          ><#assign opis=opis+".&nbsp;-&nbsp;"
                 ></#if
                 ><#assign opis=opis+val 
           ></#if
          ><#break
  ></#list 
></#if 
><#assign pom225=""  
><#if f225?exists   
          ><#if opis?ends_with(".")
              ><#assign pom225=opis+"&nbsp;-&nbsp;"
          ><#else
              ><#assign pom225=opis+".&nbsp;-&nbsp;"
          ></#if  
  ><#assign i=1
  ><#list f225 as field
          ><#assign val=""
          ><#assign allSF="adefhivx" 
          ><@field225 field/><#--
          --><#if val!=""
                   ><#if i=1
                          ><#assign opis=pom225+"&nbsp;("+val+")&nbsp;"
                   ><#else
                          ><#assign opis=opis+"&nbsp;("+val+")&nbsp;"
                   ></#if
          ></#if
          ><#assign i=i+1
  ></#list 
></#if 
><#if opis!="" &&  !opis?ends_with(".") && !opis?ends_with(")&nbsp;")
                   ><#assign opis=opis+"."                 
></#if
></#macro



><#macro toUpperFirst 
 ><#assign pom=""
 ><#if val?index_of(",")!=-1
       ><#if (val?index_of("&nbsp;")!=-1 && val?index_of("&nbsp;")<val?index_of(",")) || (val?index_of(" ")!=-1 && val?index_of(" ")<val?index_of(","))
            ><#if (val?index_of("&nbsp;")!=-1 && val?index_of(" ")=-1 )|| (val?index_of("&nbsp;")!=-1 && (val?index_of("&nbsp;")<val?index_of(" ")))

               ><#assign pom=""
               ><#assign i=1
               ><#list val?split("&nbsp;") as x
                    ><#if i=1
                        ><#assign i=2
                        ><#assign pom=pom+ x?upper_case 
                   ><#else 
                        ><#assign pom=pom+"&nbsp;"+ x
                   ></#if
             ></#list 
           ><#elseif val?index_of(" ")!=-1
                ><#assign pom=""
                ><#assign i=1
                ><#list val?split(" ") as x
                      ><#if i=1
                        ><#assign i=2
                        ><#assign pom=pom+ x?upper_case 
                      ><#else 
                        ><#assign pom=pom+"&nbsp;"+ x
                      ></#if
               ></#list
           ></#if        
           
      ><#elseif val?index_of("-")!=-1 && (val?index_of("-")<val?index_of(",")||val?index_of("-")<val?index_of(" ")|| val?index_of("-")<val?index_of("&nbsp;")) 
       ><#assign pom=""
       ><#assign i=1
       ><#list val?split("-") as x
              
              ><#if i=1
                   ><#assign i=2
                   ><#assign pom=pom+ x?upper_case 
             ><#else                   
                   ><#assign pom=pom+"-"+x
                  
             ></#if
       ></#list 
     
       
     ><#else
       ><#assign pom=""
       ><#assign i=1
       ><#list val?split(",") as x
              
              ><#if i=1
                   ><#assign i=2
                   ><#assign pom=pom+ x?upper_case 
             ><#else 
                   ><#assign pom=pom+","+x
             ></#if
       ></#list
     ></#if
 ><#elseif (val?index_of("&nbsp;")!=-1 && val?index_of(" ")=-1 )|| (val?index_of("&nbsp;")!=-1 && (val?index_of("&nbsp;")<val?index_of(" ")))

       ><#assign pom=""
       ><#assign i=1
       ><#list val?split("&nbsp;") as x
                ><#if i=1
                        ><#assign i=2
                        ><#assign pom=pom+ x?upper_case 
                ><#else 
                        ><#assign pom=pom+"&nbsp;"+ x
                ></#if
      ></#list 
 ><#elseif val?index_of(" ")!=-1
       ><#assign pom=""
       ><#assign i=1
       ><#list val?split(" ") as x
                ><#if i=1
                        ><#assign i=2
                        ><#assign pom=pom+ x?upper_case 
                ><#else 
                        ><#assign pom=pom+"&nbsp;"+ x
                ></#if
      ></#list
 ><#elseif val?index_of("-")!=-1 && val?index_of(",")=-1 && val?index_of("&nbsp;")=-1 && val?index_of(" ")=-1    
        ><#assign i=1
        ><#list val?split("-") as x
              
             ><#if i=1
                   ><#assign i=2
                   ><#assign pom=pom+ x?upper_case 
             ><#else                   
                   ><#assign pom=pom+"-"+x
                  
             ></#if
       ></#list      
 ><#else
     ><#assign pom=val?upper_case
 
 ></#if
      ><#assign val=pom
></#macro


><#macro odrednica
      ><#assign odred=""
      ><#assign abc=""
      ><#assign start532=false

       ><#if f101?exists
           ><#list f101 as field
                   ><#assign val=""
                   ><#assign allSF="a" 
                   ><@field010 field/><#--
                   --><#assign abc=val
           ></#list
       ></#if             
      ><#assign exist532=false
      ><#if f532?exists 
            ><#list f532 as field 
                    ><#if field.ind1="1" 
                            ><#assign exist532=true                            
                    ></#if
                    ><#assign val=""
                    ><#assign allSF="a" 
                    ><@field532 field/><#--
                    --><#if val?index_of("[")=0
                        ><#assign start532=true
                    ></#if
                    ><#break
            ></#list
      ></#if
      ><#assign exist500=false
      ><#if f500?exists 
            ><#list f500 as field 
                    ><#if field.ind1="1" && field.ind2="1"
                            ><#assign exist500=true                            
                    ></#if
                    ><#break
            ></#list
      ></#if
      ><#assign exist503=false
      ><#if f503?exists 
            ><#list f503 as field 
                    ><#if field.ind1="1" 
                            ><#assign exist503=true                            
                    ></#if
                    ><#break
            ></#list
      ></#if   
      ><#assign exist540=false   
      ><#if f540?exists 
            ><#list f540 as field 
                    ><#if field.ind1="1" 
                            ><#assign exist540=true                            
                    ></#if
                    ><#break
            ></#list
      ></#if     
      ><#if f700?exists               
               ><#list f700 as field
                           ><#assign val=""
                           ><#assign allSF="adbcf" 
                           ><@field700 field/><#--
                           --><@toUpperFirst/><#--
                           --><#if val!=""
                                       ><#assign odr="f700"
                                       ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                           ></#if
                           ><#return 
              ></#list
      ><#elseif f710?exists               
              ><#list f710 as field
                          ><#assign val=""
                          ><#assign allSF="abghcdfe" 
                          ><@field710 field/><#--
                          --><@toUpperFirst/><#--
                          --><#if val!=""
                                    ><#assign odr="f710"
                                    ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                          ></#if
                          ><#return 
              ></#list
     ><#elseif f720?exists              
              ><#list f720 as field
                         ><#assign val=""
                         ><#assign allSF="af" 
                         ><@field720 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="f720"
                                  ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                          ></#if
                          ><#return 
              ></#list
    ><#elseif f532?exists && exist532
             ><#list f532 as field
                    ><#if field.ind1="1"
                       ><#assign odr="f532" 
                       ><#assign val=""
                       ><#assign allSF="a"
                       ><@field532 field/><#--
                       --><@toUpperFirst/><#--
                       --><#if val!="" && abc!="scc"
                                ><#assign odr="f532" 
                                ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                       ></#if
                       ><#return 
                   ></#if
             ></#list 
      
   ><#elseif f500?exists &&  exist500
            ><#list f500 as field
                      ><#if field.ind1="1" && field.ind2="1" 
                                
                                ><#assign val=""
                                ><#assign allSF="abhiklm" 
                                ><@field500 field/><#--
                                --><@toUpperFirst/><#--
                                --><#if val!=""
                                       ><#assign odr="f500"
                                       ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                                ></#if
                                ><#return 
                     ></#if
            ></#list
  ><#elseif f503?exists  && exist503
            ><#list f503 as field
                    ><#if field.ind1="1"                                
                               ><#assign val=""
                               ><#assign allSF="ajb" 
                               ><@field503 field/><#--
                               --><@toUpperFirst/><#--
                               --><#if val!=""
                                       ><#assign odr="f503"
                                       ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                               ></#if
                               ><#return 
                    ></#if
           ></#list
  ><#elseif f540?exists  && exist540
            ><#list f540 as field
                    ><#if field.ind1="1"                                
                               ><#assign val=""
                               ><#assign allSF="a" 
                               ><@field500 field/><#--
                               --><@toUpperFirst/><#--
                               --><#if val!=""
                                       ><#assign odr="f540"
                                       ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                               ></#if
                               ><#return 
                    ></#if
           ></#list         
  ><#elseif f200?exists 
           ><#list f200 as field
                             ><#if field.ind1="1"
                                   ><#if f532?exists && start532
                                      ><#list f532 as field                    
                                          ><#assign odr="f532" 
                                          ><#assign val=""
                                          ><#assign allSF="a" 
                                          ><@field532 field/><#--
                                          --><@toUpperFirst/><#--
                                         --><#if val!="" 
                                                 ><#assign pom532=""
                                                 ><#assign i532=0
                                                 ><#list val?split("]") as x
                                                      ><#if i532=0
                                                         ><#assign pom532=pom532+x+"]"
                                                         ><#assign i532=i532+1
                                                      ></#if
                
                                                 ></#list
                                                 ><#assign val=pom532
                                                 ><#assign val=val?replace("[","")
                                                 ><#assign val=val?replace("]","")
                                                 ><#assign odr="f532" 
                                                 ><#assign odred="<odr><B>"+val+"</B><BR></odr>"
                                         ></#if                                         
                                         ><#return 
                   
                                      ></#list 
                                   ><#else          
                                       ><#assign val=""
                                       ><#assign allSF="a" 
                                       ><@field200 field/>
                                       <#if val!="" && abc!="scc"
                                               ><#assign odr="f200"
                                       ></#if
                                  ></#if     
                             ></#if
           ></#list
  ><#else
      ><#assign odr=odrednica         
  ></#if  
></#macro

><#macro cutAndUpper  
 ><#assign i=1
 ><#assign pom=""
 ><#if val?index_of(" ")!=-1
 ><#list val?split(" ") as x
        ><#if i=1
               ><#assign pom=pom+x?upper_case
        ><#elseif i!=1 && i<=3
             ><#assign pom=pom+"&nbsp;"+x
        ><#elseif i=4
             ><#assign pom=pom+"..."
             ><#break
        ></#if  
        ><#assign i=i+1
 ></#list
 ><#assign val=pom
 ></#if
></#macro

><#macro zaglavlje
><#assign zag=""
><#assign abc=""
><#if odr="f200" 
       ><#if f101?exists
           ><#list f101 as field
                   ><#assign val=""
                   ><#assign allSF="a" 
                   ><@field010 field/><#--
                   --><#assign abc=val
           ></#list
       ></#if             
       ><#if f200?exists
              ><#list f200 as field
                     ><#if field.ind1="1" 
                              ><#assign val=""
                              ><#assign allSF="a" 
                              ><@field200 field/><#--
                              --><#if val!="" && abc!="scc"                                                                                 
                                         ><@cutAndUpper/><#--  
                                         --><#assign zag="<zag><B>"+val+"</B><BR></zag>"                                                   
                              ></#if 
                   ></#if
                   ><#break
           ></#list 
     ></#if
></#if
></#macro

><#macro zaglavljeSer
><#assign zagS=""
><#if odr="f200" 
       ><#if f200?exists 
              ><#list f200 as field
                     ><#if field.ind1="1" 
                              ><#assign val=""
                              ><#assign allSF="a" 
                              ><@field200 field/><#--
                              --><#if val!=""                                    
                                         ><@cutAndUpper/><#--
                                         --><#assign zagS="<ser><B>"+val+"</B><BR></ser>"                                                   
                              ></#if 
                   ></#if
                   ><#break
           ></#list 
     ></#if
></#if
></#macro


><#macro napomene
><#assign nap=""
       ><#if recUtil.getSubfieldContent("856u")!=""
      	><#assign nap = "\x0414\x043E\x0441\x0442\x0443\x043F\x043D\x043E \x0438 \x043D\x0430:&nbsp;"+ recUtil.getSubfieldContent("856u")          
      ></#if
      ><#if f300?exists
               ><#list f300 as field
                       ><#assign val=""                        
                       ><#assign allSF="@" 
                       ><@field300 field/><#--       
                       --><#if val!=""
                                   ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if                                                                   
                                  ></#if
                                  ><#assign nap=nap+val      
                     ></#if
              ></#list
     ></#if
     ><#if f314?exists
             ><#list f314 as field
                    ><#assign val=""       
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if                                                                             
                                  ></#if
                                  ><#assign nap=nap+val
                   ></#if
          ></#list
   ></#if>
   <#if f316?exists>
		<#list f316 as field>
			<#assign val="">
			<#assign allSF="a509">
			<@field316 field/>
			<#if val!="">
				<#if nap!="">
					<#if nap?ends_with(".")>
						<#assign nap=nap+"&nbsp;-&nbsp;">
					<#else>
						<#assign nap=nap+".&nbsp;-&nbsp;">
					</#if>
				<#else>
					<#assign nap="<BR><BR>">
				</#if>
				<#assign nap=nap+val>
			</#if>
		</#list>
	</#if>   
   <#if f317?exists
             ><#list f317 as field
                    ><#assign val=""       
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if                                                                             
                                  ></#if
                                  ><#assign nap=nap+val
                   ></#if
          ></#list
   ></#if
   ><#if f320?exists 
         ><#list f320 as field
                 ><#assign val=""                      
                 ><#assign allSF="a"     
                 ><@field200 field/><#--
                 --><#if val!=""
                          ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                           
                                  ></#if
                                  ><#assign nap=nap+val
                ></#if
        ></#list
  ></#if
  ><#if f321?exists 
        ><#list f321 as field
                  ><#assign val=""     
                  ><#assign allSF="@" 
                  ><@field321 field/><#--
                  --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                            
                                  ></#if
                                  ><#assign nap=nap+val
                ></#if
       ></#list
  ></#if
  ><#if f322?exists 
       ><#list f322 as field
                   ><#assign val=""       
                   ><#assign allSF="@" 
                   ><@field300 field/><#--
                   --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                  ></#if
                                  ><#assign nap=nap+val
                  ></#if          
       ></#list
  ></#if
  ><#if f323?exists 
          ><#list f323 as field
                    ><#assign val=""        
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""

                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                          
                                  ></#if
                                  ><#assign nap=nap+val                               
                    ></#if
         ></#list
  ></#if
  ><#if f324?exists 
         ><#list f324 as field
                     ><#assign val=""       
                     ><#assign allSF="@" 
                     ><@field300 field/><#--
                     --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                           
                                  ></#if
                                  ><#assign nap=nap+val
                     ></#if          
        ></#list
  ></#if
  ><#if f326?exists 
         ><#list f326 as field
                    ><#assign val=""        
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val        
                    ></#if
        ></#list
  ></#if
  ><#if f327?exists 
         ><#list f327 as field
                    ><#assign val=""         
                    ><#assign allSF="a" 
                    ><@field200 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                    
                                  ></#if
                                  ><#assign nap=nap+val
                   ></#if
        ></#list
  ></#if
  ><#if f328?exists 
        ><#list f328 as field
                 ><#assign val=""       
                 ><#assign allSF="a" 
                 ><@field300 field/><#--
                 --><#if val!=""
                                  ><#if nap!=""

                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if                                 
                                             
                                  ></#if
                                  ><#assign nap=nap+val
               ></#if
       ></#list
  ></#if
  ><#if f330?exists 
        ><#list f330 as field
               ><#assign val=""        
               ><#assign allSF="@" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f345?exists 
        ><#list f345 as field
               ><#assign val=""        
               ><#assign allSF="@" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f370?exists 
        ><#list f370 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f371?exists 
        ><#list f371 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  
  ><#if f373?exists 
        ><#list f373 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f374?exists 
        ><#list f374 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f375?exists 
        ><#list f375 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f376?exists 
        ><#list f376 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
   ><#if f377?exists 
        ><#list f377 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
   ><#if f378?exists 
        ><#list f378 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f379?exists 
        ><#list f379 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f395?exists 
        ><#list f395 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  
  
  
  ><#if nap!="" && !nap?ends_with(".")
            ><#assign nap=nap+"."
  ></#if  
></#macro





><#macro isbn
  ><#assign isbnBR=""  
        ><#if f010?exists                 
                 ><#list f010 as field
                          ><#assign val=""
                          ><#assign allSF="ab" 
                          ><@field010 field/><#--
                          --><#if val!=""
                                 ><#if isbnBR!=""                                           
                                         ><#assign isbnBR=isbnBR+".&nbsp;-&nbsp;"        
                                 ></#if
                                 ><#assign isbnBR=isbnBR+"ISBN&nbsp;"+val+"&nbsp;"
                          ></#if
                ></#list
     ></#if
     
></#macro









><#macro issn
  ><#assign issnBR=""
  ><#if f011?exists 
    
          ><#list f011 as field
                    ><#assign val=""
                    ><#assign allSF="e"
                    ><@field011 field/><#--
                   --><#if val!=""
                                   ><#assign issnBR="<BR><BR>ISSN&nbsp;"+val
                   ></#if 
                   ><#break                    
         ></#list
  ></#if
></#macro


><#macro alone532
><#assign a532=""
  ><#if (f700?exists  || f710?exists || f720?exists 
          || f503?exists ) && f532?exists   
                  ><#list f532 as field
                          ><#assign val=""
                          ><#assign allSF="a" 
                          ><@field532 field/><#--
                          --><#if val!=""
                                     ><#assign a532="["+val+"]"+"<BR>"
                          ></#if
                          ><#break
                  ></#list
  ></#if
></#macro



><#macro opisSerijske
><#assign opisS=""
      ><#assign exsist=false
      ><#list f200 as field
          ><#if field.ind1="0" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist             
            ><#if f532?exists 
               ><#list f532 as field
                   ><#assign val=""
                   ><#assign allSF="a" 
                   ><@field532 field/><#--
                   --><#if val!=""                            
                            ><#assign opisS=opisS+val
                   ></#if
                   ><#break
               ></#list
            ><#else 
                ><#list f200 as field
                    ><#if field.ind1="0"  
                        ><#assign val=""
                        ><#assign allSF="aefhi" 
                        ><@field200 field/><#--
                        --><#if val!=""                    
                                ><#assign opisS=opisS+val
                        ></#if
                        ><#break
                    ></#if
               ></#list 
           ></#if
      ><#elseif f200?exists 
           ><#list f200 as field               
                  ><#assign val=""
                  ><#assign allSF="aefhi" 
                  ><@field200 field/><#--
                  --><#if val!=""                    
                    ><#assign opisS=opisS+val
                  ></#if
                  ><#break              
            ></#list 
      ></#if 
><#if f011?exists 
   ><#list f011 as field
      ><#assign val=""
      ><#assign allSF="e" 
      ><@field011 field/><#--
      --><#if val!=""
          ><#if opisS!=""
            ><#assign opisS=opisS+".&nbsp;-&nbsp;ISSN&nbsp;"          
          ></#if       
          ><#assign opisS=opisS+val
      ></#if
      ><#break             
   ></#list
></#if 
></#macro




><#macro napomeneSerijska

><#assign napS=""

 ><#if recUtil.getSubfieldContent("856u")!=""
 	 	><#if napS=""                    
             ><#assign napS=napS+"<BR><BR>"
        ><#elseif napS?ends_with(".")
             ><#assign napS=napS+"&nbsp;-&nbsp;"
        ><#else
            ><#assign napS=napS+".&nbsp;-&nbsp;"
        ></#if             
      	><#assign napS = napS+"\x0414\x043E\x0441\x0442\x0443\x043F\x043D\x043E \x0438 \x043D\x0430:&nbsp;"+ recUtil.getSubfieldContent("856u")          
></#if

><#if f326?exists 
  
      ><#list f326 as field
                    
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field300 field/><#--
              --><#if val!=""                
                ><#if napS=""                    
                    ><#assign napS=napS+"<BR><BR>"
                ><#elseif napS?ends_with(".")
                    ><#assign napS=napS+"&nbsp;-&nbsp;"
                ><#else
                    ><#assign napS=napS+".&nbsp;-&nbsp;"
                ></#if                  
               ><#assign napS=napS+val
            ></#if              
      ></#list
></#if


><#if f300?exists 
  
      ><#list f300 as field
                    
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field300 field/><#--
              --><#if val!=""
                
                ><#if napS=""
                    
                    ><#assign napS=napS+"<BR><BR>"
                ><#elseif napS?ends_with(".")
                    ><#assign napS=napS+"&nbsp;-&nbsp;"
                ><#else
                    ><#assign napS=napS+".&nbsp;-&nbsp;"
                ></#if 
                 
               ><#assign napS=napS+val
            ></#if
              
      ></#list
 ></#if
 ><#if recUtil.getSubfieldContent("410a")!=""
     ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
 	 ><#assign napS = napS + "\x0408\x0435 \x043F\x043E\x0434\x0441\x0435\x0440\x0438\x0458\x0430:&nbsp;"+recUtil.getSubfieldContent("410a")
     ></#if	
     ><#if (recUtil.getSubfieldContent("410x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("410x")
	></#if  
  ><#if recUtil.getSubfieldContent("411a")!=""
     ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
 	 ><#assign napS = napS + "\x0418\x043C\x0430 \x043F\x043E\x0434\x0441\x0435\x0440\x0438\x0458\x0443:&nbsp;"+recUtil.getSubfieldContent("411a")
   ></#if
   ><#if (recUtil.getSubfieldContent("411x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("411x")
		></#if
    ><#if recUtil.getSubfieldContent("421a")!=""
     ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
 	 ><#assign napS = napS + "\x041F\x0440\x0438\x043B\x043E\x0437\x0438:&nbsp;"+recUtil.getSubfieldContent("421a")
   ></#if
     ><#if (recUtil.getSubfieldContent("421x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("421x")
		></#if
    ><#if recUtil.getSubfieldContent("422a")!=""
     ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
 	 ><#assign napS = napS + "\x041C\x0430\x0442\x0438\x0447\x043D\x0430 \x043F\x0443\x0431\x043B\x0438\x043A\x0430\x0446\x0438\x0458\x0430:&nbsp;"+recUtil.getSubfieldContent("422a")
   ></#if	
    ><#if (recUtil.getSubfieldContent("422x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("422x")
		></#if
 ><#if recUtil.getSubfieldContent("430a")!=""
     ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
 	 ><#assign napS = napS + "\x0408\x0435 \x043D\x0430\x0441\x0442\x0430\x0432\x0430\x043A:&nbsp;"+recUtil.getSubfieldContent("430a")
     ></#if	
      ><#if (recUtil.getSubfieldContent("430x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("430x")
		></#if
	><#if recUtil.getSubfieldContent("431a")!=""
		 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x0414\x0435\x043B\x0438\x043C\x0438\x0447\x043D\x043E \x0458\x0435 \x043D\x0430\x0441\x0442\x0430\x0432\x0430\x043A \x043F\x0443\x0431\x043B\x0438\x043A\x0430\x0446\x0438\x0458\x0435:&nbsp;"+recUtil.getSubfieldContent("431a")
	></#if
	><#if (recUtil.getSubfieldContent("431x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("431x")
		></#if	 
	><#if recUtil.getSubfieldContent("434a")!=""
		 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     	><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
    	 ></#if
	 	><#assign napS = napS + "\x041F\x0440\x0435\x0443\x0437\x0435\x043E \x0458\x0435:&nbsp;"+recUtil.getSubfieldContent("434a")
	></#if
	><#if (recUtil.getSubfieldContent("434x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("434x")
		></#if	
	><#if recUtil.getSubfieldContent("435a")!=""
		 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     	><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x0414\x0435\x043B\x0438\x043C\x0438\x0447\x043D\x043E \x0458\x0435 \x043F\x0440\x0435\x0443\x0437\x0435\x043E:&nbsp;"+recUtil.getSubfieldContent("435a")
	></#if	
	><#if (recUtil.getSubfieldContent("435x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("435x")
		></#if
	><#if recUtil.getSubfieldContent("436a")!=""
		><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     	><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
      ><#assign brojac=0
	 ><#assign lista=recUtil.getSubfieldsContent("436a")
	 ><#list lista as content
		 ><#if brojac=0
		 	><#assign napS = napS + "\x041D\x0430\x0441\x0442\x0430\x0458\x0435 \x0441\x043F\x0430\x0458\x0430\x045A\x0435\x043C:&nbsp;"+content
		 	><#assign brojac=brojac+1
		  ><#else 
		 	><#assign napS = napS + "&nbsp;\x0438&nbsp;"+content
		 ></#if
	 ></#list	 	 
	></#if	 
	><#if recUtil.getSubfieldContent("440a")!=""
	     ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     	><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     	></#if
	 	><#assign napS = napS + "\x041D\x0430\x0441\x0442\x0430\x0432\x0459\x0430 \x0441\x0435 \x043A\x0430\x043E:&nbsp;"+recUtil.getSubfieldContent("440a")
		><#if (recUtil.getSubfieldContent("440x")!="")     
	 		><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("440x")
		></#if
	 ></#if	
	 
	
	><#if recUtil.getSubfieldContent("441a")!=""
	   ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x0414\x0435\x043B\x0438\x043C\x0438\x0447\x043D\x043E \x0441\x0435 \x043D\x0430\x0441\x0442\x0430\x0432\x0459\x0430 \x043A\x0430\x043E:&nbsp;"+recUtil.getSubfieldContent("441a")	 
	></#if
	><#if (recUtil.getSubfieldContent("441x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("441x")
		></#if
	><#if recUtil.getSubfieldContent("444a")!=""
		 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     	><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x041F\x0440\x0435\x0443\x0437\x0438\x043C\x0430 \x0458\x0435:&nbsp;"+recUtil.getSubfieldContent("444a")	 
	></#if	
	><#if (recUtil.getSubfieldContent("444x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("444x")
		></#if	
	><#if recUtil.getSubfieldContent("445a")!=""
		 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     	><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x0414\x0435\x043B\x0438\x043C\x0438\x0447\x043D\x043E \x0458\x0435 \x043F\x0440\x0435\x0443\x0437\x0438\x043C\x0430:&nbsp;"+recUtil.getSubfieldContent("445a")	 
	></#if
	><#if (recUtil.getSubfieldContent("445x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("4450x")
		></#if
	><#if recUtil.getSubfieldContent("446a")!=""
	  ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if    
     ><#assign brojac=0
	 ><#assign lista=recUtil.getSubfieldsContent("446a")
	 ><#list lista as content
		 ><#if brojac=0
		 	><#assign napS = napS + "\x0414\x0435\x043B\x0438 \x0441\x0435 \x043D\x0430:&nbsp;"+content
		 	><#assign brojac=brojac+1
		  ><#else 
		 	><#assign napS = napS + "&nbsp;; \x0438 \x043D\x0430:&nbsp;"+content
		 ></#if
	 ></#list 	 
	></#if	 
	><#if recUtil.getSubfieldContent("447a")!=""	
		 ><#if napS?ends_with(".")
	     	><#assign napS = napS +"&nbsp;-&nbsp;"
	     ><#else
	     	><#assign napS = napS +".&nbsp;-&nbsp;"
	     ></#if
	     ><#assign brojac=0
	     ><#assign lista=recUtil.getSubfieldsContent("447a")
	 	 ><#list lista as content
	 	 ><#if brojac=0
		 	><#assign napS = napS + "\x0421\x043F\x0430\x0458\x0430 \x0441\x0435 \x0441\x0430:&nbsp;"+content
			 ><#assign brojac=brojac+1	
	     ><#elseif brojac=lista?size-1
	     	><#assign napS = napS + "&nbsp;\x0438 \x043D\x0430\x0441\x0442\x0430\x0458\x0435:&nbsp;"+content	 	
		 ><#else
		  	><#assign napS = napS + "&nbsp;\x0438 \x0441\x0430:&nbsp;"+content
		  	><#assign brojac=brojac+1
	 	 ></#if	
	 	 ></#list 
	></#if
	><#if recUtil.getSubfieldContent("452a")!=""
	 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x041D\x043E\x0432\x043E \x0438\x0437\x0434\x0430\x045A\x0435 \x043D\x0430 \x0434\x0440\x0443\x0433\x043E\x043C \x043C\x0435\x0434\x0438\x0458\x0443:&nbsp;"+recUtil.getSubfieldContent("452a")	 
	></#if
	><#if (recUtil.getSubfieldContent("452x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("452x")
		></#if
	><#if recUtil.getSubfieldContent("453a")!=""
	 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x0418\x043C\x0430 \x0438\x0437\x0434\x0430\x045A\x0065 \x043D\x0430 \x0434\x0440\x0443\x0433\x043E\x043C \x0458\x0435\x0437\x0438\x043A\x0443:&nbsp;"+recUtil.getSubfieldContent("453a")	 
	></#if
	><#if (recUtil.getSubfieldContent("453x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("453x")
		></#if
	><#if recUtil.getSubfieldContent("454a")!=""
	 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x0408\x0435 \x0438\x0437\x0434\x0430\x045A\x0435 \x043D\x0430 \x0434\x0440\x0443\x0433\x043E\x043C \x0458\x0435\x0437\x0438\x043A\x0443 - \x043E\x0440\x0438\x0433\x0438\x043D\x0430\x043B:&nbsp;"+recUtil.getSubfieldContent("454a")	 
	></#if
	><#if (recUtil.getSubfieldContent("454x")!="")     
	 	><#assign napS = napS + "&nbsp;=&nbsp;ISSN&nbsp;"+recUtil.getSubfieldContentx("454x")
		></#if
	><#if recUtil.getSubfieldContent("4810")!=""
	 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x041F\x043E\x0432\x0435\x0437\x0430\x043D\x043E \x0441\x0430:&nbsp;"+recUtil.getSubfieldContent("4810")	 
	></#if	
	><#if recUtil.getSubfieldContent("4820")!=""
	 ><#if napS?ends_with(".")
     	><#assign napS = napS +"&nbsp;-&nbsp;"
     ><#else
     	><#assign napS = napS +".&nbsp;-&nbsp;"
     ></#if
	 ><#assign napS = napS + "\x041F\x0440\x0438\x0432\x0435\x0437\x0430\x043D\x043E \x0443\x0437:&nbsp;"+recUtil.getSubfieldContent("4820")	 
	></#if
	
	
><#if napS!=""  && !napS?ends_with(".")
 ><#assign napS=napS+"."
></#if

></#macro


><#macro napomeneNeknjizna
><#assign nap=""
      ><#if f300?exists
               ><#list f300 as field
                       ><#assign val=""                        
                       ><#assign allSF="@" 
                       ><@field300 field/><#--       
                       --><#if val!=""
                                   ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if                                                                   
                                  ></#if
                                  ><#assign nap=nap+val      
                     ></#if
              ></#list
     ></#if
     ><#if f314?exists
             ><#list f314 as field
                    ><#assign val=""       
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if                                                                             
                                  ></#if
                                  ><#assign nap=nap+val
                   ></#if
          ></#list
   ></#if
   ><#if f320?exists 
         ><#list f320 as field
                 ><#assign val=""                      
                 ><#assign allSF="a"     
                 ><@field200 field/><#--
                 --><#if val!=""
                          ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                           
                                  ></#if
                                  ><#assign nap=nap+val
                ></#if
        ></#list
  ></#if
  ><#if f321?exists 
        ><#list f321 as field
                  ><#assign val=""     
                  ><#assign allSF="@" 
                  ><@field321 field/><#--
                  --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                            
                                  ></#if
                                  ><#assign nap=nap+val
                ></#if
       ></#list
  ></#if
  ><#if f322?exists 
       ><#list f322 as field
                   ><#assign val=""       
                   ><#assign allSF="@" 
                   ><@field300 field/><#--
                   --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                  ></#if
                                  ><#assign nap=nap+val
                  ></#if          
       ></#list
  ></#if
  ><#if f323?exists 
          ><#list f323 as field
                    ><#assign val=""        
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""

                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                          
                                  ></#if
                                  ><#assign nap=nap+val                               
                    ></#if
         ></#list
  ></#if
  ><#if f324?exists 
         ><#list f324 as field
                     ><#assign val=""       
                     ><#assign allSF="@" 
                     ><@field300 field/><#--
                     --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                                                           
                                  ></#if
                                  ><#assign nap=nap+val
                     ></#if          
        ></#list
  ></#if
  ><#if f326?exists 
         ><#list f326 as field
                    ><#assign val=""        
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val        
                    ></#if
        ></#list
  ></#if
  ><#if f327?exists 
         ><#list f327 as field
                    ><#assign val=""         
                    ><#assign allSF="a" 
                    ><@field327Neknjizna field/><#--
                    --><#if val!=""
                           ><#if nap!=""                                           
                             ><#assign nap=nap+"<BR>" 
                           ></#if
                         ><#assign nap=nap+val
                   ></#if
        ></#list
  ></#if
  
  ><#if f328?exists 
        ><#list f328 as field
                 ><#assign val=""       
                 ><#assign allSF="a" 
                 ><@field300 field/><#--
                 --><#if val!=""
                                  ><#if nap!=""

                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if                                 
                                             
                                  ></#if
                                  ><#assign nap=nap+val
               ></#if
       ></#list
  ></#if
  ><#if f330?exists 
        ><#list f330 as field
               ><#assign val=""        
               ><#assign allSF="@" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if nap!="" && !nap?ends_with(".")
            ><#assign nap=nap+"."
  ></#if  
  ><#if f373?exists 
        ><#list f373 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if f374?exists 
        ><#list f374 as field
               ><#assign val=""        
               ><#assign allSF="a" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  
  
  
  
  ><#if nap!="" && !nap?ends_with(".")
            ><#assign nap=nap+"."
  ></#if  
></#macro

 
><#macro addSpaces 
><#assign str=str+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"?substring(0,6*num)
></#macro

><#macro specGodista
><#assign godista=""
><#if godine?exists	   
	  ><#assign str="Godište"
	  ><#assign num=4
	  ><@addSpaces/><#--	  
    --><#assign str=str+"Godina"
      ><@addSpaces/><#--	  
    --><#assign str=str+"Broj"+"<BR>"
    ><#assign godista=str    
    ><#list godine as godina                  
       ><#assign str=godina.godiste
       ><#assign num=14-godina.godiste?length
       ><@addSpaces/><#--
     --><#assign godista=godista+str+godina.godina
        ><#assign str=godista
        ><#assign num=7
        ><@addSpaces/><#--
     --><#assign godista=str+godina.broj+"<BR>"                            
                  
    ></#list    
></#if
></#macro



><#macro brojUDC
><#assign brUDC=""
><#if f675?exists   
      ><#list f675 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!=""
                  ><#if brUDC!=""                     
                     ><#assign brUDC=brUDC+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign brUDC=brUDC+"\x0423\x0414\x041A:&nbsp;"   
                  ></#if
                  ><#assign brUDC=brUDC+val
              ></#if
      ></#list
     
></#if

></#macro

><#macro brojUDC3
><#assign brUDC3=""
><#assign brojacUDK=0
><#if f675?exists   
      ><#list f675 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!="" && brojacUDK<3
                  ><#assign brojacUDK=brojacUDK+1
                  ><#if brUDC3!=""                     
                     ><#assign brUDC3=brUDC3+"<BR>"                    
                  ></#if
                  ><#assign brUDC3=brUDC3+val
              ></#if
      ></#list
     
></#if

></#macro
><#macro brojUDCPrvi
><#assign brUDCprvi=""
><#if f675?exists   
      ><#list f675 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!=""                  
                  ><#assign brUDCprvi=brUDCprvi+val
                  ><#break
              ></#if
      ></#list
     
></#if

></#macro

><#macro brojUDCOstali
><#assign first=true
><#assign brUDCostali=""
><#if f675?exists 
  
      ><#list f675 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!=""
                  ><#if first
                       ><#assign first=false
                  ><#else     
                  		><#if brUDCostali!=""                     
                     		><#assign brUDCostali=brUDCostali+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                     	><#else
                     		><#assign brUDCostali=brUDCostali+"\x0423\x0414\x041A:&nbsp;"
                  		></#if
                  		><#assign brUDCostali=brUDCostali+val
                  ></#if		
              ></#if
      ></#list
     
></#if

></#macro
><#macro brojID
  ><#assign brID="" 
   
><#if f001?exists 
         ><#list f001 as field             
                 ><#assign val=""
                 ><#assign allSF="e" 
                 ><@field001 field/><#-- 
                 --><#if val!=""
                      
                      ><#assign brID="RN&nbsp;"+val                     
                 ></#if     
         ></#list
   ></#if
   ><#assign word=brID
  ><@rightAlign/><#--
  --><#assign brID=outB+brID+"<BR>"
></#macro
><#macro brojID1
  ><#assign brID="" 
   
><#if f001?exists 
         ><#list f001 as field             
                 ><#assign val=""
                 ><#assign allSF="e" 
                 ><@field001 field/><#-- 
                 --><#if val!=""
                      
                      ><#assign brID="RN&nbsp;"+val                     
                 ></#if     
         ></#list
   ></#if
   ><#assign word=brID
  ><@rightAlign/><#--
  --><#assign brID=outB+brID
></#macro
><#macro drugiAutor
   ><#assign brojacDA=0
   ><#assign drugiA=""
   ><#if f701?exists 
         ><#list f701 as field
             ><#if brojacDA<2 
                       ><#assign val=""
                       ><#assign allSF="ab" 
                       ><@field700 field/><#--
                       --><@toUpperFirst/><#-- 
                       --><#if val!=""
                             ><#assign brojacDA=brojacDA+1  
           		             ><#if drugiA!=""           		                 
           			             ><#assign drugiA=drugiA+"<BR>"
           		             ></#if	
           		             ><#assign drugiA=drugiA+brojacDA?string+".&nbsp;"+val           
                      ></#if 
              ></#if
         ></#list
   ></#if
></#macro
><#macro blank 
><#assign ost10=duz%10
><#assign ost100=duz%100-ost10
><#assign brB=ost10
><#assign outB=""


><#if ost10=1
       ><#assign outB=outB+"<sp>"
><#elseif ost10=2
       ><#assign outB=outB+"<sp><sp>"
><#elseif ost10=3
       ><#assign outB=outB+"<sp><sp><sp>"
><#elseif ost10=4
       ><#assign outB=outB+"<sp><sp><sp><sp>"
><#elseif ost10=5
       ><#assign outB=outB+"<sp><sp><sp><sp><sp>"
><#elseif ost10=6
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp>"
><#elseif ost10=7
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost10=8
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost10=9
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp>"
></#if
><#if ost100=10
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
        
><#elseif ost100=20
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=30 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=40
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=50 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=60 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=70 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=80 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=90      
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
></#if
></#macro




><#macro rightAlign 

><#assign desno=""
><#assign rec=""
><#assign first=true
><#list word?split("&nbsp;") as x
     ><#if first
         ><#assign rec=rec+x
         ><#assign first=false
     ><#else
         ><#assign rec=rec+x+" "
     ></#if  
></#list
><#assign len=rec?length

><#assign duz=brk-len



><@blank/><#--

--></#macro

><#macro predmOdred
><#assign po=""
><#if f600?exists   
      ><#list f600 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field600 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f601?exists   
      ><#list f601 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field601 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f602?exists   
      ><#list f602 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field602 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f605?exists   
      ><#list f605 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field605 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if

><#if f606?exists   
      ><#list f606 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f607?exists   
      ><#list f607 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f608?exists   
      ><#list f608 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f609?exists   
      ><#list f609 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f610?exists   
      ><#list f610 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ><#else
                     ><#assign po=po+"\x041F\x041E:&nbsp;"   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if

></#macro  

><#macro predmOdred1
><#assign po=""
><#if f600?exists   
      ><#list f600 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field600 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                  
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f601?exists   
      ><#list f601 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field601 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                  
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f602?exists   
      ><#list f602 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field602 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                  
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f605?exists   
      ><#list f605 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field605 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                    
                  ></#if
                   ><#assign po=po+val
              ></#if
      ></#list 
          
></#if

><#if f606?exists   
      ><#list f606 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                  
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f607?exists   
      ><#list f607 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                  
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f608?exists   
      ><#list f608 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                 
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f609?exists   
      ><#list f609 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                   
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if
><#if f610?exists   
      ><#list f610 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field606 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+"<BR>"
                  
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list 
          
></#if

></#macro  





><#macro signatura
 ><#assign sign=""
 ><#assign old=","
 ><#if primerci?exists               
      ><#list primerci as primerak 
           ><#assign val=primerak.signatura
           ><#if  val!="" && old?index_of(","+val+",")=-1
                 ><#assign old=old+val+","
                 ><#if sign!=""
                    ><#assign sign=sign+",&nbsp;"
                 ></#if   
                 ><#assign sign=sign+val
          ></#if       
      ></#list     
 ></#if  
 
></#macro

><#macro signatura1
 ><#assign sign=""
 ><#assign old=","
 ><#if primerci?exists               
      ><#list primerci as primerak 
           ><#assign val=primerak.signatura
           ><#if  val!="" && old?index_of(","+val+",")=-1
                 ><#assign old=old+val+","
                 ><#if sign!=""
                    ><#assign sign=sign+"<BR>"
                 ></#if   
                 ><#assign sign=sign+val
          ></#if       
      ></#list     
 ></#if  
 
></#macro

><#macro signaturaX
 ><#assign signX=""
 ><#assign old=","
 ><#if primerci?exists               
      ><#list primerci as primerak
           ><#assign val=primerak.signaturaI
           ><#if val="X" || val="XS"  
                    ><#assign val=primerak.signatura
                    ><#if old?index_of(","+val+",")=-1
                           ><#assign old=old+val+","
                           ><#if signX!=""
                                ><#assign signX=signX+",&nbsp;"
                           ></#if   
                           ><#assign signX=signX+val
                    ></#if 
           ></#if               
      ></#list     
 ></#if 
></#macro




><#macro autor1
 ><#assign val=""
 ><#assign aut=""
 ><#if f700?exists               
      ><#list f700 as field 
           ><#assign allSF="ab"     
           ><@field700 field/><#-- 
           --><@toUpperFirst/><#--
           --><#assign aut=val
           ><#break
      ></#list     
 ></#if 
></#macro   




><#macro naslov
 ><#assign nas=""
 
 ><#if f200?exists               
      ><#list f200 as field
           ><#assign val=""
           ><#assign allSF="a"     
           ><@field200 field/><#-- 
           --><#assign nas=val
           ><#break
      ></#list     
 ></#if   
  
></#macro

><#macro deoKnj
 ><#assign deo="" 
 ><#if f200?exists               
      ><#list f200 as field 
           ><#assign val=""
           ><#assign allSF="h"     
           ><@field200 field/><#-- 
           --><#assign deo=val
           ><#break
      ></#list     
 ></#if   
  
></#macro
><#macro naslovDeo
 ><#assign nasDeo=""
 
 ><#if f200?exists               
      ><#list f200 as field 
           ><#assign val=""
           ><#assign allSF="i"     
           ><@field200 field/><#-- 
           --><#assign nasDeo=val
           ><#break
      ></#list     
 ></#if   
  
></#macro
><#macro mesto
 ><#assign mes=""
 
 ><#if f210?exists               
      ><#list f210 as field 
           ><#assign val=""
           ><#assign allSF="a"     
           ><@field210 field/><#-- 
           --><#assign mes=val
           ><#break
      ></#list     
 ></#if   
  
></#macro




><#macro izdavac
 
 ><#if f210?exists               
      ><#list f210 as field 
           ><#assign allSF="c" 
           ><#assign val=""    
           ><@field210 field/><#-- 
           --><#break
      ></#list     
 ></#if  
 ><#assign open=-1
 ><#list val?split("(") as x
        ><#assign open=open+1
 ></#list  
 ><#assign close=-1
 ><#list val?split(")") as x
        ><#assign close=close+1
 ></#list  
 ><#if (open>close)
  ><#assign val=val+")"
 ></#if
 ><#assign izd=val
></#macro 
><#macro godIzd
 
 ><#if f210?exists               
      ><#list f210 as field 
           ><#assign allSF="d" 
           ><#assign val=""    
           ><@field210 field/><#-- 
           --><#break
      ></#list     
 ></#if 
 ><#assign open=-1
 ><#list val?split("(") as x
        ><#assign open=open+1
 ></#list  
 ><#assign close=-1
 ><#list val?split(")") as x
        ><#assign close=close+1
 ></#list  
 ><#if (open>close)
  ><#assign val=val+")"
 ></#if
 ><#assign god=val
></#macro


><#macro inventarni 
 ><#assign inv=""
 ><#assign sigle=""  
 
 ><#if primerci?exists               
      ><#list primerci as primerak 
           ><#assign val=primerak.invBroj
           ><#assign inventar=val
           ><#if inv!=""
             ><#assign inv=inv+",&nbsp;"
           ></#if  
           ><#assign inv=inv+val 
           ><#assign val=primerak.status 
           ><#if val="\x010D"
             ><#assign inv=inv+"!\x010D"
           ><#elseif val="9"
             ><#assign inv=inv+"!R"
           ></#if  
            
                      
      ></#list     
 ></#if  
 
></#macro
><#macro prilozi
><#assign firstPril=true
><#assign pril=""

><#if f421?exists 
      ><#list f421 as fieldSec
            ><#assign val=""             
              ><@field421 fieldSec /><#--
              --><#if val!=""                  
                ><#assign pril=pril+"<BR>--&nbsp;"+val                
              ></#if              
      ></#list
></#if
><#if f423?exists 
      ><#list f423 as fieldSec
            ><#assign val=""             
              ><@field421 fieldSec /><#--
              --><#if val!=""                  
                ><#assign pril=pril+"<BR>--&nbsp;"+val                
              ></#if              
      ></#list
></#if

></#macro

>