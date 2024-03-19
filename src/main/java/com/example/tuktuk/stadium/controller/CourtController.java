package com.example.tuktuk.stadium.controller;

import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtCreateRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtImageDeleteRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtImageUpdateRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtUpdateRequestDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.court.*;
import com.example.tuktuk.stadium.service.CourtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
@Slf4j
public class CourtController {

    private final CourtService courtService;

    @GetMapping("/{courtId}")
    public CourtReadResponseDto getCourtById(@PathVariable(name = "courtId") long courtId) {
        return courtService.findByCourtId(courtId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CourtCreateResponseDto createCourt(
            @RequestPart("courtCreateRequest") CourtCreateRequestDto request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        return courtService.saveCourt(request, images);
    }

    @PatchMapping(value = "/{courtId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CourtUpdateResponseDto updateCourtInfo(
            @PathVariable("courtId") long courtId,
            @RequestBody CourtUpdateRequestDto request
    ) {
        return courtService.updateCourtInfo(courtId, request);
    }

    @PatchMapping(value = "/{courtId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CourtImageUpdateResponseDto updateCourtImages(
            @PathVariable("courtId") long courtId,
            @RequestPart(value = "courtImageUpdateRequest") CourtImageUpdateRequestDto request,
            @RequestPart(value = "updateImages") List<MultipartFile> images
    ) {

        return courtService.updateCourtImages(courtId, request, images);
    }

    @DeleteMapping(value = "/{courtId}/images", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CourtImageDeleteResponseDto deleteCourtImages(@PathVariable(name = "courtId") long courtId,
                                                         @RequestBody CourtImageDeleteRequestDto request
    ) {
        return courtService.deleteCourtImage(courtId, request);
    }

    @DeleteMapping(value = "/{courtId}")
    public CourtDeleteResponseDto deleteCourt(@PathVariable(name = "courtId") long courtId) {
        return courtService.deleteCourt(courtId);
    }
}
