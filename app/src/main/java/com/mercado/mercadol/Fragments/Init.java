package com.mercado.mercadol.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.mercado.mercadol.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Init#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Init extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText search;
    final String TAG = "TAG";

    private OnFragmentInteractionListener mListener;

    public Init() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Init.
     */
    // TODO: Rename and change types and number of parameters
    public static Init newInstance(String param1, String param2) {
        Init fragment = new Init();
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
        View view = inflater.inflate(R.layout.fragment_init, container, false);

        search = view.findViewById(R.id.editbuscar);

        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // si presiona enter
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String itemSearch = search.getText().toString();
                    Log.e(TAG,"" +  itemSearch);
                    mListener.CargaFicha(itemSearch);
                    Log.e("TAG", "button");

                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //+ " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * *
     * Método para envío de parámetro de búsqueda
     * *
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void CargaFicha (String item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}