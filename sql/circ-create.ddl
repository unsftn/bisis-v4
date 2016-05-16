CREATE TABLE organization (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  address VARCHAR(255) NULL,
  city VARCHAR(255) NULL,
  zip INTEGER NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE mmbr_types (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  period INTEGER NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE location (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  last_user_id INTEGER NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE warning_types (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  wtext TEXT NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE user_categs (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  titles_no INTEGER NULL,
  period INTEGER NULL,
  max_period INTEGER NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE places (
  id INTEGER NOT NULL AUTO_INCREMENT,
  zip VARCHAR(255) NULL,
  city VARCHAR(255) NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE groups (
  sys_id INTEGER NOT NULL AUTO_INCREMENT,
  user_id VARCHAR(255) NOT NULL,
  inst_name VARCHAR(255) NULL,
  sign_date DATETIME NULL,
  address VARCHAR(255) NULL,
  city VARCHAR(255) NULL,
  zip INTEGER NULL,
  phone VARCHAR(255) NULL,
  email VARCHAR(255) NULL,
  fax VARCHAR(255) NULL,
  sec_address VARCHAR(255) NULL,
  sec_city VARCHAR(255) NULL,
  sec_zip INTEGER NULL,
  sec_phone VARCHAR(255) NULL,
  cont_fname VARCHAR(255) NULL,
  cont_lname VARCHAR(255) NULL,
  cont_email VARCHAR(255) NULL,
  PRIMARY KEY(sys_id)
)
ENGINE=InnoDB;

CREATE TABLE edu_lvl (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE languages (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE archive (
  id INTEGER NOT NULL AUTO_INCREMENT,
  sys_id INTEGER NOT NULL,
  arch_date DATETIME NOT NULL,
  content TEXT NULL,
  PRIMARY KEY(id)
)
ENGINE=InnoDB;

CREATE TABLE warn_counters (
  id INTEGER NOT NULL AUTO_INCREMENT,
  warn_year INTEGER NOT NULL,
  wtype INTEGER NOT NULL,
  last_no INTEGER NULL,
  PRIMARY KEY(id),
  INDEX warn_counters_FKIndex1(wtype),
  FOREIGN KEY(wtype)
    REFERENCES warning_types(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
ENGINE=InnoDB;

CREATE TABLE membership (
  id INTEGER NOT NULL AUTO_INCREMENT,
  user_categ INTEGER NOT NULL,
  mmbr_type INTEGER NOT NULL,
  cost DECIMAL NULL,
  PRIMARY KEY(id),
  INDEX membership_FKIndex1(user_categ),
  INDEX membership_FKIndex2(mmbr_type),
  FOREIGN KEY(user_categ)
    REFERENCES user_categs(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(mmbr_type)
    REFERENCES mmbr_types(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
ENGINE=InnoDB;

CREATE TABLE users (
  sys_id INTEGER NOT NULL AUTO_INCREMENT,
  organization INTEGER NULL,
  languages INTEGER NULL,
  edu_lvl INTEGER NULL,
  mmbr_type INTEGER NULL,
  user_categ INTEGER NULL,
  groups INTEGER NULL,
  user_id VARCHAR(11) NOT NULL,
  first_name VARCHAR(255) NULL,
  last_name VARCHAR(255) NULL,
  parent_name VARCHAR(255) NULL,
  address VARCHAR(255) NULL,
  city VARCHAR(255) NULL,
  zip INTEGER NULL,
  phone VARCHAR(255) NULL,
  email VARCHAR(255) NULL,
  jmbg VARCHAR(255) NULL,
  doc_id INTEGER NULL,
  doc_no VARCHAR(255) NULL,
  doc_city VARCHAR(255) NULL,
  country VARCHAR(255) NULL,
  gender VARCHAR(1) NULL,
  age VARCHAR(1) NULL,
  sec_address VARCHAR(255) NULL,
  sec_zip INTEGER NULL,
  sec_city VARCHAR(255) NULL,
  sec_phone VARCHAR(255) NULL,
  note VARCHAR(255) NULL,
  interests VARCHAR(255) NULL,
  warning_ind INTEGER NULL,
  occupation VARCHAR(255) NULL,
  title VARCHAR(255) NULL,
  index_no VARCHAR(255) NULL,
  class_no INTEGER NULL,
  pass VARCHAR(255) NULL,
  block_reason VARCHAR(255) NULL,
  PRIMARY KEY(sys_id),
  INDEX users_FKIndex1(groups),
  INDEX users_FKIndex2(user_categ),
  INDEX users_FKIndex3(mmbr_type),
  INDEX users_FKIndex4(edu_lvl),
  INDEX users_FKIndex5(languages),
  INDEX users_FKIndex6(organization),
  INDEX users_index1378(user_id),
  INDEX users_index1392(first_name),
  INDEX users_index1393(last_name),
  INDEX users_index1394(parent_name),
  INDEX users_index1395(address),
  INDEX users_index1396(city),
  INDEX users_index1397(jmbg),
  INDEX users_index1398(doc_no),
  INDEX users_index1399(index_no),
  INDEX users_index1400(pass),
  FOREIGN KEY(groups)
    REFERENCES groups(sys_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(user_categ)
    REFERENCES user_categs(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(mmbr_type)
    REFERENCES mmbr_types(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(edu_lvl)
    REFERENCES edu_lvl(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(languages)
    REFERENCES languages(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(organization)
    REFERENCES organization(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
ENGINE=InnoDB;

CREATE TABLE duplicate (
  id INTEGER NOT NULL AUTO_INCREMENT,
  sys_id INTEGER NOT NULL,
  dup_no INTEGER NOT NULL,
  dup_date DATETIME NULL,
  PRIMARY KEY(id),
  INDEX duplicate_FKIndex1(sys_id),
  FOREIGN KEY(sys_id)
    REFERENCES users(sys_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
ENGINE=InnoDB;

CREATE TABLE lending (
  id INTEGER NOT NULL AUTO_INCREMENT,
  ctlg_no VARCHAR(255) NOT NULL,
  lend_date DATETIME NOT NULL,
  sys_id INTEGER NOT NULL,
  location INTEGER NULL,
  return_date DATETIME NULL,
  resume_date DATETIME NULL,
  deadline DATETIME NULL,
  librarian_lend VARCHAR(255) NULL,
  librarian_return VARCHAR(255) NULL,
  librarian_resume VARCHAR(255) NULL,
  PRIMARY KEY(id),
  INDEX lending_FKIndex1(sys_id),
  INDEX lending_FKIndex2(location),
  INDEX lending_index1401(ctlg_no),
  FOREIGN KEY(sys_id)
    REFERENCES users(sys_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(location)
    REFERENCES location(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
ENGINE=InnoDB;

CREATE TABLE signing (
  id INTEGER NOT NULL AUTO_INCREMENT,
  sign_date DATETIME NULL,
  sys_id INTEGER NOT NULL,
  location INTEGER NULL,
  until_date DATETIME NULL,
  cost DECIMAL NULL,
  receipt_id VARCHAR(255) NULL,
  librarian VARCHAR(255) NULL,
  PRIMARY KEY(id),
  INDEX signing_FKIndex1(sys_id),
  INDEX signing_FKIndex2(location),
  FOREIGN KEY(sys_id)
    REFERENCES users(sys_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(location)
    REFERENCES location(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
ENGINE=InnoDB;

CREATE TABLE warnings (
  id INTEGER NOT NULL AUTO_INCREMENT,
  lending_id INTEGER NOT NULL,
  wdate DATETIME NOT NULL,
  wtype INTEGER NOT NULL,
  warn_no VARCHAR(255) NULL,
  deadline DATETIME NULL,
  note VARCHAR(255) NULL,
  PRIMARY KEY(id),
  INDEX warnings_FKIndex1(wtype),
  INDEX warnings_FKIndex2(lending_id),
  FOREIGN KEY(wtype)
    REFERENCES warning_types(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(lending_id)
    REFERENCES lending(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
ENGINE=InnoDB;

CREATE TABLE picturebooks (
  id INTEGER NOT NULL AUTO_INCREMENT,
  sys_id INTEGER NOT NULL,
  sdate DATETIME NOT NULL,
  lend_no INTEGER NOT NULL,
  return_no INTEGER NOT NULL,
  state INTEGER NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(sys_id)
    REFERENCES users(sys_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
ENGINE=InnoDB;

CREATE TABLE configs (
  name VARCHAR(255) NULL NOT NULL,
  text TEXT NULL,
  PRIMARY KEY(name)
)
ENGINE=InnoDB;