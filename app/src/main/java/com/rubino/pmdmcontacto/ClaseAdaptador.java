package com.rubino.pmdmcontacto;

/**
 * Created by marco on 06/10/2015.
 */

import android.content.Context;
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
        public TextView tv1, tv2;
        public ImageView iv;
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
            TextView tv = (TextView) convertView.findViewById(R.id.tvNombre);
            gv.tv1 = tv;
            tv = (TextView) convertView.findViewById(R.id.tvTelf);
            gv.tv2 = tv;
            ImageView iv = (ImageView) convertView.findViewById(R.id.ivNum);
            gv.iv = iv;
            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }
        gv.iv.setTag(position);
        //addListener(gv.iv, position);
        gv.tv1.setText(valores.get(position));
        gv.tv2.setText("lo otro "+valores.get(position));
        return convertView;
    }

    private void addListener(ImageView iv, final int position){
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "flor "+position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
