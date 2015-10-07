package com.rubino.pmdmcontacto;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {


    private AdaptadorContacto cl;
    private List<Contacto> lista;
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
        lista = cl.getListaContactos(this);

        cl = new AdaptadorContacto(this, R.layout.elementos_lv, lista);

        lv.setAdapter(cl);
        lv.setTag(lista);

        //Comprueba la posición en el listview para ocultarlo o no según la acción realizada
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
