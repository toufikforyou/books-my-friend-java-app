package com.sopnolikhi.booksmyfriend.Design.Ui.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.sopnolikhi.booksmyfriend.R;

public class AnimationButton extends LinearLayout {
    private LottieAnimationView loadingAnimationView;
    private Button customButton;
    private OnClickListener onClickListener;

    public AnimationButton(Context context) {
        this(context, null);
        initView(context);
    }

    public AnimationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AnimationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loading_button, this, true);

        loadingAnimationView = view.findViewById(R.id.loadingAnimationView);
        customButton = view.findViewById(R.id.customButton);

        customButton.setOnClickListener(v -> {
            if (customButton.isEnabled()) {
                setLoading(true);
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });
    }

    public void setLoading(boolean loading) {
        if (loading) {
            customButton.setEnabled(false);
            loadingAnimationView.setVisibility(View.VISIBLE);
            loadingAnimationView.playAnimation();
        } else {
            customButton.setEnabled(true);
            loadingAnimationView.setVisibility(View.GONE);
            loadingAnimationView.pauseAnimation();
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setEnabled(boolean enabled) {
        customButton.setEnabled(enabled);
    }

    public CharSequence getText() {
        return customButton.getText();
    }

    public void setText(String text) {
        customButton.setText(text);
    }
}