update Primerci set stanje = 0;
update Primerci set stanje = 1 where inv_broj in (select ctlg_no from lending where return_date is null);



DROP TABLE IF EXISTS temp_primerci;
DROP TABLE IF EXISTS temp_primerci1;
DROP TABLE IF EXISTS temp_lending;
CREATE TEMPORARY TABLE temp_lending ( 
    ctlg_no varchar(25) default null,
    sys_id int, 
    lend_date datetime,
    librarian_lend varchar(255),
    resume_date datetime,
    librarian_resume varchar(255),
    index (ctlg_no)
    ) SELECT l.ctlg_no as ctlg_no , sys_id as sys_id, lend_date as lend_date, librarian_lend as librarian_lend, 
        resume_date as resume_date, librarian_resume as librarian_resume
        from lending l where l.return_date is null;
CREATE TEMPORARY TABLE temp_primerci (
    inv_broj varchar(25) default null,
	record_id int,
	stanje int,
    index (inv_broj)
    ) SELECT inv_broj as inv_broj , record_id as record_id, stanje as stanje FROM Primerci p WHERE p.stanje = "0";

CREATE TEMPORARY TABLE temp_primerci1 (
    inv_broj varchar(25) default null,
	record_id int,
	stanje int,
    index (inv_broj)
    ) SELECT inv_broj as inv_broj , record_id as record_id, stanje as stanje FROM Primerci p WHERE p.stanje = "1";

SELECT * FROM temp_primerci, temp_lending WHERE temp_lending.ctlg_no=temp_primerci.inv_broj;

SELECT tp.inv_broj, tp.record_id, tl.lend_date, tl.librarian_lend, tl.resume_date, tl.librarian_resume, u.user_id, u.first_name, u.last_name FROM temp_primerci tp, temp_lending tl, users u WHERE tl.ctlg_no=tp.inv_broj and tl.sys_id = u.sys_id;

SELECT * FROM temp_primerci1 WHERE inv_broj not in (select ctlg_no from temp_lending);

DROP TABLE IF EXISTS temp_primerci;
DROP TABLE IF EXISTS temp_primerci1;
DROP TABLE IF EXISTS temp_lending;
