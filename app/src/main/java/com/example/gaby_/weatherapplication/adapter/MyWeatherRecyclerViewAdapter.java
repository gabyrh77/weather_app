package com.example.gaby_.weatherapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gaby_.weatherapplication.R;
import com.example.gaby_.weatherapplication.fragments.WeatherFragment.OnListFragmentInteractionListener;
import com.example.gaby_.weatherapplication.api.WeatherResponse;
import com.example.gaby_.weatherapplication.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WeatherResponse} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyWeatherRecyclerViewAdapter extends RecyclerView.Adapter<MyWeatherRecyclerViewAdapter.ViewHolder> {

    private final List<WeatherResponse> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final ItemChoiceManager mICM;
    private Context mContext;
    private boolean mIsTablet;

    public MyWeatherRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener, boolean isTablet) {
        super();
        mValues = new ArrayList<>();
        mContext = context;
        mListener = listener;
        mIsTablet = isTablet;
        mICM = new ItemChoiceManager(this);
        mICM.setChoiceMode(isTablet? AbsListView.CHOICE_MODE_SINGLE:AbsListView.CHOICE_MODE_NONE);
    }

    public void updateList(List<WeatherResponse> items){
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCityNameView.setText(holder.mItem.getName());
        holder.mTemperatureView.setText(mContext.getString(R.string.temperature_format, holder.mItem.getMain().getTemp()));
        if(holder.mItem.getWeather()!=null && holder.mItem.getWeather().size()>0) {
            Glide.with(mContext).load(Utils.getImageUrl(mContext,
                    holder.mItem.getWeather().get(0).getIcon())).into(holder.mWeatherIconImage);
        }
        mICM.onBindViewHolder(holder, position);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mICM.onRestoreInstanceState(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        mICM.onSaveInstanceState(outState);
    }

    public int getSelectedItemPosition() {
        return mICM.getSelectedItemPosition();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final ImageView mWeatherIconImage;
        public final TextView mCityNameView;
        public final TextView mTemperatureView;
        public WeatherResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCityNameView = (TextView) view.findViewById(R.id.city_name_text);
            mTemperatureView = (TextView) view.findViewById(R.id.temp_text_view);
            mWeatherIconImage = (ImageView) view.findViewById(R.id.weather_icon_view);
            mView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCityNameView.getText() + ": " +mTemperatureView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            mICM.onClick(this);
            if (null != mListener) {
                mListener.onListFragmentInteraction(this.mItem, mIsTablet);
            }
        }
    }
}
