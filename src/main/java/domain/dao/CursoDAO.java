package domain.dao;

import domain.model.Curso;
import domain.util.DataBaseConnection;
import domain.util.RelatorioService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    private final RelatorioService relatorioService;

    public CursoDAO(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    public void cadastrar(Curso curso) {
        if (!isValidId(curso.getId())) {
            System.out.println("Erro: O ID do curso deve conter exatamente 4 dígitos.");
            return;
        }

        String sql = "INSERT INTO curso (id, nomeCurso, cargaHoraria, professor_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, curso.getId());
            stmt.setString(2, curso.getNomeCurso());
            stmt.setInt(3, curso.getCargaHoraria());
            stmt.setLong(4, curso.getProfessorId());
            stmt.execute();

            relatorioService.adicionarCurso(curso); // Adiciona ao relatório
            System.out.println("Curso cadastrado e adicionado ao relatório com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar o curso: " + e.getMessage());
        }
    }

    public List<Curso> listarTodos() {
        String sql = "SELECT c.id, c.nomeCurso, c.cargaHoraria, c.professor_id " +
                "FROM curso c";
        List<Curso> cursos = new ArrayList<>();

        try (Connection connection = DataBaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getLong("id"),
                        rs.getString("nomeCurso"),
                        rs.getInt("cargaHoraria"),
                        rs.getLong("professor_id"),
                        rs.getString("professor_nome")
                );
                cursos.add(curso);
                relatorioService.adicionarCurso(curso); // Atualiza o relatório com os dados do banco
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar cursos do banco: " + e.getMessage());
        }

        return cursos;
    }

    public void excluir(long id) {
        if (!isValidId(id)) {
            System.out.println("Erro: O ID do curso deve conter exatamente 4 dígitos.");
            return;
        }
        String sql = "DELETE FROM curso WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                relatorioService.getCursos().removeIf(curso -> curso.getId() == id); // Remove do relatório
                System.out.println("Curso excluído com sucesso!");
            } else {
                System.out.println("Nenhum curso encontrado com o ID especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir o curso: " + e.getMessage());
        }
    }

    public void atualizar(Curso curso) {
        if (!isValidId(curso.getId())) {
            System.out.println("Erro: O ID do curso deve conter exatamente 4 dígitos.");
            return;
        }
        String sql = "UPDATE curso SET nomeCurso = ?, cargaHoraria = ?, professor_id = ? WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, curso.getNomeCurso());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setLong(3, curso.getProfessorId());
            stmt.setLong(4, curso.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                relatorioService.getCursos().removeIf(c -> c.getId() == curso.getId()); // Remove curso antigo
                relatorioService.adicionarCurso(curso); // Atualiza com novo curso
                System.out.println("Curso atualizado com sucesso!");
            } else {
                System.out.println("Nenhum curso encontrado com o ID especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o curso: " + e.getMessage());
        }
    }

    private boolean isValidId(long id) {
        String idStr = String.valueOf(id);
        return idStr.length() == 4;
    }
}
