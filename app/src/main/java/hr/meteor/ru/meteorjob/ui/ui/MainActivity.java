package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gtomato.android.ui.transformer.CoverFlowViewTransformer;
import com.gtomato.android.ui.transformer.FlatMerryGoRoundTransformer;
import com.gtomato.android.ui.transformer.InverseTimeMachineViewTransformer;
import com.gtomato.android.ui.transformer.LinearViewTransformer;
import com.gtomato.android.ui.transformer.TimeMachineViewTransformer;
import com.gtomato.android.ui.widget.CarouselView;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionListAdapter;
import hr.meteor.ru.meteorjob.ui.beans.Profession;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

public class MainActivity extends AbstractActivity implements View.OnClickListener {
//    RecyclerView carousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isInternetActive()) {
            DialogUtility.showErrorDialog(this, getString(R.string.error_internet_connection), true);
        }

        createToolbar(R.id.actionbar_main, 0, R.string.actionbar_title, false);

        ArrayList<Profession> professionList = new ArrayList<>();
        professionList.add(new Profession(1, R.drawable.ic_profession_developer, getString(R.string.profession_dev)));
        professionList.add(new Profession(2, R.drawable.ic_profession_manager, getString(R.string.profession_manager)));

        LinearLayout managerLayout = findViewById(R.id.layout_profession_manager);
        LinearLayout developerLayout = findViewById(R.id.layout_profession_developer);

        managerLayout.setOnClickListener(this);
        developerLayout.setOnClickListener(this);

//        carousel = findViewById(R.id.carousel);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        carousel.setLayoutManager(layoutManager);
//        carousel.setAdapter(new ProfessionListAdapter(getApplicationContext(), professionList));
//        carousel.setNestedScrollingEnabled(false);

//        carousel = findViewById(R.id.carousel);
//        carousel.setTransformer(new LinearViewTransformer());
//        carousel.setAdapter(new ProfessionListAdapter(getApplicationContext(), professionList));
//        carousel.setInfinite(false);
//        carousel.setEnableFling(false);
//        carousel.setOnItemClickListener(new IntentChooser());

//        ImageView leftScroll = findViewById(R.id.img_main_left_arrow);
//        leftScroll.setOnClickListener(this);
//
//        ImageView rightScroll = findViewById(R.id.img_main_right_arrow);
//        rightScroll.setOnClickListener(this);

//
//        carousel.scrollToPosition(carousel.getCurrentAdapterPosition() == 1 ? 0 : 1);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.layout_profession_manager:
                intent = new Intent(getApplicationContext(), ManagerProfessionActivity.class);
                break;
            case R.id.layout_profession_developer:
                intent = new Intent(getApplicationContext(), DeveloperProfessionActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

//    class IntentChooser implements CarouselView.OnItemClickListener {
//        Intent intent;
//
//        @Override
//        public void onItemClick(RecyclerView.Adapter adapter, View view, int position, int adapterPosition) {
//
//        }
//    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
