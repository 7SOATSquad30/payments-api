create table tb_payment (
    amount float(53) not null check ((amount>=0) and (amount<=10000)),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    order_id bigint unique not null,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status varchar(255) check (status in ('PENDING','REJECTED','COLLECTED')),
    primary key (order_id)
);