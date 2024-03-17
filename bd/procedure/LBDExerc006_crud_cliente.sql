USE LBDExerc006

DROP PROCEDURE sp_crud_cliente

CREATE PROCEDURE sp_crud_cliente 
(
	@op CHAR(1), 
	@cpf CHAR(11), 
	@nome VARCHAR(100), 
	@email VARCHAR(200), 
	@lim_credito DECIMAL(7, 2), 
	@dt_nasc DATE, 
	@saida VARCHAR(100) OUTPUT
)
AS
	DECLARE @cpf_valido INT
	EXEC sp_verifica_cpf @cpf, @cpf_valido OUTPUT
	IF (@cpf_valido = 0)
	BEGIN 
		DECLARE @cpf_existe CHAR(11)
			SET @cpf_existe = (SELECT cpf FROM cliente WHERE cpf = @cpf)
		IF (UPPER(@op) = 'I')
		BEGIN
			IF (@cpf_existe IS NULL)
			BEGIN 
				INSERT INTO cliente (cpf, nome, email, lim_credito, dt_nasc) VALUES
				(@cpf, @nome, @email, @lim_credito, @dt_nasc)
				SET @saida = 'Cliente cadastrado!'
			END
			ELSE 
			BEGIN 
				SET @saida = 'CPF já está cadastrado!'
			END
		END
		ELSE IF (UPPER(@op) = 'U')
		BEGIN
			IF (@cpf_existe IS NOT NULL)
			BEGIN 
				UPDATE cliente
				SET nome = @nome, email = @email, lim_credito = @lim_credito, dt_nasc = @dt_nasc
				WHERE cpf = @cpf
				SET @saida = 'Cliente atualizado!'
			END
			ELSE 
			BEGIN 
				SET @saida = 'Esse CPF não existe!'
			END
		END
		ELSE IF (UPPER(@op) = 'D')
		BEGIN
			IF (@cpf_existe IS NOT NULL)
			BEGIN 
				DELETE FROM cliente
				WHERE cpf = @cpf
				SET @saida = 'Cliente excluído!'
			END
			ELSE 
			BEGIN 
				SET @saida = 'Esse CPF não existe!'
			END
		END
	END
	ELSE IF (@cpf_valido = 1)
	BEGIN 
		SET @saida = 'CPF inválido!'
	END
	ELSE IF (@cpf_valido = 2)
	BEGIN 
		SET @saida = 'CPF não pode ter todos os numeros iguais!'
	END
