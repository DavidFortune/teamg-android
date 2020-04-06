package com.example.teamg_plantproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PlantActivity extends AppCompatActivity {

    protected TextView plantName;
    protected TextView plantType;
    protected TextView plantTemp;
    protected ImageView plantPicture;

    private static final int IMAGE_CAPTURE_CODE = 1001;
    protected TextView water_percentage;
    protected TextView sunshine_percentage;

    protected GraphView graph;

    protected Button deletePlant;
    protected ImageButton takePictureButton;
    protected Button imagesButton;

    private static final int PERMISSION_CODE = 1000;
    protected TextView humidity_percentage;
    Uri image_uri;
    protected ProgressBar waterBar;
    protected ProgressBar humidityBar;
    protected ProgressBar sunBar;
    protected int plantID;
    protected DatabaseHelper db;
    protected String plantSensorID;
    private final int soilMax = 3300;
    private final int solarMax = 2200;
    private String currentGraph = "soil";
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference sensorDataRef;
    private static final String TAG = "_Plant_Indiv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        setUpUI();

        plantPicture = findViewById(R.id.plant_image_i);
        takePictureButton = findViewById(R.id.capture_image_btn);
        imagesButton = findViewById(R.id.image_archive_button);

        //picture button click listener
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if system is >= Marshmallow, request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request user permission
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        //permission is already granted
                        openCamera();
                    }
                } else {
                    //system < marshmallow
                    openCamera();
                }
            }
        });

        Intent intent = getIntent();
        plantID = intent.getIntExtra("PlantID", 0);
        System.out.println(plantID + "--------------------------------------");
        db = new DatabaseHelper(getApplicationContext());
        plantSensorID = db.getPlant(plantID).getSensorId();
        sensorDataRef = fb.collection("sensors/" + plantSensorID + "/data");
        Log.d(TAG, "onCreate: " + sensorDataRef.get());


        plantName.setText(db.getPlant(plantID).getPlantName());
        plantType.setText(db.getPlant(plantID).getPlantType());
        waterBar.setProgress(25);
        sunBar.setProgress(50);
        humidityBar.setProgress(75);
        water_percentage.setText("N/A %");
        sunshine_percentage.setText("N/A %");
        humidity_percentage.setText("N/A %");
        plantTemp.setText("N/A *C");
        if (!(db.getImage(plantID) == null))
            plantPicture.setImageBitmap(db.getImage(plantID));


        sensorDataRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(MainActivity.class.getName(), "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("rawHumidity") != null) {

                                String i = Objects.requireNonNull(doc.get("rawSoilValue")).toString();
                                int ii = (int) Math.floor(Double.parseDouble(i));
                                String j = Objects.requireNonNull(doc.get("rawHumidity")).toString();
                                int jj = (int) Math.floor(Double.parseDouble(j));
                                String k = Objects.requireNonNull(doc.get("rawSolarValue")).toString();
                                int kk = (int) Math.floor(Double.parseDouble(k));
                                String l = Objects.requireNonNull(doc.get("rawTemp")).toString();

                                waterBar.setProgress((ii * 100) / soilMax);
                                Log.d(TAG, "onEvent: " + ((ii * 100) / soilMax));
                                humidityBar.setProgress(jj);
                                Log.d(TAG, "onEvent: " + (jj));
                                sunBar.setProgress((kk * 100) / solarMax);
                                Log.d(TAG, "onEvent: " + ((kk * 100) / solarMax));
                                plantTemp.setText(l + "°C");
                                Log.d(TAG, "onEvent: " + l);

                                int water_p = ((ii * 100) / soilMax);
                                water_percentage.setText(water_p + " %");

                                int sunshine_p = ((kk * 100) / solarMax);
                                sunshine_percentage.setText(sunshine_p + " %");

                                humidity_percentage.setText(jj + " %");
                            }
                        }
                    }
                });

        setupGraph();

        //on click listener of picture archive button
        imagesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ImageArchive.class);
                intent.putExtra("SENSOR_ID", plantSensorID);
                startActivity(intent);
            }
        });
    }

            @Override
            protected void onStart() {
                super.onStart();
            }

            private void openCamera() {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From camera");
                image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                //Camera intent
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                //called when image is captured by camera
                super.onActivityResult(requestCode, resultCode, data);
                db = new DatabaseHelper(getApplicationContext());
                if (resultCode == RESULT_OK) {
                    //set the image capture to our ImageView
                    plantPicture.setDrawingCacheEnabled(true);
                    plantPicture.buildDrawingCache();
                    plantPicture.setImageURI(image_uri);
                    Bitmap bitmap = plantPicture.getDrawingCache();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] dataMy = byteArrayOutputStream.toByteArray();
                    db.addImage(dataMy, plantID);
                }
            }

            //to handle permissions
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                //this method is called when the user presses allow or deny from Permission request popup
                switch (requestCode) {
                    case PERMISSION_CODE: {
                        if (grantResults.length > 0 && grantResults[0]
                                == PackageManager.PERMISSION_GRANTED) {
                            //permission was granted
                            openCamera();
                        } else {
                            //permission was denied
                            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public boolean onSupportNavigateUp() {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                return true;
            }

            protected void setUpUI() {
                water_percentage = findViewById(R.id.water_p);
                sunshine_percentage = findViewById(R.id.sunshine_p);
                humidity_percentage = findViewById(R.id.humidity_p);
                plantName = findViewById(R.id.plant_name_i);
                plantType = findViewById(R.id.plant_type_i);
                waterBar = findViewById(R.id.water_progress_i);
                humidityBar = findViewById(R.id.humidity_progress_i);
                sunBar = findViewById(R.id.sunshine_progress_i);
                plantTemp = findViewById(R.id.temperature_i);
                graph = findViewById(R.id.graph);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            protected void goToPlantList() {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }


            protected void setupGraph() {


                sensorDataRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(24)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @SuppressLint("ClickableViewAccessibility")
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w(MainActivity.class.getName(), "Listen failed.", e);
                                    return;
                                }
                                List<DataPoint> dataPointsSoil = new ArrayList<>();
                                List<DataPoint> dataPointsSolar = new ArrayList<>();
                                List<DataPoint> dataPointsAir = new ArrayList<>();
                                List<DataPoint> dataPointsHum = new ArrayList<>();
                                int iteration = 0;
                                for (QueryDocumentSnapshot doc : value) {
                                    if (doc.get("rawHumidity") != null) {

                                        String i = Objects.requireNonNull(doc.get("rawSoilValue")).toString();
                                        int ii = (int) Math.floor(Double.parseDouble(i));
                                        String j = Objects.requireNonNull(doc.get("rawHumidity")).toString();
                                        int jj = (int) Math.floor(Double.parseDouble(j));
                                        String k = Objects.requireNonNull(doc.get("rawSolarValue")).toString();
                                        int kk = (int) Math.floor(Double.parseDouble(k));
                                        String l = Objects.requireNonNull(doc.get("rawTemp")).toString();
                                        int ll = (int) Math.floor(Double.parseDouble(l));
                                        int soilPercent = ((ii * 100) / soilMax);
                                        dataPointsSoil.add(new DataPoint(iteration, soilPercent));
                                        dataPointsSolar.add(new DataPoint(iteration, kk));
                                        dataPointsAir.add(new DataPoint(iteration, ll));
                                        dataPointsHum.add(new DataPoint(iteration, jj));

                                        iteration++;
                                    }
                                }
                                final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsSoil.toArray(new DataPoint[dataPointsSoil.size()]));
                                final LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(dataPointsSolar.toArray(new DataPoint[dataPointsSolar.size()]));
                                final LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(dataPointsAir.toArray(new DataPoint[dataPointsAir.size()]));
                                final LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(dataPointsHum.toArray(new DataPoint[dataPointsHum.size()]));
                                //our initial graph
                                graph.addSeries(series);
                                series.setTitle("Soil Moisture %");

                                //legend setup
                                graph.getLegendRenderer().setVisible(true);
                                graph.getLegendRenderer().setMargin(10);
                                graph.getLegendRenderer().setTextSize(30);
                                graph.getLegendRenderer().setBackgroundColor(0);
                                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                                //viewport setup
                                graph.getViewport().setScalable(true); // allow pinching the zoom and stuff
                                graph.getViewport().setScrollableY(true); //allow vertical scrolling
                                // set axis labels
                                graph.getGridLabelRenderer().setVerticalAxisTitle("Soil Moisture (%)");
                                graph.getGridLabelRenderer().setHorizontalAxisTitle("Last 24 hours");
                                //end of our initial graph


                                graph.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            Log.d("TEST", "onDoubleTap : " + currentGraph);
                                            if (currentGraph == "soil") {
                                                currentGraph = "solar";
                                            } else if (currentGraph == "solar") {
                                                currentGraph = "air";
                                            } else if (currentGraph == "air") {
                                                currentGraph = "humidity";
                                            } else if (currentGraph == "humidity") {
                                                currentGraph = "soil";
                                            }


                                            if (currentGraph == "soil") {
                                                graph.removeAllSeries();
                                                graph.addSeries(series);
                                                series.setTitle("Soil Moisture %");

                                                //legend setup
                                                graph.getLegendRenderer().setVisible(true);
                                                graph.getLegendRenderer().setMargin(10);
                                                graph.getLegendRenderer().setTextSize(30);
                                                graph.getLegendRenderer().setBackgroundColor(0);
                                                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                                                //viewport setup
                                                graph.getViewport().setScalable(true); // allow pinching the zoom and stuff
                                                graph.getViewport().setScrollableY(true); //allow vertical scrolling
                                                // set axis labels
                                                graph.getGridLabelRenderer().setVerticalAxisTitle("Soil Moisture (%)");
                                                graph.getGridLabelRenderer().setHorizontalAxisTitle("Last 24 hours");

                                            } else if (currentGraph == "solar") {
                                                graph.removeAllSeries();
                                                graph.addSeries(series2);
                                                series2.setTitle("Sunlight");

                                                //legend setup
                                                graph.getLegendRenderer().setVisible(true);
                                                graph.getLegendRenderer().setMargin(10);
                                                graph.getLegendRenderer().setTextSize(30);
                                                graph.getLegendRenderer().setBackgroundColor(0);
                                                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                                                //viewport setup
                                                graph.getViewport().setScalable(true); // allow pinching the zoom and stuff
                                                graph.getViewport().setScrollableY(true); //allow vertical scrolling
                                                // set axis labels
                                                graph.getGridLabelRenderer().setVerticalAxisTitle("Sunlight");
                                                graph.getGridLabelRenderer().setHorizontalAxisTitle("Last 24 hours");
                                            } else if (currentGraph == "air") {
                                                graph.removeAllSeries();
                                                graph.addSeries(series3);
                                                series3.setTitle("Air Temperature (°C)");

                                                //legend setup
                                                graph.getLegendRenderer().setVisible(true);
                                                graph.getLegendRenderer().setMargin(10);
                                                graph.getLegendRenderer().setTextSize(30);
                                                graph.getLegendRenderer().setBackgroundColor(0);
                                                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                                                //viewport setup
                                                graph.getViewport().setScalable(true); // allow pinching the zoom and stuff
                                                graph.getViewport().setScrollableY(true); //allow vertical scrolling
                                                // set axis labels
                                                graph.getGridLabelRenderer().setVerticalAxisTitle("(°C)");
                                                graph.getGridLabelRenderer().setHorizontalAxisTitle("Last 24 hours");
                                            } else if (currentGraph == "humidity") {

                                                graph.removeAllSeries();
                                                graph.addSeries(series4);
                                                series4.setTitle("Air Humidity (%)");

                                                //legend setup
                                                graph.getLegendRenderer().setVisible(true);
                                                graph.getLegendRenderer().setMargin(10);
                                                graph.getLegendRenderer().setTextSize(30);
                                                graph.getLegendRenderer().setBackgroundColor(0);
                                                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                                                //viewport setup
                                                graph.getViewport().setScalable(true); // allow pinching the zoom and stuff
                                                graph.getViewport().setScrollableY(true); //allow vertical scrolling
                                                // set axis labels
                                                graph.getGridLabelRenderer().setVerticalAxisTitle("Air Humidity (%)");
                                                graph.getGridLabelRenderer().setHorizontalAxisTitle("Last 24 hours");
                                            }


                                            //this breaks the pinching
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        // Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }


                        });
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.my_menu, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.open_plant_info) {
                    Intent intent = new Intent(getApplicationContext(), PlantInfo.class);
                    intent.putExtra("PlantID", plantID);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.edit_plant_info) {
                    Bundle sendData = new Bundle();
                    sendData.putInt("PlantId", plantID);
                    showDialog(this, sendData);
                } else if (item.getItemId() == R.id.delete_plant_option) {
                    db.deletePlant(plantID);
                    goToPlantList();
                }
                return super.onOptionsItemSelected(item);
            }


            private void showDialog(PlantActivity plantActivity, Bundle sendData) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                EditDialog editDialog = new EditDialog();
                editDialog.setArguments(sendData);
                editDialog.show(fragmentManager, "Plant Edit");
            }
}
