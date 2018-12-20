package hr.meteor.ru.meteorjob.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import hr.meteor.ru.meteorjob.R;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.rvHeightCorrector;

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
    public ProfessionFilesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_profession_files, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfessionFilesAdapter.ViewHolder viewHolder, final int i) {
        final Chip fileNameField = viewHolder.fileName;

        fileNameField.setText(fileList.get(i).getName());
        fileNameField.setOnCloseIconClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                fileList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, fileList.size());
                rvHeightCorrector(rv);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Chip fileName;


        ViewHolder(View view) {
            super(view);
            fileName = view.findViewById(R.id.text_profession_cell_files_filename);
        }
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}