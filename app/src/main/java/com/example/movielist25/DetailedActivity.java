package com.example.movielist25;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DetailedActivity extends AppCompatActivity {

    private TextView movieName,movieDates,movieVotes,movieSummary,movieReview;
    private ImageView imageViewInDetailsPoster;
    public int intGotPosition;
    public static String[] authors=new String[100];
    public static String[] content=new String[100];
    public static MovieFavDB mdb = new MovieFavDB();
    public static int scrollX = 0;
    public static int scrollY = -1;
    public static NestedScrollView nestedScrollView;
    public static AppBarLayout appBarLayout;

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{ nestedScrollView.getScrollX(), nestedScrollView.getScrollY()});
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(scrollX==0 && scrollY==0){
            appBarLayout.setExpanded(true);
        }else {
            appBarLayout.setExpanded(false);
        }
        final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
        if(position != null)
            nestedScrollView.post(new Runnable() {
                public void run() {
                    nestedScrollView.scrollTo(position[0], position[1]);
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        movieName= findViewById(R.id.movieTitleValue);
        movieDates= findViewById(R.id.movieReleaseDateValue);
        movieVotes= findViewById(R.id.movieVoteValue);
        movieSummary= findViewById(R.id.movieSummaryValue);

        final FloatingActionButton fab2 = findViewById(R.id.fab1);

        String gotPosition=getIntent().getStringExtra("position");
        intGotPosition= Integer.parseInt(gotPosition);


        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(MainActivity.movies[intGotPosition]);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }



        int idIntValue= Integer.parseInt(MainActivity.id[intGotPosition]);
        if(mdb.isMovieFavorited(MainActivity.contentResolver, idIntValue)){
            fab2.setImageDrawable(ContextCompat.getDrawable(DetailedActivity.this,R.drawable.ic_favorite_white_24px));
        }else{
            fab2.setImageDrawable(ContextCompat.getDrawable(DetailedActivity.this,R.drawable.ic_favorite_border_white_24px));
        }


        ImageView toolbarImage =  findViewById(R.id.image_id);

        String url = "https://image.tmdb.org/t/p/w1280"+MainActivity.backdrop[intGotPosition];
        picassoLoader(this, toolbarImage, url);

        movieName.setText(MainActivity.movies[intGotPosition]);
        movieDates.setText(MainActivity.dates[intGotPosition]);
        movieVotes.setText(MainActivity.votes[intGotPosition]+" / 10");
        movieSummary.setText("\t\t"+MainActivity.summary[intGotPosition]);





        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idIntValue= Integer.parseInt(MainActivity.id[intGotPosition]);
                if(mdb.isMovieFavorited(MainActivity.contentResolver, idIntValue)){
                    mdb.removeMovie(MainActivity.contentResolver, idIntValue);
                    fab2.setImageDrawable(ContextCompat.getDrawable(DetailedActivity.this,R.drawable.ic_favorite_border_white_24px));
                    Snackbar.make(view, "Rimosso dai preferiti", Snackbar.LENGTH_SHORT).show();
                }else{
                    mdb.addMovie(MainActivity.contentResolver, intGotPosition);
                    fab2.setImageDrawable(ContextCompat.getDrawable(DetailedActivity.this,R.drawable.ic_favorite_white_24px));
                    Snackbar.make(view, "Aggiunto ai preferiti", Snackbar.LENGTH_SHORT).show();
                }
                mdb.getFavoriteMovies(MainActivity.contentResolver);
            }
        });


    }

    public void picassoLoader(Context context, ImageView imageView, String url){
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.sam)
                .error(R.drawable.sam)
                .into(imageView);
    }



    @Override
    protected void onPause()
    {
        super.onPause();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }



}
