-- Table: user_status

-- DROP TABLE user_status;

CREATE TABLE user_status
(
  ut_id smallint NOT NULL,
  ut_name text NOT NULL,
  CONSTRAINT user_status_pkey PRIMARY KEY (ut_id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_status
  OWNER TO ots;
COMMENT ON TABLE user_status
  IS 'kullanicilarin durumlarini tutan tablo';


-- Table: "user"

-- DROP TABLE "user";

CREATE TABLE "user"
(
  us_id serial NOT NULL,
  us_login_name text NOT NULL,
  us_password_hash text,
  us_email text NOT NULL,
  us_status_id smallint NOT NULL,
  us_created timestamp without time zone NOT NULL DEFAULT now(),
  us_modified timestamp without time zone NOT NULL DEFAULT clock_timestamp(),
  us_last_login timestamp without time zone,
  CONSTRAINT user_pkey PRIMARY KEY (us_id ),
  CONSTRAINT user_us_status_id_fkey FOREIGN KEY (us_status_id)
      REFERENCES user_status (ut_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_us_email_key UNIQUE (us_email ),
  CONSTRAINT user_us_login_name_key UNIQUE (us_login_name )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "user"
  OWNER TO ots;
COMMENT ON TABLE "user"
  IS 'kayitli kullanicilar';

-- Index: user_created_idx

-- DROP INDEX user_created_idx;

CREATE INDEX user_created_idx
  ON "user"
  USING btree
  (us_created );

-- Index: user_modified_idx

-- DROP INDEX user_modified_idx;

CREATE INDEX user_modified_idx
  ON "user"
  USING btree
  (us_modified );

