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

import hoangviet.ndhv.demoui.model.Frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrameFragment extends Fragment implements FrameAdapter.onClickItemFrameListener {
    private RecyclerView recyclerViewFrame;
    private List<Frame> frameList;
    private FrameAdapter adapter;
    private setTypeFrameListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frame, container, false);
        recyclerViewFrame = view.findViewById(R.id.recyclerViewFrame);
        frameList = new ArrayList<>();
        addFrame();
        adapter = new FrameAdapter(this, getActivity(), frameList);
        recyclerViewFrame.setAdapter(adapter);
        recyclerViewFrame.setLayoutManager(new GridLayoutManager(getActivity(), 6, LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void addFrame() {
        frameList.add(new Frame(R.drawable.disabled, false));
        frameList.add(new Frame(R.drawable.frame_image, false));
        frameList.add(new Frame(R.drawable.flow_frame, false));
        frameList.add(new Frame(R.drawable.frame_1, false));
        frameList.add(new Frame(R.drawable.frame_2, false));
        frameList.add(new Frame(R.drawable.frame_4, false));
        frameList.add(new Frame(R.drawable.frame_7, false));
    }

    @Override
    public void onClickItemFrame(int position) {
        for (int i = 0; i < frameList.size(); i++) {
            if (i == position) {
                frameList.get(i).setChooseFrame(true);
            } else {
                frameList.get(i).setChooseFrame(false);
            }
        }
        mListener.setTypeFrame("F" + (position + 1));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        mListener = (setTypeFrameListener) getActivity();
        super.onAttach(context);
    }

    interface setTypeFrameListener {
        void setTypeFrame(String frame);
    }
}
