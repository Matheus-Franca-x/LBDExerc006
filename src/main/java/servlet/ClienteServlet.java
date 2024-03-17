package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cliente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.ClienteController;
import controller.ICrud;


@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet
	{
		private static final long serialVersionUID = 1L;

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			List<Cliente> clientes = new ArrayList<>();
			String erro = "";
			ICrud<Cliente> cControl = new ClienteController();
			
			try {
				clientes = cControl.listar();
			} catch (SQLException | ClassNotFoundException e)
			{
				erro = e.getMessage();
				e.printStackTrace();
			} finally {
				request.setAttribute("clientes", clientes);
				request.setAttribute("erro", erro);
				
				RequestDispatcher rd = request.getRequestDispatcher("cliente.jsp");
				rd.forward(request, response);
			}
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			//entrada
			String cmd = request.getParameter("botao");
			String cpf = request.getParameter("cpf");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String limCredito = request.getParameter("limCredito");
			String dtNasc = request.getParameter("dtNasc");
			List<Cliente> clientes = new ArrayList<>();
			
			//saida
			String saida = "";
			String erro = "";
			Cliente c = new Cliente();
			ICrud<Cliente> cControl = new ClienteController();
			
			try {
				if(!cmd.contains("Cadastrar") || !cmd.contains("Alterar"))
				{
					if(!cmd.contains("Buscar"))
					{
						if(cControl.validar(cpf))
						{
							saida = "CPF inválido!";
							return;
						}
						
						c.setNome(nome);
						c.setEmail(email);
						c.setLimCredito(Float.parseFloat(limCredito));
						c.setDtNasc(LocalDate.parse(dtNasc));						
					}

					c.setCpf(cpf);
				}
				if(cmd.contains("Cadastrar"))
				{
					saida = cControl.cadastrar(c);
					c = null;
				}
				if(cmd.contains("Alterar"))
				{
					saida = cControl.alterar(c);
					c = null;
				}
				if(cmd.contains("Excluir"))
				{
					saida = cControl.excluir(c);
					c = null;
				}
				if(cmd.contains("Buscar"))
				{
					c = cControl.buscar(c);
				}
			} catch (SQLException | ClassNotFoundException e)
			{
				erro = e.getMessage();
			} finally {
				try {
					clientes = cControl.listar();
				} catch (SQLException | ClassNotFoundException e) {
					erro = e.getMessage();
				}
				
				request.setAttribute("saida", saida);
				request.setAttribute("erro", erro);
				request.setAttribute("cliente", c);
				request.setAttribute("clientes", clientes);
				
				RequestDispatcher rd = request.getRequestDispatcher("cliente.jsp");
				rd.forward(request, response);
			}
			
		}

	}
