/*select all staff employed on a specific event)*/
select name, role, employeeid from staff
where employeeid = (select employeeid from staffby where edate = '2019-02-14' and etime > '08:00:00')

/*select unpaid invoices*/


/*select invoices over the year*/

/*select all vegetarian menus? or menus with alcoholic*/

/*select supplier and ingredients involved with name, and invoices with that supplier*/