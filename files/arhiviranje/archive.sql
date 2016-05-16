drop procedure if exists bisis.archive;
drop procedure if exists bisis.archiveall;
delimiter $$
create procedure bisis.archive(sysid integer) 
begin
  insert into arhiva.users select * from bisis.users where sys_id=sysid;
  insert into arhiva.lending select * from bisis.lending where sys_id=sysid;
  insert into arhiva.signing select * from bisis.signing where sys_id=sysid;
  insert into arhiva.picturebooks select * from bisis.picturebooks where sys_id=sysid;
  insert into arhiva.warnings select a.id, a.lending_id, a.wdate, a.wtype, a.warn_no, a.deadline, a.note from bisis.warnings a, bisis.lending b where b.sys_id=sysid and a.lending_id=b.id;
  insert into arhiva.duplicate select * from bisis.duplicate where sys_id=sysid;

  delete from bisis.warnings where lending_id in (select id from bisis.lending where sys_id=sysid);
  delete from bisis.duplicate where sys_id=sysid;
  delete from bisis.picturebooks where sys_id=sysid;
  delete from bisis.signing where sys_id=sysid;
  delete from bisis.lending where sys_id=sysid;
  delete from bisis.users where sys_id=sysid;
end;
$$
create procedure archiveall()
begin
  declare done int default false;
  declare sysid integer;
  declare count integer;
  declare cur1 cursor for SELECT sys_id FROM temp1;
  declare cur2 cursor for SELECT sys_id FROM temp2;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  set count = 0;
  open cur1;
  cur1_loop: LOOP
    fetch cur1 into sysid;
    if done then
      close cur1;
      leave cur1_loop;
    end if;
    call archive(sysid);
    set count = count + 1;
    if count % 1000 = 0 then
      select count;
    end if;
    commit;
  end loop cur1_loop;
  set done = false;  
  open cur2;
  cur2_loop: LOOP
    fetch cur2 into sysid;
    if done then
      close cur2;
      leave cur2_loop;
    end if;
    call archive(sysid);
    set count = count + 1;
    if count % 1000 = 0 then
      select count;
    end if;
    commit;
  end loop cur2_loop;
end;
$$
#SELECT distinct(u.user_id) FROM users u,signing s where u.sys_id=s.sys_id and :date > all(select s1.until_date from signing s1 where u.sys_id=s1.sys_id) and not exists (select l.sys_id from lending l where l.sys_id = u.sys_id and return_date is null)