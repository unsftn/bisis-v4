INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('1', 'Naruceno', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('2', 'U obradi', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('3', 'U povezu', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('4', 'U reviziji', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('5', 'Preusmereno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('6', 'Osteceno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('7', 'Zagubljeno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('8', 'Izgubljeno', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('9', 'Otpisano', 0); 
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('A', 'Aktivno', 0);

INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('a', 'kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('b', 'Razmena');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('c', 'Poklon');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('d', 'Obavezni primerak');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('e', 'Zatečeni (stari) fond');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('f', 'Sopstvena izdanja');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('o', 'Nacin nabavke o');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('z', 'Nacin nabavke z');

INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('СП', 'Interna oznaka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ПО', 'Interna oznaka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ДО', 'Interna oznaka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ЗО', 'Interna oznaka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ôÆ', 'Interna oznaka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('МЈ', 'Interna oznaka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('сп', 'Interna oznaka');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('БК', 'Interna oznaka Banja');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ЛЕ', 'Interna oznaka Lešnica');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ЗО', 'Interna oznaka Lešnica');

INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('00','Monografske publikacije');

INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('A', 'zavičajni autor');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('S', 'autor koji je poklonio knjigu');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('L', 'štampano ili izdato u Loznici');

INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('I', 'Format I');
INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('II', 'Format II');
INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('III', 'Format III');
INSERT INTO SigFormat (SigFormat_ID, Format_opis) VALUES ('fot', 'Format folio');

INSERT INTO Dostupnost (dostupnost_ID, dostupnost_Opis) VALUES ('c', 'Citaonicki primerak'); 
INSERT INTO Dostupnost (dostupnost_ID, dostupnost_Opis) VALUES ('4', 'Slobodno za izdavanje');

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

INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('00', 'Loznica');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('01', 'Banja');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('02', 'Lesnica');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('03', 'Zavicajno');

INSERT INTO Counters (counter_name, counter_value) VALUES ('recordid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('primerakid', 0); 
INSERT INTO Counters (counter_name, counter_value) VALUES ('godinaid', 0);  
INSERT INTO Counters (counter_name, counter_value) VALUES ('sveskaid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('RN', 0);

INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (1,'<?xml version="1.0"?><process-type name="Monografske - kompletna obrada" pubType="1"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="010a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><initial-subfield name="7004"/><initial-subfield name="700a"/><initial-subfield name="700b"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?><process-type name="Serijske - kompletna obrada" pubType="2"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="011a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="110a"/><initial-subfield name="110b"/><initial-subfield name="110c"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimi�','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
