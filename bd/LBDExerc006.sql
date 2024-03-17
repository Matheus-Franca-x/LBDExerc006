CREATE DATABASE LBDExerc006
GO
USE LBDExerc006
GO

CREATE TABLE cliente
(
	cpf				CHAR(11) 		NOT NULL,
	nome			VARCHAR(100) 	NOT NULL,
	email			VARCHAR(200) 	NOT NULL,
	lim_credito		DECIMAL(7, 2) 	NOT NULL,
	dt_nasc			DATE			NOT NULL
	PRIMARY KEY (cpf)
)
--Criar uma procedure que permita fazer o insert, update e delete de um cliente garantindo que:
--- Para insert, o CPF deve ser válido e não pode ser 11 números repetidos (11111111111, 22222222222, etc)
--- Para update, não se pode alterar o CPF

	
DECLARE @test INT
EXEC sp_verifica_cpf '11111111111', @test OUTPUT
PRINT @test


	
DECLARE @out VARCHAR(100)
EXEC sp_crud_cliente 'I', '51888485892', 'Matheus', 'Matheus@hotmail.com', 200.00, '2000-03-01', @out OUTPUT
PRINT @out

