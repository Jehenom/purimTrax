package com.trax.purim;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trax.purim.module.GlideApp;
import com.trax.purim.viewmodel.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GalleryActivity extends AppCompatActivity {

    private MutableLiveData<List<String>> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().hide();


        GalleryViewModel viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        images = viewModel.getImageURLs();
        images.observe(this, strings -> {
            RecyclerView rcv = findViewById(R.id.gallery_recycler_view);
            rcv.setHasFixedSize(true); // Helps improve performance
            rcv.setLayoutManager(new GridLayoutManager(this, 3));
            rcv.setAdapter(new GalleryAdapter(strings));
        });
    }


    private class GalleryAdapter extends Adapter<GalleryAdapter.ViewHolder>{

        private List<String> listOfImages = new ArrayList<>();

        public GalleryAdapter(List<String> listOfImages){
            this.listOfImages = listOfImages;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_image, parent, false);
            return new ViewHolder(view);        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GlideApp.with(GalleryActivity.this)
                    .load(listOfImages.get(position))
                    .transition(withCrossFade())
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(R.drawable.no_image)
                    .into(holder.image);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openImageViewIntent = new Intent(GalleryActivity.this, ImageViewActivity.class);
                    openImageViewIntent.putExtra("image_url", listOfImages.get(position));
                    startActivity(openImageViewIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listOfImages.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private ImageView image;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView)itemView;
            }
        }

    }
}
