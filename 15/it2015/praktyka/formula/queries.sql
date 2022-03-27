use exercise;

-- Zadanie 6.2
select concat(kierowcy.imie, " ", kierowcy.nazwisko) as Rajdowiec, 
		wyniki.Punkty as Punkty,
        wyscigi.Rok as Rok,
        wyscigi.GrandPrix as Kraj
from kierowcy 
join wyniki on kierowcy.Id_kierowcy = wyniki.Id_kierowcy 
join wyscigi on wyniki.Id_wyscigu = wyscigi.Id_wyscigu
where Nazwisko = "Kubica" 
order by wyniki.Punkty desc 
limit 1;

-- Zadanie 6.2
select wyscigi.GrandPrix as Kraj,
		count(wyscigi.GrandPrix) as liczba
from wyscigi 
group by GrandPrix order by liczba asc;

-- Zadanie 6.3
select *
from (
	select concat(kierowcy.Imie, " ", kierowcy.Nazwisko) as Kierowca,
			wyscigi.Rok as Sezon,
			sum(wyniki.Punkty) as Punkty
	from wyniki
	join kierowcy on wyniki.Id_kierowcy = kierowcy.Id_kierowcy
	join wyscigi on wyniki.Id_wyscigu = wyscigi.Id_wyscigu
	where wyscigi.Rok = 2000 or wyscigi.Rok = 2006 or wyscigi.Rok = 2012
	group by Kierowca, wyscigi.Rok) as t
 group by t.Sezon
 order by t.Sezon asc;

-- Zadanie 6.4


select
	t.kraj,
    count(*)
from (
	select 
		kierowcy.Kraj as kraj
	from kierowcy
	join wyniki on wyniki.Id_kierowcy = kierowcy.Id_kierowcy
	join wyscigi on wyniki.Id_wyscigu = wyscigi.Id_wyscigu
	where wyscigi.Rok = 2012
group by kierowcy.Id_kierowcy) as t
group by t.Kraj
order by kraj asc;


