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
    private Context context;

    public DeveloperTechnologiesAdapter(Context context, ArrayList<String> techNameList) {
        this.techNameList = techNameList;
        this.context = context;
    }

    @Override
    public DeveloperTechnologiesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_technology, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeveloperTechnologiesAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.techName.setText(techNameList.get(i));
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
            techCheckbox = view.findViewById(R.id.tech_checkbox);
            techName = view.findViewById(R.id.tech_name);
        }
    }
}