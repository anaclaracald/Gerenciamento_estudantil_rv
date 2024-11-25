package domain.dao;


import domain.model.Estudante;
import domain.util.DataBaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDAO {
    public void cadastrar(Estudante estudante) throws SQLException {
        String sql = "INSERT INTO estudante (nome, idade, matricula) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estudante.getNome());
            stmt.setInt(2, estudante.getIdade());
            stmt.setLong(3, estudante.getMatricula());
            stmt.executeUpdate();
        }
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
        }
        return alunos;
    }

    public void excluir(long matricula) throws SQLException {
        String sql = "DELETE FROM estudante WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, matricula);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Estudante estudante) throws SQLException {
        String sql = "UPDATE estudante SET nome = ?, idade = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estudante.getNome());
            stmt.setInt(2, estudante.getIdade());
            stmt.setLong(3, estudante.getMatricula());
            stmt.executeUpdate();
        }
    }
}
