use exercise_sweet2017;

-- Zadanie 4.1
select
	NIP as NIP,
    sum(tonnage) as Tonnage
from cukier
group by NIP
order by Tonnage desc
limit 3;