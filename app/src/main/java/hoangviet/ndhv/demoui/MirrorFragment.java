package hoangviet.ndhv.demoui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MirrorFragment extends Fragment implements MirrorAdapter.OnClickItemListener {
    private RecyclerView recyclerView;
    private List<Mirror> mirrorList;
    private MirrorAdapter adapter;
    private OnClickTypeMirror listener;


    public static MirrorFragment newInstance() {

        Bundle args = new Bundle();

        MirrorFragment fragment = new MirrorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        listener = (OnClickTypeMirror) getActivity();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mirror, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMirror);
        mirrorList = new ArrayList<>();
        addMirror();
        Mirror mirror = mirrorList.get(0);
        mirror.setClick(true);
        adapter = new MirrorAdapter(this, getActivity(), mirrorList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void addMirror() {
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M1", false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M2", false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M3", false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M4", false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M5", false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M6", false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M7", false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M8", false));
    }

    @Override
    public void onClickItem(int pos) {
        Mirror mirror = mirrorList.get(pos);
        for (int i = 0; i < mirrorList.size(); i++) {
            if (pos == i) {
                mirror.setClick(true);
            } else {
                mirrorList.get(i).setClick(false);
            }
        }
        if (pos == 0) {
            String M1 = "M1";
            listener.onTypeM1(M1);
        }
        if (pos == 1) {
            String M2 = "M2";
            listener.onTypeM2(M2);
        }
        if (pos == 2) {
            String M3 = "M3";
            listener.onTypeM3(M3);
        }
        if (pos == 3) {
            String M4 = "M4";
            listener.onTypeM4(M4);
        }
        if (pos == 4) {
            String M5 = "M5";
            listener.onTypeM5(M5);
        }
        if (pos == 5) {
            String M6 = "M6";
            listener.onTypeM6(M6);
        }
        if (pos == 6) {
            String M7 = "M7";
            listener.onTypeM7(M7);
        }
        if (pos == 7) {
            String M8 = "M8";
            listener.onTypeM8(M8);
        }

        adapter.notifyDataSetChanged();
    }

    public interface OnClickTypeMirror {
        void onTypeM1(String M1);

        void onTypeM2(String M2);

        void onTypeM3(String M3);

        void onTypeM4(String M4);

        void onTypeM5(String M5);

        void onTypeM6(String M6);

        void onTypeM7(String M7);

        void onTypeM8(String M8);

    }
}
