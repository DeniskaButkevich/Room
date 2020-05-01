delete from room;

INSERT INTO room(id,lamp,name,country_id) VALUES
    (1, true,'Belarus', 36),
    (2, false ,'Ukraine',222),
    (3, false ,'Japan', 111),
    (4, false ,'Germany', 56);

alter sequence hibernate_sequence restart with 10;