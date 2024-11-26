package domain.model;

public class Estudante extends Pessoa {
    private Long matricula;

    public Estudante(String nome, int idade, Long matricula) {
        super(nome, idade);
        this.matricula = matricula;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Nome: " + super.getNome() + ", Idade: " + super.getIdade() + ", Matrícula: " + this.getMatricula();
    }

}