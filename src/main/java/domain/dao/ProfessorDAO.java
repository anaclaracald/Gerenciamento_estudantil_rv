package domain.dao;

import com.itextpdf.text.pdf.PdfPTable;
import domain.model.Professor;
import domain.util.DataBaseConnection;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

public class ProfessorDAO {

    public void cadastrar(Professor professor) {
        if (professor == null) {
            throw new IllegalArgumentException("O professor não pode ser nulo.");
        }

        String sql = "INSERT INTO professor (id, nome, idade, especialidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1,professor.getId());
            stmt.setString(2, professor.getNome());
            stmt.setInt(3, professor.getIdade());
            stmt.setString(4, professor.getEspecialidade());
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("Erro: Já existe um professor com o mesmo nome ou dados duplicados.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar professor: " + e.getMessage(), e);
        }
    }

    public List<Professor> listarTodos() {
        String sql = "SELECT * FROM professor";
        List<Professor> professores = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("especialidade")
                );
                professores.add(professor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores: " + e.getMessage(), e);
        }
        return professores;
    }

    public void excluir(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para exclusão.");
        }

        String sql = "DELETE FROM professor WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum professor encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir professor: " + e.getMessage(), e);
        }
    }

    public void atualizar(Professor professor) {
        if (professor == null || professor.getId() == null || professor.getId() <= 0) {
            throw new IllegalArgumentException("Dados do professor inválidos para atualização.");
        }

        String sql = "UPDATE professor SET nome = ?, idade = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setInt(2, professor.getIdade());
            stmt.setString(3, professor.getEspecialidade());
            stmt.setLong(4, professor.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum professor encontrado com o ID fornecido para atualização.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor: " + e.getMessage(), e);
        }
    }

    public List<Professor> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome para busca não pode ser vazio.");
        }

        String sql = "SELECT * FROM professor WHERE nome LIKE ?";
        List<Professor> professores = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Professor professor = new Professor(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("especialidade")
                    );
                    professores.add(professor);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor pelo nome: " + e.getMessage(), e);
        }
        return professores;
    }

    public Professor buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para busca.");
        }

        String sql = "SELECT * FROM professor WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Professor(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("especialidade")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor pelo ID: " + e.getMessage(), e);
        }
        return null; // Retorna null se o professor não for encontrado
    }

    public void gerarRelatorio() {
        String sql = "SELECT * FROM professor";
        List<Professor> professores = listarTodos();

        Document document = new Document();
        try (FileOutputStream fos = new FileOutputStream("Relatorio_Professores.pdf")) {
            PdfWriter.getInstance(document, fos);
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph titulo = new Paragraph("Relatório de Professores", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(4); // 4 colunas: ID, Nome, Idade, Especialidade
            table.addCell("ID");
            table.addCell("Nome");
            table.addCell("Idade");
            table.addCell("Especialidade");

            for (Professor professor : professores) {
                table.addCell(String.valueOf(professor.getId()));
                table.addCell(professor.getNome());
                table.addCell(String.valueOf(professor.getIdade()));
                table.addCell(professor.getEspecialidade());
            }
            document.add(table);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório PDF: " + e.getMessage(), e);
        } finally {
            document.close();
        }
    }
}
