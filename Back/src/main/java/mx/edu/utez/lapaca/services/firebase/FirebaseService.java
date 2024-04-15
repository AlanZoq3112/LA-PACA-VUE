package mx.edu.utez.lapaca.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class FirebaseService {
    private static final String DIRECTORY_PATH = "images-products/";
    private static final String DIRECTORY_PATH_USER = "images-users/";
    private static final String DIRECTORY_PATH_VENDEDOR = "images-vendedores/";
    private static final String ERROR_SUBIR_ARCHIVO_FIREBASE_STORAGE = "Error al subir el archivo a Firebase Storage";
    private static final String FIREBASE_STORAGE_BASE_URL = "https://firebasestorage.googleapis.com/v0/b/carsishop-60e8f.appspot.com/o/%s?alt=media";
    private static final String BUCKET_NAME = "carsishop-60e8f.appspot.com";


    public String uploadFile(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(multipartFile.getOriginalFilename()));

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("static/carsishop-60e8f-firebase-adminsdk-z3qgf-5ac9aefb20.json").getInputStream()
            );

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of(BUCKET_NAME, DIRECTORY_PATH + fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            storage.create(blobInfo, multipartFile.getBytes());
            String downloadUrl = FIREBASE_STORAGE_BASE_URL;
            return String.format(downloadUrl, URLEncoder.encode(DIRECTORY_PATH + fileName, java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IOException(ERROR_SUBIR_ARCHIVO_FIREBASE_STORAGE, e);
        }
    }
    public String uploadFileUser(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(multipartFile.getOriginalFilename()));

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("static/carsishop-60e8f-firebase-adminsdk-z3qgf-5ac9aefb20.json").getInputStream()
            );

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of(BUCKET_NAME, DIRECTORY_PATH_USER + fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            storage.create(blobInfo, multipartFile.getBytes());
            String downloadUrl = FIREBASE_STORAGE_BASE_URL;
            return String.format(downloadUrl, URLEncoder.encode(DIRECTORY_PATH + fileName, java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IOException(ERROR_SUBIR_ARCHIVO_FIREBASE_STORAGE, e);
        }
    }
    public String uploadFileVendedor(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(multipartFile.getOriginalFilename()));

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("static/carsishop-60e8f-firebase-adminsdk-z3qgf-5ac9aefb20.json").getInputStream()
            );

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of(BUCKET_NAME, DIRECTORY_PATH_VENDEDOR + fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            storage.create(blobInfo, multipartFile.getBytes());
            String downloadUrl = FIREBASE_STORAGE_BASE_URL;
            return String.format(downloadUrl, URLEncoder.encode(DIRECTORY_PATH + fileName, java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IOException(ERROR_SUBIR_ARCHIVO_FIREBASE_STORAGE, e);
        }
    }



    private String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
