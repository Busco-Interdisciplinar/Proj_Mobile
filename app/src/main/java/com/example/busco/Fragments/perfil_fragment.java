    package com.example.busco.Fragments;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.SharedPreferences;
    import android.graphics.BitmapFactory;
    import android.os.Bundle;
    import android.util.Base64;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.ImageView;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AlertDialog;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.viewmodel.CreationExtras;
    import android.Manifest;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.graphics.Bitmap;
    import android.net.Uri;
    import android.provider.MediaStore;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;
    import static android.app.Activity.RESULT_OK;

    import com.example.busco.Api.ApiResponse;
    import com.example.busco.Api.ApiService;
    import com.example.busco.Api.Models.Base64Image;
    import com.example.busco.Api.Models.Usuarios;
    import com.example.busco.Cadastros.Cadastro_Fornecedor.CadastroFornecedor;
    import com.example.busco.Cadastros.Cadastro_Instituicao.CadastroInstituicao;
    import com.example.busco.Doacao.Doacao;
    import com.example.busco.Localizacao;
    import com.example.busco.MainActivity;
    import com.example.busco.R;
    import com.example.busco.SQLite.UsuarioDAO;
    import com.example.busco.SobreNos;
    import com.google.gson.Gson;

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.util.List;
    import java.util.Objects;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class perfil_fragment extends Fragment {
        private static final int PICK_IMAGE = 1;
        private static final int CAMERA_PERMISSION_CODE = 100;
        private static final int CAMERA_REQUEST_CODE = 101;
        private ImageView profileImageView;
        private TextView sair;
        private TextView doacao;
        private TextView fornecedor;
        private TextView instituicao;
        private TextView editarPerfil;
        private TextView local;
        private TextView sobre;
        Gson gson = new Gson();

        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.perfil_fragment, container, false);

            TextView nomeText = view.findViewById(R.id.nomeUsuario);
            TextView emailText = view.findViewById(R.id.emailUsuario);

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("user", "");
            Usuarios usuario = gson.fromJson(json, Usuarios.class);
            nomeText.setText(usuario.getNome());
            emailText.setText(usuario.getEmail());

            profileImageView = view.findViewById(R.id.foto);
            sair = view.findViewById(R.id.exit);
            doacao = view.findViewById(R.id.fazerDoacao);
            fornecedor = view.findViewById(R.id.tornarFornecedor);
            instituicao = view.findViewById(R.id.tornarInstituicao);
            editarPerfil = view.findViewById(R.id.editPerfil);
            local = view.findViewById(R.id.localizacao);
            sobre = view.findViewById(R.id.sobre);

            String base64Image = usuario.getFoto();
            if (base64Image  != null){
                byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                profileImageView.setImageBitmap(bitmap);
            }


            doacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fazerDoacao();
                }
            });

            sobre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sobreNos();
                }
            });

            sair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sairDoApp();
                }
            });

            fornecedor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tornarFornecedor();
                }
            });

            instituicao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tornarInstituicao();
                }
            });
            
            editarPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPerfil();
                }
            });

            local.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(requireContext(), Localizacao.class);
                    startActivity(intent);
                }
            });

            profileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageSourceDialog();
                }
            });
            return view;
        }

        private void sobreNos() {
            Intent intent = new Intent(requireContext(), SobreNos.class);
            startActivity(intent);
        }

        private void editPerfil() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.editar_perfil, null);
            builder.setView(dialogView);

            EditText nomeEditText = dialogView.findViewById(R.id.editNome);
            EditText emailEditText = dialogView.findViewById(R.id.editEmail);

            builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String novoNome = nomeEditText.getText().toString();
                    String novoEmail = emailEditText.getText().toString();

                    if (nomeValido(novoNome) && emailValido(novoEmail)) {
                        //lógica de API
                        Toast.makeText(requireContext(), "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Nome ou email inválido. Certifique-se de que o nome contenha apenas letras e o email contenha '@gmail.com'.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();
        }

        private boolean nomeValido(String name) {
            return name.matches("^[a-zA-Z]+$");
        }

        private boolean emailValido(String email) {
            return email.endsWith("@gmail.com");
        }


        private void tornarInstituicao() {
            Intent intent = new Intent(requireContext(), CadastroInstituicao.class);
            startActivity(intent);
        }

        private void tornarFornecedor() {
            Intent intent = new Intent(requireContext(), CadastroFornecedor.class);
            startActivity(intent);
        }

        private void fazerDoacao() {
            Intent intent = new Intent(requireContext(), Doacao.class);
            startActivity(intent);
        }

        private void sairDoApp() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Confirmação");
            builder.setMessage("Tem certeza de que deseja sair do aplicativo?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(requireContext(), MainActivity.class);
                    startActivity(intent);
                    UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity());
                    usuarioDAO.remover();
                    getActivity().finish();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private void showImageSourceDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Foto do perfil");
            builder.setItems(new CharSequence[]{"Galeria", "Câmera"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, PICK_IMAGE);
                            break;
                        case 1:
                            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                requestCameraPermission();
                            } else {
                                openCamera();
                            }
                            break;
                    }
                }
            });
            builder.show();
        }

        private void requestCameraPermission() {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == CAMERA_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(requireContext(), "A permissão da câmera é necessária para tirar fotos.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == PICK_IMAGE) {
                    Uri selectedImageUri = data.getData();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        byte[] imageBytes = baos.toByteArray();
                        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        Gson gson = new Gson();
                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        String json = sharedPreferences.getString("user", "");
                        Usuarios usuario = gson.fromJson(json, Usuarios.class);

                        Base64Image base64Image1 = new Base64Image(usuario.getId(), base64Image);

                        ApiService.getInstance().atualizarFoto(base64Image1).enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                                if (response.isSuccessful()){
                                    if (response.body() != null && response.body().isResponseSucessfull()){
                                        List<Object> usuarioObject = response.body().getObject();
                                        String objetoJson = gson.toJson(usuarioObject.get(0));
                                        Usuarios usuarioAlterado = gson.fromJson(objetoJson, Usuarios.class);

                                        String usuarioJson = gson.toJson(usuarioAlterado);

                                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user", usuarioJson);
                                        editor.apply();

                                        UsuarioDAO usuarioDAO = new UsuarioDAO(getContext());
                                        usuarioDAO.atualizarFoto(usuarioAlterado.getId(), base64Image);

                                        Toast.makeText(getContext(), "Foto de perfil atualizada com Sucesso", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                        profileImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == CAMERA_REQUEST_CODE) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        byte[] imageBytes = baos.toByteArray();
                        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        Gson gson = new Gson();
                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        String json = sharedPreferences.getString("user", "");
                        Usuarios usuario = gson.fromJson(json, Usuarios.class);

                        Base64Image base64Image1 = new Base64Image(usuario.getId(), base64Image);

                        ApiService.getInstance().atualizarFoto(base64Image1).enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                                if (response.isSuccessful()){
                                    if (response.body() != null && response.body().isResponseSucessfull()){
                                        List<Object> usuarioObject = response.body().getObject();
                                        String objetoJson = gson.toJson(usuarioObject.get(0));
                                        Usuarios usuarioAlterado = gson.fromJson(objetoJson, Usuarios.class);

                                        String usuarioJson = gson.toJson(usuarioAlterado);

                                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user", usuarioJson);
                                        editor.apply();

                                        UsuarioDAO usuarioDAO = new UsuarioDAO(getContext());
                                        usuarioDAO.atualizarFoto(usuarioAlterado.getId(), base64Image);

                                        Toast.makeText(getContext(), "Foto de perfil atualizada com Sucesso", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        profileImageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

        private void openCamera() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }

        @NonNull
        @Override
        public CreationExtras getDefaultViewModelCreationExtras() {
            return super.getDefaultViewModelCreationExtras();
        }
    }