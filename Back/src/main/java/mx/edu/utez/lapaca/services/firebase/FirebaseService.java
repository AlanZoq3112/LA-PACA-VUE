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

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(multipartFile.getOriginalFilename()));

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("static/carsishop-60e8f-firebase-adminsdk-z3qgf-5ac9aefb20.json").getInputStream()
            );

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of("carsishop-60e8f.appspot.com", DIRECTORY_PATH + fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            storage.create(blobInfo, multipartFile.getBytes());
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/carsishop-60e8f.appspot.com/o/%s?alt=media";
            return String.format(downloadUrl, URLEncoder.encode(DIRECTORY_PATH + fileName, java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IOException("Error al subir el archivo a Firebase Storage", e);
        }
    }
    public String uploadFileUser(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(multipartFile.getOriginalFilename()));

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("static/carsishop-60e8f-firebase-adminsdk-z3qgf-5ac9aefb20.json").getInputStream()
            );

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of("carsishop-60e8f.appspot.com", DIRECTORY_PATH_USER + fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            storage.create(blobInfo, multipartFile.getBytes());
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/carsishop-60e8f.appspot.com/o/%s?alt=media";
            return String.format(downloadUrl, URLEncoder.encode(DIRECTORY_PATH + fileName, java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IOException("Error al subir el archivo a Firebase Storage", e);
        }
    }
    public String uploadFileVendedor(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(multipartFile.getOriginalFilename()));

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("static/carsishop-60e8f-firebase-adminsdk-z3qgf-5ac9aefb20.json").getInputStream()
            );

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of("carsishop-60e8f.appspot.com", DIRECTORY_PATH_VENDEDOR + fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
            storage.create(blobInfo, multipartFile.getBytes());
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/carsishop-60e8f.appspot.com/o/%s?alt=media";
            return String.format(downloadUrl, URLEncoder.encode(DIRECTORY_PATH + fileName, java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IOException("Error al subir el archivo a Firebase Storage", e);
        }
    }



    private String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
