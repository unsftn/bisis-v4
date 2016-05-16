/*==============================================================*/
/* DBMS name:      MySQL 4.0                                    */
/* Created on:     5.12.2006 19:45:57                           */
/*==============================================================*/


/*==============================================================*/
/* Table: Bibliotekari                                          */
/*==============================================================*/
create table Bibliotekari
(
   username                       varchar(50)                    not null,
   password                       varchar(50)                    not null,
   ime							  varchar(50),
   prezime						  varchar(50),
   email						  varchar(50),
   napomena						  varchar(250),				
   obrada                         int                            not null,
   cirkulacija                    int                            not null,
   administracija                 int                            not null,
   context                        text                           not null,
   primary key (username)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Counters                                              */
/*==============================================================*/
create table Counters
(
   counter_name                   varchar(50)                    not null,
   counter_value                  int                            not null,
   primary key (counter_name)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Godine                                                */
/*==============================================================*/
create table Godine
(
   godina_id                      int                            not null,
   povez_id                       char(2) BINARY,
   odeljenje_id                   char(2),
   IntOzn_id                      char(4) BINARY,
   record_id                      int                            not null,
   nacin_id                       char(1),
   dostupnost_id				  char(4),	
   SigFormat_ID                   char(10) BINARY,
   podlokacija_id                 char(10) BINARY,
   inv_broj                       varchar(25),
   datum_racuna                   date,
   broj_racuna                    varchar(50),
   dobavljac                      varchar(255),
   cena                           decimal(12,2),
   finansijer                     varchar(255),
   sig_dublet                     varchar(25),
   sig_numerus_curens             varchar(50),
   sig_numeracija                 varchar(25),
   sig_udk                        varchar(255),
   godiste                        varchar(25),
   godina                         varchar(25),
   broj                           longtext,
   datum_inventarisanja           date,
   inventator					  varchar(255),	
   napomene                       varchar(255),
   primary key (godina_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Index: Relationship_4_FK                                     */
/*==============================================================*/
create index Relationship_4_FK on Godine
(
   record_id
);

/*==============================================================*/
/* Index: Relationship_5_FK                                     */
/*==============================================================*/
create index Relationship_5_FK on Godine
(
   povez_id
);

/*==============================================================*/
/* Index: Relationship_6_FK                                     */
/*==============================================================*/
create index Relationship_6_FK on Godine
(
   nacin_id
);

/*==============================================================*/
/* Index: Relationship_7_FK                                     */
/*==============================================================*/
create index Relationship_7_FK on Godine
(
   odeljenje_id
);

/*==============================================================*/
/* Index: Relationship_8_FK                                     */
/*==============================================================*/
create index Relationship_8_FK on Godine
(
   IntOzn_id
);

/*==============================================================*/
/* Index: Relationship_9_FK                                     */
/*==============================================================*/
create index Relationship_9_FK on Godine
(
   podlokacija_id
);

/*==============================================================*/
/* Index: Relationship_16_FK                                    */
/*==============================================================*/
create index Relationship_16_FK on Godine
(
   SigFormat_ID
);

/*==============================================================*/
/* Index: Godine_index1                                         */
/*==============================================================*/
create index Godine_index1 on Godine
(
   inv_broj
);

/*==============================================================*/
/* Table: Interna_oznaka                                        */
/*==============================================================*/
create table Interna_oznaka
(
   IntOzn_id                      char(4) BINARY                 not null,
   IntOzn_opis                    varchar(255)                   not null,
   primary key (IntOzn_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Nacin_nabavke                                         */
/*==============================================================*/
create table Nacin_nabavke
(
   nacin_id                       char(1)                        not null,
   nacin_opis                     varchar(255),
   primary key (nacin_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Odeljenje                                             */
/*==============================================================*/
create table Odeljenje
(
   odeljenje_id                   char(2)                        not null,
   odeljenje_naziv                varchar(255)                   not null,
   primary key (odeljenje_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Podlokacija                                           */
/*==============================================================*/
create table Podlokacija
(
   podlokacija_id                 char(10) BINARY                not null,
   podlokacija_opis               varchar(255)                   not null,
   primary key (podlokacija_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Povez                                                 */
/*==============================================================*/
create table Povez
(
   povez_id                       char(2) BINARY                 not null,
   povez_opis                     varchar(255)                   not null,
   primary key (povez_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: invknj                                                */
/*==============================================================*/
create table Invknj
(
   invknj_id                       char(2)                        not null,
   invknj_opis                     varchar(255)                   not null,
   primary key (invknj_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Dostupnost                                            */
/*==============================================================*/
create table Dostupnost
(
   dostupnost_id                       char(4)                    not null,
   dostupnost_opis                     varchar(255)               not null,
   primary key (dostupnost_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Primerci                                              */
/*==============================================================*/
create table Primerci
(
   primerak_id                    int                            not null,
   podlokacija_id                 char(10) BINARY,
   SigFormat_ID                   char(10) BINARY,
   odeljenje_id                   char(2),
   IntOzn_id                      char(4) BINARY,
   status_id                      char(1) BINARY,
   datum_statusa				  date,	
   record_id                      int                            not null,
   nacin_id                       char(1),
   povez_id                       char(2) BINARY,
   dostupnost_id				  char(4),	
   inv_broj                       varchar(25)                    not null,
   datum_racuna                   date,
   broj_racuna                    varchar(50),
   dobavljac                      varchar(255),
   cena                           decimal(12,2),
   finansijer                     varchar(255),
   usmeravanje                    varchar(255),
   sig_dublet                     varchar(25),
   sig_numerus_curens             varchar(50),
   sig_udk                        varchar(255),
   datum_inventarisanja           date,
   napomene                       varchar(255),   
   version                        int                            not null,
   stanje						  int,
   inventator					  varchar(255),	
   primary key (primerak_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Index: Relationship_1_FK                                     */
/*==============================================================*/
create index Relationship_1_FK on Primerci
(
   status_id
);

/*==============================================================*/
/* Index: Relationship_2_FK                                     */
/*==============================================================*/
create index Relationship_2_FK on Primerci
(
   record_id
);

/*==============================================================*/
/* Index: Relationship_10_FK                                    */
/*==============================================================*/
create index Relationship_10_FK on Primerci
(
   povez_id
);

/*==============================================================*/
/* Index: Relationship_11_FK                                    */
/*==============================================================*/
create index Relationship_11_FK on Primerci
(
   nacin_id
);

/*==============================================================*/
/* Index: Relationship_12_FK                                    */
/*==============================================================*/
create index Relationship_12_FK on Primerci
(
   odeljenje_id
);

/*==============================================================*/
/* Index: Relationship_13_FK                                    */
/*==============================================================*/
create index Relationship_13_FK on Primerci
(
   IntOzn_id
);

/*==============================================================*/
/* Index: Relationship_14_FK                                    */
/*==============================================================*/
create index Relationship_14_FK on Primerci
(
   podlokacija_id
);

/*==============================================================*/
/* Index: Relationship_17_FK                                    */
/*==============================================================*/
create index Relationship_17_FK on Primerci
(
   SigFormat_ID
);

/*==============================================================*/
/* Bojana dodala                                */
/*==============================================================*/
create index Relationship_18_FK on Primerci
(
   dostupnost_id
);

/*==============================================================*/
/* Index: Primerci_index1                                       */
/*==============================================================*/
create index Primerci_index1 on Primerci
(
   inv_broj
);

/*==============================================================*/
/* Table: Records                                               */
/*==============================================================*/
create table Records
(
   record_id                      int                            not null,
   pub_type                       int                            not null,
   creator                        varchar(255)                   not null,
   modifier                       varchar(255),
   date_created                   datetime                       not null,
   date_modified                  datetime,
   archived                       int,
   in_use_by                      varchar(255),
   content                        longtext,
   primary key (record_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: SigFormat                                             */
/*==============================================================*/
create table SigFormat
(
   SigFormat_ID                   char(10) BINARY                not null,
   Format_opis                    varchar(255)                   not null,
   primary key (SigFormat_ID)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Status_Primerka                                       */
/*==============================================================*/
create table Status_Primerka
(
   status_id                      char(1) BINARY                 not null,
   status_opis                    varchar(255)                   not null,
   zaduziv                        int                            not null,
   primary key (status_id)
)
engine =  InnoDB;

/*==============================================================*/
/* Table: Sveske                                                */
/*==============================================================*/
create table Sveske
(
   sveska_id                      int                            not null,
   status_id                      char(1) BINARY,   
   datum_statusa				  date,                    
   godina_id                      int,
   inv_br                         varchar(25)                    not null,
   signatura                      varchar(255),
   broj_sveske                    varchar(255),
   knjiga						  varchar(255),	
   cena                           decimal(12,2),
   inventator					  varchar(255),
   stanje						  int,		   
   version                        int                            not null,
   primary key (sveska_id)
   
)
engine =  InnoDB;

/*==============================================================*/
/* Index: Relationship_3_FK                                     */
/*==============================================================*/
create index Relationship_3_FK on Sveske
(
   status_id
);

/*==============================================================*/
/* Index: Relationship_15_FK                                    */
/*==============================================================*/
create index Relationship_15_FK on Sveske
(
   godina_id
);

/*==============================================================*/
/* Index: Sveske_index1                                         */
/*==============================================================*/
create index Sveske_index1 on Sveske
(
   inv_br
);

/*==============================================================*/
/* Table: Tipovi_obrade                                         */
/*==============================================================*/
create table Tipovi_obrade
(
   tipobr_id                      int                            not null,
   tipobr_spec                    text                           not null,
   primary key (tipobr_id)
)
engine =  InnoDB;

/*
 * Table:Sifarnik_992b  -dodato zbog Sabca
 */

create table Sifarnik_992b
(
   id                                 varchar(20)                       not null ,
   naziv                              varchar(120)                         ,
  primary key (id) 
)
engine =  InnoDB;

alter table Godine add constraint FK_Relationship_16 foreign key (SigFormat_ID)
      references SigFormat (SigFormat_ID) on delete restrict on update restrict;

alter table Godine add constraint FK_Relationship_4 foreign key (record_id)
      references Records (record_id) on delete cascade on update restrict;

alter table Godine add constraint FK_Relationship_5 foreign key (povez_id)
      references Povez (povez_id) on delete restrict on update restrict;

alter table Godine add constraint FK_Relationship_6 foreign key (nacin_id)
      references Nacin_nabavke (nacin_id) on delete restrict on update restrict;

alter table Godine add constraint FK_Relationship_7 foreign key (odeljenje_id)
      references Odeljenje (odeljenje_id) on delete restrict on update restrict;

alter table Godine add constraint FK_Relationship_8 foreign key (IntOzn_id)
      references Interna_oznaka (IntOzn_id) on delete restrict on update restrict;

alter table Godine add constraint FK_Relationship_9 foreign key (podlokacija_id)
      references Podlokacija (podlokacija_id) on delete restrict on update restrict;

alter table Primerci add constraint FK_Relationship_1 foreign key (status_id)
      references Status_Primerka (status_id) on delete restrict on update restrict;

alter table Primerci add constraint FK_Relationship_10 foreign key (povez_id)
      references Povez (povez_id) on delete restrict on update restrict;

alter table Primerci add constraint FK_Relationship_11 foreign key (nacin_id)
      references Nacin_nabavke (nacin_id) on delete restrict on update restrict;

alter table Primerci add constraint FK_Relationship_12 foreign key (odeljenje_id)
      references Odeljenje (odeljenje_id) on delete restrict on update restrict;

alter table Primerci add constraint FK_Relationship_13 foreign key (IntOzn_id)
      references Interna_oznaka (IntOzn_id) on delete restrict on update restrict;

alter table Primerci add constraint FK_Relationship_14 foreign key (podlokacija_id)
      references Podlokacija (podlokacija_id) on delete restrict on update restrict;

alter table Primerci add constraint FK_Relationship_17 foreign key (SigFormat_ID)
      references SigFormat (SigFormat_ID) on delete restrict on update restrict;
      
alter table Primerci add constraint FK_Relationship_18 foreign key (dostupnost_id)
      references Dostupnost (dostupnost_id) on delete restrict on update restrict;

alter table Godine add constraint FK_Relationship_19 foreign key (dostupnost_id)
      references Dostupnost (dostupnost_id) on delete restrict on update restrict;
      
alter table Primerci add constraint FK_Relationship_2 foreign key (record_id)
      references Records (record_id) on delete cascade on update restrict;

alter table Sveske add constraint FK_Relationship_15 foreign key (godina_id)
      references Godine (godina_id) on delete cascade on update restrict;

alter table Sveske add constraint FK_Relationship_3 foreign key (status_id)
      references Status_Primerka (status_id) on delete restrict on update restrict;
