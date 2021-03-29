package com.mercado.mercadol;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.mercado.mercadol.Fragments.FragmentGalery;
import com.mercado.mercadol.Fragments.FragmentItem;
import com.mercado.mercadol.Fragments.Init;
import com.mercado.mercadol.Utils.AdapterItem;
import com.mercado.mercadol.Utils.ConsultasML;
import com.mercado.mercadol.Utils.ICallbackListeners;
import com.mercado.mercadol.Utils.ItemsVo;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ICallbackListeners, Init.OnFragmentInteractionListener, FragmentGalery.OnFragmentInteractionListener {

    ConsultasML consultasML;
    final String TAG = "ConsultasML";

    AdapterItem adapterItem;
    ArrayList<ItemsVo> listaitmes;

    RecyclerView recycleritems;

    FragmentGalery fg;
    FragmentItem fragmentItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        consultasML = new ConsultasML(this, this);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container,new Init()).commit();

        adapterItem = new AdapterItem(listaitmes);
        recycleritems = findViewById(R.id.recyclerID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void listenerListItemsxName(JSONObject data) {
        Log.e(TAG,"Consulta Items Name" +  data);
        fg = new FragmentGalery();
        try {
            JSONArray arreglo = data.getJSONArray("results");
            fg.llenaritems(arreglo);
            setNextFragment(fg);

        }catch (Exception e){
        }
    }

    @Override
    public void listenerListItemsxIds(JSONArray data) {
        Log.e(TAG,"Consulta Items IDs" +  data);
        fragmentItem = new FragmentItem();
        try {
            fragmentItem.llenaritem(data);
            setNextFragment(fragmentItem);

        }catch (Exception e){
        }
    }

    public void setNextFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void CargaFicha(String item) {
        consultasML.consultaItemsXNombre(item);
    }

    @Override
    public void cargaID(String item) {
        consultasML.consultaItemsxIds(item);
    }
}