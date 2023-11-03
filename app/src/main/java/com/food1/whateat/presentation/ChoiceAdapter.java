    package com.food1.whateat.presentation;

    import android.content.Context;
    import android.content.res.ColorStateList;
    import android.graphics.Color;
    import android.util.Log;
    import android.view.Gravity;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.LinearLayout;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;

    public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {
        private String[] data;
        private boolean[] checkedStates;
        private Context context;
        private CheckBoxListener checkBoxListener;
        private static final int VIRTUAL_ITEM_COUNT = 100000;


        private CompoundButton.OnCheckedChangeListener checkBoxChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                checkedStates[position] = isChecked;
                Toast.makeText(context, isChecked + "로 변경", Toast.LENGTH_SHORT).show();
            }
        };



        public ChoiceAdapter(String[] data, Context context, String[] checkedData) {
            this.data = data;
            this.context = context;
            this.checkedStates = new boolean[data.length];
            for (int i = 0; i < data.length; i++) {
                for (String s : checkedData) {
                    if (s != null && s.equals(data[i])) {
                        checkedStates[i] = true;
                        break;
                    }
                }
            }
        }

        @NonNull
        @Override
        public ChoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setTextSize(50);
            checkBox.setPadding(40, 20, 0, 10);
            checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FF5722")));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.LEFT;
            checkBox.setLayoutParams(lp);
            return new ViewHolder(checkBox);
        }

        @Override
        public void onBindViewHolder(@NonNull ChoiceAdapter.ViewHolder holder, int position) {
            final int actualPosition = position % data.length;
            holder.checkBox.setText(data[actualPosition]);

            // 체크박스의 리스너를 잠시 제거합니다.
            holder.checkBox.setOnCheckedChangeListener(null);

            // 체크박스의 상태를 'checkedStates' 배열에 저장된 상태로 설정합니다.
            holder.checkBox.setChecked(checkedStates[actualPosition]);

            // 체크박스에 position 값을 태그로 설정하여 리스너에서 사용할 수 있도록 합니다.
            holder.checkBox.setTag(actualPosition);

            // 체크박스 상태가 변경될 때 'checkedStates' 배열을 업데이트하는 리스너를 설정합니다.
            holder.checkBox.setOnCheckedChangeListener(checkBoxChangeListener);

            // 'checkBoxListener'가 설정되어 있다면 해당 리스너를 호출합니다.
            if (checkBoxListener != null) {
                checkBoxListener.onCheckBoxCreated(holder.checkBox, actualPosition);
            }



        }



        @Override
        public int getItemCount() {
            return VIRTUAL_ITEM_COUNT;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public CheckBox checkBox;

            public ViewHolder(CheckBox v) {
                super(v);
                checkBox = v;
            }
        }

        public interface CheckBoxListener {
            void onCheckBoxCreated(CheckBox checkBox, int position);
        }

        public void setCheckBoxListener(CheckBoxListener listener) {
            this.checkBoxListener = listener;
        }

        public String[] getCheckedItems() {
            ArrayList<String> checkedItems = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                if (checkedStates[i]) {
                    checkedItems.add(data[i]);
                }
            }
            return checkedItems.toArray(new String[0]);
        }

        public String[] getData() {
            return data;
        }

        public void updateData(String[] newData) {
            this.data = newData;
            notifyDataSetChanged();
        }
        public boolean[] getCheckedStates() {
            return checkedStates;
        }

    }