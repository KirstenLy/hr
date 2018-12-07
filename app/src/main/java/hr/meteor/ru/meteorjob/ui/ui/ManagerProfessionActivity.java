package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import hr.meteor.ru.meteorjob.R;


public class ManagerProfessionActivity extends AbstractActivity implements View.OnClickListener {
    final int TAKE_USER_FILE_REQUEST = 1;
    TextView userFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == TAKE_USER_FILE_REQUEST) {
            File file = new File(data.getData().getPath());
            userFile.setText(file.getName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profession);
        createToolbar(R.id.actionbar_profession_manager, 0, R.string.profession_manager, true);

        userFile = findViewById(R.id.link_profession_manager_take_resume);
        userFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.link_profession_manager_take_resume):
                Intent intent = new Intent();
                intent.setType("file/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), TAKE_USER_FILE_REQUEST);
                break;

        }
    }
}
