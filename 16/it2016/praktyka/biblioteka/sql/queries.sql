use `exercise_library2016`;

-- Zadanie 5.1
select 
	t.Student,
	wypozyczenia.tytul as Tytul
from (
	select  
		studenci.pesel as PESEL,
		concat(studenci.imie, " ", studenci.nazwisko) as Student,
		count(*) as Liczba
	from studenci
	join wypozyczenia on studenci.pesel = wypozyczenia.pesel
	group by studenci.pesel
	order by Liczba desc
	limit 1) as t
join wypozyczenia on t.PESEL = wypozyczenia.pesel;

-- Zadanie 5.2
select 
	round(avg(t.Liczba), 4) as 'Średnia ilość studentów w jednym mieszkaniu'
from (
select 
    count(*) as Liczba
from meldunek
join studenci on studenci.pesel = meldunek.pesel
group by meldunek.id_pok) as t;

-- Zadanie 5.3
select 
	'Ilość kobiet',
    count(
		if (cast(substr(studenci.pesel, 10, 1) as unsigned) % 2 = 0, 1, null) 
    ) as 'Ilość'
from studenci
UNION
select 
	'Ilość mężczyzn',
    count(
		if (cast(substr(studenci.pesel, 10, 1) as unsigned) % 2 != 0, 1, null) 
    )
from studenci;

-- Zadanie 5.4
select 
	concat(studenci.nazwisko, " ",studenci.imie) as Student
from studenci
where studenci.pesel not in (select meldunek.pesel from meldunek)
order by Student asc;

-- Zadanie 5.5
-- zwroc tytuly ksiazek z kazdego pokoju


select 
	sum(books) as 'Wypożyczone lektury'
from (
	-- number of books rented out by the people not living in the city
	select 
		count(wypozyczenia.tytul) as books
	from studenci
	join wypozyczenia on studenci.pesel = wypozyczenia.pesel
	where studenci.pesel not in (select meldunek.pesel from meldunek)
	union
-- number of books rented out by the people living in the city(no duplcates)
	select 
		count(*)
	from (
		select distinct
			meldunek.id_pok,
			wypozyczenia.tytul
		from wypozyczenia
		join meldunek on wypozyczenia.pesel = meldunek.pesel    
	) as t
) as outerlayer


