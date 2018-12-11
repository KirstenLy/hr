package hr.meteor.ru.meteorjob.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;

public class DeveloperTechnologiesAdapter extends RecyclerView.Adapter<DeveloperTechnologiesAdapter.ViewHolder> {
    private ArrayList<String> techNameList;
    private boolean[] selectedCheckboxData;
    private Context context;

    public DeveloperTechnologiesAdapter(Context context, ArrayList<String> techNameList) {
        this.techNameList = techNameList;
        this.context = context;
        this.selectedCheckboxData = new boolean[techNameList.size()];
    }

    @Override
    public DeveloperTechnologiesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_technology, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeveloperTechnologiesAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.techName.setText(techNameList.get(i));
        if (selectedCheckboxData[i]) {
            viewHolder.techCheckbox.setChecked(true);
        }
        viewHolder.techCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckboxData[i] = !selectedCheckboxData[i];
            }
        });
    }

    @Override
    public int getItemCount() {
        return techNameList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox techCheckbox;
        private TextView techName;

        ViewHolder(View view) {
            super(view);
            techName = view.findViewById(R.id.tech_name);
            techCheckbox = view.findViewById(R.id.tech_checkbox);
        }
    }

    public boolean[] getSelectedCheckboxData() {
        return selectedCheckboxData;
    }

    public void setSelectedCheckboxData(boolean[] selectedCheckboxData) {
        this.selectedCheckboxData = selectedCheckboxData;
    }
}