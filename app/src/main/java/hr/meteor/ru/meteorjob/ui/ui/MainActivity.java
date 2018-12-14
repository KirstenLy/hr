package hr.meteor.ru.meteorjob.ui.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionListAdapter;
import hr.meteor.ru.meteorjob.ui.beans.Profession;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

public class MainActivity extends AbstractActivity implements View.OnClickListener {
    DiscreteScrollView scrollView;
    InfiniteScrollAdapter wrapper;

    CarouselLayoutManager layoutManager;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isInternetActive()) {
            DialogUtility.showErrorDialog(this, R.string.error_internet_connection, true);
        }

        createToolbar(R.id.actionbar_main, 0, R.string.actionbar_title, false);

        ArrayList<Profession> professionList = new ArrayList<>();
        professionList.add(new Profession(1, R.drawable.ic_profession_developer, getString(R.string.profession_dev)));
        professionList.add(new Profession(2, R.drawable.ic_profession_manager, getString(R.string.profession_manager)));

        layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.picker);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setAdapter(new ProfessionListAdapter(getApplicationContext(), professionList));

        ImageView leftScroll = findViewById(R.id.img_main_left_arrow);
        leftScroll.setOnClickListener(this);

        ImageView rightScroll = findViewById(R.id.img_main_right_arrow);
        rightScroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.img_main_left_arrow):
                recyclerView.scrollToPosition(layoutManager.getCenterItemPosition() == 1 ? 0 : 1);
                break;
            case (R.id.img_main_right_arrow):
                recyclerView.scrollToPosition(layoutManager.getCenterItemPosition() == 0 ? 1 : 0);
                break;
        }
    }
}
