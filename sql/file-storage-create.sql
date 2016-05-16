CREATE TABLE file_storage (
  file_id      INTEGER      NOT NULL    AUTO_INCREMENT,
  rn           INTEGER      NOT NULL,
  filename     VARCHAR(100) NOT NULL,
  content_type VARCHAR(30)  NOT NULL,
  uploader     VARCHAR(255) NOT NULL,
  web_visibility       INTEGER,
  PRIMARY KEY (file_id)
) ENGINE = InnoDB;

CREATE INDEX file_storage_rn ON file_storage (rn);

