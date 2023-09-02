package com.example.shoeapplication.fragments.admin;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.adapters.ProductAdapter;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentAdminHomeBinding;
import com.example.shoeapplication.helpers.RealPathUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeFragment extends Fragment {


    private static final int MY_REQUEST_CODE = 10;

    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAdminHomeBinding binding;
    ProductAdapter productAdapter;
    Uri mUri;
    ImageView imgAvt;
    List<Shoe> shoes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false);

        binding.btnNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCreateNewProduct();
            }
        });
        ApiController.apiService.getShoes().enqueue(new Callback<List<Shoe>>() {
            @Override
            public void onResponse(Call<List<Shoe>> call, Response<List<Shoe>> response) {
                if (response.isSuccessful()) {
                    shoes = response.body();
                    productAdapter = new ProductAdapter(shoes, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    binding.revProduct.setLayoutManager(layoutManager);
                    binding.revProduct.setAdapter(productAdapter);

                    productAdapter.setiOnClickProduct(new IOnClickProduct() {
                        @Override
                        public void iOnClickEdit(Shoe shoe, int position) {

                        }

                        @Override
                        public void iOnClickDelete(Shoe shoe, int position) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Shoe>> call, Throwable t) {

            }
        });

        return binding.getRoot();
    }

    private void openDialogCreateNewProduct() {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_create_shoe);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER_VERTICAL;
        window.setAttributes(windowAtributes);

        dialog.setCancelable(true);

        EditText edtName, edtDes, edtPrice, edtNewPrice, edtSizes;
        TextView tvCategory;


        Button btnCreate;

        edtName = dialog.findViewById(R.id.edtName);
        edtDes = dialog.findViewById(R.id.edtDes);
        edtPrice = dialog.findViewById(R.id.edtPrice);
        edtNewPrice = dialog.findViewById(R.id.edtNewPrice);
        edtSizes = dialog.findViewById(R.id.edtSizes);
        tvCategory = dialog.findViewById(R.id.tvCategory);

        final String[] category = {"Sneaker"};
        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] categories = {"Main", "Soccer", "Sneaker", "Basketball", "Oxford", "Loafer"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Categories")
                        .setItems(categories, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                category[0] = categories[which];
                                tvCategory.setText(category[0]);
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        imgAvt = dialog.findViewById(R.id.imgAvt);

        btnCreate = dialog.findViewById(R.id.btnCreate);

        imgAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String des = edtDes.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                String newPrice = edtNewPrice.getText().toString().trim();
                String sizes = edtSizes.getText().toString().trim();

                //check name > 6 kys tuwj
                //call api
//                        Shoe shoe = new Shoe(name, des, Long.parseLong(price));
                RequestBody bodyName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
                RequestBody bodyDes = RequestBody.create(MediaType.parse("multipart/form-data"), des);
                RequestBody bodyPrice = RequestBody.create(MediaType.parse("multipart/form-data"), price);
                RequestBody bodyNewPrice = RequestBody.create(MediaType.parse("multipart/form-data"), newPrice);
                RequestBody bodySizes = RequestBody.create(MediaType.parse("multipart/form-data"), sizes);
                RequestBody bodyCategory = RequestBody.create(MediaType.parse("multipart/form-data"), category[0]);

                String realPath = RealPathUtil.getRealPath(getActivity(), mUri);
                File file = new File(realPath);

                RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData("img", file.getName(), requestBodyAvt);


                ApiController.apiService.createShoe(bodyName, bodyDes, bodyPrice, bodyNewPrice, bodySizes, bodyCategory, multipartBodyAvt).enqueue(new Callback<Shoe>() {
                    @Override
                    public void onResponse(Call<Shoe> call, Response<Shoe> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
//                                    callApiGetShoes();
                            shoes.add(0, response.body());
                            productAdapter.setShoes(shoes);
                            Toast.makeText(getActivity(), "Create New Shoe Success", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                err = jsonObject.getString("error");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + err, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Shoe> call, Throwable t) {
                        //lỗi không call được api
                        Toast.makeText(getActivity(), "Lỗi đường truyền", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    public void onClickRequestPermission() {
        openGallary();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
//            openGallary();
//            return;
//        }
//        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            openGallary();
//        }else {
//            String [] requestPermission= {Manifest.permission.READ_EXTERNAL_STORAGE};
//            getActivity().requestPermissions(requestPermission, MY_REQUEST_CODE);
//        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode==MY_REQUEST_CODE){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                openGallary();
//            }
//        }
//
//    }


    private void openGallary() {
        Log.d("cr", "onClick: " + "openGallary");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            imgAvt.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
}