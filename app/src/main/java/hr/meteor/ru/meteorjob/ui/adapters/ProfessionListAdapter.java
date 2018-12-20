package hr.meteor.ru.meteorjob.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.beans.Profession;

public class ProfessionListAdapter extends RecyclerView.Adapter<ProfessionListAdapter.ViewHolder> {
    private Context context;
    private List<Profession> professionList;

    public ProfessionListAdapter(Context context, List<Profession> tagList) {
        this.context = context;
        this.professionList = tagList;
    }

    @Override
    public ProfessionListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_profession, viewGroup, false);
        int height = viewGroup.getMeasuredHeight() / 2;
        view.setMinimumHeight(height);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfessionListAdapter.ViewHolder viewHolder, final int i) {
        Profession currentProfession = professionList.get(i);
        viewHolder.name.setText(currentProfession.getName());
        Glide.with(context).load(currentProfession.getImgId()).into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return professionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView img;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_professional_cell_name);
            img = view.findViewById(R.id.img_profession_cell);
        }
    }
}