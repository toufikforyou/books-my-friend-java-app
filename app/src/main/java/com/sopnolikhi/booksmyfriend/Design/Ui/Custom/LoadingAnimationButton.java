package com.sopnolikhi.booksmyfriend.Design.Ui.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.sopnolikhi.booksmyfriend.R;

public class LoadingAnimationButton extends RelativeLayout {
    private Button button;
    private LottieAnimationView buttonAnimationView;
    private RelativeLayout commonButtonLayout;
    private OnClickListener onClickListener;

    public LoadingAnimationButton(Context context) {
        super(context);
        initView(context);
    }

    public LoadingAnimationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingAnimationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.design_loading_button, this, true);

        button = view.findViewById(R.id.button);
        buttonAnimationView = view.findViewById(R.id.buttonAnimationView);

        button.setOnClickListener(v -> {
            if (button.isEnabled()) {
                setLoading(true);
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });
    }

    public void setText(String text) {
        button.setText(text);
    }

    public void setTextColor(int color) {
        button.setTextColor(color);
    }

    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
    }

    public void setLoading(boolean loading) {
        if (loading) {
            button.setVisibility(View.GONE);
            buttonAnimationView.setVisibility(View.VISIBLE);
            buttonAnimationView.playAnimation();
        } else {
            buttonAnimationView.setVisibility(View.GONE);
            buttonAnimationView.pauseAnimation();
            button.setVisibility(View.VISIBLE);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setBackground(int background) {
        commonButtonLayout = findViewById(R.id.commonButton);
        commonButtonLayout.setBackgroundResource(background);
        buttonAnimationView.setBackgroundResource(background);

    }
}
