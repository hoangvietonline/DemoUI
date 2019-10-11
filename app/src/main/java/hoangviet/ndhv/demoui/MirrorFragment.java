package hoangviet.ndhv.demoui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hoangviet.ndhv.demoui.model.Mirror;


/**
 * A simple {@link Fragment} subclass.
 */
public class MirrorFragment extends Fragment implements MirrorAdapter.OnClickItemListener {
    private List<Mirror> mirrorList;
    private MirrorAdapter adapter;
    private OnClickTypeMirror listener;
    @Override
    public void onAttach(Context context) {
        listener = (OnClickTypeMirror) getActivity();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mirror, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMirror);
        mirrorList = new ArrayList<>();
        addMirror();
        Mirror mirror = mirrorList.get(0);
        mirror.setClick(true);
        adapter = new MirrorAdapter(this, getActivity(), mirrorList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void addMirror() {
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M1", false));
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M2", false));
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M3", false));
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M4", false));
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M5", false));
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M6", false));
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M7", false));
        mirrorList.add(new Mirror(R.drawable.image_backgoung, "M8", false));
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
        String mirrorType = "M" + (pos + 1);
        listener.onTypeMirror(mirrorType);
        adapter.notifyDataSetChanged();
    }

    public interface OnClickTypeMirror {
        void onTypeMirror(String mirror);
    }
}
