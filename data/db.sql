﻿CREATE TABLE IF NOT EXISTS books (
   isbn CHAR(13) NOT NULL,
   title VARCHAR(64) NOT NULL,
   author VARCHAR(64) NOT NULL,
   year INT,
   PRIMARY KEY (isbn));

INSERT INTO books VALUES 
('1234567891234','W poszukiwaniu straconego czasu','Marcel Proust',2008),
('1234567891235','Komu bije dzwon', 'Ernest Hemingway', 2008),
('1234567891236','Slonce tez wstaje','Ernest Hemingway',2008),
('1234567891237','Stary czlowiek i morze', 'Ernest Hemingway',2008),
('1234567891238','Bracia Karamazow', 'Fiodor Dostojewski',2008),
('1234567891239','Zbrodnia i kara','Fiodor Dostojewski',2008),
('1234567891240','Kronika Ptaka nakrecacza','Haruki Murakami',2008),
('1234567891241','Tancz tancz tancz', 'Haruki Murakami',2008),
('1234567891242','Przygoda z owca', 'Haruki Murakami',2008),
('1234567891243','Snieg', 'Orhan Pamuk',2004),
('1234567891244','Nazwyam sie Czerwien', 'Orhan Pamuk', 2008),
('1234567891245','Nowe zycie', 'Orhan Pamuk',2008),
('1234567891246','Solaris', 'Stanislaw Lem', 2008),
('1234567891247','Nowy wspanialy swiat', 'Aldous Huxley', 2007),
('1234567891248','Rok 1984', 'George Orwell', 2003),
('1234567891249','Mechaniczna pomarancza', 'Antoni Burgess', 1999);


   


