package forsk.in.photo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by student on 22-04-2016.
 */
public class ImageLoaderAsyncTask extends AsyncTask<String, Integer, Bitmap> {

    Context context;
    ImageView imageView;
    String url = "";
    ProgressDialog pd;


    public ImageLoaderAsyncTask(Context context, String url, ImageView imageView) {
        this.context = context;
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setMessage("Loading Image..");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream in = Utils.openHttpConnection(url);
        Bitmap bmp = BitmapFactory.decodeStream(in);


        for (int i = 1; 10 > i; i++) {
            try {
                onProgressUpdate(i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bmp;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);


        final int progress = values[0] * 10;

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.setMessage("Loading Image  " + progress + "%..");
            }
        });

    }



    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (result != null)
            imageView.setImageBitmap(result);

        if (pd != null)
            pd.dismiss();
    }


}
