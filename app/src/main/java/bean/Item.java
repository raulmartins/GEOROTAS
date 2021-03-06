package bean;

/**
 * Created by raullima on 25/11/15.
 */
public class Item {
    private String empCodigo;
    private String codigo;
    private String tipo;
    private String nome;
    private String endereco;
    private String ordem;
    private String status;
    private String parada;


    public Item() {
    }

    public Item(String empCodigo, String codigo, String tipo, String nome, String endereco, String ordem, String status, String parada) {
        this.empCodigo = empCodigo;
        this.tipo = tipo;
        this.nome = nome;
        this.endereco = endereco;
        this.ordem = ordem;
        this.status = status;
        this.parada = parada;
        this.codigo = codigo;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(String empCodigo) {
        this.empCodigo = empCodigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParada() {
        return parada;
    }

    public void setParada(String parada) {
        this.parada = parada;
    }

    @Override
    public String toString() {
        return
                "   Código da Empresa:" + empCodigo + "       "+
                "   Código:" + codigo + "\n"+
                "   Tipo:" + tipo +"\n"+
                "   Nome:" + nome +"\n"+
                "   Endereço:" + endereco +"\n"+
                "   Ordem:" + ordem +"\n"+
                "   Status:" + status +"\n"+
                "   Parada:" + parada ;
    }
}



