package it.polito.did.digitalinteractiondesign.structures;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;


import java.util.ArrayList;

import it.polito.did.digitalinteractiondesign.R;
import it.polito.did.digitalinteractiondesign.fragments.CustomCalendarDialog;
import it.polito.did.digitalinteractiondesign.fragments.CustomDeathDialog;

public class CalendarChildAdapter extends RecyclerView.Adapter<CalendarChildAdapter.CalendarChildViewHolder> {
    ArrayList<CalendarChild> calendarChildArrayList;
    private Context context;
    public CalendarChildAdapter(ArrayList<CalendarChild> calendarChildArrayList) {
        this.calendarChildArrayList = calendarChildArrayList;

    }

    @NonNull
    @Override
    public CalendarChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_switch_card,
                parent,false);
        context = parent.getContext();
        return new CalendarChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarChildViewHolder holder, int position) {

        CalendarChild calendarChild = calendarChildArrayList.get(position);

        holder.nestedRV_Text00.setText(calendarChild.itemName);
        holder.nestedRV_switch00.setChecked(calendarChild.switchStatus);


        if(calendarChild.switchStatus) {
          //  holder.infoCalendarButton.setVisibility(View.VISIBLE);
            holder.switchCard.setCardBackgroundColor(Color.WHITE);
            holder.infoSelectedRange.setVisibility(View.VISIBLE);
            holder.selectDateRange.setVisibility(View.VISIBLE);
        }
        else {
           // holder.infoCalendarButton.setVisibility(View.INVISIBLE);
            holder.switchCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
            holder.infoSelectedRange.setVisibility(View.GONE);
            holder.selectDateRange.setVisibility(View.GONE);
        }

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        materialDateBuilder.setTitleText("SELECT A DATE");

        //disable past days
        Long now = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constraintsBuilder =
                new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.from(now));
        materialDateBuilder.setCalendarConstraints(constraintsBuilder.build());


        MaterialDatePicker datePicker = materialDateBuilder.build();

        holder.nestedRV_switch00.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                   // holder.infoCalendarButton.setVisibility(View.VISIBLE);
                    holder.switchCard.setCardBackgroundColor(Color.WHITE);
                    holder.selectDateRange.setVisibility(View.VISIBLE);
                    holder.infoSelectedRange.setVisibility(View.VISIBLE);
                    // apri custom calendar
                   /* FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    CustomCalendarDialog calendarDialog = new CustomCalendarDialog();

                    calendarDialog.show(fragmentManager, "dialog"); */
                }
                else {
                   // holder.infoCalendarButton.setVisibility(View.INVISIBLE);
                    holder.switchCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
                    holder.infoSelectedRange.setVisibility(View.GONE);
                    holder.selectDateRange.setVisibility(View.GONE);


                }
                calendarChild.switchStatus = isChecked;
                Log.d("switchStatus: ", calendarChild.switchStatus.toString());
            }
        });

       /* holder.infoCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show( ((AppCompatActivity)context).getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                datePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                holder.infoSelectedRange.setVisibility(View.VISIBLE);
                                holder.infoSelectedRange.setText(datePicker.getHeaderText());
                            }
                        });
            }
        });*/

       /* holder.infoSelectedRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show( ((AppCompatActivity)context).getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                datePicker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                holder.infoSelectedRange.setVisibility(View.VISIBLE);
                                holder.infoSelectedRange.setText(datePicker.getHeaderText());
                            }
                        });


                datePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendarChild.switchStatus = false;
                        holder.nestedRV_switch00.setChecked(calendarChild.switchStatus);
                    }
                });

            }
        });*/

        holder.infoSelectedRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(holder.infoSelectedRange)
                        .navigate(R.id.action_calendarizzazione_to_selectCalendarRangeFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return calendarChildArrayList.size();
    }

    public class CalendarChildViewHolder extends RecyclerView.ViewHolder{

        TextView nestedRV_Text00;
        Switch nestedRV_switch00;
        CardView switchCard;
        ImageButton infoCalendarButton;
        TextView infoSelectedRange;
        TextView selectDateRange;
        public CalendarChildViewHolder(@NonNull View itemView) {
            super(itemView);
            nestedRV_Text00 = itemView.findViewById(R.id.nestedRV_Text00);
            nestedRV_switch00 = itemView.findViewById(R.id.nestedRV_switch00);
            switchCard = itemView.findViewById(R.id.switchCard);
            infoCalendarButton = itemView.findViewById(R.id.infoCalendarButton);
            infoSelectedRange = itemView.findViewById(R.id.infoSelectedRange);
            selectDateRange = itemView.findViewById(R.id.selectDateRange);
        }
    }



}
