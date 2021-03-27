package com.mercado.mercadol.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ConsultasML {

    final String TAG = "ConsultasML";

    Context myContext;

    final String  urlServidor = "https://api.mercadolibre.com/" ;

    /**
     * Urls de las consultas
     */
    final String urlConsultaItemsNombre = "sites/MLA/search?";
    final String urlConsultaItemsIds    = "items?";

    /**
     * Listado de las consultas que tenemos
     */
    final int CONSULTA_ITEMS_X_NOMBRE = 1;
    final int CONSULTA_ITEMS_X_IDS    = 2;

    /**
     * Identifica que consulta estamos haciendo
     */
    int tipoConsulta = CONSULTA_ITEMS_X_NOMBRE;


    public ICallbackListeners callback;

    public ConsultasML(Context myContext) {
        this.myContext = myContext;
    }

    public ConsultasML(Context myContext, ICallbackListeners listener) {
        this.myContext = myContext;
        this.callback = listener;
    }

    /**
     * Método que establece el listener para las respuestas.
     *
     * @param listener
     */
    public void setListener(ICallbackListeners listener){
        callback = listener;
    }

    /**
     * Método que hace la búsqueda de items x parte del nombre.
     *
     * Ejemplo : https://api.mercadolibre.com/sites/MLA/search?q=calentador
     *
     * @param texto a buscar
     */
    public void consultaItemsXNombre(String texto){

        tipoConsulta = CONSULTA_ITEMS_X_NOMBRE;

        String[] data = {"q",texto};

        new ConsultasAsincronas(urlConsultaItemsNombre, data).execute();
    }


    /**
     * Método que hace la búsqueda de items x Id
     * Se pueden consultar varios ítems separando sus ids con comas.
     *
     * Ejemplo : https://api.mercadolibre.com/items?ids=MLA750241991,MLA594239600
     *
     * @param ids de los artículos
     */
    public void consultaItemsxIds(String... ids ){

        tipoConsulta = CONSULTA_ITEMS_X_IDS;

        String temp = String.join(",",ids);

        String[] data = {"ids",temp};

        new ConsultasAsincronas(urlConsultaItemsIds, data).execute();

    }

    /**
     * Clase asíncrona que realiza las consultas al servidor
     */
    private class ConsultasAsincronas extends AsyncTask<String, Void, String> {

        private String url;
        private JSONObject objectResponse;
        private JSONArray  objectResponse2;

        private String complemento;

        public ConsultasAsincronas(String modulo, String... datos) {

            Log.d(TAG, "Consultas()");

            complemento = String.join("=", datos);

            url = urlServidor.concat(modulo).concat(complemento);
        }


        protected String doInBackground(String... strings) {

            InputStream inputStream = null;

            Log.d(TAG, "Url "+ url);

            try {
                URL url2 = new URL(url); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url2.openConnection();
                //httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("GET"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                //httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }

                if(inputStream != null)
                    return convertInputStreamToString(inputStream);
                else
                    return "";

            } catch (MalformedURLException e) {
                //e.printStackTrace();
                Log.d(TAG, "Excepcion 1 = "+e.getMessage());
                return "";
            } catch (IOException e) {
                //e.printStackTrace();
                Log.d(TAG, "Excepcion 2 = "+e.getMessage());
                return "";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            Log.d(TAG, "Recibido del servidor = "+result);

            String result2 = "";

            result = result.trim();

            // Validar el tamaño
            if (result.length()>5){
                result2 = result;
            }

            // Extraer datos de la respuesta
            try {

                if(tipoConsulta==CONSULTA_ITEMS_X_NOMBRE){
                    objectResponse = new JSONObject(result2);
                }else if (tipoConsulta==CONSULTA_ITEMS_X_IDS){
                    objectResponse2 = new JSONArray(result2);
                }

            } catch (JSONException e) {
                Log.d(TAG, "JSONException 1 " +e.getMessage());
            }

            if (callback!=null){
                if(tipoConsulta==CONSULTA_ITEMS_X_NOMBRE){
                    callback.listenerListItemsxName(objectResponse);
                }else if (tipoConsulta==CONSULTA_ITEMS_X_IDS){
                    callback.listenerListItemsxIds(objectResponse2);
                }
            }

        }

    }

    /**
     * Convertir el input de la petición en un String
     *
     * @param inputStream
     * @return String
     * @throws IOException
     */
    public String convertInputStreamToString(InputStream inputStream) throws IOException {

        Log.d(TAG,"convertInputStreamToString()");

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}

