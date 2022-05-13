package com.ssafy.forestkeeper.application.service.userinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.forestkeeper.application.dto.response.mountain.MountainUserInfoResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.mountain.MountainUserInfoWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingListResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.plogging.PloggingListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.user.UserPloggingInfoDTO;
import com.ssafy.forestkeeper.domain.dao.mountain.Mountain;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.repository.image.ImageRepository;
import com.ssafy.forestkeeper.domain.repository.mountain.MountainRepository;
import com.ssafy.forestkeeper.domain.repository.plogging.PloggingRepository;
import com.ssafy.forestkeeper.domain.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final PloggingRepository ploggingRepository;

    private final UserRepository userRepository;

	private final MountainRepository mountainRepository;
	
	private final ImageRepository imageRepository;
	
    @Value("${cloud.aws.s3.hosting}")
    public String hosting;
    
	@Override
	public PloggingListWrapperResponseDTO getPloggingList(int page) {
        List<Plogging> ploggingList = ploggingRepository.findByUserIdOrderByStartTimeDesc(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(),false).get().getId(),PageRequest.of(page - 1, 10))
                .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));

    	List<PloggingListResponseDTO> ploggingListResponseDTOList = new ArrayList<>();
		
    	ploggingList.forEach(plogging ->
    		ploggingListResponseDTOList.add(
                        PloggingListResponseDTO.builder()
                        		.date(plogging.getStartTime().toLocalDate().toString())
                        		.ploggingId(plogging.getId())
                        		.distance(plogging.getDistance())
                        		.time(plogging.getDurationTime())
                        		.exp(plogging.getExp())
                        		.mountainName(plogging.getMountain().getName())
//                        		.imagePath(hosting + "plogging/" + imageRepository.findByPloggingId(plogging.getId()).get().getSavedFileName())
                        		.imagePath("")
                                .build()
                )
        );

        return PloggingListWrapperResponseDTO.builder()
                .list(ploggingListResponseDTOList)
                .build();
	}

	@Override
	public MountainUserInfoWrapperResponseDTO getMountainList(int page) {
		Map<String, String> map = new HashMap<>();
        List<Plogging> ploggingList = ploggingRepository.findByUserId(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(),false).get().getId())
                .orElseThrow(() -> new IllegalArgumentException("플로깅 기록을 찾을 수 없습니다."));
        ploggingList.forEach(plogging ->
        	map.put(plogging.getMountain().getCode(), plogging.getMountain().getName())
        );
        
        List<MountainUserInfoResponseDTO> list = new ArrayList<>();
        for(String key : map.keySet()) {
        	list.add(MountainUserInfoResponseDTO.builder().mountainCode(key).mountainName(map.get(key)).build());
        }
        
	    return MountainUserInfoWrapperResponseDTO.builder()
	    										.list(list)
	    										.build();
	}
	
	//유저상세 페이지내 산별 플로깅 목록
	@Override
	public PloggingListWrapperResponseDTO getPloggingInMountain(String mountainCode) {
		Mountain mountain = mountainRepository.findByCode(mountainCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 산을 찾을 수 없습니다."));
        List<Plogging> ploggingList = ploggingRepository.findByUserIdAndMountainIdOrderByStartTimeDesc(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(),false).get().getId(),mountain.getId())
                .orElseThrow(() -> new IllegalArgumentException("플로깅 기록을 찾을 수 없습니다."));

    	List<PloggingListResponseDTO> ploggingListResponseDTOList = new ArrayList<>();

    	ploggingList.forEach(plogging ->
    		ploggingListResponseDTOList.add(
                        PloggingListResponseDTO.builder()
                        		.date(plogging.getStartTime().toLocalDate().toString())
                        		.ploggingId(plogging.getId())
                        		.distance(plogging.getDistance())
                        		.time(plogging.getDurationTime())
                        		.exp(plogging.getExp())
                        		.mountainName(plogging.getMountain().getName())
//                        		.imagePath(hosting + "plogging/" + imageRepository.findByPloggingId(plogging.getId()).get().getSavedFileName())
                        		.imagePath("")
                                .build()
                )
        );

        return PloggingListWrapperResponseDTO.builder()
                .list(ploggingListResponseDTOList)
                .build();
	}
	
	@Override
	public UserPloggingInfoDTO getUserAccumulative() {
        List<Plogging> ploggingList = ploggingRepository.findByUserId(userRepository.findByEmailAndDelete(SecurityContextHolder.getContext().getAuthentication().getName(),false).get().getId())
                .orElseThrow(() -> new IllegalArgumentException("플로깅 기록을 찾을 수 없습니다."));
        double distance = (double)0;
        int exp = 0;
        int minute = 0;
        int hour = 0;
        String[] str;
        for(Plogging plogging : ploggingList) {
        	distance += plogging.getDistance();
        	exp += plogging.getExp();
        	str = plogging.getDurationTime().split(" : ");
        	System.out.println(plogging.getDurationTime());
        	hour += Integer.parseInt(str[0]);
        	minute += Integer.parseInt(str[1]);
        }
        String time = calcDuration(hour, minute);
        
	    return UserPloggingInfoDTO.builder()
	    						.distance(distance)
	    						.time(time)
	    						.exp(exp)
	    						.build();
	}
	
	public String calcDuration(int hour, int minute) {
		hour += minute/60;
		minute = minute%60;
		StringBuilder sb = new StringBuilder();
		sb.append(hour).append(" : ");
		if(minute <10) sb.append(0).append(minute);
		else sb.append(minute);
		
		return sb.toString();
	}
}
