package domain.dao;

import domain.model.Estudante;
import domain.model.Curso;
import domain.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VinculacaoDAO {

    // Vincula um estudante a um curso
    public void vincularEstudanteAoCurso(Long estudanteId, Long cursoId) throws SQLException {
        String sql = "INSERT INTO estudante_curso (estudante_id, curso_id) VALUES (?, ?)";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, estudanteId);
            stmt.setLong(2, cursoId);
            stmt.executeUpdate();
        }
    }

    // Desvincula um estudante de um curso
    public void desvincularEstudanteDoCurso(Long estudanteId, Long cursoId) throws SQLException {
        String sql = "DELETE FROM estudante_curso WHERE estudante_id = ? AND curso_id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, estudanteId);
            stmt.setLong(2, cursoId);
            stmt.executeUpdate();
        }
    }

    // Lista todos os cursos de um estudante
    public List<Curso> listarCursosDeEstudante(Long estudanteId) throws SQLException {
        String sql = "SELECT c.id, c.nomeCurso, c.cargaHoraria " +
                "FROM curso c " +
                "JOIN estudante_curso ec ON c.id = ec.curso_id " +
                "WHERE ec.estudante_id = ?";
        List<Curso> cursos = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, estudanteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getLong("id"),
                        rs.getString("nomeCurso"),
                        rs.getInt("cargaHoraria"),
                        rs.getLong("professor_id"),
                        rs.getString("professor_nome")
                );
                cursos.add(curso);
            }
        }
        return cursos;
    }

    // Lista todos os estudantes de um curso
    public List<Estudante> listarEstudantesDeCurso(Long cursoId) throws SQLException {
        String sql = "SELECT e.id, e.nome " +
                "FROM estudante e " +
                "JOIN estudante_curso ec ON e.id = ec.estudante_id " +
                "WHERE ec.curso_id = ?";
        List<Estudante> estudantes = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, cursoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Estudante estudante = new Estudante(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getLong("matricula")
                );
                estudantes.add(estudante);
            }
        }
        return estudantes;
    }
}
