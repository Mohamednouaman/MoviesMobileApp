package aya.net.myapplication;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aya.net.myapplication.R;
import models.MovieItem;

public class ListItemModel extends ArrayAdapter<MovieItem> {

    private int resource;
    private List<MovieItem> data;

    public ListItemModel(@NonNull Context context, int resource, List data) {
        super(context, resource, data);
        this.resource = resource;
        this.data = data;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

      View   listItem=convertView;
      if(listItem==null){
          listItem= LayoutInflater.from(getContext()).inflate(resource,parent,false);
      }
        ImageView   imageViewMov=listItem.findViewById(R.id.imageMovie);
        TextView    textViewTitleMov=listItem.findViewById(R.id.titleMovie);
        TextView    textViewdateMov=listItem.findViewById(R.id.dateMovie);
        String      titleMovie=data.get(position).getTitle();
        String      dateMovie=data.get(position).getDate();
        String      pathImageMovie=data.get(position).getPathImage();
        Log.i("path", "getView: "+pathImageMovie);
//        imageView.setImageResource(images.get(image));
//        https://image.tmdb.org/t/p/original/5hNcsnMkwU2LknLoru73c76el3z.jpg

        Picasso.get().load("https://image.tmdb.org/t/p/original"+pathImageMovie).resize(400,270).into(imageViewMov);

        textViewTitleMov.setText(titleMovie);
        textViewdateMov.setText(dateMovie);
      return listItem;
    }
}