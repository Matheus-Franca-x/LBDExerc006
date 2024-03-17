package controller;

import java.sql.SQLException;
import java.util.List;

public interface ICrud<T> 
{
	String cadastrar(T t) throws SQLException, ClassNotFoundException;
	String alterar(T t) throws SQLException, ClassNotFoundException;
	String excluir(T t) throws SQLException, ClassNotFoundException;
	T buscar(T t) throws SQLException, ClassNotFoundException;
	List<T> listar() throws SQLException, ClassNotFoundException;
	boolean validar(String t);
}
