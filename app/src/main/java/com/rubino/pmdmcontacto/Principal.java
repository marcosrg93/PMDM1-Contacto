package com.rubino.pmdmcontacto;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rubino.pmdmcontacto.datos.contacto.Contacto;
import com.rubino.pmdmcontacto.datos.contacto.GestionContactos;
import com.rubino.pmdmcontacto.datos.contacto.OrdenaNombresAsc;
import com.rubino.pmdmcontacto.datos.contacto.OrdenaNombresDes;
import com.rubino.pmdmcontacto.filtros.OnScrollUpDownListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class  Principal extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private AdaptadorContacto ac;
    private List<Contacto> lContactos;
    private FloatingActionButton fab;
    private ListView listView;

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

       /* SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.mn_ordenaMayor) {
            ordenaNombresAsc();
            ac.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.mn_ordenaMenor) {
            ordenaNombresDes();
            ac.notifyDataSetChanged();
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
            ac.dgEdit(posicion);
            return true;
        } else if(id==R.id.mn_borrar){
            ac.borrar(posicion);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    //---------------------------------------------------------------------------------------------------------------------//
    //Inicializamos todos los componentes que llamaremos en el onCreate
    private void init() {
        //Floating Action Button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ac.dgInsert();
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

        listView = (ListView) findViewById(R.id.lvContactos);
        lContactos = GestionContactos.getLista(this);

        ac = new AdaptadorContacto(this, R.layout.elementos_lv, lContactos);

        listView.setTextFilterEnabled(true);

        listView.setAdapter(ac);

        listView.setTag(lContactos);



        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        //Comprueba la posición en el listview para ocultarlo o no según la acción realizada
        listView.setOnScrollListener(new OnScrollUpDownListener(listView, 8, scrollAction));

        registerForContextMenu(listView);

    }

    //---------------------------------------------------------------------------------------------------------------------//
    public void ordenaNombresAsc(){
        Collections.sort(lContactos, new OrdenaNombresAsc());
    }

    public void ordenaNombresDes(){
        Collections.sort(lContactos, new OrdenaNombresDes());
    }


    //---------------------------------------------BUSCAR--------------------------------------------------------------------//

    @Override
    public boolean onQueryTextChange(String newText)
    {

        // this is your adapter that will be filtered
       if (TextUtils.isEmpty(newText))
        {

            listView.clearTextFilter();
            ac.notifyDataSetChanged();
        }
        else
        {
            listView.setFilterText(newText.toString());
            ac.notifyDataSetChanged();
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }


}
