package com.example.gaby_.weatherapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gaby_.weatherapplication.adapter.MyWeatherRecyclerViewAdapter;
import com.example.gaby_.weatherapplication.R;
import com.example.gaby_.weatherapplication.api.WeatherListResponse;
import com.example.gaby_.weatherapplication.api.WeatherResponse;
import com.example.gaby_.weatherapplication.api.WeatherService;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A fragment representing a list of Weather.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WeatherFragment extends Fragment {
    private Subscription mSubscription;
    private OnListFragmentInteractionListener mListener;
    private MyWeatherRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mView;
    private View mDetailView;
    private boolean isTablet;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_weather_list, container, false);

        Context context = mView.getContext();
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.list_recycler);
        mDetailView = mView.findViewById(R.id.weather_detail_container);
        isTablet = mDetailView!=null;
       
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyWeatherRecyclerViewAdapter(getContext(), mListener, isTablet);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchWeather();
                    }
                }
        );
        if(savedInstanceState!=null){
            mAdapter.onRestoreInstanceState(savedInstanceState);
        }
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchWeather();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mAdapter!=null) {
            mAdapter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void fetchWeather(){
        double latitude = 9.935697;
        double longitude = -84.1483648;
        String units = getResources().getString(R.string.units);
        Observable<WeatherListResponse> responseObservable = WeatherService.fetchWeatherByCoordinates(units, latitude, longitude);
        if(responseObservable!=null){
            if(mSubscription!=null && !mSubscription.isUnsubscribed()){
                mSubscription.unsubscribe();
            }
            mSubscription = responseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WeatherListResponse>() {
                        @Override
                        public void onCompleted() {
                            mSwipeRefreshLayout.setRefreshing(false);
                            if(!isUnsubscribed()){
                                unsubscribe();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            mSwipeRefreshLayout.setRefreshing(false);
                            if(isNetworkAvailable()){
                                Snackbar.make(mView, R.string.error, Snackbar.LENGTH_LONG).show();
                            }
                            else {
                                Snackbar
                                        .make(mView, R.string.no_network, Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.action_settings), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(Settings.ACTION_SETTINGS));
                                                //dismiss
                                            }
                                        })
                                        .show();
                            }
                        }

                        @Override
                        public void onNext(WeatherListResponse resultMovieResponse) {
                            if(resultMovieResponse!=null){
                                mAdapter.updateList(resultMovieResponse.getList());
                            }
                        }
                    });
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(WeatherResponse item, boolean isTablet);
    }
}
