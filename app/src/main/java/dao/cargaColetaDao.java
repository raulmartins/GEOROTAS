package dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.Carga;
import bean.Item;
import bean.smbc;

/**
 * Created by raullima on 18/11/15.
 */
public class cargaColetaDao {

    private static final String METHOD_NAME = "smbc";
    private static final String NAMESPACE = "urn:server.smbc";
    private static final String URL = "http://www.termaco.com.br/cargasmobile/cargasmobiledev.php";

    public List<Carga> smbcRequest(smbc smbc) {
        //instanciando variaveis
        PropertyInfo req = new PropertyInfo();
        ArrayList<Carga> lista = new ArrayList<>();
        SoapObject spSmbc = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        HttpTransportSE http = new HttpTransportSE(URL);
        System.setProperty("http.keepAlive", "false");
        http.debug = true;
        envelope.dotNet = true;
        //configuração das variaveis

        req.setValue("<smbc><imei>" + smbc.getImei() + "</imei><senha>" + smbc.getSenha() + "</senha><operacao>" + smbc.getOperacao() + "</operacao></smbc>");
        req.namespace = NAMESPACE;
        req.name = "smbc";
        req.type = String.class;
        spSmbc.addProperty(req);


        //add objectSoap in envelope to send web service
        envelope.setOutputSoapObject(spSmbc);
        envelope.implicitTypes = true;


        try {

            http.call(URL, envelope);
            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault sf = (SoapFault) envelope.bodyIn;
                throw new Exception(sf.getMessage());
            }

            else if (envelope.bodyIn instanceof SoapObject) {
                SoapObject respostas = (SoapObject)envelope.bodyIn;
                for (int i = 0; i <respostas.getPropertyCount() ; i++) {

                    Carga carga = new Carga();
                    Item item = new Item();

                    carga.setOperacao(extrairTagXml(respostas.getProperty(i).toString(), "operacao"));
                    carga.setRetorno(extrairTagXml(respostas.getProperty(i).toString(), "retorno"));
                    carga.setDescricao(extrairTagXml(respostas.getProperty(i).toString(), "descricao"));
                    item.setEmpCodigo(extrairTagXml(respostas.getProperty(i).toString(), "empcodigo"));
                    item.setCodigo(extrairTagXml(respostas.getProperty(i).toString(), "codigo"));
                    item.setEndereco(extrairTagXml(respostas.getProperty(i).toString(), "endereco"));
                    item.setNome(extrairTagXml(respostas.getProperty(i).toString(), "nome"));
                    item.setOrdem(extrairTagXml(respostas.getProperty(i).toString(), "ordem"));
                    item.setParada(extrairTagXml(respostas.getProperty(i).toString(), "parada"));
                    item.setStatus(extrairTagXml(respostas.getProperty(i).toString(), "status"));
                    item.setTipo(extrairTagXml(respostas.getProperty(i).toString(), "tipo"));

                    carga.setItem(item);
                    lista.add(carga);
                }
            }
//             }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    //meu lindo metodo para extrair as tags do xml respondido pelo servidor. Apanheiiii muito para fazer essa proximas 10 linhas de código para resolver a ausencia de uma função que api ksoup2 não implementou.
    public static String extrairTagXml(String xml, String tag) {
        String result = "";
        String valor = "";
        String endTag = "</" + tag.substring(0);

        Pattern p = Pattern.compile(tag + "(.*?)" + endTag);
        Matcher m = p.matcher(xml);

        if (m.find()) {
            valor = m.group(1);
        }
        if (tag == "codigo") {
            valor = valor.replace(">FOR</empcodigo><codigo>", "");
            result = valor;
        } else {
            valor = valor.replace(">", "");
            result = valor;

        }
        return result;
    }
}
//
//        SoapObject carregarDados = new SoapObject(targetNamespace, SMBC);
//        spSmbc.addProperty("&lt;imei&gt;", smbc.getImei());
//        spSmbc.addProperty("&lt;senha&gt;", smbc.getSenha());
//        spSmbc.addProperty("&lt;operacao&gt;", smbc.getOperacao());
//        carregarDados.addSoapObject(spSmbc);