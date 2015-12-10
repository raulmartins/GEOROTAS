package bean;

/**
 * Created by raullima on 25/11/15.
 */
public class Carga {

    private String operacao;
    private String retorno;
    private String descricao;
    private Item item;

    public Carga() {
    }

    public Carga(String operacao, Item item, String retorno, String descricao) {
        this.operacao = operacao;
        this.item = item;
        this.retorno = retorno;
        this.descricao = descricao;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Carga{" +
                "operacao='" + operacao + '\'' +
                ", retorno='" + retorno + '\'' +
                ", descricao='" + descricao + '\'' +
                ", item=" + item.toString() +
                '}';
    }
}


