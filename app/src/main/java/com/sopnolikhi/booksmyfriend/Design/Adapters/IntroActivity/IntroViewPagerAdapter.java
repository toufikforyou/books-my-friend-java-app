package com.sopnolikhi.booksmyfriend.Design.Adapters.IntroActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Models.ViewPager.IntroScreenModel;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context context;
    List<IntroScreenModel> introScreenModelList;

    public IntroViewPagerAdapter(Context context, List<IntroScreenModel> introScreenModelList) {
        this.context = context;
        this.introScreenModelList = introScreenModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layoutScreen = layoutInflater.inflate(R.layout.intro_sample_layout, null);

        ImageView sliderImage = layoutScreen.findViewById(R.id.intro_image);
        TextView title = layoutScreen.findViewById(R.id.intro_title_text);
        TextView description = layoutScreen.findViewById(R.id.intro_description);

        sliderImage.setImageResource(introScreenModelList.get(position).getScreenImage());
        title.setText(introScreenModelList.get(position).getTitle());
        description.setText(introScreenModelList.get(position).getDescription());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return introScreenModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
