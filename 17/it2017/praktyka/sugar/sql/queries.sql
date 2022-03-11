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
-- calculating discounts
	-- regular cost
		select 
			cukier.nip as NIP,
			sum(cukier.tonnage * cennik.price) as cost
		from cukier
		join cennik on year(cukier.dateID) = cennik.yearDate
		group by cukier.NIP
		order by cost desc;
    
    -- discounted
		select 
			cukier.nip as NIP,
            case 
				when sum(cukier.tonnage) > 100 and sum(cukier.tonnage) < 100 then sum(cukier.tonnage * (cennik.price - 0.05))
                when sum(cukier.tonnage) > 1000 and sum(cukier.tonnage) < 10000 then sum(cukier.tonnage * (cennik.price - 0.1))
                when sum(cukier.tonnage) > 10000 then sum(cukier.tonnage * (cennik.price - 0.2))
                else sum(cukier.tonnage * cennik.price)
            end as cost
		from cukier
		join cennik on year(cukier.dateID) = cennik.yearDate
		group by cukier.NIP
		order by cost desc;

-- substract regular and discounted cost

select 
	*
from (
(
	select 
		cukier.nip as NIP,
		sum(cukier.tonnage * cennik.price) as cost
	from cukier
	join cennik on year(cukier.dateID) = cennik.yearDate
	order by cost desc
) as regular,

(
	select 
		cukier.nip as NIP,
		case 
			when sum(cukier.tonnage) > 100 and sum(cukier.tonnage) < 100 then sum(cukier.tonnage * (cennik.price - 0.05))
			when sum(cukier.tonnage) > 1000 and sum(cukier.tonnage) < 10000 then sum(cukier.tonnage * (cennik.price - 0.1))
			when sum(cukier.tonnage) > 10000 then sum(cukier.tonnage * (cennik.price - 0.2))
			else sum(cukier.tonnage * cennik.price)
		end as cost
	from cukier
	join cennik on year(cukier.dateID) = cennik.yearDate
	order by cost desc
) as discounted
) group by discounted.NIP