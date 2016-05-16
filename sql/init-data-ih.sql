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
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('9', 'Otpisano', 0);

INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('a', 'kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('b', 'Razmena');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('c', 'Poklon');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('d', 'Obavezni primerak');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('e', 'Zatečeni (stari) fond');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('f', 'Sopstvena izdanja');

INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('00', 'Cela biblioteka');


INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('D', 'Podlokacija D');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Dd', 'Podlokacija Dd');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('MrHe', 'Podlokacija MrHe');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('DrHe', 'Podlokacija DrHe');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('SpHe', 'Podlokacija SpHe');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('He', 'Podlokacija He');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Hez', 'Podlokacija Hez');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('H', 'Podlokacija H');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Hes', 'Podlokacija Hes');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('VPŠ', 'Podlokacija VPŠ');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Mh', 'Podlokacija Mh');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Č', 'Podlokacija Č');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Čhe', 'Podlokacija Čhe');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Čz', 'Podlokacija Čz');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Čl', 'Podlokacija Čl');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Čs', 'Podlokacija Čs');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Hep', 'Podlokacija Hep');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Hs', 'Podlokacija Hs');

INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('IO', 'Interna oznaka');

INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('00','Inventarnaj knjiga 00');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('10','Inventarnaj knjiga 10');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('20','Inventarnaj knjiga 20');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('70','Inventarnaj knjiga 70');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('79','Inventarnaj knjiga 79');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('80','Inventarnaj knjiga 80');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('02','Inventarnaj knjiga 02');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('01','Inventarnaj knjiga 01');

INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('a', 'Bibliofilski povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('b', 'Broširano (tvrde korice, meke korice)');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('c', 'Fascikl povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('d', 'Italijanski povez');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('f', 'Platno');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('g', 'Poluplatno');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('h', 'Polukoža');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('i', 'Spiralni povez (npr. kalendar)');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('k', 'Veštačka koža');
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('l', 'Ostali povezi (nove vrste, nenaved. pov, moderni)');

INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('1', 'Format I');
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('2', 'Format II'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('3', 'Format III'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('4', 'Format IV'); 

INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('1', 'Vremenski ograničena dostupnost - do 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('2', 'Vremenski ograničena dostupnost - 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('3', 'Vremenski ograničena dostupnost - 14 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('4', 'Delimina dostupnost - čitaonica');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('5', 'Delimična dostupnost - sa dozvolom autora');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('6', 'Sadržaj dokumenta nedostupan (poseban tretman)');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('7', 'Potpuna nedostupnost (arhivski primerak ...)');

INSERT INTO Counters (counter_name, counter_value) VALUES ('recordid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('primerakid', 0); 
INSERT INTO Counters (counter_name, counter_value) VALUES ('godinaid', 0);  
INSERT INTO Counters (counter_name, counter_value) VALUES ('sveskaid', 0);

INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (1,'<?xml version="1.0"?><process-type name="Monografske - kompletna obrada" pubType="1"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="010a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><initial-subfield name="7004"/><initial-subfield name="700a"/><initial-subfield name="700b"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?><process-type name="Serijske - kompletna obrada" pubType="2"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="011a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="110a"/><initial-subfield name="110b"/><initial-subfield name="110c"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimi�','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
