CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
)^

CREATE TABLE IF NOT EXISTS authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
)^

DROP PROCEDURE IF EXISTS `CreateIndex` ^
CREATE PROCEDURE `CreateIndex`
(
    given_database VARCHAR(64),
    given_table    VARCHAR(64),
    given_index    VARCHAR(64),
    given_columns  VARCHAR(64)
)
BEGIN

    DECLARE IndexIsThere INTEGER;

    SELECT COUNT(1) INTO IndexIsThere
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE table_schema = given_database
    AND   table_name   = given_table
    AND   index_name   = given_index;

    IF IndexIsThere = 0 THEN
        SET @sqlstmt = CONCAT('CREATE UNIQUE INDEX ',given_index,' ON ',
        given_database,'.',given_table,' (',given_columns,')');
        PREPARE st FROM @sqlstmt;
        EXECUTE st;
        DEALLOCATE PREPARE st;
    ELSE
        SELECT CONCAT('Index ',given_index,' already exists on Table ',
        given_database,'.',given_table) CreateindexErrorMessage;
    END IF;

END ^

call CreateIndex('bookingvetdb','authorities','ix_auth_username','username,authority')^