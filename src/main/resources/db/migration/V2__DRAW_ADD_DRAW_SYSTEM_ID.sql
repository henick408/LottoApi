ALTER TABLE draw
    ADD draw_system_id BIGINT;

CREATE SEQUENCE IF NOT EXISTS draw_id_seq;
ALTER TABLE draw
    ALTER COLUMN id SET NOT NULL;
ALTER TABLE draw
    ALTER COLUMN id SET DEFAULT nextval('draw_id_seq');

ALTER SEQUENCE draw_id_seq OWNED BY draw.id;