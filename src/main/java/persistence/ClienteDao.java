package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteDao implements ICrud<Cliente>, ICrudProcedure<Cliente>
{
	private GenericDao gDao;

	public ClienteDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public void cadastrar(Cliente cl) throws SQLException, ClassNotFoundException 
	{
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO cliente VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cl.getCpf());
		ps.setString(2, cl.getNome());
		ps.setString(3, cl.getEmail());
		ps.setFloat(4, cl.getLimCredito());
		ps.setDate(5, java.sql.Date.valueOf(cl.getDtNasc()));
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void alterar(Cliente cl) throws SQLException, ClassNotFoundException 
	{
		Connection c = gDao.getConnection();
		String sql = "UPDATE cliente SET nome = ?, email = ?, lim_credito = ?, dt_nasc = ? WHERE cpf = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cl.getNome());
		ps.setString(2, cl.getEmail());
		ps.setFloat(3, cl.getLimCredito());
		ps.setDate(4, java.sql.Date.valueOf(cl.getDtNasc()));
		ps.setString(5, cl.getCpf());
		ps.execute();
		ps.close();
		c.close();
	}
	

	@Override
	public void excluir(Cliente cl) throws SQLException, ClassNotFoundException 
	{
		Connection c = gDao.getConnection();
		String sql = "DELETE cliente WHERE cpf = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cl.getCpf());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public Cliente consultar(Cliente cl) throws SQLException, ClassNotFoundException
	{
		Connection c = gDao.getConnection();
		String sql = "SELECT cpf, nome, email, lim_credito, dt_nasc FROM cliente WHERE cpf = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cl.getCpf());
		ResultSet rs = ps.executeQuery();
		
		if(rs.next())
		{
			cl.setCpf(rs.getString("cpf"));
			cl.setNome(rs.getString("nome"));
			cl.setEmail(rs.getString("email"));
			cl.setLimCredito(rs.getFloat("lim_credito"));
			cl.setDtNasc(rs.getDate("dt_nasc").toLocalDate());
		}
		rs.close();
		ps.close();
		c.close();
		
		return cl;
	}

	@Override
	public List<Cliente> listar() throws SQLException, ClassNotFoundException
	{
		List<Cliente> clientes = new ArrayList<Cliente>();
		Connection c = gDao.getConnection();
		String sql = "SELECT cpf, nome, email, lim_credito, dt_nasc FROM cliente";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			Cliente cl = new Cliente();
			cl.setCpf(rs.getString("cpf"));
			cl.setNome(rs.getString("nome"));
			cl.setEmail(rs.getString("email"));
			cl.setLimCredito(rs.getFloat("lim_credito"));
			cl.setDtNasc(rs.getDate("dt_nasc").toLocalDate());
			
			clientes.add(cl);
		}
		
		rs.close();
		ps.close();
		c.close();
		
		return clientes;
	}

	@Override
	public String crud(String acao, Cliente cl) throws SQLException, ClassNotFoundException
	{
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_crud_cliente (?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, acao);
		cs.setString(2, cl.getCpf());
		cs.setString(3, cl.getNome());
		cs.setString(4, cl.getEmail());
		cs.setFloat(5, cl.getLimCredito());
		cs.setDate(6, java.sql.Date.valueOf(cl.getDtNasc()));
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(7);
		
		cs.close();
		c.close();
		return saida;
	}

}
