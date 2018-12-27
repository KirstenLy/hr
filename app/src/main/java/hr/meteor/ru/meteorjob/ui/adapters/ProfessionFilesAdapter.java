package hr.meteor.ru.meteorjob.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.io.File;
import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.ui.LogoActivity;
import hr.meteor.ru.meteorjob.ui.ui.MainActivity;

public class ProfessionFilesAdapter extends RecyclerView.Adapter<ProfessionFilesAdapter.ViewHolder> {
    private Context context;
    private List<File> fileList;
    private RecyclerView rv;

    public ProfessionFilesAdapter(Context context, List<File> fileList, RecyclerView rv) {
        this.context = context;
        this.fileList = fileList;
        this.rv = rv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_profession_files, viewGroup, false);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfessionFilesAdapter.ViewHolder viewHolder, final int i) {
        final Chip fileNameField = viewHolder.fileName;

//        viewHolder.cellLayout.setAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.animation_add));

        fileNameField.setText(fileList.get(i).getName());
        fileNameField.setOnCloseIconClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                fileList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, fileList.size());

//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                };
//                Handler handler = new Handler();
//                handler.postDelayed(runnable, 1000);
//                rv.startLayoutAnimation();
//                viewHolder.cellLayout.setAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.animation_del));
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Chip fileName;
        private LinearLayout cellLayout;

        ViewHolder(View view) {
            super(view);
            fileName = view.findViewById(R.id.text_profession_cell_files_filename);
            cellLayout = view.findViewById(R.id.cell_layout);
        }
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}