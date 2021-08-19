package com.izhar.ahcsportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.izhar.ahcsportal.R;
import com.izhar.ahcsportal.objects.MedicationObject;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.Holder> {
    Context context;
    List<MedicationObject> medications;

    public MedicationAdapter(Context context, List<MedicationObject> medications) {
        this.context = context;
        this.medications = medications;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_medication_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        MedicationObject medication = medications.get(position);
        holder.medication.setText(medication.getMedication());
        holder.start_date.setText(medication.getStart_date().substring(0, medication.getStart_date().indexOf('T')));
        holder.end_date.setText(medication.getEnd_date().substring(0, medication.getEnd_date().indexOf('T')));
        holder.dosage.setText(medication.getDosage());
        holder.description.setText(medication.getDescription());
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView medication, start_date, end_date, dosage, description;
        public Holder(@NonNull View itemView) {
            super(itemView);
            medication = itemView.findViewById(R.id.medication);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);
            dosage = itemView.findViewById(R.id.dosage);
            description = itemView.findViewById(R.id.description);
        }
    }
}
