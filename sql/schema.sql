CREATE TABLE Documents
(
  id      INT AUTO_INCREMENT PRIMARY KEY,
  title   VARCHAR(40) NOT NULL,
  content TEXT        NOT NULL
);

CREATE TABLE Tags
(
  id   INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE DocumentTags
(
  fk_document INT NOT NULL,
  fk_tag      INT NOT NULL,
  FOREIGN KEY (fk_document) REFERENCES Documents (id),
  FOREIGN KEY (fk_tag) REFERENCES Tags (id)
);
