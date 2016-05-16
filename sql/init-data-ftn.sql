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

INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('U', 'Podlokacija U');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('D', 'Diplomski radovi');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('A', 'Podlokacija A');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('M', 'Podlokacija M');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Č', 'Podlokacija Č');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('D/I', 'diplomski radovi prvog stepena');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('D/B', 'diplomski-bachelor radovi');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('D/M', 'diplomski-master radovi');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('X', 'podlokacija X');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('L', 'podlokacija L');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('K', 'podlokacija K');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Z', 'podlokacija Z');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('S', 'podlokacija S');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('E-', 'podlokacija E-');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('MR', 'podlokacija MR');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('DR', 'podlokacija DR');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('LR', 'podlokacija LR');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('SR', 'podlokacija SR');

INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('1', 'Naruceno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('2', 'U obradi', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('3', 'U povezu', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('4', 'U reviziji', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('5', 'Preusmereno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('6', 'Osteceno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('7', 'Zagubljeno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('8', 'Izgubljeno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('9', 'Otpisano', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('a', 'aktivan', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('p', 'pasivan', 0);

INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('1', 'Format I');
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('2', 'Format II'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('3', 'Format III'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('4', 'Format IV'); 

INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('b', 'biblioteka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('m', 'magacin');


INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('a', 'nepoznato');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('c', 'nepoznato');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('d', 'diplomski');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('mr', 'magistarski');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('lr', 'legat');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('m', 'monografije');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('k', 'katalozi');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('cd', 'cd-ovi');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('dr', 'doktorske');

INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('00','inventarna knjiga 00');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('01','diplomski radovi');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('02','monografije i udžbenici');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('03','specijalistički, magistarski, doktorski radovi');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('04','časopisi, zbornici');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('05','legat');  

INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('r', 'Razmena'); 
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('o', 'Obavezni primerak'); 
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('e', 'Stari (zateceni) fond'); 
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('f', 'Sopstvena izdanja'); 
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('k', 'Kupovina'); 
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('p', 'Poklon'); 
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('m', 'Poklon ministarstva'); 

INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('1', 'Vremenski ograničena dostupnost - do 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('2', 'Vremenski ograničena dostupnost - 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('3', 'Vremenski ograničena dostupnost - 14 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('4', 'Delimina dostupnost - čitaonica');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('5', 'Delimična dostupnost - sa dozvolom autora');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('6', 'Sadržaj dokumenta nedostupan (poseban tretman)');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('7', 'Potpuna nedostupnost (arhivski primerak ...)');

INSERT INTO Counters (counter_name, counter_value) VALUES ('recordid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('RN', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('primerakid', 0); 
INSERT INTO Counters (counter_name, counter_value) VALUES ('godinaid', 0);  
INSERT INTO Counters (counter_name, counter_value) VALUES ('sveskaid', 0);  

INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (1,'<?xml version="1.0"?><process-type name="Monografske - kompletna obrada" pubType="1"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="010a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><initial-subfield name="7004"/><initial-subfield name="700a"/><initial-subfield name="700b"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?><process-type name="Serijske - kompletna obrada" pubType="2"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="011a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="110a"/><initial-subfield name="110b"/><initial-subfield name="110c"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimiæ','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
