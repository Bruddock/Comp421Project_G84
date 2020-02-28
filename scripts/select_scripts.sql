/*select all accountants tied to a specific event*/
select staff.employeeid, role, name
from staff join runs on staff.employeeid = runs.employeeid
where runs.acctid = 304;

/*select unpaid invoices*/
select name, emailaddress
from customer
where emailaddress in (select clientemail
                        from invoice
                        where descriptionofservices = 'reservation payment' and status = 'pending')
  and emailaddress IS NOT NULL;

/*select invoices over the year*/
select invoiceid, date, amount from invoice
where extract(year from date) = 2019 and extract(month from date) = 04;

/*select menus that contain at least one dish that is vegetarian.*/
select menuid from menu
where numcourses > 0
intersect
select menuid from contains
where name in (select name
                from dish
                where type = 'vegetarian');

/*select supplier and ingredients involved with name, and invoices with that supplier*/
select address from venue
where kitchen = false
  and exists (select address
                from event
                where attendees > 25);