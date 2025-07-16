package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.dto.auth.ProfileViewDto;

public interface ProfileService {
    ProfileViewDto getProfileDataByUserId(Long userId);

    void updateNickname(Long userId, String newNickname); // ✅ 닉네임 수정
    boolean isNicknameDuplicated(String nickname);        // ✅ 중복 체크
}