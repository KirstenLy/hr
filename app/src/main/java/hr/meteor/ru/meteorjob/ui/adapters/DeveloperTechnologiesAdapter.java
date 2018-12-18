package hr.meteor.ru.meteorjob.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;

public class DeveloperTechnologiesAdapter extends RecyclerView.Adapter<DeveloperTechnologiesAdapter.ViewHolder> {
    private ArrayList<String> techNameList;
    private boolean[] selectedCheckboxArray;
    private Context context;

    public DeveloperTechnologiesAdapter(Context context, ArrayList<String> techNameList) {
        this.techNameList = techNameList;
        this.context = context;
        this.selectedCheckboxArray = new boolean[techNameList.size()];
    }

    @Override
    public DeveloperTechnologiesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_technology, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeveloperTechnologiesAdapter.ViewHolder viewHolder, final int i) {
        TextView techName = viewHolder.techName;
        final CheckBox techCheckBox = viewHolder.techCheckbox;

        techName.setText(techNameList.get(i));

        if (selectedCheckboxArray[i]) {
            techCheckBox.setChecked(true);
        }

        techCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckboxArray[i] = !selectedCheckboxArray[i];
            }
        });

        techName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckboxArray[i] = !selectedCheckboxArray[i];
                techCheckBox.setChecked(!techCheckBox.isChecked());
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

    public boolean[] getSelectedCheckboxArray() {
        return selectedCheckboxArray;
    }

    public void setSelectedCheckboxArray(boolean[] selectedCheckboxArray) {
        this.selectedCheckboxArray = selectedCheckboxArray;
    }

    public String[] getSelectedTechnologiesArray() {
        ArrayList<String> selectedTechnologiesList = new ArrayList<>();
        for (int i = 0; i < selectedCheckboxArray.length; i++) {
            if (selectedCheckboxArray[i]) {
                selectedTechnologiesList.add(techNameList.get(i));
            }
        }
        String[] result = new String[selectedTechnologiesList.size()];
        selectedTechnologiesList.toArray(result);
        return result;
    }
}