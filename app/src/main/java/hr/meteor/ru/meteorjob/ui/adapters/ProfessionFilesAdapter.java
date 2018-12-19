package hr.meteor.ru.meteorjob.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import hr.meteor.ru.meteorjob.R;

public class ProfessionFilesAdapter extends RecyclerView.Adapter<ProfessionFilesAdapter.ViewHolder> {
    private Context context;
    private int position;

    private List<File> fileList;

    public ProfessionFilesAdapter(Context context, List<File> fileList) {
        this.context = context;
        this.fileList = fileList;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }


}