package bean;

import java.util.List;

/**
 * Created by raullima on 25/11/15.
 */
public class Carga {

    private String operacao;
    private String retorno;
    private String descricao;
    private List<Item> listaItem;

    public Carga() {
    }

    public Carga(String operacao, List item, String retorno, String descricao) {
        this.operacao = operacao;
        this.listaItem = item;
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


    public List<Item> getListaItem() {
        return listaItem;
    }

    public void setListaItem(List<Item> listaItem) {
        this.listaItem = listaItem;
    }

    @Override
    public String toString() {
        return "Carga{" +
                "operacao='" + operacao + '\'' +
                ", retorno='" + retorno + '\'' +
                ", descricao='" + descricao + '\'' +
                ", listaItem=" + listaItem +
                '}';
    }
}


