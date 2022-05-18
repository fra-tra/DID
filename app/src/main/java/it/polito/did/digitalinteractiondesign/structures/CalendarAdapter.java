package it.polito.did.digitalinteractiondesign.structures;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.polito.did.digitalinteractiondesign.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private Activity activity;
    ArrayList<Calendar> calendarArrayList;
    ArrayList<CalendarChild> calendarChildArrayList;

    public CalendarAdapter(Activity activity, ArrayList<Calendar> calendarArrayList, ArrayList<CalendarChild> calendarChildArrayList) {
        this.activity = activity;
        this.calendarArrayList = calendarArrayList;
        this.calendarChildArrayList = calendarChildArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_room_card,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Calendar calendar = calendarArrayList.get(position);

        holder.orderId.setText(calendar.orderId);

        CalendarChildAdapter calendarChildAdapter = new CalendarChildAdapter(calendarChildArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.calendar_nestedRV.setLayoutManager(linearLayoutManager);
        holder.calendar_nestedRV.setAdapter(calendarChildAdapter);

    }

    @Override
    public int getItemCount() {
        return calendarArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView orderId;
        RecyclerView calendar_nestedRV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.RoomCalendarTitle);
            calendar_nestedRV = itemView.findViewById((R.id.RecyclerCalendar));
        }
    }
}
