package com.ssafy.forestkeeper.application.service.plogging;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingExperienceResponseDTO;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepository;
import com.ssafy.forestkeeper.exception.PloggingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PloggingAiServiceImpl implements PloggingAiService {
    private final PloggingRepository ploggingRepository;

    @Override
    public void detectObject(MultipartFile multipartFile, String ploggingId) throws IOException {

        int score = 0;

        List<AnnotateImageRequest> requests = new ArrayList<>();

        byte[] byteArr = multipartFile.getBytes();

        InputStream inputStream = new ByteArrayInputStream(byteArr);

        ByteString imgBytes = ByteString.readFrom(inputStream);

        Image img = Image.newBuilder()
                .setContent(imgBytes)
                .build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION))
                .setImage(img)
                .build();

        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);

            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
                    int tmpScore = 0;

                    if (entity.getName().equals("Bottle")) tmpScore = 100;
                    else if (entity.getName().equals("Packaged goods")) tmpScore = 80;
                    else tmpScore = 40;

                    tmpScore *= entity.getScore();

                    score += tmpScore;
                }
            }
        }

        Plogging plogging = ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException("????????? ????????? ???????????? ????????????."));

        plogging.changeExp(score);

        ploggingRepository.save(plogging);

    }

    @Override
    public PloggingExperienceResponseDTO detectLabels(MultipartFile multipartFile, String ploggingId) throws IOException {

        int exp = 0;

        HashMap<String, Integer> experience = new HashMap<>();

        experience.put("Plastic bag", 60);
        experience.put("Litter", 60);
        experience.put("Plastic", 60);
        experience.put("Packing materials", 60);
        experience.put("Waste container", 50);
        experience.put("Waste", 50);
        experience.put("Bottle", 50);
        experience.put("Tin can", 50);
        experience.put("Box", 50);
        experience.put("Glass bottle", 50);
        experience.put("Beverage can", 40);
        experience.put("Aluminum can", 40);
        experience.put("Plastic bottle", 40);
        experience.put("Water bottle", 40);
        experience.put("Plastic wrap", 40);

        HashMap<String, String> translate = new HashMap<>();

        translate.put("Plastic bag", "????????????");
        translate.put("Litter", "?????????");
        translate.put("Plastic", "????????????");
        translate.put("Packing materials", "?????????");
        translate.put("Waste container", "????????? ??????");
        translate.put("Waste", "?????????");
        translate.put("Bottle", "???");
        translate.put("Tin can", "??????");
        translate.put("Beverage can", "????????? ???");
        translate.put("Aluminum can", "???????????? ???");
        translate.put("Box", "??????");
        translate.put("Glass bottle", "?????????");
        translate.put("Plastic bottle", "???????????? ???");
        translate.put("Water bottle", "??????");
        translate.put("Plastic wrap", "?????????");

        List<AnnotateImageRequest> requests = new ArrayList<>();

        byte[] byteArr = multipartFile.getBytes();

        InputStream inputStream = new ByteArrayInputStream(byteArr);

        ByteString imgBytes = ByteString.readFrom(inputStream);

        Image img = Image.newBuilder()
                .setContent(imgBytes)
                .build();

        Feature feat = Feature.newBuilder()
                .setType(Feature.Type.LABEL_DETECTION)
                .build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();

        requests.add(request);

        List<String> type = new ArrayList<>();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);

            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    return PloggingExperienceResponseDTO.builder()
                            .exp(0)
                            .build();
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    if (!experience.containsKey(annotation.getDescription())) continue;

                    type.add(translate.get(annotation.getDescription()));

                    int tmpScore = experience.get(annotation.getDescription());

                    tmpScore *= annotation.getScore();

                    exp += tmpScore;
                }
            }
        }

        if (exp < 100) exp = 100;

        Plogging plogging = ploggingRepository.findById(ploggingId)
                .orElseThrow(() -> new PloggingNotFoundException("????????? ????????? ???????????? ????????????."));

        plogging.changeExp(exp);

        ploggingRepository.save(plogging);

        return PloggingExperienceResponseDTO.builder().exp(exp).type(type).build();

    }

}