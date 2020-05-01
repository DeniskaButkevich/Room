delete from room;
delete from sxgeo_country;

INSERT INTO sxgeo_country(id,iso,continent,name_ru,name_en,lat,lon,timezone) VALUES
(222,'UA','EU','Украина','Ukraine',49,32,'Europe/Kiev'),
(36,'BY','EU','Беларусь','Belarus',53,28,'Europe/Minsk'),
(111,'JP','AS','Япония','Japan',35.69,139.75,'Asia/Tokyo'),
(56,'DE','EU','Германия','Germany',51.5,10.5,'Europe/Berlin');