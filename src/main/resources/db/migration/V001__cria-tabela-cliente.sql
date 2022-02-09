create table cliente (

	id bigint not null auto_increment,
    nome varchar(60) not null,
    endereco varchar(60) not null,
    tipo_cliente varchar (2) not null,
    identificador_cliente varchar(20) not null,
    telefone varchar(20) not null,
    
    primary key(id)
    
    )