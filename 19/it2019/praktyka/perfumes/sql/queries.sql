use exercise_perfumes2019;
-- 1
select
	*
from perfumy
join sklad on sklad.id_perfum = perfumy.id_perfum
where sklad.nazwa_skladnika = 'absolut jasminu';

-- 2
select 
	perfumy.rodzina_zapachow,
    perfumy.cena,
    perfumy.nazwa_p
from (
	select
		perfumy.rodzina_zapachow as family,
		min(cena) as price
	from perfumy
	group by perfumy.rodzina_zapachow
) as t, perfumy
where perfumy.rodzina_zapachow = t.family and perfumy.cena = t.price
order by perfumy.rodzina_zapachow asc;

-- 3
select
	marki.nazwa_m
from marki
where marki.nazwa_m not in (
	select distinct
		marki.nazwa_m
	from perfumy 
	join marki on perfumy.id_marki = marki.id_marki
	join sklad on perfumy.id_perfum = sklad.id_perfum
	where sklad.nazwa_skladnika like '%paczula%'
);

-- 4
select
    perfumy.nazwa_p,
    (0.85 * perfumy.cena) as price
from perfumy
join marki on perfumy.id_marki = marki.id_marki
where marki.nazwa_m = 'Mou De Rosine' and perfumy.rodzina_zapachow = 'orientalno-drzewna'
order by price asc;

-- 5
select 
	t.brand,
    t.family,
    count(*) over (partition by t.brand)
from (
	select distinct
		marki.nazwa_m as brand,
		perfumy.rodzina_zapachow as family
	from perfumy
	join marki on perfumy.id_marki = marki.id_marki
) as t



