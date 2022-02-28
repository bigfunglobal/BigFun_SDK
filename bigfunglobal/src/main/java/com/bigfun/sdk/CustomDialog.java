package com.bigfun.sdk;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
        setCanceledOnTouchOutside(false);
        setContentView(view);

        EditText etPhone = view.findViewById(R.id.et_phone);
        EditText etEmail = view.findViewById(R.id.et_email);
        Button btnCertain = view.findViewById(R.id.btn_certain);

        btnCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(context, "please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() != 10) {
                    Toast.makeText(
                            context,
                            "Please fill in the correct phone number",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(context, "please input your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkEmail(email)) {
                    Toast.makeText(
                            context,
                            "Please fill in the correct email address",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                mListener.ok(email, phone);
                dismiss();
            }
        });
    }

    private IOnClickListener mListener;

    public interface IOnClickListener {
        void ok(String email, String phone);
    }

    public void setOnClickListener(IOnClickListener listener) {
        this.mListener = listener;
    }

    private boolean checkEmail(String email) {
        String regex =
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
