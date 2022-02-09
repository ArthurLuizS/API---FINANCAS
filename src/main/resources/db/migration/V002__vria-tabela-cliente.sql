create table contas (
	id bigint not null auto_increment,
	cliente_id bigint not null,
	numero_conta varchar(20),
	saldo_inicial float(10, 2),
	saldo float(10, 2),
    
    primary key(id)
);

alter table contas add constraint fk_contas_cliente
foreign key (cliente_id) references cliente(id)
