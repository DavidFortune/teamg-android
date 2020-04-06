package com.example.teamg_plantproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageArchive extends AppCompatActivity {

    private static final String TAG = "My Activity";
    protected Button addImageButton;
    protected DatabaseHelper db;
    protected String sensorID;
    static final int IMAGE_CAPTURE_CODE = 1001;
    static final int SELECT_FILE = 1000;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    protected List<String> myList;
    ArrayList<Plant> plants;
    ArrayList<Bitmap> plantPictures;
    protected GridView gridView;
    protected String PhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_archive);

        addImageButton = findViewById(R.id.add_image_button);
        addImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final CharSequence[] items= {"Camera", "Gallery", "Cancel"};
                AlertDialog.Builder builder =  new AlertDialog.Builder(ImageArchive.this);
                builder.setTitle("Select image from");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Camera"))
                        {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        } else if (items[which].equals("Gallery"))
                        {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser
                                            (intent, "Select file"), PICK_FROM_GALLERY);
                        }else if (items[which].equals("Cancel"))
                        {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        sensorID = getIntent().getStringExtra("SENSOR_ID");
        db = new DatabaseHelper(getApplicationContext());
        Log.d(TAG, "onCreate, sensor ID: " + sensorID);

        gridView = (GridView) findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this, plantPictures);
        gridView.setAdapter(imageAdapter);

        // Initialize GridView Thumbnail Click Handler
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                intent.putExtra("path", db.getPlantPictures(sensorID));
                startActivity(intent);
            }
        });
    }   //end of onCreate

    public class ImageAdapter extends BaseAdapter
    {
        private Context context;
        //ArrayList<Plant> plantArrayList;
        ArrayList <Bitmap> plantPictures;

        public ImageAdapter(Context c, ArrayList <Bitmap> plantPictures)
        {
            context = c;
            this.plantPictures = plantPictures;
        }
        public int getCount()
        {
           return plantPictures.size();
        }
        public Object getItem(int position)
        {
            return null;
        }
        public long getItemId(int position)
        {
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final DatabaseHelper db = new DatabaseHelper(context);
            //final Plant plant = plantArrayList.get(position);
            if (convertView == null)
            {
                final LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.gridview_layout, null);
            }

            final ImageView plantPic = (ImageView)convertView.findViewById(R.id.imageview_plant_pic);
            final TextView date = (TextView)convertView.findViewById(R.id.textview_date);
            plantPic.setImageBitmap(db.getPlantPictures(sensorID));
            //date.setText(plantArrayList.get(position));

            return convertView;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("TAG", "in onActivity for Result");
        super.onActivityResult(requestCode, resultCode, data);
        //db = new DatabaseHelper(getApplicationContext());
        SimpleDateFormat s = new SimpleDateFormat("MMM-dd-yyyy");
        String format = s.format(new Date());
            if ( resultCode == RESULT_OK)
            {
                Log.d("TAG", "onActivityResult: getting there");
                    Bundle bundle = data.getExtras();
                    Bitmap myImage = bundle.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();

                    // Adding plant picture
                    //db.addPlantPicture(imageInByte, "TEMPz1qwerwe","TEMP22-04-20//12:40:12");
                    db.addPlantPicture(imageInByte, sensorID, format);
                    Log.d(TAG, "onActivityResult: inserted picture");
                    Log.d(TAG, "onActivityResult: sensor ID: " + sensorID + "date " + format);
                    Intent i = new Intent(ImageArchive.this,
                            ImageArchive.class);
                    startActivity(i);
                    finish();
        }
    }
}


