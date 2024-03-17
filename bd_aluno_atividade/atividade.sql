USE master
GO
DROP DATABASE atividade

CREATE DATABASE atividade
GO
USE atividade
GO
CREATE TABLE aluno
(
	codigo				INT 			NOT NULL,
	nome				VARCHAR(100) 	NOT NULL
	PRIMARY KEY (codigo)
)
GO
CREATE TABLE atividade
(
	codigo				INT 			NOT NULL,
	descricao			VARCHAR(50) 	NOT NULL,
	imc					DECIMAL(7, 2) 	NOT NULL
	PRIMARY KEY (codigo)
)
GO
CREATE TABLE aluno_atividade
(
	codigo_aluno		INT 			NOT NULL,
	altura				DECIMAL(7, 2) 	NOT NULL,
	peso				DECIMAL(7, 2)	NOT NULL,
	imc					DECIMAL(7, 2)	NOT NULL,
	codigo_atividade	INT				NOT NULL
	PRIMARY KEY (codigo_aluno, codigo_atividade)
	FOREIGN KEY (codigo_aluno) REFERENCES aluno (codigo),
	FOREIGN KEY (codigo_atividade) REFERENCES atividade (codigo)
)

INSERT INTO atividade VALUES
(1, 'Corrida + Step', 18.5),
(2, 'Biceps + Costas + Pernas', 24.9),
(3, 'Esteira + Biceps + Costas + Pernas', 29.9),
(4, 'Bicicleta + Biceps + Costas + Pernas', 34.9),
(5, 'Esteira + Bicicleta', 39.9)
