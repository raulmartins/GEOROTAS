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
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GEOROTAS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Runnable{

    private TextView listaCargas;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_georotas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listaCargas = (TextView) findViewById(R.id.textView_ListaCargas);
        ListView lc = (ListView) findViewById(R.id.listaDeCargas);
        FloatingActionButton novaCarga = (FloatingActionButton) findViewById(R.id.novaCarga);
        novaCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Carregando Novas Cargas...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //dialog = ProgressDialog.show(GEOROTAS.this,"Aguardar","Aguarde carregando dados...", false);
       new Thread(GEOROTAS.this).start();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void run() {
        try {
            carregarCargas();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void carregarCargas() throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc  = builder.parse("http://www.cinemark.com.br/mobile/xml/films/");
        NodeList listDeFilms = doc.getElementsByTagName("films");
        int qtdFilms = listDeFilms.getLength();
        for(int x=0;x<qtdFilms;x++){

            Node noFilme = listDeFilms.item(x);

            if(noFilme.getNodeType()== Node.ELEMENT_NODE){
                Element elementFilm = (Element) noFilme;
                String id = elementFilm.getAttribute("id");
                Log.e("ID",id);
                NodeList listAttributFilm = elementFilm.getChildNodes();
                int qtdAttribuiltFilms = listAttributFilm.getLength();
                for (int i=0; i<qtdAttribuiltFilms;i++){
                    Node noAttribuit = listAttributFilm.item(i);
                    if(noAttribuit.getNodeType() == Node.ELEMENT_NODE){
                        Element elementAttribut = (Element) noAttribuit;
                        switch (elementAttribut.getTagName()){
                            case "genre":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "parent-guide-rating":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "media-3d":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "media-35mm":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "trailer":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "top":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "first-print":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "runtime":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "screens":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "showtimes":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                            case "distributor":
                                Log.e("",elementAttribut.getTextContent());
                                break;
                        }
                    }
                }
            }
        }

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
