package domain.dao;

import domain.model.Estudante;
import domain.model.Curso;
import domain.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VinculacaoDAO {

    // Verifica se um estudante existe
    private boolean estudanteExiste(Long estudanteId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM estudante WHERE matricula = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, estudanteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // Verifica se um curso existe
    private boolean cursoExiste(Long cursoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM curso WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, cursoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void matricular(Long estudanteId, Long cursoId) throws SQLException {
        if (!estudanteExiste(estudanteId)) {
            throw new SQLException("Estudante com ID " + estudanteId + " não encontrado.");
        }
        if (!cursoExiste(cursoId)) {
            throw new SQLException("Curso com ID " + cursoId + " não encontrado.");
        }

        String sql = "INSERT INTO vinculacao (estudante_id, curso_id) VALUES (?, ?)";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, estudanteId);
            stmt.setLong(2, cursoId);
            stmt.executeUpdate();
            System.out.println("Estudante vinculado ao curso com sucesso.");
        }
    }

    public void desmatricular(Long estudanteId, Long cursoId) throws SQLException {
        if (!estudanteExiste(estudanteId)) {
            throw new SQLException("Estudante com ID " + estudanteId + " não encontrado.");
        }
        if (!cursoExiste(cursoId)) {
            throw new SQLException("Curso com ID " + cursoId + " não encontrado.");
        }

        String sql = "DELETE FROM vinculacao WHERE estudante_id = ? AND curso_id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, estudanteId);
            stmt.setLong(2, cursoId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Estudante desvinculado do curso com sucesso.");
            } else {
                System.out.println("Nenhuma vinculação encontrada entre o estudante e o curso.");
            }
        }
    }

    public List<Curso> listarCursosDoEstudante(Long estudanteId) throws SQLException {
        if (!estudanteExiste(estudanteId)) {
            throw new SQLException("Estudante com ID " + estudanteId + " não encontrado.");
        }

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

    public List<Estudante> listarEstudantesDeCurso(Long cursoId) throws SQLException {
        if (!cursoExiste(cursoId)) {
            throw new SQLException("Curso com ID " + cursoId + " não encontrado.");
        }

        String sql = "SELECT e.id, e.nome, e.idade, e.matricula " +
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
