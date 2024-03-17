USE atividade

--Atividade: Buscar a PRIMEIRA atividade referente ao IMC imediatamente acima do calculado.
--Exemplo, se o IMC for igual a 27, deve-se fazer a atividade para IMC = 29.9
--* Caso o IMC seja maior que 40, utilizar o código 5.

--Criar uma Stored Procedure (sp_alunoatividades), com as seguintes regras:
--- Se, dos dados inseridos, o código for nulo, mas, existirem nome, altura, peso, deve-se inserir um
--novo registro nas tabelas aluno e aluno atividade com o imc calculado e as atividades pelas
--regras estabelecidas acima.
--- Se, dos dados inseridos, o nome for (ou não nulo), mas, existirem código, altura, peso, deve-se
--verificar se aquele código existe na base de dados e atualizar a altura, o peso, o imc calculado e
--as atividades pelas regras estabelecidas acima.
--DROP PROCEDURE sp_alunoatividades

CREATE PROCEDURE sp_alunoatividades (@codigo INT, @nome VARCHAR(100), @altura DECIMAL(7, 2), @peso DECIMAL(7, 2), @saida VARCHAR(100) OUTPUT)
AS
	IF (@altura IS NOT NULL AND @peso IS NOT NULL)
	BEGIN
		DECLARE @imc DECIMAL(7, 2),
				@atividade INT
		SET @imc = @peso / @altura
		SET @atividade = 
			CASE
				WHEN @imc < 18.5 THEN (SELECT codigo FROM atividade WHERE imc = 18.5)
				WHEN @imc < 24.9 THEN (SELECT codigo FROM atividade WHERE imc = 24.9)
				WHEN @imc < 29.9 THEN (SELECT codigo FROM atividade WHERE imc = 29.9)
				WHEN @imc < 34.9 THEN (SELECT codigo FROM atividade WHERE imc = 34.9)
				ELSE (SELECT codigo FROM atividade WHERE imc = 39.9)
			END
		IF (@codigo IS NULL)
		BEGIN 
			DECLARE @codigo_aluno INT
			SET @codigo_aluno = (SELECT COALESCE(MAX(codigo) + 1, 1) FROM aluno)
			INSERT INTO aluno (codigo, nome) VALUES
			(@codigo_aluno, @nome)
			INSERT INTO aluno_atividade (codigo_aluno, altura, peso, imc, codigo_atividade) VALUES
			(@codigo_aluno, @altura, @peso, @imc, @atividade)
		END
		ELSE
		BEGIN
			DECLARE @codigo_valida INT
			SET @codigo_valida = (SELECT codigo_aluno FROM aluno_atividade WHERE codigo_aluno = @codigo)
			IF (@codigo_valida IS NOT NULL)
			BEGIN 
				IF (@nome IS NOT NULL)
				BEGIN 
					UPDATE aluno
					SET nome = @nome
					WHERE codigo = @codigo
				END
				UPDATE aluno_atividade
				SET altura = @altura, peso = @peso, imc = @imc, codigo_atividade = @atividade
				WHERE codigo_aluno = @codigo
			END
			ELSE
			BEGIN
				SET @saida = 'Esse codigo não existe'
			END
		END
	END
	ELSE
	BEGIN
		SET @saida = 'Peso ou altura deve ser inserido.'
	END