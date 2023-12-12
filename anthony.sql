-- CREATE DATABASE s5s3;
-- \c s5s3;

CREATE SEQUENCE seq_utilisateur;

CREATE OR REPLACE FUNCTION build_id(IN chaine_param VARCHAR, IN nombre_param INTEGER)
RETURNS VARCHAR AS $$
DECLARE 
    resultat VARCHAR;
BEGIN
    IF LENGTH(nombre_param::TEXT)<3 THEN
        nombre_param := LPAD(nombre_param::TEXT, 3, '0');
    END IF;
    resultat := chaine_param || TO_CHAR(nombre_param, 'FM0000');

    RETURN resultat;
END;
$$ LANGUAGE plpgsql;


CREATE TABLE utilisateur(
    id_user VARCHAR PRIMARY KEY DEFAULT build_id('USR',nextval('seq_utilisateur')::INTEGER),
    nom VARCHAR(255),
    prenom VARCHAR(255),
    date_naissance  DATE,
    email VARCHAR(255),
    password VARCHAR(255),
    role INTEGER DEFAULT 10,
    etat INTEGER DEFAULT 10
);

INSERT INTO utilisateur(nom, prenom, date_naissance , email, password, role) VALUES ('Anthony','Ton','2010-10-10','anthony@gmail.com','anthony',100);