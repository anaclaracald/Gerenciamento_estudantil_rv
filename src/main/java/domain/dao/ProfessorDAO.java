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

    public void cadastrar(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor (nome, idade, especialidade) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setInt(2, professor.getIdade());
            stmt.setString(3, professor.getEspecialidade());
            stmt.executeUpdate();
        }
    }

    public List<Professor> listarTodos() throws SQLException {
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
        }
        return professores;
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM professor WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Professor professor) throws SQLException {
        String sql = "UPDATE professor SET nome = ?, idade = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setInt(2, professor.getIdade());
            stmt.setString(3, professor.getEspecialidade());
            stmt.setLong(4, professor.getId());
            stmt.executeUpdate();
        }
    }

    // Buscar por Nome
    public List<Professor> buscarPorNome(String nome) throws SQLException {
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
        }
        return professores;
    }

    // Buscar por ID
    public Professor buscarPorId(Long id) throws SQLException {
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
        }
        return null; // Retorna null se o professor não for encontrado
    }

    // Gerar Relatório
    public void gerarRelatorio() throws Exception {
        String sql = "SELECT * FROM professor";
        List<Professor> professores = listarTodos();

        Document document = new Document();
        try (FileOutputStream fos = new FileOutputStream("Relatorio_Professores.pdf")) {
            PdfWriter.getInstance(document, fos);
            document.open();

            // Adicionando título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph titulo = new Paragraph("Relatório de Professores", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            // Adicionando tabela
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

            document.close();
        }
    }
}
