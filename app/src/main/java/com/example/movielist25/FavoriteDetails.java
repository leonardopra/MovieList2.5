package com.example.movielist25;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.example.movielist25.DetailedActivity.mdb;

public class FavoriteDetails extends AppCompatActivity {

    private TextView movieName,movieDates,movieVotes,movieSummary;
    public int intGotPosition;
    public static String[] backdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_details);

        movieName= findViewById(R.id.movieTitleValue);
        movieDates= findViewById(R.id.movieReleaseDateValue);
        movieVotes= findViewById(R.id.movieVoteValue);
        movieSummary= findViewById(R.id.movieSummaryValue);
       // imageViewInDetailsPoster= findViewById(R.id.imageViewPosterDetails);
        final FloatingActionButton fab2 = findViewById(R.id.fab1);

        String gotPosition=getIntent().getStringExtra("position");
        intGotPosition= Integer.parseInt(gotPosition);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(MovieFavDB.fav_movies[intGotPosition]);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

       ImageView toolbarImage =  findViewById(R.id.images_id);

        String url = "https://image.tmdb.org/t/p/w1280"+ MovieFavDB.fav_backdrop[intGotPosition];
        picassoLoader(this, toolbarImage, url);

        movieName.setText(MovieFavDB.fav_movies[intGotPosition]);
        movieDates.setText(MovieFavDB.fav_dates[intGotPosition]);
        movieVotes.setText(MovieFavDB.fav_votes[intGotPosition]+" / 10");
        movieSummary.setText(MovieFavDB.fav_summary[intGotPosition]);





        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idIntValue= Integer.parseInt(MainActivity.id[intGotPosition]);
                if(mdb.isMovieFavorited(MainActivity.contentResolver, idIntValue)){
                    mdb.removeMovie(MainActivity.contentResolver, idIntValue);
                    fab2.setImageDrawable(ContextCompat.getDrawable(FavoriteDetails.this,R.drawable.ic_favorite_border_white_24px));
                    Snackbar.make(view, "Rimosso dai preferiti", Snackbar.LENGTH_SHORT).show();

                }else{
                    mdb.addMovie(MainActivity.contentResolver, intGotPosition);
                    fab2.setImageDrawable(ContextCompat.getDrawable(FavoriteDetails.this,R.drawable.ic_favorite_white_24px));
                    Snackbar.make(view, "Aggiunto ai preferiti", Snackbar.LENGTH_SHORT).show();
                }
                mdb.getFavoriteMovies(MainActivity.contentResolver);
            }
        });

    }



    public void picassoLoader(Context context, ImageView imageView, String url){
        Picasso.get()
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.sam)
                .error(R.drawable.sam)
                .into(imageView);
    }
}
