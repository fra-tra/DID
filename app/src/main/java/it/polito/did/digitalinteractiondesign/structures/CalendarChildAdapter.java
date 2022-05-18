package it.polito.did.digitalinteractiondesign.structures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.polito.did.digitalinteractiondesign.R;

public class CalendarChildAdapter extends RecyclerView.Adapter<CalendarChildAdapter.ViewHolder> {

    ArrayList<CalendarChild> calendarChildArrayList;

    public CalendarChildAdapter(ArrayList<CalendarChild> calendarChildArrayList) {
        this.calendarChildArrayList = calendarChildArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_switch_card,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CalendarChild calendarChild = calendarChildArrayList.get(position);

        holder.nestedRV_Text00.setText(calendarChild.itemName);
        holder.nestedRV_switch00.setChecked(calendarChild.switchStatus);

    }

    @Override
    public int getItemCount() {
        return calendarChildArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nestedRV_Text00;
        Switch nestedRV_switch00;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nestedRV_Text00 = itemView.findViewById(R.id.nestedRV_Text00);
            nestedRV_switch00 = itemView.findViewById(R.id.nestedRV_switch00);
        }
    }

}
