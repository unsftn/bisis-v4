SET SESSION sql_mode='NO_AUTO_VALUE_ON_ZERO';
insert into configs (name, text) values ('circ-options', '<?xml version="1.0" encoding="UTF-8"?>
<opt:options xmlns:opt="options">
  <client mac="default">
    <general>
      <nonCtlgNo>false</nonCtlgNo>
      <blockedCard>true</blockedCard>
      <autoReturn>true</autoReturn>
      <defaultZipCity>true</defaultZipCity>
      <defaultCity>Novi Sad</defaultCity>
      <defaultZip>21000</defaultZip>
      <fontSize>12</fontSize>
      <maximize>false</maximize>
      <lookAndFeel>default</lookAndFeel>
      <theme>com.jgoodies.looks.plastic.theme.LightGray</theme>
      <location>0</location>
    </general>
    <userid>
      <length>11</length>
      <prefix>false</prefix>
      <prefixLength>2</prefixLength>
      <defaultPrefix>1</defaultPrefix>
      <separator>false</separator>
      <separatorSign>/</separatorSign>
      <inputUserid>1</inputUserid>
    </userid>
    <ctlgno>
      <lengthCtlgno>11</lengthCtlgno>
      <locationCtlgno>true</locationCtlgno>
      <locationLength>2</locationLength>
      <defaultLocation>0</defaultLocation>
      <book>true</book>
      <bookLength>2</bookLength>
      <defaultBook>0</defaultBook>
      <separators>true</separators>
      <separator1>/</separator1>
      <separator2>-</separator2>
      <inputCtlgno>2</inputCtlgno>
    </ctlgno>
    <revers>
      <libraryName>Biblioteka Departmana za matematiku i informatiku</libraryName>
      <libraryAddress>Biblioteka Departmana za fiziku</libraryAddress>
      <selected>true</selected>
      <height>1</height>
      <width>1</width>
      <rowCount>1</rowCount>
      <count>1</count>
    </revers>
  </client>
</opt:options>');

insert into configs (name, text) values ('circ-validator', '<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE form-validation PUBLIC "-//Apache Software Foundation//DTD Commons Validator Rules Configuration 1.3.0//EN" "http://jakarta.apache.org/commons/dtds/validator_1_3_0.dtd">
<form-validation>
  <global>
    <validator name="required" classname="com.gint.app.bisis4.client.circ.validator.Validator" method="validateRequired" methodParams="java.lang.Object, org.apache.commons.validator.Field" msg="required.field"/>
    <validator name="intorblank" classname="com.gint.app.bisis4.client.circ.validator.Validator" method="validateIntOrBlank" methodParams="java.lang.Object, org.apache.commons.validator.Field" msg="int.field"/>
    <validator name="positiveorblank" classname="com.gint.app.bisis4.client.circ.validator.Validator" method="validatePositiveOrBlank" methodParams="java.lang.Object, org.apache.commons.validator.Field" msg="positive.field"/>
    <validator name="doubleorblank" classname="com.gint.app.bisis4.client.circ.validator.Validator" method="validateDoubleOrBlank" methodParams="java.lang.Object, org.apache.commons.validator.Field" msg="double.field"/>
    <validator name="emailorblank" classname="com.gint.app.bisis4.client.circ.validator.Validator" method="validateEmailOrBlank" methodParams="java.lang.Object, org.apache.commons.validator.Field" msg="invalid.email"/>
    <validator name="dateorblank" classname="com.gint.app.bisis4.client.circ.validator.Validator" method="validateDateOrBlank" methodParams="java.lang.Object, org.apache.commons.validator.Field" msg="invalid.date"/>
    <validator name="userorblank" classname="com.gint.app.bisis4.client.circ.validator.Validator" method="validateUserIdOrBlank" methodParams="java.lang.Object, org.apache.commons.validator.Field" msg="invalid.userid"/>
  </global>
  <formset>
    <form name="userData">
      <field property="firstName" depends="required">
        <arg key="userData.firstname.displayname"/>
      </field>
      <field property="lastName" depends="required">
        <arg key="userData.lastname.displayname"/>
      </field>
      <field property="parentName" depends="required">
        <arg key="userData.parentname.displayname"/>
      </field>
      <field property="address" depends="required">
        <arg key="userData.address.displayname"/>
      </field>
      <field property="zip" depends="intorblank,required">
        <arg key="userData.zip.displayname"/>
      </field>
      <field property="city" depends="required">
        <arg key="userData.city.displayname"/>
      </field>
      <field property="phone" depends="">
        <arg key="userData.phone.displayname"/>
      </field>
      <field property="email" depends="emailorblank">
        <arg key="userData.email.displayname"/>
      </field>
      <field property="tmpAddress" depends="">
        <arg key="userData.tmpaddress.displayname"/>
      </field>
      <field property="tmpZip" depends="intorblank">
        <arg key="userData.tmpzip.displayname"/>
      </field>
      <field property="tmpCity" depends="">
        <arg key="userData.tmpcity.displayname"/>
      </field>
      <field property="tmpPhone" depends="">
        <arg key="userData.tmpphone.displayname"/>
      </field>
      <field property="jmbg" depends="">
        <arg key="userData.jmbg.displayname"/>
      </field>
      <field property="docNo" depends="">
        <arg key="userData.docno.displayname"/>
      </field>
      <field property="docCity" depends="">
        <arg key="userData.doccity.displayname"/>
      </field>
      <field property="country" depends="">
        <arg key="userData.country.displayname"/>
      </field>
      <field property="title" depends="">
        <arg key="userData.title.displayname"/>
      </field>
      <field property="occupation" depends="">
        <arg key="userData.occupation.displayname"/>
      </field>
      <field property="organization" depends="">
        <arg key="userData.organization.displayname"/>
      </field>
      <field property="eduLvl" depends="">
        <arg key="userData.edulvl.displayname"/>
      </field>
      <field property="classNo" depends="">
        <arg key="userData.classno.displayname"/>
      </field>
      <field property="indexNo" depends="">
        <arg key="userData.indexno.displayname"/>
      </field>
      <field property="note" depends="">
        <arg key="userData.note.displayname"/>
      </field>
      <field property="interests" depends="">
        <arg key="userData.interests.displayname"/>
      </field>
      <field property="languages" depends="">
        <arg key="userData.language.displayname"/>
      </field>
      <field property="dupDate" depends="">
        <var>
          <var-name>datePattern</var-name>
          <var-value>dd.MM.yyyy</var-value>
        </var>
        <arg key="userData.dupdate.displayname"/>
      </field>
      <field property="dupNo" depends="intorblank">
        <arg key="userData.dupno.displayname"/>
      </field>
    </form>
    <form name="mmbrship">
      <field property="userID" depends="userorblank,required">
        <arg key="mmbrship.userid.displayname"/>
      </field>
      <field property="mmbrshipDate" depends="required">
        <var>
          <var-name>datePattern</var-name>
          <var-value>dd.MM.yyyy</var-value>
        </var>
        <arg key="mmbrship.mmbrshipdate.displayname"/>
      </field>
      <field property="mmbrshipUntilDate" depends="required">
        <var>
          <var-name>datePattern</var-name>
          <var-value>dd.MM.yyyy</var-value>
        </var>
        <arg key="mmbrship.mmbrshipuntildate.displayname"/>
      </field>
      <field property="mmbrshipCost" depends="">
        <arg key="mmbrship.mmbrshipcost.displayname"/>
      </field>
      <field property="mmbrshipReceiptId" depends="">
        <arg key="mmbrship.mmbrshipreceiptid.displayname"/>
      </field>
      <field property="mmbrType" depends="required">
        <arg key="mmbrship.mmbrtype.displayname"/>
      </field>
      <field property="userCateg" depends="required">
        <arg key="mmbrship.usercateg.displayname"/>
      </field>
    </form>
  </formset>
</form-validation>');

insert into warning_types (id, name, wtext) values (0, 'Opomena', '<war:root xmlns:war="warning"><opomena><zaglavlje><naziv>Biblioteka grada Beograda</naziv><biblioteka>BIBLIOTEKA GRADA BEOGRADA</biblioteka><ogranak>Ogranak </ogranak><sifra>Šifra: 01</sifra><adresa>Knez Mihailova 56</adresa><mesto>11000 Beograd</mesto><bropomenetext/><bropomene/><naslov>OPOMENA</naslov><roktext>rok vraćanja:</roktext></zaglavlje><body><textiznad>Uvidom u našu evidenciju ustanovili smo da niste u predviđenom roku vratili pozajmljene knjigu-e:</textiznad><tabelazg><rbr>R.br.</rbr><naslov>Naslov</naslov><autor>Autor</autor><invbroj>Inv.broj</invbroj><signatura>Signatura</signatura><brdana>Br.dana prekoračenja</brdana></tabelazg><textispod>Molimo Vas da svoju obavezu isputnite u roku od 3 (tri) dana kako ne bi došlo do primene drugih predviđenih mera, kao i da prilikom vraćanja knjiga u Biblioteku grada Beograda, ul. Knez Mihailova 56, izvršite obavezu nadoknade troškova u iznosu:</textispod><nadoknada1>nadoknada za prekoračenje roka dnevno po svakoj knjizi:</nadoknada1><nadoknada2>nadoknada za troškove opomene:</nadoknada2><cena1>10</cena1><cena2>100</cena2><din>din.</din><dodatuma>Ukupno do</dodatuma><napomena>Napomena: Obavezno poneti i ovu opomenu.</napomena></body><podaci><prezime/><ime/><roditelj/><imeroditelja/><adresa/><mesto/><zip/><useridtext>br. članske karte:</useridtext><userid/><docno/><docmesto/><jmbg/></podaci><footer><pojedinacno/><trostrukotext/><biblioteka/><direktor/><ime/></footer></opomena><cirilica>0</cirilica></war:root>');

insert into warning_types (id, name, wtext) values (1, 'Opomena pred utuženje', '<war:root xmlns:war="warning"><opomena><zaglavlje><naziv>Библиотека града Београда</naziv><biblioteka>БИБЛИОТЕКА ГРАДА БЕОГРАДА</biblioteka><ogranak>Огранак</ogranak><sifra>Шифра: 01</sifra><adresa>Кнез Михаилова 56</adresa><mesto>11000 Београд</mesto><bropomenetext>Бр. опомене:</bropomenetext><bropomene/><naslov>ОПОМЕНА ПРЕД УТУЖЕЊЕ</naslov><roktext>рок враћања:</roktext></zaglavlje><body><textiznad>Увидом у нашу евиденцију установили смо да нисте у предвиђеном року вратили позајмљене књигу-е:</textiznad><tabelazg><rbr>r.br.</rbr><naslov>Naslov</naslov><autor>Autor</autor><invbroj>inv. br.</invbroj><signatura>Signatura</signatura><brdana>br. dana prekoračenja</brdana></tabelazg><textispod>Молимо вас да своју обавезу испуните у року од 3 (три) дана како не би дошло до примене других предвиђених мера, као и да приликом враћања књига у Библиотеку града Београда, ул. Кнез Михаилова 56, извршите обавезу надокнаде трошкова у износу:</textispod><nadoknada1>надокнада за прекорачење рока дневно по свакој књизи:</nadoknada1><nadoknada2>надокнада за трошкове опомене:</nadoknada2><cena1>10</cena1><cena2>200</cena2><din>дин.</din><dodatuma>Укупно до:</dodatuma><napomena/></body><podaci><prezime/><ime/><roditelj>roditelj:</roditelj><imeroditelja/><adresa/><mesto/><zip/><useridtext>br. članske karte:</useridtext><userid/><docno>br. lične karte:</docno><docmesto>mesto izdavanja:</docmesto><jmbg>JMBG:</jmbg></podaci><footer><pojedinacno>Појединачна вредност књига:</pojedinacno><trostrukotext>Трострука вредност књига:</trostrukotext><biblioteka>Библиотека града Београда</biblioteka><direktor>Директор</direktor><ime>Јован Радуловић</ime></footer></opomena><cirilica>0</cirilica></war:root>');

insert into warning_types (id, name, wtext) values (2, 'Opomena pred utuženje - treća lica', '<war:root xmlns:war="warning"><opomena><zaglavlje><naziv>Библиотека града Београда</naziv><biblioteka>БИБЛИОТЕКА ГРАДА БЕОГРАДА</biblioteka><ogranak>Огранак</ogranak><sifra>Шифра: 01</sifra><adresa>Кнез Михаилова 56</adresa><mesto>11000 Београд</mesto><bropomenetext>Бр. опомене:</bropomenetext><bropomene/><naslov>ОПОМЕНА ПРЕД УТУЖЕЊЕ</naslov><roktext>рок  враћања:</roktext></zaglavlje><body><textiznad>Увидом у нашу евиденцију установили смо да нисте у предвиђеном року вратили позајмљене књигу-е:</textiznad><tabelazg><rbr>r.br..</rbr><naslov>Naslov</naslov><autor>Autor</autor><invbroj>Inv. br.</invbroj><signatura>Signatura</signatura><brdana>Br. dana prekoračenja</brdana></tabelazg><textispod>Молимо вас да своју обавезу испуните у року од 3 (три) дана како не би дошло до примене других предвиђених мера, као и да приликом враћања књига у Библиотеку града Београда, ул. Кнез Михаилова 56, извршите обавезу надокнаде трошкова у износу:</textispod><nadoknada1>надокнада за прекорачење рока дневно по свакој књизи:</nadoknada1><nadoknada2>надокнада за трошкове опомене:</nadoknada2><cena1>10</cena1><cena2>200</cena2><din>din.</din><dodatuma>Укупно до:</dodatuma><napomena/></body><podaci><prezime/><ime/><roditelj>roditelj:</roditelj><imeroditelja/><adresa/><mesto/><zip/><useridtext>br. članske karte:</useridtext><userid/><docno>br.lične karte:</docno><docmesto>mesto izdavanja:</docmesto><jmbg>JMBG:</jmbg></podaci><footer><pojedinacno/><trostrukotext>Трострука вредност књига:</trostrukotext><biblioteka>Библиотека града Београда</biblioteka><direktor>Директор</direktor><ime>Јован Радуловић</ime></footer></opomena><cirilica>0</cirilica></war:root>');
