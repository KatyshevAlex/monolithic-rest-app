DELETE FROM statistic.calls_info WHERE id > 0;

-------------------------------------- сливаем с одной таблицы в другую (результат select положить в таблицу + dblink)
--------------------also перетягиваем все параметры из конструктора в свою БД
insert into roomparms(idroomparm, parmscope, parmtype,allowvalues,defaultvalue,parmname,caption,help) SELECT l.id, l.parmscope, l.parmtype, l.allowvalues, l.defaultvalue, l.parmname, l.caption, l.help FROM 
	dblink('hostaddr=10.247.52.37 port=5433 dbname=ivr_cms port=5432 '::text,
	'SELECT l.id, l.parmscope,l.parmtype, l.allowvalues, l.defaultvalue, l.parmname, l.caption, l.help FROM roomparms l'::text) l
	(id integer, parmscope integer, parmtype integer, allowvalues character varying(256), defaultvalue character varying(256), parmname character varying(32), caption character varying(32), help text);
---------------------------------------------------------------------------

-------------------------------------- выбрать с LIKE
SELECT count(DISTINCT a.msisdn )
FROM statistic.ivr_stat_2019_03 a
where idtype = 4 and idsubtype = 1
--and inserted >= '2017-11-09' and inserted < '2017-11-10'
and params::text like '%Library%'
-------------------------------------- выбрать с LIKE и сравнить
SELECT 
    a.msisdn,b.msisdn,a.inserted,b.inserted
FROM statistic.ivr_stat a
INNER JOIN statistic.ivr_stat b USING (uid)
WHERE 
    a.idtype = 4 AND a.idsubtype = 1 AND b.idtype = 4 AND b.idsubtype = 2
    AND a.inserted BETWEEN DATE '2019-03-01' AND DATE '2019-03-29' AND b.inserted BETWEEN DATE '2019-03-01' AND DATE '2019-03-29'
    AND a.params::text ~ '%Library%' AND b.params::text ~ '%Library%';
-------------------------------------- ЗАМЕНЯЕМ ВСЕ ЗНАКИ ВО ВСЕХ ПОЛЯХ
UPDATE sms.delivery_messages
SET message = regexp_replace(message, '’', '`');
_______________________________________________________________________

-------------------------------------- количество уникальных записей
SELECT count(distinct(msisdn)) FROM billing.billing_stat WHERE
billing_error = -1 
AND
date_service < '2019-10-01 00:00:00'
AND
date_service >= '2019-09-29 00:00:00'


~~~~~~~~

SELECT count(*) from accounts a
inner join subscribers.dispatch_subscribers d on a.msisdn = d.msisdn
inner join (SELECT DISTINCT msisdn FROM billing.billing_stat_ok WHERE date_added > (now()- INTERVAL '90 DAYS') and content_id = 92 group by msisdn)
b on a.msisdn = b.msisdn
WHERE 
(d.dateunsubscriber >= now() and d.iddispatch = 92 and a.sex = 2 AND a.hellomsg IS NOT NULL )

~~~~~~~~

SELECT msisdn, content_id, count(content_id)
FROM billing.billing_stat_2018_09 
WHERE date_added > (now()- INTERVAL '20 DAYS') 
AND (billing_error = 0 OR billing_error = -7) 
GROUP BY content_id,msisdn;
~~~~~~~~
UPDATE subscribers.dispatch_subscribers 
SET dateunsubscriber = (now() + INTERVAL '40 YEARS'), unsubscribe_source = -1  
WHERE msisdn='998935128810'
RETURNING *
															
_______________________________________________________________________

SELECT count(*) FROM  billing.billing_stat_2018_10 WHERE
(billing_error = 0 or billing_error = -7)
AND
date_service >= '2018-10-13  20:16:43'
AND
date_added < '2018-10-13 22:38:30'
----------------------------------------------------------------------------------------
SELECT count(distinct(msisdn)) FROM billing.billing_stat WHERE
billing_error = -1 
AND
date_service < '2018-09-30 23:59:59'
AND
date_service >= '2018-09-30 00:00:00'

======================errors=======================
	13		NOT_DEF_ERROR(-6),        
	14273		BAD_DREQUEST(-1),           // Bad request. Some parameters missing / Tariffication group not permited(Postpay)       
	44		WRONG_MSISDN(-4),           // Incorrect MSISDN format
	125		OPERATION_ERROR1(-5),       // Throttling error

====================================================
				all of subscribers
====================================================
SELECT count(*) FROM billing.get_billing_queue


