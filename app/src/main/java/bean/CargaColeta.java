package bean;

/**
 * Created by raullima on 18/11/15.
 */
public class CargaColeta {
    private ctrc ctrc;

    public bean.ctrc getCtrc() {
        return ctrc;
    }

    public void setCtrc(bean.ctrc ctrc) {
        this.ctrc = ctrc;
    }

    @Override
    public String toString() {
        return "CargaColeta{" +
                "ctrc=" + ctrc.getCodigo() +
                '}';
    }
}
