create table vehicle.tb_vehicle (
    id bigserial not null,
    code varchar(255) not null,
    cor varchar(255) not null,
    pic varchar(255) not null,
    vehicle_year int not null,
    description varchar(255) not null,
    price numeric(19,2) not null,
    vehicle_type_id bigint not null,
    model_id bigint not null,
    vehicle_status varchar(255) not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id)
);