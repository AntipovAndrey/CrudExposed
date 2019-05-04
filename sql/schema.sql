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
