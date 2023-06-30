package br.com.agenda.aplicação;
import java.util.Date;

import br.com.agenda.dao.ContatoDAO;
import br.com.agenda.model.Contato;


public class Main {

	
	public static void main(String[] args) throws Exception {
		
		ContatoDAO contatoDAO = new ContatoDAO();
		
	
		
		Contato contato = new Contato();
		contato.setNome("queila ozorio");
		contato.setIdade(42);
		contato.setDatacadastro(new Date());
		contatoDAO.save(contato);
		
		//atualizar o contato
		//Contato c1 = new Contato();
		//c1.setNome("leonardo ozorio");
		//c1.setIdade(19);
		//c1.setDatacadastro(new Date());
		//c1.setId(2); // é o numero que esta no banco de dados da pk
		
		//contatoDAO.update(c1);
		
		//deletar o contato pelo seu numero de id 
		contatoDAO.authorizeEntry(3);
		
		//vizualizando TODOS os regostros
		
		for(Contato c : contatoDAO.getContatos()) {
			System.out.println("contatos : " + c.getNome() + " idade : " + c.getIdade() + " data de cadastro : " + c.getDatacadastro());
		}
		
	}

}
