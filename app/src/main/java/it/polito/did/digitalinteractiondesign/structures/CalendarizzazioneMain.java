package it.polito.did.digitalinteractiondesign.structures;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;

import it.polito.did.digitalinteractiondesign.R;

import java.util.ArrayList;

public class CalendarizzazioneMain extends AppCompatActivity {
    CalendarAdapter calendarAdapter;
    ArrayList<Calendar> calendarArrayList;
    ArrayList<CalendarChild> calendarChildArrayList;
    RecyclerView RVparent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendarizzazione);

        RVparent = findViewById(R.id.RVparent);

        String[] orderId = {"Cucina", "Salotto", "Bagno", "Balcone", "Camera da Letto 1", "Camera da Letto 2"};
        String[] itemName = {"Basilico", "Ficus", "Cactus", "Margherita", "Rosmarino", "Olmo"};
        Boolean[] switchStatus = {false,true,false,true,false,false};
        calendarArrayList = new ArrayList<>();
        calendarChildArrayList = new ArrayList<>();

        for(int i =0; i< orderId.length; i++)
        {
            Calendar calendar = new Calendar(orderId[i]);
            calendarArrayList.add(calendar);

            if(i< itemName.length){

                CalendarChild calendarChild = new CalendarChild(itemName[i],switchStatus[i]);
                calendarChildArrayList.add(calendarChild);
            }
        }

        calendarAdapter = new CalendarAdapter(this, calendarArrayList, calendarChildArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RVparent.setLayoutManager(linearLayoutManager);
        RVparent.setAdapter(calendarAdapter);
    }
}
