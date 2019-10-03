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

import hoangviet.ndhv.demoui.model.FilterData;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {
    List<FilterData> filterDataList;
    RecyclerView recyclerView;
    FilterAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewFilter);
        filterDataList = new ArrayList<>();
        addFilterList();
        adapter = new FilterAdapter(getActivity(),filterDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),6, LinearLayoutManager.VERTICAL,false));

        return view;
    }

    public static final String[] EFFECT_CONFIGS = {
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
    private void addFilterList() {

        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[1],R.drawable.icon_girl));
        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[2],R.drawable.icon_girl));
        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[3],R.drawable.icon_girl));
        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[4],R.drawable.icon_girl));
        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[5],R.drawable.icon_girl));
        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[6],R.drawable.icon_girl));
        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[7],R.drawable.icon_girl));
        filterDataList.add(new FilterData("original",EFFECT_CONFIGS[8],R.drawable.icon_girl));
    }

}
