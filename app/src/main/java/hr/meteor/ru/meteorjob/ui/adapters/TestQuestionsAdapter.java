package hr.meteor.ru.meteorjob.ui.adapters;

import android.content.Context;
import android.content.Intent;
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
import hr.meteor.ru.meteorjob.ui.beans.Test;
import hr.meteor.ru.meteorjob.ui.ui.TestQuestionsActivity;

public class TestQuestionsAdapter extends RecyclerView.Adapter<TestQuestionsAdapter.ViewHolder> {
    private Context context;
    private List<Test> testList;

    public TestQuestionsAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    @Override
    public TestQuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_test, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TestQuestionsAdapter.ViewHolder viewHolder, final int i) {
        final TextView name = viewHolder.name;
        name.setText(testList.get(i).getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_test_title);
        }
    }
}