~~~~~~~~
<-- = 179698 Подлежат таррификации -->
SELECT count(*) FROM subscribers.get_billing_subscribers; 
<-- = 180882 всего подписанных(разница в тех у кого есть бесплатный период)-->
SELECT count(*) FROM subscribers.get_active_subscribers; 

~~~~~~~~
<--87237 уникальных номеров с успешной тарификацией за последние 20 дней
SELECT count(distinct(msisdn)) FROM billing.billing_stat_2018_09 WHERE date_added > (now()- INTERVAL '20 DAYS') AND (billing_error = 0 OR billing_error = -7);
~~~~~~~~




create index concurrently "<имя>"
on <таблица> using btree(<поле>)





~~~~~~~~
<--33961 успешных тарификации за 30 сентября 
SELECT count(*) FROM billing.billing_stat_2018_09 WHERE date_added >= '2018-09-30 00:00:00' AND date_added < '2018-09-30 23:59:59' AND (billing_error = 0 OR billing_error = -7);

~~~~~~~~

SELECT msisdn, content_id, count(content_id)
FROM billing.billing_stat_2018_09 
WHERE date_added > (now()- INTERVAL '20 DAYS') 
AND (billing_error = 0 OR billing_error = -7) 
GROUP BY content_id,msisdn;

~~~~~~~~

SELECT count(*) FROM billing.billing_stat_2018_09 a
INNER JOIN billing.billing_stat_2018_09 b
WHERE 
date_added > (now()- INTERVAL '20 DAYS') AND 
(billing_error = 0 OR billing_error = -7) AND
(content_id)

; <-- = 87267 -->

``````````````````````````````````````
select TIMESTAMP 'yesterday';
select DATE 'yesterday';
select TIMESTAMP 'today';
select DATE 'today';
select TIMESTAMP 'tomorrow';
select DATE 'tomorrow';
select TIME 'allballs';
select now();
select TIMESTAMP 'now';
select DATE 'now';

 SELECT DISTINCT b.msisdn,
    b.iddispatch
   FROM billing.get_payments b
  WHERE b.date_service BETWEEN 
  TIMESTAMP 'yesterday'+'21h' AND
  TIMESTAMP 'today'+'21h';

