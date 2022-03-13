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
	cukier.nip as NIP,
	sum(cukier.tonnage * cennik.price) as total
from cukier
join cennik on year(cukier.dateID) = cennik.yearDate
group by cukier.nip
order by total desc;

select
	cukier.nip as NIP,
    case
		when sum(cukier.tonnage) >= 100 and sum(cukier.tonnage) < 1000 then sum(cukier.tonnage * (cennik.price - 0.05))
        when sum(cukier.tonnage) >= 1000 and sum(cukier.tonnage) < 10000 then sum(cukier.tonnage * (cennik.price - 0.10))
        when sum(cukier.tonnage) >= 10000 then sum(cukier.tonnage * (cennik.price - 0.20))
        else sum(cukier.tonnage * cennik.price)
	end as discounted_total
from cukier
join cennik on year(cukier.dateID) = cennik.yearDate
group by cukier.nip
order by discounted_total desc;
