package domain.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipal extends JFrame {
    public JanelaPrincipal() {
        setTitle("Gerenciamento Estudantil");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnEstudante = new JButton("Gerenciar Estudantes");
        btnEstudante.setBounds(100, 50, 200, 40);
        add(btnEstudante);

        JButton btnProfessor = new JButton("Gerenciar Professores");
        btnProfessor.setBounds(100, 100, 200, 40);
        add(btnProfessor);

        JButton btnCurso = new JButton("Gerenciar Cursos");
        btnCurso.setBounds(100, 150, 200, 40);
        add(btnCurso);

        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(150, 200, 100, 40);
        add(btnSair);

        btnEstudante.addActionListener(e -> new TelaEstudante());
        btnProfessor.addActionListener(e -> new TelaProfessor());
        btnCurso.addActionListener(e -> new TelaCurso());
        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        new JanelaPrincipal();
    }
}
