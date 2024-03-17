package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cliente 
{
	String cpf;
	String nome;
	String email;
	Float limCredito;
	LocalDate dtNasc;
	
	public String getDtNascFormat()
	{
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.dtNasc.format(formatacao);
	}
}
