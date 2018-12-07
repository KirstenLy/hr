package hr.meteor.ru.meteorjob.ui.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionListAdapter;
import hr.meteor.ru.meteorjob.ui.beans.Profession;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

public class MainActivity extends AbstractActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isInternetActive()) {
            DialogUtility.showErrorDialog(this, R.string.error_internet_connection, true);
        }

        createToolbar(R.id.actionbar_main, 0, R.string.actionbar_title, false);

        RecyclerView recyclerView = findViewById(R.id.recycler_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Profession> professionList = new ArrayList<>();
        professionList.add(new Profession(1, R.drawable.icon_profession_developer, getString(R.string.profession_dev)));
        professionList.add(new Profession(2, R.drawable.icon_profession_manager, getString(R.string.profession_manager)));

        ProfessionListAdapter adapter = new ProfessionListAdapter(getApplicationContext(), professionList);
        recyclerView.setAdapter(adapter);
    }
}
