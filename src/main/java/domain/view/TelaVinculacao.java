package domain.view;

import domain.dao.VinculacaoDAO;

import javax.swing.*;
import java.sql.SQLException;

public class TelaVinculacao extends JFrame {
    private JButton desmatricularButton;
    private JButton matricularEstButton;
    private JButton voltarButton;

    public TelaVinculacao() {
        setTitle("Vinculação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        desmatricularButton = new JButton("Desmatricular");
        desmatricularButton.setBounds(100, 50, 200, 40);
        add(desmatricularButton);

        matricularEstButton = new JButton("Matricular Aluno");
        matricularEstButton.setBounds(100, 100, 200, 40);
        add(matricularEstButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setBounds(100, 150, 200, 40);
        add(voltarButton);

        desmatricularButton.addActionListener(e -> {
            try {
                Long matricula = Long.parseLong(JOptionPane.showInputDialog("Insira a matrícula do aluno que deseja desmatricular:"));
                Long idCurso = Long.parseLong(JOptionPane.showInputDialog("Insira o ID do curso:"));

                VinculacaoDAO dao = new VinculacaoDAO();
                dao.desmatricular(matricula, idCurso); // Chama a função existente

                JOptionPane.showMessageDialog(null, "Estudante desmatriculado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro: IDs devem ser numéricos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao desmatricular: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        matricularEstButton.addActionListener(e -> {
            try {
                Long estudante_id = Long.parseLong(JOptionPane.showInputDialog("Insira a matrícula do aluno que deseja matricular:"));
                Long curso_id = Long.parseLong(JOptionPane.showInputDialog("Insira o ID do curso:"));

                VinculacaoDAO dao = new VinculacaoDAO();
                dao.matricular(estudante_id, curso_id); // Chama a função existente

                JOptionPane.showMessageDialog(null, "Estudante matriculado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro: IDs devem ser numéricos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao matricular aluno: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        voltarButton.addActionListener(e -> setVisible(false));

        setVisible(true);
    }
}
