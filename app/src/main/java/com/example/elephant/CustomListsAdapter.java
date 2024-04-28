package com.example.elephant;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomListsAdapter extends RecyclerView.Adapter<CustomListsAdapter.ViewHolder> {

    private Activity mcontext;

    private ArrayList<List> lists =  new ArrayList<>();
    ;

    public CustomListsAdapter(Activity context) {
        mcontext = context;
    }

    // 리사이클러뷰에 데이터 추가 메소드
    public void addItem(List item) {
        lists.add(0,item);                                                                    //인덱스 0에 해당하는 리스트부터 넣는다
    }


    @NonNull
    @Override
    public CustomListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        // 미리 만들어 놓은 alram_list.xml 기입
        View view = inflater.inflate(R.layout.alram_list, parent, false) ;
        CustomListsAdapter.ViewHolder vh = new CustomListsAdapter.ViewHolder(view) ;

        return vh;
    }

    @Override
    public int getItemCount() {
        return lists.size() ;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final CustomListsAdapter.ViewHolder holder, final int position) {
        List item = lists.get(position);
        // 메모 아이템 xml상에 메모 데이터가 적용되도록 세팅
        holder.setItem(item);

        // 메모 아이템 안에 있는 보기 버튼을 클릭하여 상세보기(ViewActivity)로 이동
        //추가 해야함


    }

    // 커스텀 뷰 홀더가 아님
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView day;
        TextView contents;
        TextView time;
        CheckBox checkBox;

        ViewHolder(View itemView){
            super(itemView);

            //뷰홀더에 필요한 아이템데이터 findview
            day = itemView.findViewById(R.id.event_item_day);//아이템에 들어갈 텍스트
            contents = itemView.findViewById(R.id.event_item_title);//아이템에 들어갈 텍스트
            time = itemView.findViewById(R.id.event_item_time);//아이템에 들어갈 텍스트
            checkBox = itemView.findViewById(R.id.CheckBox);
        }
        //아이템뷰에 binding할 데이터
        public void setItem(List item) {
            day.setText(item.getDay());
            contents.setText(item.getContents());
            time.setText(item.getTime());
        }
    }

}
