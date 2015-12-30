package rmtech.georotas;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import bean.Carga;
import bean.Item;
import bean.Smbc;
import dao.ItemDao;
import helper.RegistroHelperCarregarCarga;

public class GEOROTAS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listViewCargas;
    private ArrayAdapter<Item> adaptador;
    private FloatingActionButton novaCarga;
    private Carga carga;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_georotas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listViewCargas = (ListView) findViewById(R.id.listView_cargas);
        //corrigindo o bug
        corrigirProblemaDeConexaoThread();
        final ItemDao itemDao = new ItemDao(this);
        //atualiza o banco com base no xml recebido do webservice
        itemDao.carregarBaseDeDados();
        //trazendo as informações do banco para listView
        adaptador = new ArrayAdapter<Item>(GEOROTAS.this, android.R.layout.simple_list_item_1, itemDao.recuperarRegistrosCargaItens());
        adaptador.notifyDataSetChanged();
        listViewCargas.setAdapter(adaptador);
        registerForContextMenu(listViewCargas);


        novaCarga = (FloatingActionButton) findViewById(R.id.novaCarga);
        novaCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carga = new Carga();
                RegistroHelperCarregarCarga rhcc = new RegistroHelperCarregarCarga();
                carga = rhcc.smbcRequest(new Smbc("123", "123", "1"));
                itemDao.carregarBaseDeDados();
                carga.setListaItem(itemDao.recuperarRegistrosCargaItens());
                Log.e("Retorno do WebService:", carga.toString());
                Snackbar.make(view, "Operação:"+carga.getOperacao()+" Retorno:"+carga.getRetorno()+"\n"+carga.getDescricao(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                adaptador = new ArrayAdapter<Item>(GEOROTAS.this, android.R.layout.simple_list_item_1, carga.getListaItem());
                adaptador.notifyDataSetChanged();
                listViewCargas.setAdapter(adaptador);
                registerForContextMenu(listViewCargas);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        FragmentManager fm = getFragmentManager();
        switch (id){
            case R.id.novaCarga:
                fm.beginTransaction().replace(R.id.content_frame_georotas, new FragmentMain()).commit();
                novaCarga.show();
                listViewCargas.setVisibility(View.VISIBLE);
                break;
            case R.id.scanner:
                fm.beginTransaction().replace(R.id.content_frame_georotas,new FragmentScanner()).commit();
                novaCarga.hide();
                listViewCargas.setVisibility(View.GONE);
                break;
            case R.id.codigoBarras:
                fm.beginTransaction().replace(R.id.content_frame_georotas, new FragmentCodigoBarras()).commit();
                novaCarga.hide();
                listViewCargas.setVisibility(View.GONE);
                break;
            default:{

            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "GEOROTAS Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://rmtech.georotas/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "GEOROTAS Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://rmtech.georotas/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }
    public void corrigirProblemaDeConexaoThread(){

        //permissão de request http na thread principal
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
}

