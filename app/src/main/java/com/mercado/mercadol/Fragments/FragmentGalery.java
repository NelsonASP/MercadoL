package com.mercado.mercadol.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mercado.mercadol.Articulo;
import com.mercado.mercadol.MainActivity;
import com.mercado.mercadol.R;
import com.mercado.mercadol.Utils.AdapterItem;
import com.mercado.mercadol.Utils.ItemsVo;
import com.mercado.mercadol.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGalery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGalery extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<ItemsVo> listaitmes;
    RecyclerView recycleritems;
    ImageButton btnlist, btngrid;
    ImageView imageView;

    AdapterItem adapterItem;

    final String TAG = "ConsultasML";

    Articulo  articulo;

    public FragmentGalery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGalery.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGalery newInstance(String param1, String param2) {
        FragmentGalery fragment = new FragmentGalery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_galery, container, false);

        recycleritems = view.findViewById(R.id.recyclerID);
        btngrid = view.findViewById(R.id.btngrid);
        btnlist = view.findViewById(R.id.btnlist);

        btngrid.setOnClickListener(listener2);
        btnlist.setOnClickListener(listener2);

        imageView = view.findViewById(R.id.idImagen);

        construirRecycler();
        return view;
    }

    public void llenaritems(JSONArray arreglo){

        listaitmes = new ArrayList<>();

        Log.e(TAG,"entrada");
        try {
            Log.e(TAG,"entrada2");
            for (int i = 0; i < arreglo.length(); i ++) {
                JSONObject temp  = (JSONObject) arreglo.get(i);
                String id = temp.getString("id");
                String titulo = temp.getString("title");
                String precio = temp.getString("price");
                String imagen = temp.getString("thumbnail");

                Log.e(TAG,"estados"+ i + titulo + precio);

                listaitmes.add(new ItemsVo(id, titulo,precio, "En 36 x 5000", R.mipmap.xbox ));
            }
            Log.e(TAG,"salida1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG,"salida2");

        //adapter.notify();
    }



    private void construirRecycler() {
        //listaitmes = new ArrayList<>();

        if (Utils.visualizacion == Utils.LIST){
            recycleritems.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else {
            recycleritems.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }

       // llenaritems();

        adapterItem = new AdapterItem(listaitmes);

        adapterItem.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.getId();

                articulo = new Articulo();
                ((MainActivity) requireActivity()).setNextFragment(new FragmentItem(articulo));

            }
        });
        recycleritems.setAdapter(adapterItem);
    }

    private View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnlist:
                    Utils.visualizacion = Utils.LIST;
                    Log.e("TAG", "List");
                    break;
                case R.id.btngrid:
                    Utils.visualizacion = Utils.GRID;
                    Log.e("TAG", "Grid");
                    break;
            }
            construirRecycler();
        }
    };
}