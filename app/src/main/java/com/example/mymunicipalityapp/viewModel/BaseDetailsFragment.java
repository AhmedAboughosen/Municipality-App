package com.example.mymunicipalityapp.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mymunicipalityapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.models.sort.SortingTypes;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BaseDetailsFragment extends Fragment implements EasyPermissions.PermissionCallbacks{


    public  @BindView(R.id.selectFile)
    CircleImageView file;
    public @BindView(R.id.selectImage)
    CircleImageView image;
    public   @BindView(R.id.details)
    TextInputEditText desc;
    public  @BindView(R.id.send_request)
    Button send_request;
    public  @BindView(R.id.string_count)
    TextView string_count;
    public  @BindView(R.id.userIDTextInputLayout)
    TextInputLayout textInputLayout;

    protected ArrayList<String> photoPaths = new ArrayList<>();
    protected ArrayList<String> docPaths = new ArrayList<>();
    private  final int CUSTOM_IMAGE_REQUEST_CODE = 532;
    private   final int RC_PHOTO_PICKER_PERM = 123;
    private  final int RC_FILE_PICKER_PERM = 321;
    private final int COUNT = 1;


    protected void initMediaRequest(){
        try {
            image.setOnClickListener(v -> {
                try {
                    onPickPhoto();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            });

            file.setOnClickListener(view -> {
                try {
                    onPickFile();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }


    private void onPickPhoto()
    {
        try {
            String [] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.CAMERA"};

            if (EasyPermissions.hasPermissions(Objects.requireNonNull(getActivity()), permissions)) {
                PickPhoto();
            } else {
                // Ask for one permission
                EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo_picker), RC_PHOTO_PICKER_PERM, permissions);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void PickPhoto(){
        try {
            FilePickerBuilder.getInstance()
                    .setMaxCount(COUNT)
                    .setSelectedFiles(photoPaths)
                    .setActivityTheme(R.style.AppTheme)
                    .setActivityTitle("اختار صورة")
                    .enableVideoPicker(false)
                    .enableCameraSupport(true)
                    .showGifs(false)
                    .showFolderView(true)
                    .enableSelectAll(false)
                    .enableImagePicker(true)
                    .pickPhoto(this, CUSTOM_IMAGE_REQUEST_CODE);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void onPickFile() {
        try {

            if (EasyPermissions.hasPermissions(Objects.requireNonNull(getActivity()), FilePickerConst.PERMISSIONS_FILE_PICKER))
            {
                PickDoc();
            } else {
                // Ask for one permission
                EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo_picker),
                        RC_FILE_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void PickDoc(){
        try {
            String[] pdfs = { ".pdf" };
            FilePickerBuilder.getInstance().addFileSupport("PDF", pdfs)
                    .setMaxCount(COUNT)
                    .setSelectedFiles(docPaths)
                    .setActivityTheme(R.style.AppTheme)
                    .setActivityTitle("اختار الملف")
                    .enableDocSupport(true)
                    .enableSelectAll(false)
                    .sortDocumentsBy(SortingTypes.name)
                    .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .pickFile(this , FilePickerConst.REQUEST_CODE_DOC);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        if(requestCode == RC_PHOTO_PICKER_PERM){
            PickPhoto();
        }

        if(requestCode == RC_FILE_PICKER_PERM){
            PickDoc();
        }

    }


    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        try {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).build().show();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void ActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case CUSTOM_IMAGE_REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        try {
                            photoPaths = new ArrayList<>();
                            photoPaths.addAll(Objects.requireNonNull(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA)));
                            if(photoPaths.size() >= 1){
                                Glide.with(Objects.requireNonNull(getActivity()))
                                        .load(new File(photoPaths.get(0)))
                                        .into(image);
                                image.setPadding(0,0,0,0);
                            }
                            else{
                                image.setImageResource(R.drawable.ic_photo_camera_black_24dp);
                                image.setPadding(12,6,12,4);
                                photoPaths = new ArrayList<>();
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                    break;

                case FilePickerConst.REQUEST_CODE_DOC:
                    try {
                        if (resultCode == Activity.RESULT_OK && data != null) {
                            docPaths = new ArrayList<>();
                            docPaths.addAll(Objects.requireNonNull(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS)));
                            if(docPaths.size() >= 1){
                                String item = docPaths.get(0);


                                if(ispdfFile(item)){
                                    file.setImageResource(R.drawable.ic_document);
                                    file.setPadding(0,0,0,0);
                                }
                                else {
                                    NODOC();
                                    Toast.makeText(getActivity() ,"لم يتم اختيار الملف , يجب يكون الملف من نوع pdf" ,Toast.LENGTH_LONG).show();
                                }

                            }else{
                                NODOC();
                            }
                        }else{
                            NODOC();
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }


    private boolean ispdfFile(String s)
    {
        try {
            StringBuilder extension  = new StringBuilder();
            int  index =  s.indexOf(".")+1;
            for(int i =  index ; i < s.length() ; ++i){
                extension = extension.append(s.charAt(i));
            }
            return extension.toString().equals("pdf");

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    private void NODOC(){
        docPaths = new ArrayList<>();
        file.setImageResource(R.drawable.ic_attach_file_gray);
        file.setPadding(12,6,12,4);
    }
}
