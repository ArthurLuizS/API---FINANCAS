create table transacoes(
		id bigint not null auto_increment,
        conta_id bigint not null,
        valor float(10, 2) not null,
        tipo_movimentacao varchar(10),
        data_registro datetime not null,
        primary key(id)
);

alter table transacoes add constraint fk_transacoes_conta
foreign key(conta_id) references contas(id);