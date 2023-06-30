package br.com.agenda.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.agenda.factory.ConnectionFactory;
import br.com.agenda.model.Contato;

/*
 * Esta classe é responsável por manipular os dados dos contatos no banco de dados.
 * Ela possui métodos para criar, ler, atualizar e deletar contatos (CRUD).
 */

public class ContatoDAO {
    /*
     * CRUD
     * c: CREATE - criar (salvar) um contato
     * r: READ - ler os contatos
     * u: UPDATE - atualizar um contato
     * d: DELETE - deletar um contato
     */

    // Método que salva um contato no banco de dados
    public void save(Contato contato) {
        // SQL para inserir um novo contato no banco
        String sql = "INSERT INTO contatos(nome, idade, cadastro) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com o banco de dados
            conn = ConnectionFactory.createConnectionToMySQL();

            // Cria um PreparedStatement para executar a query SQL
            pstm = conn.prepareStatement(sql);

            // Adiciona os valores do contato à query SQL
            pstm.setString(1, contato.getNome());
            pstm.setInt(2, contato.getIdade());
            
            // Verifica se a data de cadastro é nula
            if (contato.getDatacadastro() != null) {
                pstm.setDate(3, new Date(contato.getDatacadastro().getTime()));
            } else {
                pstm.setNull(3, java.sql.Types.DATE);
            }

            // Executa a query
            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fecha as conexões com o banco de dados
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método que atualiza um contato no banco de dados
    public void update(Contato contato) {
        // SQL para atualizar um contato no banco
        String sql = "UPDATE contatos SET nome = ?, idade = ?, cadastro = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com o banco de dados
            conn = ConnectionFactory.createConnectionToMySQL();

            // Cria um PreparedStatement para executar a query SQL
            pstm = conn.prepareStatement(sql);

            // Adiciona os novos valores do contato à query SQL
            pstm.setNString(1, contato.getNome());
            pstm.setInt(2, contato.getIdade());
            pstm.setDate(3, new Date(contato.getDatacadastro().getTime()));
            pstm.setInt(4, contato.getId()); // Qual registro (contato) será atualizado

            // Executa a query
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fecha as conexões com o banco de dados
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método que deleta um contato do banco de dados pelo ID
    public void deleteByID(int id) {
        // SQL para deletar um contato pelo ID
        String sql = "DELETE FROM contatos WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com o banco de dados
            conn = ConnectionFactory.createConnectionToMySQL();

            // Cria um PreparedStatement para executar a query SQL
            pstm = conn.prepareStatement(sql);

            // Adiciona o ID do contato à query SQL
            pstm.setInt(1, id);

            // Executa a query
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fecha as conexões com o banco de dados
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método que retorna uma lista de todos os contatos do banco de dados
    public List<Contato> getContatos() {
        String sql = "SELECT * FROM contatos";

        List<Contato> contatos = new ArrayList<Contato>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            // Cria uma conexão com o banco de dados
            conn = ConnectionFactory.createConnectionToMySQL();

            // Cria um PreparedStatement para executar a query SQL
            pstm = conn.prepareStatement(sql);

            // Executa a query e obtém os resultados
            rset = pstm.executeQuery();

            // Itera sobre os resultados e cria objetos Contato para cada registro
            while (rset.next()) {
                Contato contato = new Contato();

                // Obtém as informações do registro atual e as adiciona ao objeto Contato
                contato.setId(rset.getInt("id"));
                contato.setNome(rset.getString("nome"));
                contato.setIdade(rset.getInt("idade"));
                contato.setDatacadastro(rset.getDate("cadastro"));

                // Adiciona o contato à lista de contatos
                contatos.add(contato);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fecha as conexões com o banco de dados
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Retorna a lista de contatos
        return contatos;
    }

    // Método que retorna um contato específico pelo ID
    public List<Contato> getContatoByID(int id) throws Exception {
        String sql = "SELECT * FROM contatos WHERE id = ?";

        List<Contato> contatos = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Cria uma conexão com o banco de dados
            conn = ConnectionFactory.createConnectionToMySQL();

            // Cria um PreparedStatement para executar a query SQL
            pstm = conn.prepareStatement(sql);

            // Adiciona o ID do contato à query SQL
            pstm.setInt(1, id);

            // Executa a query e obtém os resultados
            rs = pstm.executeQuery();

            // Itera sobre os resultados e cria objetos Contato para cada registro
            while (rs.next()) {
                Contato contato = new Contato();

                // Obtém as informações do registro atual e as adiciona ao objeto Contato
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setIdade(rs.getInt("idade"));
                contato.setDatacadastro(rs.getDate("cadastro"));

                // Adiciona o contato à lista de contatos
                contatos.add(contato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fecha as conexões com o banco de dados
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Retorna a lista de contatos
        return contatos;
    }

    // Método que verifica se um contato existe no banco de dados pelo ID
    public boolean authorizeEntry(int id) throws Exception {
        String sql = "SELECT COUNT(*) FROM contatos WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Cria uma conexão com o banco de dados
            conn = ConnectionFactory.createConnectionToMySQL();

            // Cria um PreparedStatement para executar a query SQL
            pstm = conn.prepareStatement(sql);

            // Adiciona o ID do contato à query SQL
            pstm.setInt(1, id);

            // Executa a query e obtém o resultado
            rs = pstm.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fecha as conexões com o banco de dados
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}