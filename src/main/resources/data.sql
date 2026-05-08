INSERT INTO ESPECIE (especie_id, descricao,ind_rastejante,ind_voador) VALUES
	 (1,'Papagaio',0,0),
	 (2,'Ave',0,1),
	 (3,'C�O',0,0),
	 (4,'GATO',0,0),
	 (5,'REPTIL',0,0),
	 (6,'TARTARUGA',0,0),
	 (7,'COBRA',1,0),
	 (8,'Lagarto',0,0),
	 (9,'Coelho',0,0),
	 (10,'Abelha',0,1),
	 (11,'Anf�bio',0,0);

INSERT INTO PET (pet_id, nome,data_nascimento,especie_id,raca,cor,porte,detalhes) VALUES
	 (1, 'Jaraqu�','946692000000',5,'Papo Amarelo','verde e amarelo',NULL,NULL),
	 (2, 'Loro','592455600000',1,'Real','verde amarelo vermelho azul e etc',NULL,NULL),
	 (3, 'Danger','1129773600000',3,'Dobermann','Preto e marrom',NULL,NULL),
	 (4, 'Pirulito','1097377200000',2,NULL,NULL,NULL,NULL);

INSERT INTO REGISTRO_PERDA (pet_id,data_perda,local_perda,latitude,longitude,detalhes,valores_busca) VALUES
	 (1,'1778036400000','Vila nova cachoeirinha',NULL,NULL,NULL,'jaraque jacare papo amarelo verde e amarelo vila nova cachoeirinha'),
	 (2,'1777777200000','Vila nova cachoeirinha',NULL,NULL,NULL,'loro papagaio real verde amarelo vermelho azul e etc vila nova cachoeirinha'),
	 (3,'1776049200000','Esquina da Rua Nova York com Av Aurora',NULL,NULL,NULL,'danger cao dobermann preto e marrom esquina da rua nova york com av aurora'),
	 (4,'1760065200000','Voou pela janela do edificio Yotk Shine',NULL,NULL,NULL,'pirulito ave voou pela janela do edificio yotk shine');
