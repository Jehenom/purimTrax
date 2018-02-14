package com.trax.purim.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.trax.purim.R;
import com.trax.purim.ThankYouActivity;
import com.trax.purim.VoteActivity;
import com.trax.purim.model.VoteOption;

public class VoteOptionFragment extends Fragment {
    private static final String OPTION_JSON = "option_json";
    private VoteOption option;


    public VoteOptionFragment() {
        // Required empty public constructor
    }

    public static VoteOptionFragment newInstance(VoteOption option) {
        VoteOptionFragment fragment = new VoteOptionFragment();
        Bundle args = new Bundle();
        args.putString(OPTION_JSON, new Gson().toJson(option));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String optionJson = getArguments().getString(OPTION_JSON);
            option = new Gson().fromJson(optionJson, VoteOption.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View res =  inflater.inflate(R.layout.fragment_vote_option, container, false);
        TextView tv = res.findViewById(R.id.textView);
        tv.setText(option.getText());


        SimpleDraweeView sdv = res.findViewById(R.id.vote_image);
        Uri uri = Uri.parse(option.getImageURL());
        sdv.setImageURI(uri);

        ((VoteActivity)getActivity()).animOff();


        Button voteBtn = res.findViewById(R.id.choose_picture_button);
        voteBtn.setOnClickListener(v->{
            ((VoteActivity)getActivity()).vote(option.getId());
//            Toast.makeText(getActivity(), "Thanks for voting!!!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), ThankYouActivity.class));
        });

        return res;
    }


}
