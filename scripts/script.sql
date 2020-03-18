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