```````````````````````````````````````````
посчитать повторы
https://stackoverflow.com/questions/2594829/finding-duplicate-values-in-a-sql-table

SELECT
    msisdn, iddispatch, COUNT(*)
FROM
    billing.get_payments_today
GROUP BY
    msisdn, iddispatch
HAVING 
    COUNT(*) > 1
	
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
CREATE OR REPLACE FUNCTION subscribers.is_can_priv(_msisdn character varying,_dispatch integer)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    VOLATILE
    PARALLEL UNSAFE
    COST 100
AS $BODY$
declare
  ret boolean=false;
BEGIN                                                                 
  select 
  <-- каждый count  фактически if который проверяет что-бы небыло совпадений по этим условиям -->
  (SELECT count(*) = 0 FROM subscribers.phone_priv p 
  inner join subscribers.dispatch_list d on p.iddispatch = d.id 
  WHERE p.msisdn = _msisdn and p.iddispatch = _dispatch 
  and p.date_end > (now() - d.no_free_period)) 
  
  AND
  
  (SELECT count(*) = 0 FROM blacklist p WHERE p.dst = _msisdn and p."type" = 3) 
  
  into ret;
    
  return ret;                         
END;

$BODY$;
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

CREATE OR REPLACE FUNCTION public.getf_common_data2()
    RETURNS TABLE(msisdn character varying, status text, datetimestarted text, datetimetrialended text, datetimestoped text, datepaiduntil text, datetimelastcharge text, datetimelastsuccesscharge text, langid integer, iddispatch integer, issubscribedatoperatorside integer)
    LANGUAGE 'plpgsql'
    VOLATILE
    PARALLEL UNSAFE
    COST 100    ROWS 1000 
AS $BODY$BEGIN

  RETURN query SELECT 
  a.msisdn, 
  a.status, 
  a.datetimeStarted, 
  a.datetimeTrialEnded, 
  a.datetimeStoped,
  (case WHEN a.idDispatch > 100 then a.datetimeTrialEnded else a.datePaidUntil end) as datePaidUntil,
  COALESCE(a.datetimeLastCharge, a.datetimeStarted) as datetimeLastCharge,
  (case WHEN a.idDispatch > 100 then a.datetimeStarted when a.datetimeLastSuccessCharge is null then a.datetimeStarted else a.datetimeLastSuccessCharge end) as datetimeLastSuccessCharge,
  a.langID, 
  a.idDispatch,
  (case WHEN a.idDispatch > 100 then 0 else 1  end) as isSubscribedAtOperatorSide
    FROM ( 
      SELECT s.msisdn, 
      case WHEN now() BETWEEN s.datesubscriber and s.dateunsubscriber then 'ACTIVE' else 'NOT_ACTIVE' end as status,
      to_char(s.datesubscriber, 'YYYY-MM-DD HH24:MI:SS') as datetimeStarted,
      case WHEN s.idDispatch > 100 then to_char(s.dateunsubscriber, 'YYYY-MM-DD') else null end as datetimeTrialEnded,
      to_char(s.dateunsubscriber, 'YYYY-MM-DD HH24:MI:SS') as datetimeStoped,
      to_char(now(), 'YYYY-MM-DD') as datePaidUntil,
      to_char(b2.date_service, 'YYYY-MM-DD HH24:MI:SS') as datetimeLastCharge,
      to_char(b1.date_service, 'YYYY-MM-DD HH24:MI:SS') as datetimeLastSuccessCharge,
      case when a.lang='ru' then 1 when a.lang='kg' then 3 end as langID,
      s.iddispatch as idDispatch
      FROM subscribers.dispatch_subscribers s      
      inner join public.accounts a on a.msisdn = s.msisdn  
      inner join billing.billing_stat_err be on a.msisdn = be.msisdn 
      inner join (
        select max(s2.id) as id, s2.msisdn,
        case when s2.iddispatch > 100 then s2.iddispatch-100 else s2.iddispatch end as iddispatch
        from subscribers.dispatch_subscribers s2
        group by s2.msisdn, case when s2.iddispatch > 100 then s2.iddispatch-100 else s2.iddispatch end
      ) s1 on s1.id = s.id    
      left outer join (
        select max(b11.date_service) as date_service, b11.msisdn, b11.content_id
        from billing.billing_stat_ok b11
        group by b11.msisdn, b11.content_id
      ) b1 on b1.msisdn = s.msisdn and b1.content_id = s.iddispatch
      left outer join (
        select max(b21.date_service) as date_service, b21.msisdn, b21.content_id
        from billing.billing_stat b21
        group by b21.msisdn, b21.content_id
      ) b2 on b2.msisdn = s.msisdn and b2.content_id = s.iddispatch     
      WHERE be.date_added > '2019-12-06 00:10:00' and be.billing_error IN (-200,-300,-6)
    ) a;
END;
$BODY$;


CREATE OR REPLACE FUNCTION public.get_random_top_packages(integer)
    RETURNS SETOF record
    LANGUAGE 'plpgsql'
    VOLATILE
    PARALLEL UNSAFE
    COST 100    ROWS 1000 
AS $BODY$DECLARE 
    _type integer := $1;
    _k integer;
        _stepcount integer:=0;        
        rec record;              
BEGIN         
    FOR _k IN SELECT id FROM tops WHERE idtype=_type ORDER BY RANDOM()*7 LOOP                 
        _stepcount:=_stepcount+1;           
        FOR rec in SELECT _stepcount::integer, m.idmelody::integer, songername::text, songname::text, rating::integer, idtop::integer,
        code::text, path::text, m.isactive, m.idcategory, m.rbt_code 
        FROM melodies m 
        JOIN melodies_in_tops mt on m.idmelody = mt.idmelody
        WHERE (mt.idtop = _k) ORDER BY mt.rating LOOP               
        return NEXT  rec;        
        END LOOP;
    END LOOP;
END;
$BODY$;




--Супер функция копирования из одной БД в другую  - просто добавь в query
insert into melodies select *
from dblink('hostaddr=10.230.17.3 port=5434 dbname=icmp_content',
            'SELECT 
			idmelody, songername, songname, code, path, isactive, idcategory, buycount,
			idgenre, upload_date, idtype, mp3_path, limitcount, limitday, idserver, replypattern, rbt_code, price 
			FROM melodies')
       as t(idmelody integer, songername text, songname text, code text, path text, isactive boolean, idcategory integer, buycount bigint,  
		   idgenre smallint, upload_date timestamp, idtype integer, mp3_path text, limitcount integer, limitday integer, idserver integer,
		   replypattern text, rbt_code integer, price integer );




~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SELECT p.paramval, p.msisdn 
FROM phone_param AS p 
inner join accounts AS a 
on p.msisdn = a.msisdn 
where  age = 2 and paramid=16 and paramval LIKE 'F%'

-- удалить по LIKE + join
  delete from phone_param 
  where id in (
      SELECT p.id FROM phone_param p
      inner join autocall.callshistory c on p.msisdn = c.destination
      where c.reason > -1
	  and c.destination = p.msisdn 
	  and p.paramid = 19
	  and p.paramval LIKE CONCAT('{idAutocall=',c.id_autocall,'%');
	 )


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
--простой group by считает за каждый день все записи в течении месяца
	select dateupdate::date, cast(count(*) as integer)
    from public.sms_to_send_failed
	where dateupdate > '2019-04-29 00:00:00'
	group by dateupdate::date
	order by 1

--считает выручку, группируя по полю source
 SELECT source, count(*)
   FROM sms.sms_history_billing b
  WHERE b.isprocessed = true 
  AND dateadded < '2019-07-1 00:00:00' and dateadded > '2019-06-20 00:00:00'
  AND b.lasterror IS NULL
  group by source;


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
CREATE TABLE celebrities
(
  id   INTEGER   PRIMARY KEY,
  name         TEXT,
  msisdn        TEXT      NOT NULL,
  room_number   INTEGER,
  status       BOOLEAN default false
);


CREATE TABLE calls
(
  id INTEGER PRIMARY KEY,
  status TEXT,
  error_message TEXT,
  phone TEXT,
  request_id TEXT,
  call_time timestamp without time zone DEFAULT now()
);

CREATE SEQUENCE public.sq_calls
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.sq_calls
    OWNER TO postgres;

-----------------------------------------------BILLING
--выручка

select count(*) from billing.billing_stat_ok where 
	date_service > (TIMESTAMP 'yesterday'+'21h')  
--	- INTERVAL '1 DAY'
	and date_service < now()
--	- INTERVAL '1 DAY'


----------------------------------------------------------------------------------------------------------------USEFUL
--если у нас есть записи за 31 число можно продублировать их и за другое число
--для этого делаем селект, но там где обычно * указываем поля '- 1 day'
--и всё это пишется под командой инсерта, поэтому у нас добавляются новые поля с другими датами

INSERT INTO monitoring.criterions(monitor_name, start_timestamp, end_timestamp, average, median, sigma, min_threshold, max_threshold, alarm_indicator, measured_data, project, server_name)
	SELECT monitor_name, start_timestamp - INTERVAL'1 day', end_timestamp - INTERVAL'1 day', average, median, sigma, min_threshold, max_threshold, alarm_indicator, measured_data, project, server_name FROM monitoring.criterions
		WHERE project = 2 and start_timestamp >= '2019-05-31 00:00:00'
		ORDER BY id desc 




----------------------------------------------------------------------------------------------------------------USEFUL
SELECT generate_series('2019-05-20 00:00:00'::date, '2019-05-21 00:00:00'::date - (('300000'||' milliseconds')::interval), ('300000'||' milliseconds')::interval) as min

--------

f_ivr_stat_bi_tr ----  тригер в статистике
закоминтировать эту запись = отключить статистику в админке

/*
  sql_query := 'select statistic.get_partition('||new.idtype||');';
  EXECUTE sql_query into table_name;

  sql_query :=  'insert into '||table_name||' select $1.*';
  EXECUTE sql_query USING new;   
*/

------------ucell triger
SELECT oid, * FROM statistic.calls_info
WHERE date_close is null
and date_call < now() - INTERVAL'20 minutes'


----------------------------------------------------------------------------------какие запросы висят
SELECT datname, NOW() - query_start AS duration, 
    pid, query
    FROM pg_stat_activity where state <> 'idle'
    ORDER BY duration DESC; 
-----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------узбеки
--
SELECT * 
FROM phone_areas a 
INNER JOIN statistic.ivr_stat_1 s 
ON s.msisdn != 'anonymous' and cast(s.msisdn AS BIGINT) = a.begcode
WhERE idtype = 1 and idsubtype = 1 
and s.inserted > now() - INTERVAL'1 day' 
-----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------
--выбрать какое поле вернется у записи после инсерта - например id который генериться внутри БД
--returning id
            retId = ((BigInteger)session.createSQLQuery("INSERT INTO public.recordings(msisdn, prefix, path) "
                    + "VALUES (cast(:msisdn as varchar), cast(:prefix as varchar), cast(:path as varchar)) returning id")
                    .setParameter("msisdn", msisdn)
                    .setParameter("prefix", prefix)
                    .setParameter("path", path)
                    .uniqueResult()).longValue();

-----------------------------------------------------------------------------------------------------
