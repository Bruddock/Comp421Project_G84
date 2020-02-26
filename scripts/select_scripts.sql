/*select all staff employed on a specific event)*/
select staff.employeeid, role, name
from staff join staffby on staff.employeeid = staffby.employeeid
where staffby.reservationid = 1093;

/*select unpaid invoices*/
select name, emailaddress
from customer
where emailaddress in (select clientemail
                        from invoice
                        where descriptionofservices = 'reservation payment' and status = 'pending')
  and emailaddress IS NOT NULL;

/*select invoices over the year*/
select invoiceid, date, amount from invoice
where extract(year from date) = 2019;

/*select all vegetarian menus? or menus with alcoholic*/



/*select supplier and ingredients involved with name, and invoices with that supplier*/