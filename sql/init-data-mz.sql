INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('+', 'Slobodno za razmenu', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('-', 'Deziderat', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('1', 'Naručeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('2', 'U obradi', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('3', 'U povezu', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('4', 'U reviziji', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('5', 'Preusmereno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('6', 'Oštećeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('7', 'Zagubljeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('8', 'Izgubljeno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('9', 'Otpisano', 2);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('A', 'Aktivno', 1);

INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('a', 'kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('m', 'poklon ministarstva');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('p', 'poklon');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('c', 'poklon čitalaca');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('o', 'opština Mali Zvornik');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('z', 'zamena za izgubljenu knjigu');


INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('m', 'meki');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('t', 'tvrdi');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('l', 'povez l');

INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('01', 'odrasli');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('02', 'dečje');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('03', 'zavičajno');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('04', 'periodika');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('05', 'novine');


INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('00','Monografske publikacije');

INSERT INTO Counters (counter_name, counter_value) VALUES ('recordid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('primerakid', 0); 
INSERT INTO Counters (counter_name, counter_value) VALUES ('godinaid', 0);  
INSERT INTO Counters (counter_name, counter_value) VALUES ('sveskaid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('RN', 0);

INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (1,'<?xml version="1.0"?>
<process-type name="Monografske - kompletna obrada" pubType="1">
 <initial-subfield name="0017"/>
 <initial-subfield name="001a" defaultValue="i" />
 <initial-subfield name="001b" defaultValue="a" />
 <initial-subfield name="001c" defaultValue="m" />
 <initial-subfield name="001d" defaultValue="0" />
 <initial-subfield name="001e"/>
 <initial-subfield name="001f"/>
 <initial-subfield name="010a"/>
 <initial-subfield name="100b"/>
 <initial-subfield name="100c"/>
 <initial-subfield name="100h" defaultValue="scr" />
 <initial-subfield name="101a"/>
 <initial-subfield name="101c"/>
 <initial-subfield name="102a"/>
 <initial-subfield name="105a"/>
 <initial-subfield name="105b"/>
 <initial-subfield name="200a"/>
 <initial-subfield name="200e"/>
 <initial-subfield name="200f"/>
 <initial-subfield name="200g"/>
 <initial-subfield name="200h"/>
 <initial-subfield name="200i"/>
 <initial-subfield name="205a"/>
 <initial-subfield name="210a"/>
 <initial-subfield name="210c"/>
 <initial-subfield name="210d"/>
 <initial-subfield name="215a"/>
 <initial-subfield name="215e"/>
 <initial-subfield name="215c"/>
 <initial-subfield name="215d"/>
 <initial-subfield name="225a"/>
 <initial-subfield name="225e"/>
 <initial-subfield name="225f"/>
 <initial-subfield name="225h"/>
 <initial-subfield name="225i"/>
 <initial-subfield name="225v"/>
 <initial-subfield name="225x"/>
 <initial-subfield name="300a"/>
 <initial-subfield name="610a"/>
 <initial-subfield name="675a"/>
 <initial-subfield name="7004" defaultValue="070" />
 <initial-subfield name="700a"/>
 <initial-subfield name="700b"/>
 <initial-subfield name="7014" defaultValue="071" />
 <initial-subfield name="701a"/>
 <initial-subfield name="701b"/>
 <initial-subfield name="7024"/>
 <initial-subfield name="702a"/>
 <initial-subfield name="702b"/>
 <initial-subfield name="710a"/>
 <initial-subfield name="710b"/>
 <initial-subfield name="710d"/>
 <initial-subfield name="710f"/>
 <initial-subfield name="710e"/>
 <initial-subfield name="992b"/>
  <mandatory-subfield name="100c"/>
  <mandatory-subfield name="200a"/>
</process-type>
');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?>
<process-type name="Serijske - kompletna obrada" pubType="2">  
  <initial-subfield name="0017"/>
  <initial-subfield name="001c" defaultValue="s" />
  <initial-subfield name="001d" defaultValue="0" />
  <initial-subfield name="011e"/>
  <initial-subfield name="100b" defaultValue="a" />
  <initial-subfield name="100c"/>
  <initial-subfield name="101a"/>
  <initial-subfield name="102a"/>
  <initial-subfield name="110a"/>
  <initial-subfield name="110b"/>
  <initial-subfield name="110c"/>
  <initial-subfield name="200a"/>
  <initial-subfield name="200f"/>
  <initial-subfield name="200g"/>
  <initial-subfield name="200h"/>
  <initial-subfield name="200i"/>
  <initial-subfield name="205a"/>
  <initial-subfield name="210a"/>
  <initial-subfield name="300a"/>
  <initial-subfield name="207a"/>
  <initial-subfield name="210c"/>
  <initial-subfield name="210d"/>
  <initial-subfield name="326a"/>
  <initial-subfield name="992b"/>
  <initial-subfield name="702a"/>
  <initial-subfield name="702b"/>
  <initial-subfield name="215d"/>
  <initial-subfield name="100d" defaultValue="9999" />
  <initial-subfield name="7024"/>
  <mandatory-subfield name="100c"/>
  <mandatory-subfield name="200a"/>
 </process-type>');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimi�','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');

