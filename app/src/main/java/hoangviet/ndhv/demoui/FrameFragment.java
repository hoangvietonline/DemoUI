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
public class FrameFragment extends Fragment implements FrameAdapter.onClickItemFrameListener{
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
        adapter = new FrameAdapter(this,getActivity(),frameList);
        recyclerViewFrame.setAdapter(adapter);
        recyclerViewFrame.setLayoutManager(new GridLayoutManager(getActivity(),6, LinearLayoutManager.VERTICAL,false));
        return view;
    }

    private void addFrame() {
        frameList.add(new Frame(R.drawable.disabled,false));
        frameList.add(new Frame(R.drawable.frame_image,false));
        frameList.add(new Frame(R.drawable.flow_frame,false));
        frameList.add(new Frame(R.drawable.frame_khung,false));
        frameList.add(new Frame(R.drawable.frame4,false));
        frameList.add(new Frame(R.drawable.icon,false));
    }

    @Override
    public void onClickItemFrame(int position) {
        for (int i = 0; i<frameList.size();i++){
            if (i == position){
                frameList.get(i).setChooseFrame(true);
            }else {
                frameList.get(i).setChooseFrame(false);
            }
        }
        if (position == 0){
            mListener.setTypeF1("F1");
        }
        if (position == 1){
            mListener.setTypeF2("F2");
        }
        if (position == 2){
            mListener.setTypeF3("F3");
        }
        if (position == 3){
            mListener.setTypeF4("F4");
        }
        if (position == 4){
            mListener.setTypeF5("F5");
        }
        if (position == 5){
            mListener.setTypeF6("F6");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        mListener = (setTypeFrameListener) getActivity();
        super.onAttach(context);
    }

    interface setTypeFrameListener{
        void setTypeF1(String F1);
        void setTypeF2(String F2);
        void setTypeF3(String F3);
        void setTypeF4(String F4);
        void setTypeF5(String F5);
        void setTypeF6(String F6);
    }
}
