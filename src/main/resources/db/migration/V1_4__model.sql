create table vehicle.tb_model (
    id bigserial not null,
    name varchar(255) not null,
    brand_id bigint not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id)
);

ALTER TABLE vehicle.tb_model ADD CONSTRAINT fk_brand_model FOREIGN KEY (brand_id) REFERENCES vehicle.tb_brand(id);