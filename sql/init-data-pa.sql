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
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('n', '***'); 
INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('p', '***'); 

INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('М', 'prvi primerak');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('З', 'drugi primerak');
INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('Р', 'raritet');


INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('S', 'Slobodno', 1);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('Z', 'Zauzeto', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('+', 'Slobodno za razmenu', 0);
INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('-', 'Deziderat', 0);
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

INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('1', 'Format I');
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('2', 'Format II'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('3', 'Format III'); 
INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('4', 'Format IV'); 

INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ср', 'srpski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('e', 'engleski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('м', 'makedonski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('р', 'rusinski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('рс', 'ruski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('n', 'nemački');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ma', 'mađarski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('ru', 'rumunski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('sl', 'slovački');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('s', 'slovenački');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('grč', 'grčki');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('i', 'italijanski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('l', 'latinski');
INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('f', 'francuski');



INSERT INTO Counters (counter_name, counter_value) VALUES ('recordid', 0);
INSERT INTO Counters (counter_name, counter_value) VALUES ('primerakid', 0); 
INSERT INTO Counters (counter_name, counter_value) VALUES ('godinaid', 0);  
INSERT INTO Counters (counter_name, counter_value) VALUES ('sveskaid', 0);  
INSERT INTO Counters (counter_name, counter_value) VALUES ('RN', 0);  

INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('01', 'Đura Daničić, Dunavska 1, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('02', 'Stevan Sremac, Narodnih heroja 19, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('03', 'Petefi Šandor, Jožefa Atile 16, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('04', 'Toša Trifunov, Kralja Petra I 1, Begeč');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('05', 'Kosta Trifković, Braće Mogin 2, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('06', 'Jovan Jovanović Zmaj, Kralja Petra I 1, Sremska Kamenica');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('07', 'Dečje odelenje, Dunavska 1, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('08', 'Vidovdansko naselje');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('09', 'Petar Kočić, Poslovni centar, Veternik');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('10', 'Milica Stojadinović Srpkinja, Vidovdanska 17, Bukovac');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('11', 'Đorđe Aracki, Zmaj Jovina 17, Ledinci');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('12', 'Majur');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('13', 'Mihal Babinka, Slovačka 49, Kisač');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('14', 'Endre Adi, Vojvođanska 73, Budisava');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('15', '7. jul');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('16', 'Nikola Tesla, Vojvode Putnika 9, Stepanovićevo');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('17', 'Jovan Jovanović Zmaj, Cara Lazara 42, Futog');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('18', 'Đura Jakšić, Kralja Petra I 4, Kać');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('19', 'Veljko Petrović, Zadružna 17, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('20', 'Laza Kostić, Laze Kostića 32, Kovilj');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('21', 'Rumunska baza');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('22', 'Vladimir Nazor, Koste Nađa 1, Petrovaradin');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('23', 'Čitaonica');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('24', 'Medicinska škola');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('25', 'Branko Radičević, Karlovačkih đaka 4, Sremski Karlovci');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('26', 'Žarko Zrenjanin, Dušana Danilovića 12, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('27', 'Danilo Kiš, Narodnog fronta 47, Novi Sad');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('28', 'Ivo Andrić, Arsenija Čarnojevića 24, Rumenka');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('29', 'Serijske publikacije');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('30', 'Knjigobus');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('31', 'Zavičajna zbirka');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('39', 'B fond');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('a', '***');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('c', '***');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('d', '***');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('n', '***');
INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('p', '***');


INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('a', 'Kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('b', 'Razmena');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('c', 'Poklon');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('d', 'Obavezni primerak');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('e', 'Zatečeni (stari) fond');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('f', 'Sopstvena izdanja');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('k', 'Kupovina');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('o', 'Otkup');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('p', 'Poklon');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('s', 'Sopstvena izdanja');
INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('r', '***');

INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('1', 'Vremenski ograničena dostupnost - do 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('2', 'Vremenski ograničena dostupnost - 7 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('3', 'Vremenski ograničena dostupnost - 14 dana');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('4', 'Delimična dostupnost - čitaonica');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('5', 'Delimična dostupnost - sa dozvolom autora');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('6', 'Sadržaj dokumenta nedostupan (poseban tretman)');
INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('7', 'Potpuna nedostupnost (arhivski primerak ...)');

INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (1,'<?xml version="1.0"?><process-type name="Monografske - kompletna obrada" pubType="1"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="010a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><initial-subfield name="7004"/><initial-subfield name="700a"/><initial-subfield name="700b"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');
INSERT INTO Tipovi_obrade (tipobr_id, tipobr_spec) VALUES (2,'<?xml version="1.0"?><process-type name="Serijske - kompletna obrada" pubType="2"><initial-subfield name="0017"/><initial-subfield name="001c"/><initial-subfield name="001d"/><initial-subfield name="011a"/><initial-subfield name="100b"/><initial-subfield name="100c"/><initial-subfield name="101a"/><initial-subfield name="102a"/><initial-subfield name="110a"/><initial-subfield name="110b"/><initial-subfield name="110c"/><initial-subfield name="200a"/><initial-subfield name="200f"/><initial-subfield name="200g"/><initial-subfield name="200h"/><initial-subfield name="200i"/><initial-subfield name="205a"/><initial-subfield name="210a"/><initial-subfield name="300a"/><mandatory-subfield name="100c"/><mandatory-subfield name="200a"/></process-type>');

INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('admin', 'admin','Bojana', 'Dimic','bdimic@uns.ns.ac.yu','napomena', 1, 1, 1, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><process-type name="Serijske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
INSERT INTO Bibliotekari (username, password, ime, prezime, email, napomena, obrada, cirkulacija, administracija, context) VALUES ('circ', 'circ','Danijela','Tesendic','tesendic@uns.ns.ac.yu','', 0, 1, 0, '<?xml version="1.0"?><librarian-context><process-type name="Monografske - kompletna obrada"/><default-process-type name="Monografske - kompletna obrada"/><prefixes pref1="AU" pref2="TI" pref3="PU" pref4="PY" pref5="KW"/></librarian-context>');
