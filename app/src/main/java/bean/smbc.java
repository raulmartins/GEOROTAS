package bean;

/**
 * Created by raullima on 20/11/15.
 */
public class Smbc {
    private String imei;
    private String senha;
    private String operacao;
    private String empcodigo;
    private String codigo;
    private String tipo;
    private String status;
    private String ordem;
    private String oco;


    public Smbc(String imei, String senha, String operacao) {
        this.imei = imei;
        this.senha = senha;
        this.operacao = operacao;
    }

    public Smbc(String imei, String senha, String operacao, String empcodigo, String codigo, String tipo, String status, String ordem, String oco) {
        this.imei = imei;
        this.senha = senha;
        this.operacao = operacao;
        this.empcodigo = empcodigo;
        this.codigo = codigo;
        this.tipo = tipo;
        this.status = status;
        this.ordem = ordem;
        this.oco = oco;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getEmpcodigo() {
        return empcodigo;
    }

    public void setEmpcodigo(String empcodigo) {
        this.empcodigo = empcodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getOco() {
        return oco;
    }

    public void setOco(String oco) {
        this.oco = oco;
    }
}
