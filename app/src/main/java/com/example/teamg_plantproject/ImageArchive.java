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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

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
    protected int plantID;
    static final int IMAGE_CAPTURE_CODE = 1001;
    static final int SELECT_FILE = 1000;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    protected List<String> myList;
    protected GridView gridView;
    protected String PhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_archive);

        addImageButton = findViewById(R.id.add_image_button);
        gridView = (GridView) findViewById(R.id.gridView);

        //SharedPreferences settings = getSharedPreferences(getString(R.string.appSettings), MODE_PRIVATE);
        // Initialize List
        myList = new ArrayList<String>();
        myList.clear();
        /*// Retrieve Image List Size
        int size = settings.getInt("myList.size()", 0);
        // Retrieve Image List
        for (int i=0; i < size; i++) {
            String imagePath = settings.getString(String.format("myList[%d]", i), "");
            myList.add(imagePath);
        }*/

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
                        if (items[which].equals("Camera")){
                            takePicture();
                        }else if (items[which].equals("Gallery")){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.putExtra("crop", "true");
                            intent.putExtra("aspectX", 0);
                            intent.putExtra("aspectY", 0);
                            intent.putExtra("outputX", 200);
                            intent.putExtra("outputY", 150);
                            intent.putExtra("return-data", true);
                            startActivityForResult(
                                    Intent.createChooser(intent, "Complete action using"),
                                    PICK_FROM_GALLERY);
                            /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore
                                    .Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Select File"),
                                    SELECT_FILE);*/
                        }else if (items[which].equals("Cancel")){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        Intent intent = getIntent();
        plantID = intent.getIntExtra("PlantID", 0);
        db = new DatabaseHelper(getApplicationContext());


        gridView.setAdapter(new ImageAdapter(this));
        // Initialize GridView Thumbnail Click Handler
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // Send File Path to Image Display Activity
                Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                intent.putExtra("path", myList.get(position));
                startActivity(intent);
            }
        });
    }   //end of onCreate

    public class ImageAdapter extends BaseAdapter
    {
        private Context mContext;
        public ImageAdapter(Context c)
        {
            mContext = c;
        }
        public int getCount()
        {
            return myList.size();
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
            ImageView imageView;
            if (convertView == null)
            {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(450, 450));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions. inJustDecodeBounds = false ;
            bmOptions. inSampleSize = 4;
            bmOptions. inPurgeable = true ;
            Bitmap bitmap = BitmapFactory.decodeFile(myList.get(position), bmOptions);

            imageView.setImageBitmap(bitmap);
            return imageView;
        }
    }

    private void takePicture()
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("crop", "true");
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        /*//ensure there is a camera activity to handle intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            //Log.d(TAG, "in first if condition");
            // Create the File where picture should go
            File photoFile=null;
            try
            {
                photoFile = createImageFile();
                Log.d(TAG, "created image file");
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
                Log.d(TAG, "error in creating file");
            }
            //Continue if File was successfully created
            if (photoFile != null)
            {
                Uri apkURI = FileProvider.getUriForFile(getApplicationContext(),
                        "com.your.package.fileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, apkURI);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE_CODE);
            }
        }*/
    }
    /*private File createImageFile() throws IOException
    {
        Log.d(TAG, "in createImageFile function");
        // Create image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format( new Date());
        //Log.d(TAG, "Time Stamp: " + timeStamp);
        String imageFileName = "JPEG_" + timeStamp + "_" ;
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment. DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", this.getCacheDir());
        //File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        //File image = new File(this.getCacheDir(), imageFileName);
         //Save a file: path for use with ACTION_VIEW intents
        PhotoPath = image.getAbsolutePath();
        return image;
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d(TAG, "in onActivity for Result");
        super.onActivityResult(requestCode, resultCode, data);
        db = new DatabaseHelper(getApplicationContext());
            if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK)
            {
                /*// Save Image To Gallery
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                //File f = new File(PhotoPath);
                File f = new File("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
                Log.d(TAG, "PhotoPath " + PhotoPath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                // Add Image Path To List
                myList.add(PhotoPath);
                // Refresh Gridview Image Thumbnails
                //gridView.invalidateViews();*/

        switch (requestCode) {
                case CAMERA_REQUEST:

                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();

                    // Inserting plant picture
                    db.addPlantPicture(imageInByte, plantID);
                    Intent i = new Intent(ImageArchive.this,
                            ImageArchive.class);
                    startActivity(i);
                    finish();
                    // }
                }

            case PICK_FROM_GALLERY:
            Bundle extras2 = data.getExtras();

            if (extras2 != null) {
                Bitmap yourImage = extras2.getParcelable("data");
                // convert bitmap to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte imageInByte[] = stream.toByteArray();
                // Inserting plant picture
                db.addPlantPicture(imageInByte, plantID);
                Intent i = new Intent(ImageArchive.this, ImageArchive.class);
                startActivity(i);
                finish();
            }

            }

        }
    }
}


