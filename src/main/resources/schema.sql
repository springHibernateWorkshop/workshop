-- CREATE TABLE USER_TAB (
--   USER_ID INT NOT NULL,
--   NAME VARCHAR(150) NOT NULL,
--   PRIMARY KEY (USER_ID),
--   CONSTRAINT USER_NAME_DX_UNIQUE UNIQUE (NAME)
-- );

-- CREATE TABLE CATEGORY_TAB (
--   CATEGORY_ID INT NOT NULL,
--   NAME VARCHAR(150) NOT NULL,
--   PRIMARY KEY (CATEGORY_ID),
--   CONSTRAINT CATEGORY_NAME_DX_UNIQUE UNIQUE (NAME)
-- );

CREATE test_table (
  ID INT NOT NULL,
  USER_ID INT NOT NULL,
  CATEGORY_ID INT NOT NULL,
  PRIMARY KEY (ID));
