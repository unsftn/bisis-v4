INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('k', '');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('p', '');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('z', '');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('s', '');

INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('a', '', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('p', '', 0);

INSERT INTO Dostupnost (dostupnost_ID, dostupnost_Opis) VALUES ('c', 'Citaonicki primerak'); 
INSERT INTO Dostupnost (dostupnost_ID, dostupnost_Opis) VALUES ('4', 'Slobodno za izdavanje'); 

INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('07', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('09', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('0D', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('0Z', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('05', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('06', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('96', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('03', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('02', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('04', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('010', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('P', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('08', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('01', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('E', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('M', '');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('I', '');


INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('00','');

INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('1', 'Format I');
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('2', 'Format II'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('3', 'Format III'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('4', 'Format IV'); 

INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('00', 'Cela biblioteka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('01', 'Prvo odeljenje');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('02', 'Drugo odeljenje');

INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('00', 'Cela biblioteka');

INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('a', 'Bibliofilski povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('b', 'Brosirano (tvrde korice, meke korice)'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('c', 'Fascikl povez'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('d', 'Italijanski povez'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('f', 'Platno'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('g', 'Poluplatno'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('h', 'Polukoza'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('i', 'Spiralni povez'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('k', 'Vestacka koza'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('l', 'Ostali povezi'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('t', 'Tvrdi povez'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('m', 'Meki povez'); 

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
 <initial-subfield name="215c"/>
 <initial-subfield name="215d"/>
 <initial-subfield name="215e"/>
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
 <initial-subfield name="710e"/>
 <initial-subfield name="710f"/>
 <initial-subfield name="992b"/>
  <mandatory-subfield name="100c"/>
  <mandatory-subfield name="200a"/>
</process-type>
');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?>
<process-type name="Serijske - kompletna obrada" pubType="2">
 <initial-subfield name="0017"/>
 <initial-subfield name="001c"/>
 <initial-subfield name="001d"/>
 <initial-subfield name="011a"/>
 <initial-subfield name="100b"/>
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
  <mandatory-subfield name="100c"/>
  <mandatory-subfield name="200a"/>
</process-type>
');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimiæ','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');



