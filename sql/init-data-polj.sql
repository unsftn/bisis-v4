INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('III', 'Format III');
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('II', 'Format II');
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('IIII', 'Format IIII');


INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('k', 'Kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('p', 'Poklon');

INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('a', 'Status a', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('r', 'Status r', 0);


INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('D', 'Podlokacija D');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('S', 'Podlokacija S');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('B', 'Podlokacija B');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('III-D', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('BG', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('E', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('CA', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('P', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('PR', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('R', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('T', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('U', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('UN', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('V', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('VET', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('VIV', 'nepoznato');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Z', 'nepoznato');


INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('00','Monografske publikacije');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('01','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('02','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('03','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('04','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('05','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('06','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('07','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('08','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('09','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('10','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('11','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('12','nepoznata');
INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('13','Serijske publikacije');

INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('A1', 'Interna oznaka A1');

INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('00', 'Cela biblioteka');

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
INSERT INTO Counters (counter_name, counter_value) VALUES ('RN', 0);

INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (1,'<?xml version="1.0"?><process-type name="Monografske - kompletna obrada" pubType="1"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="010a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><initial-subfield name="7004"/><initial-subfield name="700a"/><initial-subfield name="700b"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?><process-type name="Serijske - kompletna obrada" pubType="2"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="011a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="110a"/><initial-subfield name="110b"/><initial-subfield name="110c"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimi�','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
