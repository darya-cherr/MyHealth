package com.example.myhealth;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseReference myRef;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private TextView welcomeTxt;
    ImageView zodiacImg;
    String zodiac;
    TextView horoscope, sign;

    LinearLayout relax, calm, focus;


    private String mParam1;
    private String mParam2;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeTxt = view.findViewById(R.id.welcome_txt);
        zodiacImg = view.findViewById(R.id.zodiac_img);
        horoscope = view.findViewById(R.id.horoscope);
        sign = view.findViewById(R.id.sign);

        relax = view.findViewById(R.id.relax);
        focus = view.findViewById(R.id.focus);
        calm = view.findViewById(R.id.calm);

        relax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relax.getLayoutParams().width = dpToPx(180, getContext());
                calm.getLayoutParams().width = dpToPx(60, getContext());
                focus.getLayoutParams().width = dpToPx(60, getContext());
                calm.requestLayout();
                relax.requestLayout();
                focus.requestLayout();
            }
        });

        calm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calm.getLayoutParams().width = dpToPx(160, getContext());
                relax.getLayoutParams().width = dpToPx(60, getContext());
                focus.getLayoutParams().width = dpToPx(60, getContext());
                calm.requestLayout();
                relax.requestLayout();
                focus.requestLayout();
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focus.getLayoutParams().width = dpToPx(160, getContext());
                calm.getLayoutParams().width = dpToPx(60, getContext());
                relax.getLayoutParams().width = dpToPx(60, getContext());
                calm.requestLayout();
                relax.requestLayout();
                focus.requestLayout();
            }
        });

        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                welcomeTxt.setText("С возвращением, " +  snapshot.child("name").getValue().toString() + "!");
                zodiac =  snapshot.child("zodiac").getValue().toString();
                sign.setText(zodiac);

                switch (zodiac){
                    case "Водолей":
                        setHoroscope(R.drawable.vodoley, "https://ohmanda.com/api/horoscope/aquarius");
                        return;
                    case "Овен":
                        setHoroscope(R.drawable.oven, "https://ohmanda.com/api/horoscope/aries");
                        return;
                    case "Козерог":
                        setHoroscope(R.drawable.kozerog, "https://ohmanda.com/api/horoscope/capricorn");
                        return;
                    case "Рыбы":
                        setHoroscope(R.drawable.ryby, "https://ohmanda.com/api/horoscope/pisces");
                        return;
                    case "Телец":
                        setHoroscope(R.drawable.telets, "https://ohmanda.com/api/horoscope/taurus");
                        return;
                    case "Близнецы":
                        setHoroscope(R.drawable.bliznetsy, "https://ohmanda.com/api/horoscope/gemini");
                        return;
                    case "Весы":
                        setHoroscope(R.drawable.vesy, "https://ohmanda.com/api/horoscope/libra");
                        return;
                    case "Дева":
                        setHoroscope(R.drawable.deva, "https://ohmanda.com/api/horoscope/virgo");
                        return;
                    case "Стрелец":
                        setHoroscope(R.drawable.strelets, "https://ohmanda.com/api/horoscope/sagittarius");
                        return;
                    case "Скорпион":
                        setHoroscope(R.drawable.scorpion, "https://ohmanda.com/api/horoscope/scorpio");
                        return;
                    case "Рак":
                        setHoroscope(R.drawable.rak, "https://ohmanda.com/api/horoscope/cancer");
                        return;
                    case "Лев":
                        setHoroscope(R.drawable.lev, "https://ohmanda.com/api/horoscope/leo");
                        return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void setHoroscope(int img, String url) {
        zodiacImg.setImageResource(img);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject data = new JSONObject(response);
                    horoscope.setText(data.get("horoscope").toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(mStringRequest);
    }


}