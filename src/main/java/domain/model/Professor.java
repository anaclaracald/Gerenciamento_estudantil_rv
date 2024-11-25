package domain.model;


public class Professor extends Pessoa {
    private Long id;
    private String especialidade;


    public Professor(Long id, String nome, int idade, String especialidade) {
        super(nome, idade);
        this.id = id;
        this.especialidade = especialidade;
    }


    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public void exibirDados() {
        System.out.println("Nome: " + super.getNome() + ", Idade: " + super.getIdade() + ", Especialidade: " + this.getEspecialidade());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
