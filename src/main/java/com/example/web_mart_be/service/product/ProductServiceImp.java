package com.example.web_mart_be.service.product;

import com.example.web_mart_be.dao.ImageRepository;
import com.example.web_mart_be.dao.ProductRepository;
import com.example.web_mart_be.dao.ProductTypeRepository;
import com.example.web_mart_be.entity.Image;
import com.example.web_mart_be.entity.Product;
import com.example.web_mart_be.entity.ProductType;
import com.example.web_mart_be.service.uploadimage.UploadImageService;
import com.example.web_mart_be.service.util.Base64ToMultipartFileConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    private final ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired

    private UploadImageService uploadImageService;
    public ProductServiceImp (ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }
    @Override
    @Transactional
    public ResponseEntity<?> save(JsonNode productJson) {
        try {
            Product product = objectMapper.treeToValue(productJson, Product.class);

            // Lưu thể loại của sách
            List<Integer> idProductTypeList = objectMapper.readValue(productJson.get("idProductType").traverse(), new TypeReference<List<Integer>>() {
            });
            List<ProductType> productTypeList = new ArrayList<>();
            for (int idProductType : idProductTypeList) {
                Optional<ProductType> productType = productTypeRepository.findById(idProductType);
                productTypeList.add(productType.get());
            }
            product.setListProductTypes(productTypeList);

            // Lưu trước để lấy id sách đặt tên cho ảnh
            Product newProduct = productRepository.save(product);

            // Lưu thumbnail cho ảnh
            String dataThumbnail = formatStringByJson(String.valueOf((productJson.get("thumbnail"))));

            Image thumbnail = new Image();
            thumbnail.setProduct(newProduct);
//            thumbnail.setDataImage(dataThumbnail);
            thumbnail.setIcon(true);
            MultipartFile multipartFile = Base64ToMultipartFileConverter.convert(dataThumbnail);
            String thumbnailUrl = uploadImageService.uploadImage(multipartFile, "Product_" + newProduct.getIdProduct());
            thumbnail.setUrlImage(thumbnailUrl);

            List<Image> imagesList = new ArrayList<>();
            imagesList.add(thumbnail);


            // Lưu những ảnh có liên quan
            String dataRelatedImg = formatStringByJson(String.valueOf((productJson.get("relatedImg"))));
            List<String> arrDataRelatedImg = objectMapper.readValue(productJson.get("relatedImg").traverse(), new TypeReference<List<String>>() {
            });

            for (int i = 0; i < arrDataRelatedImg.size(); i++) {
                String img = arrDataRelatedImg.get(i);
                Image image = new Image();
                image.setProduct(newProduct);
//                image.setDataImage(img);
                image.setIcon(false);
                MultipartFile relatedImgFile = Base64ToMultipartFileConverter.convert(img);
                String imgURL = uploadImageService.uploadImage(relatedImgFile, "Product_" + newProduct.getIdProduct() + "." + i);
                image.setUrlImage(imgURL);
                imagesList.add(image);
            }

            newProduct.setListImages(imagesList);
            // Cập nhật lại ảnh
            productRepository.save(newProduct);

            return ResponseEntity.ok("Success!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<?> update(JsonNode productJson) {
        try {
           Product product = objectMapper.treeToValue(productJson, Product.class);
            List<Image> imagesList = imageRepository.findImagesByProduct(product);

            // Lưu thể loại của sách
            List<Integer> idProductTypeList = objectMapper.readValue(productJson.get("idProductType").traverse(), new TypeReference<List<Integer>>() {
            });
            List<ProductType> productTypeList = new ArrayList<>();
            for (int idProductType : idProductTypeList) {
                Optional<ProductType> productType = productTypeRepository.findById(idProductType);
                productTypeList.add(productType.get());
            }
           product.setListProductTypes(productTypeList);

            // Kiểm tra xem thumbnail có thay đổi không
            String dataThumbnail = formatStringByJson(String.valueOf((productJson.get("icon"))));
            if (Base64ToMultipartFileConverter.isBase64(dataThumbnail)) {
                for (Image image : imagesList) {
                    if (image.isIcon()) {
//                        image.setDataImage(dataThumbnail);
                        MultipartFile multipartFile = Base64ToMultipartFileConverter.convert(dataThumbnail);
                        String thumbnailUrl = uploadImageService.uploadImage(multipartFile, "Product_" + product.getIdProduct());
                        image.setUrlImage(thumbnailUrl);
                        imageRepository.save(image);
                        break;
                    }
                }
            }

           Product newProduct = productRepository.save(product);

            // Kiểm tra ảnh có liên quan
            List<String> arrDataRelatedImg = objectMapper.readValue(productJson.get("relatedImg").traverse(), new TypeReference<List<String>>() {});

            // Xem có xoá tất ở bên FE không
            boolean isCheckDelete = true;

            for (String img : arrDataRelatedImg) {
                if (!Base64ToMultipartFileConverter.isBase64(img)) {
                    isCheckDelete = false;
                }
            }

            // Nếu xoá hết tất cả
            if (isCheckDelete) {
                imageRepository.deleteImagesWithFalseIconByProductId(newProduct.getIdProduct());
                Image thumbnailTemp = imagesList.get(0);
                imagesList.clear();
                imagesList.add(thumbnailTemp);
                for (int i = 0; i < arrDataRelatedImg.size(); i++) {
                    String img = arrDataRelatedImg.get(i);
                    Image image = new Image();
                    image.setProduct(newProduct);
//                    image.setDataImage(img);
                    image.setIcon(false);
                    MultipartFile relatedImgFile = Base64ToMultipartFileConverter.convert(img);
                    String imgURL = uploadImageService.uploadImage(relatedImgFile, "Product_" + newProduct.getIdProduct() + "." + i);
                    image.setUrlImage(imgURL);
                    imagesList.add(image);
                }
            } else {
                // Nếu không xoá hết tất cả (Giữ nguyên ảnh hoặc thêm ảnh vào)
                for (int i = 0; i < arrDataRelatedImg.size(); i++) {
                    String img = arrDataRelatedImg.get(i);
                    if (Base64ToMultipartFileConverter.isBase64(img)) {
                        Image image = new Image();
                        image.setProduct(newProduct);
//                        image.setDataImage(img);
                        image.setIcon(false);
                        MultipartFile relatedImgFile = Base64ToMultipartFileConverter.convert(img);
                        String imgURL = uploadImageService.uploadImage(relatedImgFile, "Product_" + newProduct.getIdProduct() + "." + i);
                        image.setUrlImage(imgURL);
                        imageRepository.save(image);
                    }
                }
            }

            newProduct.setListImages(imagesList);
            // Cập nhật lại ảnh
            productRepository.save(newProduct);

            return ResponseEntity.ok("Success!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public long getTotalProduct() {
        return productRepository.count();
    }
    private String formatStringByJson(String json) {
        return json.replaceAll("\"", "");
    }
}
