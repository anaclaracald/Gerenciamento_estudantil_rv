package domain.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaVinculacao extends JFrame {
    private JButton vincularProfButton;
    private JButton matricularEstButton;
    private JButton voltarButton;

    public TelaVinculacao() {
        setTitle("Vinculação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        vincularProfButton = new JButton("Vincular Professor");
        vincularProfButton.setBounds(100, 50, 200, 40);
        add(vincularProfButton);

        matricularEstButton = new JButton("Matricular Aluno");
        matricularEstButton.setBounds(100, 100, 200, 40);
        add(matricularEstButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setBounds(100, 150, 200, 40);
        add(voltarButton);

        // Ações dos botões
        vincularProfButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idProf = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do professor que deseja vincular:"));
                    int idCurso = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do curso:"));
                    // Verificar se professor e curso estão na DB
                    // Vincular
                }
                catch (/*Se professor não estiver*/) {
                    JOptionPane.showMessageDialog(null, "Professor não encontrado.");
                }
                catch (/*Se curso não estiver*/) {
                    JOptionPane.showMessageDialog(null, "Curso não encontrado.");
                }

            }
        });

        matricularEstButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int matricula = Integer.parseInt(JOptionPane.showInputDialog("Insira a matrícula do aluno que deseja matricular:"));
                    int idCurso = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do curso:"));
                    // Verificar se aluno e curso estão na DB
                    // Matricular
                }
                catch (/*Se aluno não estiver*/) {
                    JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
                }
                catch (/*Se curso não estiver*/) {
                    JOptionPane.showMessageDialog(null, "Curso não encontrado.");
                }

            }
        });

        voltarButton.addActionListener(e -> setVisible(false));

        setVisible(true);
    }
}
