package dao;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import bean.Carga;
import bean.Item;
import bean.Smbc;


/**
 * Created by raullima on 18/11/15.
 */
public class CargaColetaDao {

    private static final String METHOD_NAME = "smbc";
    private static final String NAMESPACE = "urn:server.smbc";
    private static final String URL = "http://www.termaco.com.br/cargasmobile/cargasmobiledev.php";
    private Carga carga = new Carga();
    private Item item;
    List<Item> listaItens = new ArrayList<>();

    public List<Item> smbcRequest(Smbc smbc) {
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

        req.setValue("<Smbc><imei>" + smbc.getImei() + "</imei><senha>" + smbc.getSenha() + "</senha><operacao>" + smbc.getOperacao() + "</operacao></Smbc>");
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

                Log.e("Resposta bodyin", respostas.toString());
                Log.e("Resposta Envelope", envelope.getResponse().toString());
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(envelope.getResponse().toString()));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("smbc");
                NodeList children = nodes.item(0).getChildNodes();

                for (int i = 1; i < children.getLength()-2; i++) {
                    NodeList grandson = children.item(i).getChildNodes();
                    
                    item = new Item();
                    for (int j = 0; j <grandson.getLength() ; j++) {

                        switch (j){
                            case 0:
                                item.setEmpCodigo(grandson.item(j).getTextContent());
                                break;
                            case 1:
                                item.setCodigo(grandson.item(j).getTextContent());
                                break;
                            case 2:
                                item.setTipo(grandson.item(j).getTextContent());
                                break;
                            case 3:
                                item.setNome(grandson.item(j).getTextContent());
                                break;
                            case 4:
                                item.setEndereco(grandson.item(j).getTextContent());
                                break;
                            case 5:
                                item.setOrdem(grandson.item(j).getTextContent());
                                break;
                            case 6:
                                item.setStatus(grandson.item(j).getTextContent());
                                break;
                            case 7:
                                item.setParada(grandson.item(j).getTextContent());
                                break;

                        }
                    }
                    listaItens.add(item);
                }

//                for (int i = 0; i <100 ; i++) {
//
//                    if(i == 0) {
//                        carga.setOperacao(extrairTagXml(respostas.getProperty(i).toString(), "operacao"));
//                        carga.setRetorno(extrairTagXml(respostas.getProperty(i).toString(), "retorno"));
//                        carga.setDescricao(extrairTagXml(respostas.getProperty(i).toString(), "descricao"));
//                    }
//
//                    item.setEmpCodigo(extrairTagXml(respostas.getProperty(i).toString(), "empcodigo"));
//                    item.setCodigo(extrairTagXml(respostas.getProperty(i).toString(), "codigo", item));
//                    item.setEndereco(extrairTagXml(respostas.getProperty(i).toString(), "endereco"));
//                    item.setNome(extrairTagXml(respostas.getProperty(i).toString(), "nome"));
//                    item.setOrdem(extrairTagXml(respostas.getProperty(i).toString(), "ordem"));
//                    item.setParada(extrairTagXml(respostas.getProperty(i).toString(), "parada"));
//                    item.setStatus(extrairTagXml(respostas.getProperty(i).toString(), "status"));
//                    item.setTipo(extrairTagXml(respostas.getProperty(i).toString(), "tipo"));
//                    carga.getListaItem().add(item);
//
//                    lista.add(carga);
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
        return listaItens;
    }


    //meus lindo metodos para extrair as tags do xml respondido pelo servidor. Apanheiiii muito para fazer essa proximas 10 linhas de código para resolver a ausencia de uma função que api ksoup2 não implementou.
    public static String extrairTagXml(String xml, String tag, Item item) {

        String valor = "";
        String endTag = "</" + tag.substring(0);

        Pattern p = Pattern.compile(tag + "(.*?)" + endTag);
        Matcher m = p.matcher(xml);

        if (m.find()) {
            valor = m.group(1);
        }
        //Gambirra para remover os erros gerados pelo empCodigo e Codigo para não ficar feio na hora de retornar os dados
        if (tag == "codigo") {
            valor = valor.replace(">"+item.getEmpCodigo()+"</empcodigo><codigo>", "");

        }
        return valor;
    }
    public static String extrairTagXml(String xml, String tag) {
        String valor = "";
        String endTag = "</" + tag.substring(0);

        Pattern p = Pattern.compile(tag + "(.*?)" + endTag);
        Matcher m = p.matcher(xml);

        if (m.find()) {
            valor = m.group(1);
        }
            valor = valor.replace(">", "");
        return valor;
    }
}
//
//        SoapObject carregarDados = new SoapObject(targetNamespace, SMBC);
//        spSmbc.addProperty("&lt;imei&gt;", Smbc.getImei());
//        spSmbc.addProperty("&lt;senha&gt;", Smbc.getSenha());
//        spSmbc.addProperty("&lt;operacao&gt;", Smbc.getOperacao());
//        carregarDados.addSoapObject(spSmbc);