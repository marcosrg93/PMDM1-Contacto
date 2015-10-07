package com.rubino.pmdmcontacto;

/**
 * Created by marco on 06/10/2015.
 */

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class ClaseAdaptador extends ArrayAdapter<String>{

    private Context ctx;
    private int res;
    private LayoutInflater lInflator;
    private List<String> valores;
    private List<Contacto> valoresC;

    static class ViewHolder {
        public TextView tv2;

    }

    public ClaseAdaptador(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.ctx = context;//actividad
        this.res = resource;//layout del item
        this.valores = objects;//lista de valores
        this.lInflator = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }



    public boolean borrar(int position) {
        try {
            valores.remove(position);
            this.notifyDataSetChanged();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1
        ViewHolder gv = new ViewHolder();
        if(convertView==null){
            convertView = lInflator.inflate(res, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tvTelf);
            gv.tv2 = tv;
            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }
        gv.tv2.setText("Tlf: "+valores.get(position));
        return convertView;
    }


}
