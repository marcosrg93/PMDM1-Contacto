package com.rubino.pmdmcontacto;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.ViewCompat.animate;

public class Principal extends AppCompatActivity {

    private ClaseAdaptador cl;
    private AdaptadorContacto cl2;
    private List<String> lista;
    private List<Contacto> lista2;
    private FloatingActionButton fab;
    private Contacto c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

    //Creamos el menu contextual que nos dará las opciones de editar y borrar de cada Contacto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);

    }
    //Damos funcionalidad al los elementos del menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int posicion = vistaInfo.position;
        if(id==R.id.mn_editar){
            return true;
        } else if(id==R.id.mn_borrar){
            cl.borrar(posicion);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    //Inicializamos todos los componentes que llamaremos en el onCreate
    private void init() {
        //Floating Action Button
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Comprobamos la posicion para que se muestre o no
        OnScrollUpDownListener.Action scrollAction = new OnScrollUpDownListener.Action() {

            @Override
            public void up() {
                fab.hide();
            }

            @Override
            public void down() {
                fab.show();
            }

        };




        final ListView lv = (ListView) findViewById(R.id.lvContactos);
        lista = new ArrayList<>();
        lista2 = new ArrayList<>();

        long id = 0;

        lista2 = cl2.getListaContactos(this);




        cl2 = new AdaptadorContacto(this, R.layout.elementos_lv, lista2);


        lv.setAdapter(cl2);

        lv.setTag(lista2);


        lv.setOnScrollListener(new OnScrollUpDownListener(lv, 8, scrollAction));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Pulsado encima", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });


        registerForContextMenu(lv);

    }


}
