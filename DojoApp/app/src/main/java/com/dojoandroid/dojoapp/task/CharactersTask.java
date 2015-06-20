package com.dojoandroid.dojoapp.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dojoandroid.dojoapp.entity.Character;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CharactersTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = CharactersTask.class.getSimpleName();

    public static final String URL = "http://swapi.co/api/people/";
    private Context mContext = null;
    private ProgressDialog mDialog = null;
    private CharactersTaskCallback mCallback = null;
    private List<Character> mContent = null;

    public interface CharactersTaskCallback {
        void onFinishWithSuccess(List<Character> content);
        void onFinishWithError();
    }

    /**
     * Constructor
     *
     * @param context of the application
     */
    public CharactersTask(Context context, CharactersTaskCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = ProgressDialog.show(mContext,"Carregando","Baixando JSON...",true, false);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean result = true;

        HttpGet get = new HttpGet(URI.create(URL));
        HttpClient client = new DefaultHttpClient();

        try {
            HttpResponse httpResponse = client.execute(get);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            String message = httpResponse.getStatusLine().getReasonPhrase();

            Log.d(TAG,"responseCode: "+responseCode);
            Log.d(TAG,"message: "+message);

            if(responseCode == HttpURLConnection.HTTP_OK) {
                HttpEntity entity = httpResponse.getEntity();
                String content = EntityUtils.toString(entity);
                Log.d(TAG,"content: "+content);

                parseContent(content);
            }

        } catch(IOException exception) {
            Log.e(TAG, "Error: " + exception.getLocalizedMessage());
            result = false;
        } catch(JSONException exception) {
            Log.e(TAG, "Error: " + exception.getLocalizedMessage());
            result = false;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if(mDialog != null) {
            mDialog.dismiss();
        }

        if(result) {
            mCallback.onFinishWithSuccess(mContent);
        } else {
            mCallback.onFinishWithError();
        }
    }

    /**
     * Parse the content of the JSON
     *
     * @param content JSON from the server
     *
     * @throws JSONException
     */
    private void parseContent(String content) throws JSONException {

        mContent = new ArrayList<>();

        JSONObject object = new JSONObject(content);
        JSONArray array = object.getJSONArray("results");

        for(int i=0 ; i<array.length() ; i++) {
            JSONObject item = (JSONObject) array.get(i);
            String name = item.getString("name");
            Log.e(TAG,""+name);

            Character character = new Character();
            character.setName(name);

            mContent.add(character);
        }
    }
}
