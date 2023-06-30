package br.com.agenda.model;

import java.util.Date;

/*
 * objeto principal 
 */

public class Contato {
	
	private int id;
	private String nome;
	private int idade;
	private Date datacadastro;
	
	
	
	public Contato(String nome2, int idade2) {
		// TODO Auto-generated constructor stub
	}
	public Contato() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public Date getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Date datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	
	

}
