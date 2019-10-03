package hoangviet.ndhv.demoui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerMirrorAdapter extends FragmentStatePagerAdapter {
    public ViewPagerMirrorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MirrorFragment();
            case 1:
                return new FrameFragment();
            case 2:
                return new FilterFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
