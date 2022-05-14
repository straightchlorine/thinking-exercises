use exercise_languages2020;

-- 1
select 
	jezyki.Rodzina,
	count(jezyki.Jezyk) as total
from jezyki
group by jezyki.rodzina
order by total desc;

-- 2
select
	count(*)
from (
select
	t.lang,
    sum(id) as total
from (
select
	jezyki.Jezyk as lang,
    uzytkownicy.Urzedowy,
    case 
		when uzytkownicy.Urzedowy = "nie" then 0
        else 1
	end as id
from jezyki
join uzytkownicy on uzytkownicy.Jezyk = jezyki.Jezyk
join panstwa on uzytkownicy.Panstwo = panstwa.Panstwo) as t
group by t.lang
having total = 0) as a;

-- 3
select
	a.lang_out
from (
	select distinct 
		t.lang as lang_out, 
		count(t.continent) over (partition by t.lang) as total
	from (
		select distinct
			uzytkownicy.Jezyk as lang,
			panstwa.Kontynent as continent
		from uzytkownicy
		join panstwa on panstwa.Panstwo = uzytkownicy.Panstwo
	) as t
) as a
where total >= 4;

-- 4
select
	t.lang,
    t.family,
    round(t.total, 2) as Total_users
from (
	select
		jezyki.Jezyk as lang,
		jezyki.Rodzina as family,
		sum(uzytkownicy.Uzytkownicy) as total
	from jezyki
	join uzytkownicy on jezyki.Jezyk = uzytkownicy.Jezyk
	join panstwa on panstwa.Panstwo = uzytkownicy.Panstwo
	where (panstwa.Kontynent = "Ameryka Polnocna" or 
		  panstwa.Kontynent = "Ameryka Poludniowa") and 
		  jezyki.Rodzina != "indoeuropejska"
	group by jezyki.Jezyk, jezyki.Rodzina
	order by total desc
) as t
limit 6;

-- 5
select
	panstwa.Panstwo,
	uzytkownicy.Jezyk,
    round(((uzytkownicy.Uzytkownicy / panstwa.Populacja) * 100), 2) as percentage
from uzytkownicy
join panstwa on uzytkownicy.Panstwo = panstwa.Panstwo
where uzytkownicy.Urzedowy = "nie"
having percentage >= 30
order by percentage desc;
