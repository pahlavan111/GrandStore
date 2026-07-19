
create table public.orders
(
    id          bigint generated always as identity
        constraint orders_pk
            primary key,
    customer_id bigint                              not null
        constraint orders_users_id_fk
            references public.users(id),
    status      varchar(20)                         not null,
    created_at  TIMESTAMP default current_timestamp not null,
    total_price decimal(10, 2)                      not null
);

create table public.order_items
(
    id           bigint generated always as identity
        constraint order_items_pk
        primary key,
    order_id     bigint         not null
        constraint order_items_order_id_fk
            references public.orders(id),
    product_id   bigint         not null
        constraint order_items_product_id_fk
            references public.products(id),
    unique_price decimal(10, 2) not null,
    quantity     integer        not null,
    total_price  decimal(10, 2) not null
);

