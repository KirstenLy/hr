package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gtomato.android.ui.transformer.LinearViewTransformer;
import com.gtomato.android.ui.widget.CarouselView;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionListAdapter;
import hr.meteor.ru.meteorjob.ui.beans.Profession;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

public class MainActivity extends AbstractActivity implements View.OnClickListener {
    CarouselView carousel;

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

        carousel = findViewById(R.id.carousel);
        carousel.setTransformer(new LinearViewTransformer());
        carousel.setAdapter(new ProfessionListAdapter(getApplicationContext(), professionList));
        carousel.setInfinite(true);
        carousel.setEnableFling(false);

        carousel.setOnItemClickListener(new IntentChooser());

        ImageView leftScroll = findViewById(R.id.img_main_left_arrow);
        leftScroll.setOnClickListener(this);

        ImageView rightScroll = findViewById(R.id.img_main_right_arrow);
        rightScroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (((v.getId() == R.id.img_main_left_arrow) || (v.getId() == R.id.img_main_right_arrow))) {
            carousel.scrollToPosition(carousel.getCurrentAdapterPosition() == 1 ? 0 : 1);
        }
    }

    class IntentChooser implements CarouselView.OnItemClickListener {
        Intent intent;

        @Override
        public void onItemClick(RecyclerView.Adapter adapter, View view, int position, int adapterPosition) {
            switch (adapterPosition) {
                case 0:
                    intent = new Intent(getApplicationContext(), DeveloperProfessionActivity.class);
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(), ManagerProfessionActivity.class);
                    break;
            }
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
