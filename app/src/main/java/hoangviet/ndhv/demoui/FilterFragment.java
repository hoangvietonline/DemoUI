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

import hoangviet.ndhv.demoui.model.FilterData;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment implements FilterAdapter.onCLickItemFilterLitener {

    public static final String[] EFFECT_CONFIGS = {
            "",
            "@adjust lut original.png",
            "@adjust lut natural01.png",
            "@adjust lut natural02.png",
            "@adjust lut pure01.png",
            "@adjust lut pure02.png",
            "@adjust lut lovely01.png",
            "@adjust lut lovely02.png",
            "@adjust lut lovely03.png",
            "@adjust lut lovely04.png",
            "@adjust lut warm01.png",
            "@adjust lut warm02.png",
            "@adjust lut cool01.png",
            "@adjust lut cool02.png",
            "@adjust lut vintage.png",
            "@adjust lut gray.png",
    };
    private List<FilterData> filterDataList;
    private RecyclerView recyclerView;
    private FilterAdapter adapter;
    private OnTypeFilterListener onTypeFilterListener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFilter);
        filterDataList = new ArrayList<>();
        addFilterList();
        adapter = new FilterAdapter(this, getActivity(), filterDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6, LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void addFilterList() {
        filterDataList.add(new FilterData("None",EFFECT_CONFIGS[0], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("Original", EFFECT_CONFIGS[1], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("natural1", EFFECT_CONFIGS[2], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("natural2", EFFECT_CONFIGS[3], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("pure01", EFFECT_CONFIGS[4], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("pure02", EFFECT_CONFIGS[5], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("lovely01", EFFECT_CONFIGS[6], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("lovely02", EFFECT_CONFIGS[7], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("lovely03", EFFECT_CONFIGS[8], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("lovely04", EFFECT_CONFIGS[9], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("warm01", EFFECT_CONFIGS[10], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("warm02", EFFECT_CONFIGS[11], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("cool01", EFFECT_CONFIGS[12], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("cool02", EFFECT_CONFIGS[13], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("vintage", EFFECT_CONFIGS[14], R.drawable.image_backgoung, false));
        filterDataList.add(new FilterData("gray", EFFECT_CONFIGS[15], R.drawable.image_backgoung, false));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onTypeFilterListener = (OnTypeFilterListener) getActivity();
    }

    @Override
    public void clickItem(int position) {
        for (int i = 0; i < filterDataList.size(); i++) {
            if (i == position)
                filterDataList.get(i).setChooseFilter(true);
            else
                filterDataList.get(i).setChooseFilter(false);
        }
        adapter.notifyDataSetChanged();
        onTypeFilterListener.onTypeFilter(position);
    }

    interface OnTypeFilterListener {
        void onTypeFilter(int position);
    }
}