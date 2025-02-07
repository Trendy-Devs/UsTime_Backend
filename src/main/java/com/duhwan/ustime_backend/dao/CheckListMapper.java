package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.Couple.CheckListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckListMapper {

    void insertChecklistItem(Long userId, Long coupleId, String category, String title);

    List<CheckListDto> getChecklistByCouple(Long coupleId);

    void updateChecklistItemStatus(Long checklistId, boolean isChecked);

    void deleteChecklistItem(Long checklistId);

}
