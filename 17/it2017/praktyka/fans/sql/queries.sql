use exercise_fans2017;

-- Zadanie 5.1 a)
select
	Rodzaj_meczu as rodzaj,
    count(Rodzaj_meczu) as meczy
from wyniki
where Id_druzyny in (select Id_druzyny from druzyny where Miasto = 'Kucykowo')
group by Rodzaj_meczu;

-- b)
	select 
		year(Data_meczu) as rok,
        count(*) as mecze
	from wyniki
    where Id_druzyny in (select Id_druzyny from druzyny where Miasto = 'Kucykowo')
    group by year(Data_meczu)
    order by mecze desc;
    
-- Zadanie 5.2
select
	case
		when sum(Bramki_zdobyte) = sum(Bramki_stracone) then druzyny.Nazwa
	end as bilans
from wyniki
join druzyny on wyniki.Id_druzyny = druzyny.Id_druzyny
group by druzyny.Id_druzyny
order by bilans desc;

-- Zadanie 5.3
-- 1 oznacza mecz wygrany, -1 przegrany, a 0 zakończony remisem
select
	t.wynik as rezultat,
    count(t.wynik) as ilosc
from (
	select
		case
			when Bramki_zdobyte > Bramki_stracone then 1
			when Bramki_zdobyte < Bramki_stracone then -1
			else 0
		end as wynik
	from wyniki
	where Gdzie = 'W'
) as t
group by t.wynik
order by ilosc desc;

-- Zadanie 5.4
select
	b.total - count(a.excluded) as 'Liczba sędziów, którzy nie sędziowali w żadnym meczu pucharowym drużyny Galop Kucykowo'
from (
(
	select distinct
		sedziowie.Nr_licencji as excluded
	from wyniki
	join sedziowie on sedziowie.Nr_licencji = wyniki.Nr_licencji
	where Rodzaj_meczu = 'P'
	group by sedziowie.Nr_licencji
) as a,
(
	select
		count(*) as total
	from sedziowie
) as b
);
	