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
-- over partition by 
select
    sum(t.tonnage * t.discount) as 'Total discount'
from (
	select
		src.id as id,
		src.nip as nip,
		src.tonnage as tonnage,
		src.tonnages as tonnages,
		case
			when src.tonnages >= 100 and src.tonnages < 1000 then 0.05
			when src.tonnages >= 1000 and src.tonnages < 10000 then 0.1
			when src.tonnages >= 10000 then 0.2
			else 0.0
		end as discount
	from (
		select 
			cukier.dateID as id,
			cukier.nip, 
			cukier.tonnage as tonnage,
			sum(tonnage) over (partition by cukier.nip order by cukier.dateID) tonnages
		from cukier
		join cennik on year(cukier.dateID) = cennik.yearDate
	) as src
) as t;

-- later make this without over partition by, for better understanding
select
    sum(t.tonnage * t.discount) as 'Total discount'
from (
	select
		src.id as id,
		src.nip as nip,
		src.tonnage as tonnage,
		src.tonnages as tonnages,
		case
			when src.tonnages >= 100 and src.tonnages < 1000 then 0.05
			when src.tonnages >= 1000 and src.tonnages < 10000 then 0.1
			when src.tonnages >= 10000 then 0.2
			else 0.0
		end as discount
	from (
		select 
			cukier.dateID as id,
			cukier.nip, 
			cukier.tonnage as tonnage,
			sum(tonnage) over (partition by cukier.nip order by cukier.dateID) tonnages
		from cukier
		join cennik on year(cukier.dateID) = cennik.yearDate
	) as src
) as t;
