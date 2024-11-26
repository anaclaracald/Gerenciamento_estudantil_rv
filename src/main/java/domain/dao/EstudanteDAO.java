package domain.dao;

import domain.model.Estudante;
import domain.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDAO {
    public void cadastrar(Estudante estudante) throws SQLException, IllegalArgumentException {
        if (String.valueOf(estudante.getMatricula()).length() != 4) {
            throw new IllegalArgumentException("A matrícula deve ter exatamente 4 dígitos.");
        }

        if (existeMatricula(estudante.getMatricula())) {
            throw new IllegalArgumentException("A matrícula já está cadastrada.");
        }

        String sql = "INSERT INTO estudante (nome, idade, matricula) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estudante.getNome());
            stmt.setInt(2, estudante.getIdade());
            stmt.setLong(3, estudante.getMatricula());
            stmt.execute();
        }
    }

    public boolean existeMatricula(long matricula) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM estudante WHERE matricula = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }
        return false;
    }

    public List<Estudante> listarTodos() throws SQLException {
        String sql = "SELECT * FROM estudante";
        List<Estudante> alunos = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Estudante estudante = new Estudante(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getLong("matricula")
                );
                alunos.add(estudante);
            }
            if (alunos.isEmpty()) {
                throw new SQLException("Nenhum estudante encontrado no banco de dados.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar os estudantes. Verifique a conexão ou os dados no banco.", e);
        }
        return alunos;
    }

    public String gerarRelatorio() {
        // SQL ajustado para refletir apenas as tabelas existentes
        String sql = "SELECT matricula, nome FROM estudante";

        StringBuilder relatorio = new StringBuilder();

        // Cabeçalho do relatório
        relatorio.append("Relatório de Estudantes\n");
        relatorio.append("=========================\n\n");
        relatorio.append(String.format("%-10s %-20s\n", "Matrícula", "Nome"));
        relatorio.append("-------------------------------\n");

        try (Connection connection = DataBaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                long matricula = rs.getLong("matricula");
                String nome = rs.getString("nome");

                // Adiciona os dados do estudante ao relatório
                relatorio.append(String.format("%-10d %-20s\n", matricula, nome));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar relatório de estudantes: " + e.getMessage(), e);
        }

        return relatorio.toString();
    }

    public void excluir(long matricula) throws SQLException {
        String sqlVerificar = "SELECT * FROM estudante WHERE matricula = ?";
        String sqlExcluir = "DELETE FROM estudante WHERE matricula = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement verificarStmt = conn.prepareStatement(sqlVerificar);
             PreparedStatement excluirStmt = conn.prepareStatement(sqlExcluir)) {
            verificarStmt.setLong(1, matricula);
            ResultSet rs = verificarStmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Matrícula não encontrada. Não há estudante com a matrícula fornecida.");
            }
            excluirStmt.setLong(1, matricula);
            excluirStmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir o estudante. Verifique a matrícula fornecida.", e);
        }
    }
}