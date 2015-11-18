package bean;

/**
 * Created by raullima on 18/11/15.
 */
public class CargaEntrega {
    private String empcodigo;
    private int codigo;
    private String tipo;
    private String statusEntrega;
    private String retorno;
    private String smbc;
    private ctrc ctrc;


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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(String statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public String getSmbc() {
        return smbc;
    }

    public void setSmbc(String smbc) {
        this.smbc = smbc;
    }

    public bean.ctrc getCtrc() {
        return ctrc;
    }

    public void setCtrc(bean.ctrc ctrc) {
        this.ctrc = ctrc;
    }

    @Override
    public String toString() {
        return "CargaEntrega{" +
                "empcodigo='" + empcodigo + '\'' +
                ", codigo=" + codigo +
                ", tipo='" + tipo + '\'' +
                ", statusEntrega='" + statusEntrega + '\'' +
                ", retorno='" + retorno + '\'' +
                ", smbc='" + smbc + '\'' +
                ", ctrc=" + ctrc.getCodigo() +
                '}';
    }
}
