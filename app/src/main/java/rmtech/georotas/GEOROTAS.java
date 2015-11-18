package rmtech.georotas;

import android.app.ProgressDialog;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class GEOROTAS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Runnable {

    private TextView listaCargas;
    ProgressDialog dialog;

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
                run();
                Snackbar.make(view, "Carregando Novas Cargas...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        //dialog = ProgressDialog.show(GEOROTAS.this,"Aguardar","Aguarde carregando dados...", true);

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
        carregarCargas();
    }
    private void carregarCargas() {

        SoapObject soap = new SoapObject("http://www.termaco.com.br", "carrega");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);
        Log.i("Termaco", "Chamando WebService para carregar cargas");
        String url = "http://www.termaco.com.br/cargasmobile/cargasmobiledev.php?wsdl";

        HttpTransportSE httpTransport = new HttpTransportSE(url);

        try{
            httpTransport.call("", envelope);
            listaCargas.setText(envelope.getResponse().toString());
            Object msg = envelope.getResponse();
            Log.d("Termaco", "Dado: " + msg);
            listaCargas.setText("teste");




        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            Log.e("Termaco", "erro de busca CEP!");
        finish();
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
