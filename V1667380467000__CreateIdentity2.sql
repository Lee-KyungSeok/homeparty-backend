create table t_identity2
(
    sequence bigint auto_increment primary key,
    id       varchar(36) null,
    provider varchar(50) null,
    two      varchar(50) null,
    constraint UK_82vtj290j43vvnj
        unique (id)
);