package com.example.jjapstagram_java.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.jjapstagram_java.R;


//커스텀 해보자
// underline, textCursor
public class ChangedColorEditText extends ConstraintLayout {

    AppCompatEditText changedColorEdtText;
    View underlineView;

    public ChangedColorEditText(Context context) {
        super(context);
        init();
    }

    public ChangedColorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        getAttrs(attrs);
    }

    public ChangedColorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttrs(attrs, defStyleAttr);
    }


    private void init() {
        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.edittext_changedcolor, this, false);

        addView(view);

        changedColorEdtText = view.findViewById(R.id.changedColorEdtText);
        underlineView = view.findViewById(R.id.underlineView);

        changedColorEdtText.setOnFocusChangeListener((editText, hasFocus) -> {
            if (hasFocus) {
                underlineView.setBackgroundResource(R.color.colorAccent);
            } else {
                underlineView.setBackgroundResource(R.color.lgtGray);
            }
        });
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ChangedColorEditText);
        setTypeArray(typedArray);
    }
    private void getAttrs(AttributeSet attrs , int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ChangedColorEditText, defStyleAttr, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typeArray) {
        String hintResID = typeArray.getString(R.styleable.ChangedColorEditText_hint);
        changedColorEdtText.setHint(hintResID);

        boolean focusableInTouchMode = typeArray.getBoolean(R.styleable.ChangedColorEditText_focusableInTouchMode, true);
        changedColorEdtText.setFocusableInTouchMode(focusableInTouchMode);
        typeArray.recycle();
    }

    public void setText(String text) {
        changedColorEdtText.setText(text);
    }

    public void setText(int resId) {
        changedColorEdtText.setText(resId);
    }

    public void setHint(String hint) {
        changedColorEdtText.setHint(hint);
    }

    public void setHint(int resID) {
        changedColorEdtText.setHint(resID);
    }


    public String getText() {
        return changedColorEdtText.getText() == null ? "none" :changedColorEdtText.getText().toString();
    }
}
