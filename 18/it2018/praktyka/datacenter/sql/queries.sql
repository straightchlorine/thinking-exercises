use exercise_data2018;

-- Zadanie 6.1
select
	Pojemnosc_dysku as rodzaj,
	count(*) as liczba
from komputery
group by Pojemnosc_dysku
order by liczba desc
limit 10;

-- Zadanie 6.2
select
	komputery.Numer_komputera as numer,
    count(*) as liczba
from komputery
join awarie on komputery.Numer_komputera = awarie.Numer_komputera
join naprawy on awarie.Numer_zgloszenia = naprawy.Numer_zgloszenia
where komputery.Sekcja = "A" and naprawy.Rodzaj = "wymiana"
group by komputery.Numer_komputera
having count(*) >= 10;

-- Zadanie 6.3 
select
	komputery.Sekcja,
    count(komputery.Numer_komputera) as liczba,
    month(Czas_awarii),
    day(Czas_awarii)
from komputery
join awarie on awarie.Numer_komputera = komputery.Numer_komputera
group by komputery.Sekcja, month(Czas_awarii), day(Czas_awarii)
order by liczba desc;

-- Zadanie 6.4
select 
	awarie.Numer_zgloszenia as ID,
    awarie.Czas_awarii as entry,
    naprawy.Czas_naprawy as finish,
    timediff(naprawy.Czas_naprawy, awarie.Czas_awarii) as duration 
from awarie
join naprawy on awarie.Numer_zgloszenia = naprawy.Numer_zgloszenia
order by duration desc
limit 1;

-- Zadanie 6.5(fix)
select
	count(*)
from (

select
	komputery.Numer_komputera
from komputery
join awarie on komputery.Numer_komputera = awarie.Numer_komputera
where awarie.Priorytet < 8

union

select
	komputery.Numer_komputera
from komputery
where komputery.Numer_komputera not in (
	select
		Numer_komputera
	from awarie
)

) as t;


