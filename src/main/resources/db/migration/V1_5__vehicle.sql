create table vehicle.tb_vehicle (
    id bigserial not null,
    code varchar(255) not null,
    cor varchar(255) not null,
    vehicle_year int not null,
    km int not null,
    description varchar(255) not null,
    price numeric(19,2) not null,
    vehicle_category_id bigint not null,
    model_id bigint not null,
    media_id bigint not null,
    vehicle_status varchar(255) not null,
    vehicle_type varchar(255) not null,
    location varchar(255) not null,
    vehicle_fuel varchar(255) not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id)
);

ALTER TABLE vehicle.tb_vehicle ADD CONSTRAINT fk_vehicle_model FOREIGN KEY (model_id) REFERENCES vehicle.tb_model(id);
ALTER TABLE vehicle.tb_vehicle ADD CONSTRAINT fk_vehicle_media FOREIGN KEY (media_id) REFERENCES vehicle.tb_media(id);
ALTER TABLE vehicle.tb_vehicle ADD CONSTRAINT fk_vehicle_vehicle_category FOREIGN KEY (vehicle_category_id) REFERENCES vehicle.tb_vehicle_category(id);