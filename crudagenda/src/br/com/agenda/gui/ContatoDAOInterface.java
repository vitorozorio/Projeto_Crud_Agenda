package br.com.agenda.gui;

import javax.swing.*;
import br.com.agenda.dao.ContatoDAO;
import br.com.agenda.model.Contato;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ContatoDAOInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private ContatoDAO contatoDAO;
    private JTextField nomeField;
    private JTextField idadeField;
    private JButton btnAdicionar;
    private JButton btnAtualizar;
    private JButton btnRemover;
    private JList<String> listaContatos;

    public ContatoDAOInterface() {
        // Cria uma instância do ContatoDAO para acessar os métodos de acesso a dados
        contatoDAO = new ContatoDAO();

        // Configurações da janela principal
        setTitle("Agenda de Contatos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Criação do painel de formulário para entrada de nome e idade
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2));
        panelFormulario.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panelFormulario.add(nomeField);
        panelFormulario.add(new JLabel("Idade:"));
        idadeField = new JTextField();
        panelFormulario.add(idadeField);

        // Criação do painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnAdicionar = new JButton("Adicionar");
        btnAtualizar = new JButton("Atualizar");
        btnRemover = new JButton("Remover");
        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnRemover);

        // Criação do painel principal para agrupar os demais painéis
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(panelBotoes, BorderLayout.CENTER);

        // Criação da lista de contatos com barra de rolagem
        listaContatos = new JList<>();
        JScrollPane scrollPane = new JScrollPane(listaContatos);

        // Adição dos painéis à janela principal
        add(panelPrincipal, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Carrega a lista de contatos na interface gráfica
        carregarContatos();

        // Configura os ouvintes de eventos para os botões
        configurarOuvintesBotoes();
    }

    private void carregarContatos() {
        // Obtém a lista de contatos do banco de dados
        List<Contato> contatos = contatoDAO.getContatos();

        // Cria um modelo de lista para exibir os contatos na JList
        DefaultListModel<String> model = new DefaultListModel<>();

        // Adiciona cada contato ao modelo de lista, exibindo o nome e a idade
        for (Contato contato : contatos) {
            model.addElement(contato.getNome() + " - " + contato.getIdade() + " anos");
        }

        // Define o modelo de lista para a JList de contatos
        listaContatos.setModel(model);
    }

    private void configurarOuvintesBotoes() {
        // Ouvinte de evento para o botão "Adicionar"
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exibe uma caixa de diálogo de confirmação
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja adicionar o contato?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    if (validarCampos()) {
                        adicionarContato();
                    } else {
                        // Exibe uma mensagem de aviso se os campos não estiverem preenchidos
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de adicionar um contato.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        // Ouvinte de evento para o botão "Atualizar"
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarContato();
            }
        });

        // Ouvinte de evento para o botão "Remover"
        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerContato();
            }
        });
    }

    private void adicionarContato() {
        // Obtém o nome e a idade dos campos de texto
        String nome = nomeField.getText();
        int idade = Integer.parseInt(idadeField.getText());

        // Cria um novo objeto Contato e define os valores
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setIdade(idade);

        // Salva o contato no banco de dados
        contatoDAO.save(contato);

        // Atualiza a lista de contatos na interface gráfica
        carregarContatos();

        // Limpa os campos de texto
        limparCampos();

        // Exibe uma mensagem de sucesso
        JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!");
    }

    private void atualizarContato() {
    	  int selectedIndex = listaContatos.getSelectedIndex();
    	    if (selectedIndex != -1) {
    	        String nome = nomeField.getText();
    	        int idade = Integer.parseInt(idadeField.getText());
    	        
    	        // Obtém a lista de contatos
    	        List<Contato> contatos = contatoDAO.getContatos();
    	        
    	        // Verifica se a lista de contatos não está vazia
    	        if (!contatos.isEmpty()) {
    	            // Obtém o contato selecionado com base no índice selecionado na lista de contatos
    	            Contato contato = contatos.get(selectedIndex);
    	            
    	            // Atualiza o nome e a idade do contato com os novos valores
    	            contato.setNome(nome);
    	            contato.setIdade(idade);
    	            
    	            // Atualiza o contato no banco de dados
    	            contatoDAO.update(contato);
    	            
    	            // Recarrega a lista de contatos para exibir as alterações atualizadas
    	            carregarContatos();
    	            
    	            // Limpa os campos do formulário após a atualização
    	            limparCampos();
    	            
    	            JOptionPane.showMessageDialog(this, "Contato atualizado com sucesso!");
    	        } else {
    	            JOptionPane.showMessageDialog(this, "Não há contatos para atualizar.");
    	        }
    	    } else {
    	        JOptionPane.showMessageDialog(this, "Selecione um contato para atualizar.");
    	    }
    }

    private void removerContato() {
        // Obtém o índice do contato selecionado na lista de contatos
        int selectedIndex = listaContatos.getSelectedIndex();

        if (selectedIndex != -1) {
            // Exibe uma caixa de diálogo de confirmação
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente remover o contato?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                // Obtém a lista de contatos
                List<Contato> contatos = contatoDAO.getContatos();

                // Obtém o contato correspondente ao índice selecionado
                Contato contato = contatos.get(selectedIndex);

                // Remove o contato do banco de dados
                contatoDAO.deleteByID(contato.getId());

                // Atualiza a lista de contatos na interface gráfica
                carregarContatos();

                // Limpa os campos de texto
                limparCampos();

                // Exibe uma mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Contato removido com sucesso!");
            }
        } else {
            // Exibe uma mensagem de aviso se nenhum contato estiver selecionado
            JOptionPane.showMessageDialog(this, "Selecione um contato para remover.");
        }
    }

    private void limparCampos() {
        // Limpa os campos de texto
        nomeField.setText("");
        idadeField.setText("");
    }

    private boolean validarCampos() {
        // Verifica se os campos de texto estão preenchidos
        String nome = nomeField.getText();
        String idade = idadeField.getText();
        return !nome.isEmpty() && !idade.isEmpty();
    }

    public static void main(String[] args) {
        // Cria e exibe a interface gráfica
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ContatoDAOInterface();
            }
        });
    }
}
