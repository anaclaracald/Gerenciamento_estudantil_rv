package domain.view;

import domain.dao.VinculacaoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

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

        // Ações dos botões
        desmatricularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Long matricula = Long.parseLong(JOptionPane.showInputDialog("Insira a matrícula do aluno que deseja desmatricular:"));
                    Long idCurso = Long.parseLong(JOptionPane.showInputDialog("Insira o ID do curso:"));
                    // Verificar se professor e curso estão na DB
                    // Vincular
                    VinculacaoDAO dao = new VinculacaoDAO();
                    dao.desmatricular(matricula, idCurso); // Chama a função existente
                    JOptionPane.showMessageDialog(null, "Estudante desmatriculado com sucesso.");
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao desmatricular: ");
                    throw new RuntimeException(ex.getMessage());
                }

            }
        });

        matricularEstButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Long estudante_id = Long.parseLong(JOptionPane.showInputDialog("Insira a matrícula do aluno que deseja matricular:"));
                    Long curso_id = Long.parseLong(JOptionPane.showInputDialog("Insira o ID do curso:"));
                    // Verificar se aluno e curso estão na DB
                    // Matricular
                    VinculacaoDAO dao = new VinculacaoDAO();
                    dao.matricular(estudante_id, curso_id); // Chama a função existente
                    JOptionPane.showMessageDialog(null, "Estudante matriculado com sucesso.");
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao matricular aluno: ");
                    throw new RuntimeException(ex.getMessage());
                }

            }
        });

        voltarButton.addActionListener(e -> setVisible(false));

        setVisible(true);
    }
}
