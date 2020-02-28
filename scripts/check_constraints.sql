alter table invoice
    add constraint only_one
        check ((clientemail is not null and suppliername is null) or
               (clientemail is null and suppliername is not null) or
               (clientemail is null and suppliername is null));

alter table staff
    add constraint viable_date
        check (employedsince > '2013-01-01')