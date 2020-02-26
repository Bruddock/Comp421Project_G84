create table customer
(
    acctcreated  date,
    lastorder    date,
    emailaddress varchar(30) not null
        constraint customer_pkey
            primary key,
    phonenumber  bigint,
    name         varchar(20)
);

alter table customer
    owner to cs421g84;

create table reservation
(
    rdate         date,
    rtime         time,
    reservationid integer not null
        constraint reservation_pkey
            primary key
);

alter table reservation
    owner to cs421g84;

create table invoice
(
    date                  date,
    amount                numeric,
    descriptionofservices varchar(100),
    invoiceid             integer not null
        constraint invoice_pkey
            primary key,
    status                varchar(20),
    seller                varchar(20),
    buyer                 varchar(20)
);

alter table invoice
    owner to cs421g84;

create table supplier
(
    delivers    boolean,
    companyname varchar(20) not null
        constraint supplier_pkey
            primary key,
    address     varchar(20)
);

alter table supplier
    owner to cs421g84;

create table dish
(
    name               varchar(20) not null
        constraint dish_pkey
            primary key,
    preptime           time,
    type               varchar(20),
    dietaryrestriction varchar(20),
    frozen             boolean
);

alter table dish
    owner to cs421g84;

create table menu
(
    numcourses integer,
    menuid     integer not null
        constraint menu_pkey
            primary key,
    seasonal   varchar(20)
);

alter table menu
    owner to cs421g84;

create table event
(
    corporate        boolean,
    staffamount      integer,
    etime            time    not null,
    mature           boolean,
    eventdescription varchar(100),
    edate            date    not null,
    attendees        integer,
    reservationid    integer not null
        constraint event_reservationid_fkey
            references reservation,
    constraint event_pkey
        primary key (edate, etime, reservationid)
);

alter table event
    owner to cs421g84;

create unique index event_etime_uindex
    on event (etime);

create unique index event_edate_uindex
    on event (edate);

create table venue
(
    address   varchar(20) not null
        constraint venue_pkey
            primary key,
    kitchen   boolean,
    tableware boolean,
    rented    boolean
);

alter table venue
    owner to cs421g84;

create table staff
(
    role          varchar(20),
    employedsince date,
    name          varchar(20),
    employeeid    integer not null
        constraint staff_pkey
            primary key
);

alter table staff
    owner to cs421g84;

create table account
(
    acctid integer not null
        constraint account_pkey
            primary key
);

alter table account
    owner to cs421g84;

create table accountreceivable
(
    revenue integer,
    acctid  integer not null
        constraint accountreceivable_pkey
            primary key
        constraint accountreceivable_acctid_fkey
            references account
);

alter table accountreceivable
    owner to cs421g84;

create table accountpayable
(
    budget integer,
    acctid integer not null
        constraint accountpayable_pkey
            primary key
        constraint accountpayable_acctid_fkey
            references account
);

alter table accountpayable
    owner to cs421g84;

create table initiates
(
    reservationid integer not null
        constraint initiates_reservationid_fkey
            references reservation,
    edate         date    not null
        constraint initiates_edate_fkey
            references event (edate),
    etime         time    not null
        constraint initiates_etime_fkey
            references event (etime),
    constraint initiates_pkey
        primary key (reservationid, edate, etime)
);

alter table initiates
    owner to cs421g84;

create table reserves
(
    reservationid integer     not null
        constraint reserves_reservationid_fkey
            references reservation,
    emailaddress  varchar(35) not null
        constraint reserves_emailaddress_fkey
            references customer,
    constraint reserves_pkey
        primary key (reservationid, emailaddress)
);

alter table reserves
    owner to cs421g84;

create table located
(
    address varchar(20) not null
        constraint located_address_fkey
            references venue,
    edate   date        not null
        constraint located_edate_fkey
            references event (edate),
    etime   time        not null
        constraint located_etime_fkey
            references event (etime),
    constraint located_pkey
        primary key (address, edate, etime)
);

alter table located
    owner to cs421g84;

create table reservationpayment
(
    emailaddress varchar(30) not null
        constraint reservationpayment_emailaddress_fkey
            references customer,
    acctid       integer     not null
        constraint reservationpayment_acctid_fkey
            references accountreceivable,
    invoiceid    integer     not null
        constraint reservationpayment_invoiceid_fkey
            references invoice,
    constraint reservationpayment_pkey
        primary key (emailaddress, invoiceid, acctid)
);

alter table reservationpayment
    owner to cs421g84;

create table supplypayment
(
    companyname varchar(20) not null
        constraint supplypayment_companyname_fkey
            references supplier,
    acctid      integer     not null
        constraint supplypayment_acctid_fkey
            references accountpayable,
    invoiceid   integer     not null
        constraint supplypayment_invoiceid_fkey
            references invoice,
    constraint supplypayment_pkey
        primary key (companyname, invoiceid, acctid)
);

alter table supplypayment
    owner to cs421g84;

create table salary
(
    acctid     integer not null
        constraint salary_acctid_fkey
            references accountpayable,
    employeeid integer not null
        constraint salary_employeeid_fkey
            references staff,
    constraint salary_pkey
        primary key (employeeid, acctid)
);

alter table salary
    owner to cs421g84;

create table staffby
(
    employeeid integer not null
        constraint staffby_employeeid_fkey
            references staff,
    edate      date    not null
        constraint staffby_edate_fkey
            references event (edate),
    etime      time    not null
        constraint staffby_etime_fkey
            references event (etime),
    constraint staffby_pkey
        primary key (employeeid, edate, etime)
);

alter table staffby
    owner to cs421g84;

create table runs
(
    employeeid integer not null
        constraint runs_employeeid_fkey
            references staff,
    acctid     integer not null
        constraint runs_acctid_fkey
            references account,
    constraint runs_pkey
        primary key (employeeid, acctid)
);

alter table runs
    owner to cs421g84;

create table prepares
(
    menuid     integer not null
        constraint prepares_menuid_fkey
            references menu,
    employeeid integer not null
        constraint prepares_employeeid_fkey
            references staff,
    constraint prepares_pkey
        primary key (menuid, employeeid)
);

alter table prepares
    owner to cs421g84;

create table contains
(
    menuid integer     not null
        constraint contains_menuid_fkey
            references menu,
    name   varchar(20) not null
        constraint contains_name_fkey
            references dish,
    constraint contains_pkey
        primary key (menuid, name)
);

alter table contains
    owner to cs421g84;

create table ingredients
(
    companyname    varchar(20) not null
        constraint ingredients_companyname_fkey
            references supplier,
    name           varchar(20) not null
        constraint ingredients_name_fkey
            references dish,
    ordered        date,
    received       date,
    quantity       integer,
    ingredientname varchar(35),
    constraint ingredients_pkey
        primary key (companyname, name)
);

alter table ingredients
    owner to cs421g84;

create table drink
(
    name   varchar(20) not null
        constraint drink_pkey
            primary key
        constraint drink_name_fkey
            references dish,
    mature boolean
);

alter table drink
    owner to cs421g84;

create table has
(
    menuid integer not null
        constraint has_menuid_fkey
            references menu,
    edate  date    not null
        constraint has_edate_fkey
            references event (edate),
    etime  time    not null
        constraint has_etime_fkey
            references event (etime),
    constraint has_pkey
        primary key (menuid, edate, etime)
);

alter table has
    owner to cs421g84;


