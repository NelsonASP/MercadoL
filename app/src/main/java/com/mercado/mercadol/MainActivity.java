package com.mercado.mercadol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.mercado.mercadol.Fragments.FragmentGalery;
import com.mercado.mercadol.Fragments.Init;
import com.mercado.mercadol.Utils.ConsultasML;
import com.mercado.mercadol.Utils.ICallbackListeners;
import com.mercado.mercadol.Utils.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import static androidx.annotation.InspectableProperty.ValueType.RESOURCE_ID;

public class MainActivity extends AppCompatActivity implements ICallbackListeners, Init.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;

    ConsultasML consultasML;
    final String TAG = "ConsultasML";

    FragmentGalery fg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        consultasML = new ConsultasML(this, this);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container,new Init()).commit();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        fg = new FragmentGalery();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void listenerListItemsxName(JSONObject data) {
        Log.e(TAG,"Arreglo" +  data);

        try {
            JSONArray arreglo = data.getJSONArray("results");
            Log.e(TAG,"one");
            fg.llenaritems(arreglo);
            Log.e(TAG,"thwo");
            setNextFragment(fg);

        }catch (Exception e){
        }
    }

    @Override
    public void listenerListItemsxIds(JSONArray data) {

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

}