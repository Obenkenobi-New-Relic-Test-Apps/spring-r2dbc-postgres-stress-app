CREATE OR REPLACE PROCEDURE create_document(IN name_val varchar(255))
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO department (name) VALUES (name_val);
END;
$$;