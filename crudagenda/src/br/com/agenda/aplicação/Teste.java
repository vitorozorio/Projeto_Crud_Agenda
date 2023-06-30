package br.com.agenda.aplicação;
import br.com.agenda.dao.ContatoDAO;

public class Teste {

	public static void main(String[] args) throws Exception {
		
		int teste = 8;
		
		ContatoDAO contatoDAO = new ContatoDAO();
		
		if(contatoDAO.authorizeEntry(teste)) {
			System.out.print("autorização permitida");
		}else {
			System.out.print("autorização negada");
		}
		

	}

}
