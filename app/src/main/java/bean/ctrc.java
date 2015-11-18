package bean;

/**
 * Created by raullima on 18/11/15.
 */
public class ctrc {
    private String empcodigo;
    private int codigo;
    private String nome;
    private String endereco;
    private String statusCtrc;
    private String ocorrencia;


    public String getEmpcodigo() {
        return empcodigo;
    }

    public void setEmpcodigo(String empcodigo) {
        this.empcodigo = empcodigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getStatusCtrc() {
        return statusCtrc;
    }

    public void setStatusCtrc(String statusCtrc) {
        this.statusCtrc = statusCtrc;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    @Override
    public String toString() {
        return "ctrc{" +
                "empcodigo='" + empcodigo + '\'' +
                ", codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", statusCtrc='" + statusCtrc + '\'' +
                ", ocorrencia='" + ocorrencia + '\'' +
                '}';
    }
}
