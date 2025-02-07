package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CheckListMapper;
import com.duhwan.ustime_backend.dto.Couple.CheckListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListMapper checkListMapper;

    // 체크리스트 항목 추가
    @Transactional
    public void addChecklistItem(Long userId, Long coupleId, String category, String title) {
        checkListMapper.insertChecklistItem(userId, coupleId, category, title);
    }

    // 체크리스트 보기
    public List<CheckListDto> getChecklistByCouple(Long coupleId) {
        return checkListMapper.getChecklistByCouple(coupleId);
    }

    // 체크상태 업데이트
    @Transactional
    public void updateChecklistItemStatus(Long checklistId, boolean isChecked) {
        checkListMapper.updateChecklistItemStatus(checklistId, isChecked);
    }

    // 체크리스트 삭제
    @Transactional
    public void deleteChecklistItem(Long checklistId) {
        checkListMapper.deleteChecklistItem(checklistId);
    }


}
