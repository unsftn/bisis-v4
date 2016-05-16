CREATE TABLE registar_autori (autor VARCHAR(255), original VARCHAR(255));
CREATE INDEX registar_autori_1 ON registar_autori (autor);
CREATE INDEX registar_autori_2 ON registar_autori (original);
CREATE TABLE registar_kolektivni (kolektivac VARCHAR(255));
CREATE INDEX registar_kolektivni_1 ON registar_kolektivni (kolektivac);
CREATE TABLE registar_odr (pojam VARCHAR(255));
CREATE INDEX registar_odr_1 ON registar_odr (pojam);
CREATE TABLE registar_pododr (pojam VARCHAR(255));
CREATE INDEX registar_pododr_1 ON registar_pododr (pojam);
CREATE TABLE registar_zbirke (naziv VARCHAR(255));
CREATE INDEX registar_zbirke_1 ON registar_zbirke (naziv);
CREATE TABLE registar_udk (grupa VARCHAR(255), opis VARCHAR(255));
CREATE INDEX registar_udk_1 ON registar_udk (grupa);
CREATE INDEX registar_udk_2 ON registar_udk (opis);

