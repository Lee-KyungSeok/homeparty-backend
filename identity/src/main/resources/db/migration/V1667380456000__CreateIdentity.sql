create table t_identity
(
    sequence bigint auto_increment primary key,
    id       varchar(36) null,
    provider varchar(50) null,
    constraint UK_82vtj290j43vvnjtt7un0vdcy
        unique (id)
);