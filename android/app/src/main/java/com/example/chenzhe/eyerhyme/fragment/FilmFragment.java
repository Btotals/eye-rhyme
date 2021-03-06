package com.example.chenzhe.eyerhyme.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.activity.ChoosingMovieAndDateActivity;
import com.example.chenzhe.eyerhyme.activity.MainActivity;
import com.example.chenzhe.eyerhyme.activity.MovieDetailActivity;
import com.example.chenzhe.eyerhyme.activity.MovieListActivity;
import com.example.chenzhe.eyerhyme.activity.TheaterListActivity;
import com.example.chenzhe.eyerhyme.adapter.TheatersNearbyAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.MovieItem;
import com.example.chenzhe.eyerhyme.model.TheaterItem;
import com.example.chenzhe.eyerhyme.model.TheatersNearbyResponse;
import com.example.chenzhe.eyerhyme.model.getMoviesResponse;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFragment extends Fragment implements viewController, View.OnClickListener {


    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;
    @Bind(R.id.iv_post1)
    ImageView ivPost1;
    @Bind(R.id.tv_post1)
    TextView tvPost1;
    @Bind(R.id.iv_post2)
    ImageView ivPost2;
    @Bind(R.id.tv_post2)
    TextView tvPost2;
    @Bind(R.id.iv_post3)
    ImageView ivPost3;
    @Bind(R.id.tv_post3)
    TextView tvPost3;
    @Bind(R.id.iv_post4)
    ImageView ivPost4;
    @Bind(R.id.tv_post4)
    TextView tvPost4;
    @Bind(R.id.iv_post5)
    ImageView ivPost5;
    @Bind(R.id.tv_post5)
    TextView tvPost5;
    @Bind(R.id.iv_post6)
    ImageView ivPost6;
    @Bind(R.id.tv_post6)
    TextView tvPost6;
    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.tv_all_movies)
    TextView tvAllMovies;
    @Bind(R.id.rl_theater)
    RelativeLayout rlTheater;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private String getTheatersNearby = "/theater/get_theaters_nearby";
    private String getMovies = "/movie/get_movies";
    private TheatersNearbyResponse theatersNearbyResponse;
    private getMoviesResponse moviesResponse;
    private ArrayList<TextView> filmNames;
    private ArrayList<ImageView> filmImages;
    private TheatersNearbyAdapter theatersNearbyAdapter;

    public FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        ButterKnife.bind(this, view);
        slideLeftIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_left_out);
        slideRightIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_right_out);
        init();
        return view;
    }

    private void init() {
        initViewFlipper();
        initMoviesAndTheaters();
        initListener();

    }

    private void initViewFlipper() {
        viewFlipper.setInAnimation(slideLeftIn);
        viewFlipper.setOutAnimation(slideLeftOut);
        viewFlipper.startFlipping();
    }

    private void initListener() {
        rlTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TheaterListActivity.class);
                startActivity(it);
            }
        });

        tvAllMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), MovieListActivity.class);
                startActivity(it);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TheaterItem item = theatersNearbyResponse.theaters.get(position);
                Intent it = new Intent(getActivity(), ChoosingMovieAndDateActivity.class);
                it.putExtra("theater", item);
                startActivity(it);
            }
        });
    }

    private void initMoviesAndTheaters() {
        filmNames = new ArrayList<>();
        filmImages = new ArrayList<>();

        filmImages.add(ivPost1);
        filmImages.add(ivPost2);
        filmImages.add(ivPost3);
        filmImages.add(ivPost4);
        filmImages.add(ivPost5);
        filmImages.add(ivPost6);

        filmNames.add(tvPost1);
        filmNames.add(tvPost2);
        filmNames.add(tvPost3);
        filmNames.add(tvPost4);
        filmNames.add(tvPost5);
        filmNames.add(tvPost6);

        for (int i = 0; i < filmImages.size(); i++) {
            filmImages.get(i).setOnClickListener(this);
        }

        getMovies();

        getTheaters();

    }

    private void getMovies() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PostUtil.newInstance().sendPost(FilmFragment.this, getMovies, map);
    }

    public void getTheaters() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("longitude", MainActivity.longitude);
        map.put("latitude", MainActivity.latitude);
        PostUtil.newInstance().sendPost(FilmFragment.this, getTheatersNearby, map);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(getActivity(), "network fail");
            return;
        }
        if (url.equals(getMovies)) {
            moviesResponse = new Gson().fromJson(response, getMoviesResponse.class);
            if (!moviesResponse.status) {
                ToastUtil.printToast(getActivity(), "获取电影失败");
                return;
            }
            ArrayList<MovieItem> movieItems = moviesResponse.movies;
            for (int i = 0; i < movieItems.size() && i < filmNames.size(); i++) {
                filmNames.get(i).setText(movieItems.get(i).getName());
                PostUtil.newInstance().imageGET(filmImages.get(i), "movie", movieItems.get(i).getMovie_id());
            }

        } else if (url.equals(getTheatersNearby)) {
            theatersNearbyResponse = new Gson().fromJson(response, TheatersNearbyResponse.class);
            if (!theatersNearbyResponse.status) {
                ToastUtil.printToast(getActivity(), "获取影院失败");
                return;
            }
            ArrayList<TheaterItem> theaterItems = theatersNearbyResponse.theaters;

            theatersNearbyAdapter = new TheatersNearbyAdapter(theaterItems, getContext());
            if (listview != null)
                listview.setAdapter(theatersNearbyAdapter);
        }
    }


    @Override
    public void onClick(View v) {
        ImageView temp = (ImageView)v;
        for (int i = 0; i < filmImages.size(); i++) {
            if (temp.equals(filmImages.get(i))) {
                Intent it = new Intent(getActivity(), MovieDetailActivity.class);
                it.putExtra("movie_id", moviesResponse.movies.get(i).getMovie_id());
                it.putExtra("name", filmNames.get(i).getText().toString());
                startActivity(it);
            }
        }
    }
}
