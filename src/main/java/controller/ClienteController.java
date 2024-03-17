package controller;

import java.sql.SQLException;
import java.util.List;

import model.Cliente;
import persistence.GenericDao;
import persistence.ClienteDao;

public class ClienteController implements ICrud<Cliente>
{

	@Override
	public String cadastrar(Cliente c) throws SQLException, ClassNotFoundException 
	{
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		String saida = cDao.crud("I", c);
		return saida;
		
	}

	@Override
	public String alterar(Cliente c) throws SQLException, ClassNotFoundException 
	{
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		String saida = cDao.crud("U", c);
		return saida;
	}

	@Override
	public String excluir(Cliente c) throws SQLException, ClassNotFoundException 
	{
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		String saida = cDao.crud("D", c);
		return saida;
	}

	@Override
	public Cliente buscar(Cliente c) throws SQLException, ClassNotFoundException 
	{
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		c = cDao.consultar(c);
		return c;
	}

	@Override
	public List<Cliente> listar() throws SQLException, ClassNotFoundException 
	{
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		List<Cliente> clientes = cDao.listar();
		
		return clientes;
	}
	
	@Override
	public boolean validar(String v)
	{
		if(v.strip().equals(""))
			return true;
		
		return false;
	}

}
