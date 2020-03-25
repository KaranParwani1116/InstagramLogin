package com.example.karan.instagramlogin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karan.instagramlogin.R;
import com.example.karan.instagramlogin.models.Media;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Viewholder> {

    private List<Media.Datum> data;
    private Context context;

    public PostsAdapter(List<Media.Datum> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setData(List<Media.Datum>list) {
        data = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Picasso.get().load(data.get(position).getMediaUrl()).into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static
    public class Viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_image)
        PhotoView photoView;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            photoView = itemView.findViewById(R.id.post_image);
        }
    }
}
