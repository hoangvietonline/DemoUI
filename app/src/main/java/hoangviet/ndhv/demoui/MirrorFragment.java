package hoangviet.ndhv.demoui;


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
public class MirrorFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Mirror> mirrorList;
    private MirrorAdapter adapter;
    public MirrorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mirror, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMirror);
        mirrorList = new ArrayList<>();
        addMirror();
        adapter = new MirrorAdapter(getActivity(),mirrorList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),6, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void addMirror() {
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M1"));
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M2"));
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M3"));
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M4"));
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M5"));
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M6"));
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M7"));
        mirrorList.add(new Mirror(R.drawable.icon_girl,"M8"));

    }

}
