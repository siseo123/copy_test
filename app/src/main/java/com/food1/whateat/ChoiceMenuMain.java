package com.food1.whateat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.food1.whateat.presentation.ChoiceAdapter;

import java.util.ArrayList;

public class ChoiceMenuMain extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private static final float FONT_SIZE = 50;

    ListView listView;

    String[] qqqqq0;
    CheckBox[] qqqqq;
    CheckBox[] qqqqq2;
    String []myLoc2;
    String[] myLoc;
    ChoiceAdapter adapter;
    TextView mainText;  // 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_menu_main);

        mainText = findViewById(R.id.main_text);  // 초기화

        Intent it = getIntent();
        String tag = it.getStringExtra("it_tag");
        myLoc2 = it.getStringArrayExtra("arr");

        int stringId = getResources().getIdentifier(tag,"array",getPackageName());
        myLoc = getResources().getStringArray(stringId);

        qqqqq0 = new String[myLoc.length];
        qqqqq = new CheckBox[myLoc.length];
        qqqqq2 = new CheckBox[myLoc.length];

        for(int a = 0; a < qqqqq2.length; a++) {
            qqqqq2[a] = new CheckBox(this);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(100000 / 2);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        adapter = new ChoiceAdapter(myLoc, this, myLoc2);
        recyclerView.setAdapter(adapter);
        adapter.setCheckBoxListener(new ChoiceAdapter.CheckBoxListener() {
            @Override
            public void onCheckBoxCreated(CheckBox checkBox, int position) {
                qqqqq[position] = checkBox;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int actualPosition = firstVisibleItemPosition % myLoc.length;

                    if (actualPosition != RecyclerView.NO_POSITION && actualPosition < myLoc.length) {
                        String visibleItemValue = myLoc[actualPosition];
                        //Toast.makeText(ChoiceMenuMain.this, "Scroll detected! " + visibleItemValue, Toast.LENGTH_SHORT).show();
                        mainText.setText(visibleItemValue);
                    } else {
                        //Toast.makeText(ChoiceMenuMain.this, "Condition not met!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void loadMoreData() {
        String[] currentData = adapter.getData();
        String[] newData = new String[currentData.length * 2];
        System.arraycopy(currentData, 0, newData, 0, currentData.length);
        System.arraycopy(currentData, 0, newData, currentData.length, currentData.length);
        adapter.updateData(newData);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (int a = 0; a < qqqqq.length; a++) {
            if (qqqqq[a] != null && qqqqq[a].isChecked()) {
                qqqqq2[a].setText(qqqqq[a].getText());
                qqqqq0[a] = qqqqq[a].getText().toString();
            } else {
                qqqqq2[a].setText(null);
                qqqqq0[a] = null;
            }
        }
    }

    public void on_Click_sub(View v){
        ArrayList<String> checkedItems = new ArrayList<>();
        boolean[] checkedStates = adapter.getCheckedStates();
        for (int i = 0; i < checkedStates.length; i++) {
            if (checkedStates[i]) {
                checkedItems.add(myLoc[i]);
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CheckBox", checkedItems.toArray(new String[0]));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        String[] myMenu = myLoc2;
        intent.putExtra("CheckBox", myMenu);
        setResult(RESULT_OK, intent);
        finish();
    }
}
