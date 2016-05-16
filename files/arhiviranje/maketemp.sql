drop table if exists temp1;
drop table if exists temp2; 
create table temp1 (sys_id integer not null);
create table temp2 (sys_id integer not null);
drop procedure if exists temp1;
drop procedure if exists temp2;
delimiter $$
create procedure temp1()
begin
  declare done int default false;
  declare sysid integer;
  declare cur1 cursor for SELECT distinct(u.sys_id) FROM bisis.users u,bisis.signing s where u.sys_id=s.sys_id and '2010-01-01' > all(select s1.until_date from bisis.signing s1 where u.sys_id=s1.sys_id) and not exists (select l.sys_id from bisis.lending l where l.sys_id = u.sys_id and return_date is null);
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  open cur1;  
  loop1: LOOP
    fetch cur1 into sysid;
    if done then
      close cur1;
      leave loop1;
    end if;
    insert into temp1 values (sysid);
    commit;
  end loop loop1;
  
end;
$$
create procedure temp2()
begin
  declare done int default false;
  declare sysid integer;
  declare cur2 cursor for SELECT u.sys_id FROM bisis.users u where not exists(select s.sys_id from bisis.signing s where u.sys_id=s.sys_id) and not exists (select l.sys_id from bisis.lending l where l.sys_id = u.sys_id and return_date is null);
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  open cur2;  
  loop2: LOOP
    fetch cur2 into sysid;
    if done then
      close cur2;
      leave loop2;
    end if;
    insert into temp2 values (sysid);
    commit;
  end loop loop2;
  
end;
$$
