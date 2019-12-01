package com.example.textureview_listview.Adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textureview_listview.Model.DataSetList;
import com.example.textureview_listview.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    List<DataSetList> listData = new ArrayList<>();
    Context context;
    File dir;
    String VIDEO_PATH;
    URL url;
    int count = 0;
    ProgressBar progressBar;

    public RvAdapter(List<DataSetList> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DataSetList current = listData.get(position);

        holder.webView.loadUrl(current.getLink());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String VIDEO_PATH = current.getLink();
                Toast.makeText(context, "Your File Is Downloading....", Toast.LENGTH_SHORT).show();
                DownloadAndShareVideo asyncTask = new DownloadAndShareVideo(0, VIDEO_PATH, 1);
                asyncTask.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        Button button;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.video_view);
            button = itemView.findViewById(R.id.fullscreen);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }


    public class DownloadAndShareVideo extends AsyncTask<String, Integer, String> {

        DownloadAndShareVideo(int share_medieum, String video_hqURL, int isWatermarkAdded) {
            VIDEO_PATH = video_hqURL;

            Log.d("path_video", VIDEO_PATH);
        }

        @Override
        protected String doInBackground(String... strings) {

            ContextWrapper cw = new ContextWrapper(context);
            dir = cw.getExternalCacheDir();
            Log.d("dirr", String.valueOf(dir));
            if (dir == null) {
                String externalPath = Environment.getExternalStorageDirectory() + File.separator + "Dub_My_Selfi";
                dir = new File(externalPath);

                Log.d("external_path", String.valueOf(dir));
            }
            String externalPath = Environment.getExternalStorageDirectory() + File.separator + "Dub_My_Selfi" + File.separator + "Downloads";
            dir = new File(externalPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!dir.exists()) {
                dir.mkdir();
            }
            //byte[] data = null;
            String temp[] = VIDEO_PATH.split("/");
            File file = new File(dir, temp[temp.length - 1]);
            System.out.println("Video Url  " + VIDEO_PATH + "     temp  Video >>>   " + temp[temp.length - 1]);

            try {
                if (!file.exists()) {
                    url = null;
                    url = new URL(VIDEO_PATH);

                    Log.d("uurl", String.valueOf(url));

                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(file.getAbsolutePath());

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress(Integer.valueOf("" + (int) ((total * 100) / lenghtOfFile)));
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } else {
                    file.getAbsolutePath();
                }
            } catch (Exception e) {
                return null;
            }
            return file.getAbsolutePath();
        }
    }
}
