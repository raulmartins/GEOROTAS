package rmtech.georotas;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GEOROTAS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Runnable {
    private String respostaServce;


    private TextView text_viewlistaCargas;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_georotas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        text_viewlistaCargas = (TextView) findViewById(R.id.textView_ListaCargas);
        //ListView lc = (ListView) findViewById(R.id.listaDeCargas);
        FloatingActionButton novaCarga = (FloatingActionButton) findViewById(R.id.novaCarga);
        novaCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_viewlistaCargas.setText(respostaServce);

                Snackbar.make(view, "Carregando Novas Cargas...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new Thread(GEOROTAS.this).start();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void run(){
        try {
            Log.e("XML:", carregarCargas());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String carregarCargas() throws IOException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpPost postRequest = new HttpPost("http://www.termaco.com.br/cargasmobile/cargasmobiledev.php?wsdl");
        //final StringEntity input = new StringEntity("<smbc><imei>123</imei>123<senha></senha><operacao>1</operacao></smbc>");
        //input.setContentType("text/xml");

                try {
                    //postRequest.setEntity(input);
                    HttpResponse response = httpClient.execute(postRequest);
                    respostaServce = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return respostaServce;


    }



        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.georota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.novaCarga) {
           item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
               @Override
               public boolean onMenuItemClick(MenuItem menuItem) {


                   return false;
               }
           });

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

//        try {
// '<smbc>
//      <imei>123</imei>
//      <senha>123</senha>
//      <operacao>1</operacao>
//      <empcodigo>SPO</empcodigo>
//      <codigo>820812</codigo>
//      <tipo>1</tipo>
//      <ordem>1</ordem>
//      <status>P</status>
//      <oco>25</oco>
// </smbc>';

//            TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//            mngr.getDeviceId();

//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.newDocument();
//

//            Element smbc = doc.createElement("smbc");
//            doc.appendChild(smbc);
//
//            Element imei = doc.createElement("imei");
//            imei.setTextContent(mngr.toString());
//            doc.appendChild(imei);
//
//            Element senha = doc.createElement("senha");
//            imei.setTextContent("123");
//            doc.appendChild(senha);
//
//            Element operacao = doc.createElement("operacao");
//            operacao.setTextContent("1");
//            doc.appendChild(operacao);
//
//            OutputStream os = connection.getOutputStream();
//
//
////                 Write your XML to the OutputStream (JAXB is used in this example)
//                jaxbContext.createMarshaller().marshal(customer, os);
//                os.flush();
//                connection.getResponseCode();
//                connection.disconnect();




//            Element empcodigo = doc.createElement("empcodigo");
//            empcodigo.setTextContent("SPO");
//            doc.appendChild(empcodigo);
//
//            Element codigo = doc.createElement("codigo");
//            codigo.setTextContent("820812");
//            doc.appendChild(codigo);
//
//            Element tipo = doc.createElement("tipo");
//            tipo.setTextContent("1");
//            doc.appendChild(tipo);
//
//            Element ordem = doc.createElement("ordem");
//            ordem.setTextContent("1");
//            doc.appendChild(ordem);
//
//            Element status = doc.createElement("status");
//            status.setTextContent("P");
//            doc.appendChild(status);
//
//            Element oco = doc.createElement("oco");
//            oco.setTextContent("25");
//            doc.appendChild(oco);

//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//
//            DOMSource documentoFonte = new DOMSource(doc);
//
//            StreamResult
//



//        } catch (ParserConfigurationException e) {
//            Log.e("Erro Builder:",e.getMessage());
////        }
//        catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//


//for(int x=0;x<qtdFilms;x++){
//        Node noFilme = listDeFilms.item(x);
//        if(noFilme.getNodeType()== Node.ELEMENT_NODE) {
//        Element elementFilm = (Element) noFilme;
//
//        Log.e("text", elementFilm.getTextContent());
//
//
//
//        if(elementFilm.getTagName().equals("documentation")){
//        text_viewlistaCargas.setText(elementFilm.getTextContent().toString());
//        Log.e("mudei?",text_viewlistaCargas.getText().toString());
//        }
//        }
//        }



//        URL url = new URL("http://www.termaco.com.br/cargasmobile/cargasmobiledev.php?wsdl");
//        URLConnection conn = url.openConnection();
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(conn.getInputStream());
//
//
//        TransformerFactory factoryT = TransformerFactory.newInstance();
//        Transformer xform = factoryT.newTransformer();
//
//        Log.i("este", "AQUiiiiiiiiiii*******************************" + doc.toString());
//
//// that’s the default xform; use a stylesheet to get a real one
//        //xform.transform(new DOMSource(doc), new StreamResult(System.out));
