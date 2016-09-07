package com.example.santa.redhare.mainactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.santa.redhare.R;
import com.example.santa.redhare.connfragment.ConnentionFragment;
import com.example.santa.redhare.mefragment.MineFragment;
import com.example.santa.redhare.messagefragment.MessageFragment;
import com.example.santa.redhare.redharefragment.RedFragment;
import com.example.santa.redhare.searchfragment.SearchFragment;
import com.example.santa.redhare.utils.MenuLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();
    }



    private void initViewPager() {
        ViewPager viewPage = (ViewPager) this.findViewById(R.id.main_viewpager);
        List<Fragment> listFragment = new ArrayList<>();
        Fragment red = RedFragment.getInstance();
        Fragment connection = ConnentionFragment.getInstance();
        Fragment message = MessageFragment.getInstance();
        Fragment search = SearchFragment.getInstance();
        Fragment mine = MineFragment.getInstance();

        listFragment.add(red);
        listFragment.add(connection);
        listFragment.add(message);
        listFragment.add(search);
        listFragment.add(mine);

        viewPage.setAdapter(new RedFragmentAdapter(this.getSupportFragmentManager(), listFragment));


        MenuLayout menuLayout = (MenuLayout) findViewById(R.id.main_menulayout);
        menuLayout.setViewPager(viewPage);
    }


    public class RedFragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList;

        public RedFragmentAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            mFragmentList= list;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = this.mFragmentList.get(position);
            if (fragment != null) {
                return this.mFragmentList.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
