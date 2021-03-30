package com.mercado.mercadol.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mercado.mercadol.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentItem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    final String TAG = "ConsultasML";

    TextView titulo;
    TextView precio;
    ImageView imagen;
    TextView condicion;
    TextView vendidos;
    TextView moneda;
    TextView mercadoPago;
    String tvPrecio;
    String tvTitulo;
    String tvMoneda;
    String tvItemsVendidos;
    String tvCondicion;
    Boolean acceptsmercadopago;
    String imagenurl;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentItem.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentItem newInstance(String param1, String param2) {
        FragmentItem fragment = new FragmentItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentItem() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        titulo = view.findViewById(R.id.idtitulo);
        precio = view.findViewById(R.id.idprecio);
        imagen = view.findViewById(R.id.idImagen);
        condicion = view.findViewById(R.id.idCondicion);
        vendidos = view.findViewById(R.id.idVendidos);
        moneda = view.findViewById(R.id.idmoneda);
        mercadoPago = view.findViewById(R.id.idmercadopago);

        titulo.setText(tvTitulo);
        precio.setText(formatAmount(tvPrecio));
        vendidos.setText(tvItemsVendidos);
        moneda.setText(tvMoneda);

        try {
            Glide.with(view)
                    .load(new URL(imagenurl))
                    .into(imagen);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (acceptsmercadopago == true){
           mercadoPago.setText("Si");
       }else {
           mercadoPago.setText("No");
       }

        switch (tvCondicion){
            case "used":
                condicion.setText("Usado");
                break;

            case "new":
                condicion.setText("Nuevo");
                break;

        }
        return view;
    }

    /**
     * *
     * Método que recibe los datos del Main Activity
     * *
     */
    public void llenaritem(JSONArray arreglo){
        Log.e(TAG,"Consulta" + arreglo);

        try {
            JSONObject articulo = (JSONObject) arreglo.get(0);
            JSONObject body = articulo.getJSONObject("body");
            JSONArray imgenes = body.getJSONArray("pictures");

            for (int i = 0; i < imgenes.length(); i ++) {
                JSONObject temp1  = (JSONObject) imgenes.get(0);
                imagenurl = temp1.getString("url");
            }

            tvTitulo = body.getString("title");
            tvPrecio = body.getString("price");
            tvMoneda = body.getString("currency_id");
            tvItemsVendidos = body.getString("sold_quantity");
            tvCondicion = body.getString("condition");
            acceptsmercadopago = body.getBoolean("accepts_mercadopago");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String formatAmount(String amount) {
        Double fAmt = Double.parseDouble(amount);
        return String.format(Locale.US, "%.2f", fAmt);
    }
}