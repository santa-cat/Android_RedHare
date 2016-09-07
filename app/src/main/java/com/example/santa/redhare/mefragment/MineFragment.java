package com.example.santa.redhare.mefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/2.
 */
public class MineFragment extends Fragment {
    private View self;
    private static MineFragment instance;

    public static MineFragment getInstance() {
        if (instance == null) {
            synchronized (MineFragment.class) {
                if (instance == null)
                    instance = new MineFragment();
            }
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null == this.self) {
            this.self = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, container, false);
        }

        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }

        return this.self;
    }
}
