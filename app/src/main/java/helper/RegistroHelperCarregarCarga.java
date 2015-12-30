package helper;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import bean.Carga;
import bean.Item;
import bean.Smbc;


/**
 * Created by raullima on 18/11/15.
 */
public class RegistroHelperCarregarCarga {

    private static final String METHOD_NAME = "smbc";
    private static final String NAMESPACE = "urn:server.smbc";
    private static final String URL = "http://www.termaco.com.br/cargasmobile/cargasmobiledev.php";
    private Carga carga = new Carga();
    private Item item;
    List<Item> listaItens = new ArrayList<>();

    public Carga smbcRequest(Smbc smbc) {
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
            } else if (envelope.bodyIn instanceof SoapObject) {
                SoapObject respostas = (SoapObject) envelope.bodyIn;

                Log.e("Resposta bodyin", respostas.toString());
                Log.e("Resposta Envelope", envelope.getResponse().toString());
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(envelope.getResponse().toString()));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("smbc");
                NodeList children = nodes.item(0).getChildNodes();

                for (int i = 0; i < children.getLength(); i++) {
                    if(i == 0){
                    carga.setOperacao(children.item(i).getTextContent());
                    }
                    else if(children.item(children.getLength()-2).equals(children.item(i))){
                        carga.setRetorno(children.item(i).getTextContent());
                    } else if(children.item(children.getLength()-1).equals(children.item(i))){
                        carga.setDescricao(children.item(i).getTextContent());
                    }else {

                        NodeList grandson = children.item(i).getChildNodes();

                        item = new Item();
                        for (int j = 0; j < grandson.getLength(); j++) {

                            switch (j) {
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
                }

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        carga.setListaItem(listaItens);
        return carga;
    }



}


