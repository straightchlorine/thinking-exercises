use exercise_sweet2017;

-- Zadanie 4.1
select
	nip as NIP,
    sum(tonnage) as Tonnage
from cukier
group by NIP
order by Tonnage desc
limit 3;

-- Zadanie 4.2
select 
	sum(t.netto) as 'Overall Income'
from (
	select 
		cukier.tonnage * cennik.price as netto
	from cukier
	join cennik on year(cukier.dateID) = cennik.yearDate
) as t;

-- Zadanie 4.3
select 
	cennik.yearDate as Data,
    sum(cukier.tonnage) as 'Tonaż'
from cukier
join cennik on year(cukier.dateID) = cennik.yearDate
group by cennik.yearDate
order by cennik.yearDate asc;

-- Zadanie 4.4
select
	cukier.nip,
    sum(tonnage) over (partition by cukier.nip order by cukier.dateID) tonnages
from cukier