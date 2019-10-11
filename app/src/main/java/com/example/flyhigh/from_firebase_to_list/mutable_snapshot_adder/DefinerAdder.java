package com.example.flyhigh.from_firebase_to_list.mutable_snapshot_adder;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

//import com.maidenhair.flyhigh.data.Dias;
//import com.maidenhair.flyhigh.data.Horas;
//import com.maidenhair.flyhigh.data.Itinerary;

public class DefinerAdder {

    private static final String TAG = "DefinerAdder";

    private String nameOfChild;
    private ValueClassRelator[] relations;

    private Object object;

    public DefinerAdder(String nameOfChild, ValueClassRelator[] relations) {
        Log.d(TAG, "DefinerAdder: ");
        
        this.nameOfChild = nameOfChild;
        this.relations = relations;
    }

    public DefinerAdder(ValueClassRelator[] relations) {
        Log.d(TAG, "DefinerAdder: ");

        this.relations = relations;
    }


    public DefinerAdder defineByInnerValue(DataSnapshot snapshot) {


        for (int i = 0; i < relations.length; i++) {

            Log.d(TAG, "define: i = " + i);

                if (snapshot.child(nameOfChild).getValue(String.class).equals(relations[i].getValue())) {
    //            if (mSnapshot.child(nameOfChild).getValue(String.class).equals(relations[i].getValue())) {
                    object = snapshot.getValue(relations[i].getAClass());

                    Log.d(TAG, "define: object is: " + object.toString());

                    break;
                }
        }
        
        return this;

    }



    public DefinerAdder defineByKeyThenAdd(DataSnapshot snapshot, ArrayList<Object> objects) {

        for (int i = 0; i < relations.length; i++) {

            Log.d(TAG, "define: i = " + i);

            Log.d(TAG, "defineByKey: snapshot Key is: " + snapshot.getKey());

            Log.d(TAG, "defineByKey: relation String Value is: " + relations[i].getValue());

                if (snapshot.getKey().equals(relations[i].getValue())) {
                    object = snapshot.getValue(relations[i].getAClass());

                    Log.d(TAG, "define: object is: " + object.toString());

                    objects.add(object);

                    break;
                } /*else {defineItinerario(snapshot, objects);}*/
        }

        return this;

    }

    public DefinerAdder defineByParentThenAdd(DataSnapshot snapshot, ArrayList<Object> objects/*, BaseAdapter adapter*/) {

        for (int i = 0; i < relations.length; i++) {

            Log.d(TAG, "defineByParentThenAdd: i = " + i);

            Log.d(TAG, "defineByParentThenAdd: snapshot Parent Key is: " + snapshot.getRef().getParent().getKey());

            Log.d(TAG, "defineByParentThenAdd: relation String Value is: " + relations[i].getValue());

                if (snapshot.getRef().getParent().getKey().equals(relations[i].getValue())) {
                    object = snapshot.getValue(relations[i].getAClass());

                    Log.d(TAG, "defineByParentThenAdd: object is: " + object.toString());

                    objects.add(object);

//                    adapter.notifyDataSetChanged();

                    Log.d(TAG, "defineByParentThenAdd: List of Objects is: " + objects.toString());

                    break;
                } /*else {defineItinerario(snapshot, objects);}*/
        }

        return this;

    }

    public Object defineByParentThenReturn2Object(DataSnapshot snapshot/*, ArrayList<Object> objects*//*, BaseAdapter adapter*/) {

        Object mObject;

        for (int i = 0; i < relations.length; i++) {

            Log.d(TAG, "defineByParentThenAdd: i = " + i);

            Log.d(TAG, "defineByParentThenAdd: snapshot Parent Key is: " + snapshot.getRef().getParent().getKey());

            Log.d(TAG, "defineByParentThenReturn2Object: Snapshot Key is: " + snapshot.getKey());

            Log.d(TAG, "defineByParentThenAdd: relation String Value is: " + relations[i].getValue());

                if (snapshot.getRef().getParent().getKey().equals(relations[i].getValue())) {
                    mObject = snapshot.getValue(relations[i].getAClass());

//                    Log.d(TAG, "defineByParentThenAdd: object is: " + object.toString());

//                    objects.add(object);

//                    adapter.notifyDataSetChanged();

                    Log.d(TAG, "defineByParentThenAdd: List of Objects is: " + mObject.toString());

                    return mObject;


                } /*else {defineItinerario(snapshot, objects);}*/
        }

        return null;

    }



//    public DefinerAdder defineItinerario(DataSnapshot snapshot, ArrayList<Object> objects) {
//
//        for (int i = 0; i < relations.length; i++) {
//
//            Log.d(TAG, "define: i = " + i);
//
//            Log.d(TAG, "defineByKey: snapshot Key is: " + snapshot.getKey());
//
//
//
//            Log.d(TAG, "defineByKey: relation String Value is: " + relations[i].getValue());
//
//                if (snapshot.getKey().equals("Itinerario")) {
//
//                    Itinerary itinerary = snapshot.getValue(Itinerary.class);
//
//                    Log.d(TAG, "defineItinerario: itinerary object is: " + itinerary.toString());
//
//                    for (int j = 0; j < itinerary.getAllDias().size(); j++) {
//
//                        Dias dia = itinerary.getDiaAt(j);
//
//                        for (int k = 0; k < dia.getHoras().length; k++) {
//
//                            Horas hora = dia.getHoraAt(k);
//
//                            for (int l = 0; l < hora.getActividad().length; l++) {
//
//                                objects.add(hora.getActividadAt(l));
//
//                                Log.d(TAG, "defineItinerario: itinerario object added: " + hora.getActividadAt(l));
//                            }
//
//                        }
//
//
//                    }
//
//                    Log.d(TAG, "define: object is: " + object.toString());
//
//                    objects.add(object);
//
//                    break;
//                }
//        }
//
//        return this;
//
//    }

}
