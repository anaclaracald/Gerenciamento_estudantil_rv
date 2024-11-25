package domain.dao;

import domain.model.Curso;
import domain.model.Professor;
import domain.util.DataBaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    public void cadastrar(Curso curso) throws SQLException {
        String sql = "INSERT INTO cursos (nomeCurso, cargaHoraria, professor_id) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNomeCurso());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setLong(3, curso.getId());
            stmt.setObject(4, curso.getProfessor());
            stmt.executeUpdate();
        }
    }

    public List<Curso> listarTodos() throws SQLException {
        String sql = "SELECT c.id, c.nomeCurso, c.cargaHoraria, p.nome AS professor FROM cursos c JOIN professores p ON c.professor_id = p.id";
        List<Curso> cursos = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getLong("id"),
                        rs.getString("nomeCurso"),
                        rs.getInt("cargaHoraria"),
                        rs.getString("professor") // Apenas o nome do professor
                );
                cursos.add(curso);
            }
        }
        return cursos;
    }


    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Curso curso) throws SQLException {
        String sql = "UPDATE cursos SET nomeCurso = ?, cargaHoraria = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNomeCurso());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setLong(3, curso.getId());
            stmt.setObject(4, curso.getProfessor());
            stmt.executeUpdate();
        }
    }
}

