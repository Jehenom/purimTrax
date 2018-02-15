package com.trax.purim;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.trax.purim.fragment.VoteOptionFragment;
import com.trax.purim.viewmodel.VoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity {

    private VoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        viewModel = ViewModelProviders.of(this).get(VoteViewModel.class);
        viewModel.getOptions().observe(this, voteOptions -> {
            ViewPager viewPager = findViewById(R.id.vp_slider);
            viewPager.setAdapter(new VotePageAdapter(getSupportFragmentManager(), getFragments()));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                // This method will be invoked when a new page becomes selected.
                @Override
                public void onPageSelected(int position) {
                    viewPager.setCurrentItem(position);
                }

                // This method will be invoked when the current page is scrolled
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // Code goes here
                }

                 @Override
                public void onPageScrollStateChanged(int state) {
                    // Code goes here
                }
            });
        });
        getSupportActionBar().hide();
    }

    private List<Fragment> getFragments() {

        ArrayList<Fragment> res = new ArrayList<>();
        for (int i =0; i< viewModel.getNumberOfPictures(); i++) {
            res.add(VoteOptionFragment.newInstance(viewModel.getOption(i)));
        }
        return res;
    }

    public void animOff(){
        findViewById(R.id.loader).setVisibility(View.GONE);
   }

    public void vote(int id){ viewModel.vote(id);}

    public int getVotedId() {return viewModel.getVoted();}

    private class VotePageAdapter extends FragmentPagerAdapter{

        private final List<Fragment> fragments;

        public VotePageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
