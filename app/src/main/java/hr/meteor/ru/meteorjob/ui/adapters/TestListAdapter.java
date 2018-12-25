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

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder> {
    private Context context;
    private List<Test> testList;
    private int professionId;

    public TestListAdapter(Context context, List<Test> testList, int professionId) {
        this.context = context;
        this.testList = testList;
        this.professionId = professionId;
    }

    @Override
    public TestListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_test, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TestListAdapter.ViewHolder viewHolder, final int i) {
        final TextView name = viewHolder.name;
        name.setText(testList.get(i).getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TestQuestionsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("test_id", i + 1);
                intent.putExtra("profession_key", professionId);
                context.startActivity(intent);
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