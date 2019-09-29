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
public class MirrorFragment extends Fragment implements MirrorAdapter.OnClickItemListener {
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
        adapter = new MirrorAdapter(this, getActivity(), mirrorList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void addMirror() {
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M1",false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M2",false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M3",false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M4",false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M5",false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M6",false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M7",false));
        mirrorList.add(new Mirror(R.drawable.icon_girl, "M8",false));

    }

    @Override
    public void onClickItem(int pos) {
        Mirror mirror = mirrorList.get(pos);
        for (int i = 0; i < mirrorList.size(); i++) {
            if (pos == i){
                mirror.setClick(true);
            }else {
                mirrorList.get(i).setClick(false);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
