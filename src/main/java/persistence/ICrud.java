package persistence;

import java.sql.SQLException;
import java.util.List;

public interface ICrud<T> 
{
	void cadastrar(T t) throws SQLException, ClassNotFoundException;
	void alterar(T t) throws SQLException, ClassNotFoundException;
	void excluir(T t) throws SQLException, ClassNotFoundException;
	T consultar(T t) throws SQLException, ClassNotFoundException;
	List<T> listar() throws SQLException, ClassNotFoundException;
}
