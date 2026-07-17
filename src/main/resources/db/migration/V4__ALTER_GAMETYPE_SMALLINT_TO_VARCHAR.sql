ALTER TABLE draw_prize
ALTER COLUMN game_type TYPE varchar(20)
USING (
    CASE game_type
        WHEN 0 THEN 'LOTTO'
        WHEN 1 THEN 'LOTTOPLUS'
        WHEN 2 THEN 'MINILOTTO'
        WHEN 3 THEN 'EUROJACKPOT'
    END
);