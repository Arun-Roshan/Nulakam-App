package com.example.nulakam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.Key;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FILE = 101;
    ImageView imageView;
    EditText inputImageName;
    TextView textViewProgress;
    ProgressBar progressBar;
    Button btnupload;

    Uri fileUri;
    boolean isFileAdded=false;

    DatabaseReference dataref;
    StorageReference Storageref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageView = findViewById(R.id.bookseachicon);
        inputImageName = findViewById(R.id.inputfile);
        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressbar);
        btnupload = findViewById(R.id.upload);

        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        dataref = FirebaseDatabase.getInstance().getReference().child("Book");
        Storageref = FirebaseStorage.getInstance().getReference().child("BookFile");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_FILE);
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final String filename= inputImageName.getText().toString();
                 if (isFileAdded!=false && filename!=null){
                     uploadfile(filename);
                 }
            }
        });
    }

    private void uploadfile(final String filename) {
        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        final String key=dataref.push().getKey();
        Storageref.child(key+".jpg").putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Storageref.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("BookFile",filename);
                        hashMap.put("FileUri",uri.toString());

                        dataref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SearchActivity.this, "Data Successfullly Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(snapshot.getBytesTransferred()*100/snapshot.getTotalByteCount());
                progressBar.setProgress((int) progress);
                textViewProgress.setText(progress+""+"%");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_FILE && data!=null){
            fileUri = data.getData();
            isFileAdded=true;
            imageView.setImageURI(fileUri);

        }
    }
}