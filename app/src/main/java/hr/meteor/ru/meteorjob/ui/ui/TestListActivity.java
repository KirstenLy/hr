package hr.meteor.ru.meteorjob.ui.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.TestListAdapter;
import hr.meteor.ru.meteorjob.ui.beans.Test;

public class TestListActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        createToolbar(R.id.actionbar_test, 0, R.string.actionbar_test, false);

        ArrayList<Test> testList = new ArrayList<>();
        testList.add(new Test("Test 1"));
        testList.add(new Test("Test 2"));
        testList.add(new Test("Test 3"));

        int professionId = getIntent().getExtras().getInt("profession_ID");

        RecyclerView testRV = findViewById(R.id.recycler_test);
        testRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        TestListAdapter testListAdapter = new TestListAdapter(this, testList, professionId);
        testRV.setAdapter(testListAdapter);
    }
}
