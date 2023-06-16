package com.example.hosp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class Rating extends AppCompatActivity {
    RatingBar ratingBar;
    Button button;
    Helper_doc helper=new Helper_doc(this);
    Status_Helper helper2=new Status_Helper(this);
    specialist_helper helper1=new specialist_helper(this);
    Previous_Rate_Helper previous_rate_helper=new Previous_Rate_Helper(this);
    private SharedPreferences sharedPreferences_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        sharedPreferences_patient = this.getSharedPreferences("Login_Patient", MODE_PRIVATE);


        SharedPreferences preferences=getSharedPreferences("Rate",MODE_PRIVATE);
        float rate=preferences.getFloat("R",0);



        ratingBar=findViewById(R.id.Rating_BR);
        button=findViewById(R.id.Rate_BTN);

        Intent intent=getIntent();
        String username=intent.getStringExtra("doctor_key");
        String patient_name=sharedPreferences_patient.getString("username","");


        float averageScore =  helper.getAverageScore(username);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                float rating=ratingBar.getRating();










                Float count=helper.Get_Count_sum(username);
                if (count==0){

                    Boolean update_count=helper.Update_Count_Sum(username,count+1);
                    if (update_count==true){
                        Toast.makeText(Rating.this, "ZERO Count Added", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Rating.this, "Not Zero COunted Not Added", Toast.LENGTH_SHORT).show();
                    }




                }else {
                    float count_value=helper.Get_Count_sum(username);

                    Boolean update_count=helper.Update_Count_Sum(username,count_value+1);

                    if (update_count==true){

                        Toast.makeText(Rating.this, "Not ZERO COUNT ADD", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Rating.this, "Not ZERO NOT UPDATED", Toast.LENGTH_SHORT).show();
                    }




                }












                Float sum=helper.Get_Total_sum(username);
                if (sum==0){
                    Boolean update_sum=helper.Update_Total_Sum(username,rating);
                    if (update_sum==true)
                    {
                        Toast.makeText(Rating.this, " ZERO SO UPDATED", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Rating.this, "ZERO NOT UPDATED", Toast.LENGTH_SHORT).show();
                    }


                }
                else {

                    Boolean update_sum=helper.Update_Total_Sum(username,rating+sum);
                    if (update_sum==true)
                    {
                        Toast.makeText(Rating.this, "NOT ZERO UPDATED", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Rating.this, "NoT Zero NOT UPDATED", Toast.LENGTH_SHORT).show();
                    }



                }






                Float rate=helper.getAverageScore(username);
                if (rate==0){

                    Boolean update_rate=helper.UpdateRating(username,rating);
                    if (update_rate==true){

                        Boolean delete=helper2.DeleteData_Notification(username);

                        if (delete==true){
                            Toast.makeText(Rating.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();


                            Intent intent1=new Intent(Rating.this,Home_Patient.class);
                            startActivity(intent1);


                        }else {
                            Toast.makeText(Rating.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



                    }

                }else {

                    Float T=helper.Get_Total_sum(username);
                    Float C=helper.Get_Count_sum(username);


                    Boolean update_rate=helper.UpdateRating(username,T/C);
                    if (update_rate==true){

                        Toast.makeText(Rating.this, "RATE UPDATED", Toast.LENGTH_SHORT).show();


                        Boolean delete=helper2.DeleteData_Notification(username);



                        if (delete==true){
                            Toast.makeText(Rating.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();


                            Intent intent1=new Intent(Rating.this,Home_Patient.class);
                            startActivity(intent1);


                        }
                        else
                        {
                            Toast.makeText(Rating.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }


                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                   //     Boolean add=previous_rate_helper.InsertData(username,patient_name,sum/count);
                   //     if (add==true){
                   //         Toast.makeText(Rating.this, "True", Toast.LENGTH_SHORT).show();
                   //     }else {
                     //       Toast.makeText(Rating.this, "False", Toast.LENGTH_SHORT).show();
                     //   }










                    }else{
                        Toast.makeText(Rating.this, "RATE NOT UPDATED", Toast.LENGTH_SHORT).show();
                    }





                }























     /*


                SharedPreferences sharedPreferences=getSharedPreferences("Rate",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putFloat("R",rating);
                editor.commit();




                    Boolean update=helper.UpdateRating(username,(rating+averageScore)/2);
                    if (update==true){
                        Toast.makeText(Rating.this, "Rating insert", Toast.LENGTH_SHORT).show();

                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                     Boolean get= previous_rate_helper.InsertData(username,patient_name,rating);
                     if (get==true){
                         Toast.makeText(Rating.this, "Inserted", Toast.LENGTH_SHORT).show();
                     }else{
                         Toast.makeText(Rating.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                     }


                        Boolean delete=helper2.DeleteData_Notification(username);

                        if (delete==true){
                            Toast.makeText(Rating.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();


                            Intent intent1=new Intent(Rating.this,Home_Patient.class);
                            startActivity(intent1);


                        }else {
                            Toast.makeText(Rating.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






                    }else {
                        Toast.makeText(Rating.this, "Rating not inserted", Toast.LENGTH_SHORT).show();
                    }










            */}
        });


    }


    public  void Get_Count(){








    }


}