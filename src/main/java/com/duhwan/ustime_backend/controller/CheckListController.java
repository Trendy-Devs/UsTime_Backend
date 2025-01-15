package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.CheckListDto;
import com.duhwan.ustime_backend.service.CheckListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("check")
@RequiredArgsConstructor
@Tag(name="체크리스트 API", description = "체크리스트 관련 기능을 제공하는 API")
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping("/add")
    @Operation(summary = "체크리스트 항목 추가")
    public ResponseEntity<String> addChecklistItem(@RequestParam Long userId,
                                                   @RequestParam Long coupleId,
                                                   @RequestParam String category,
                                                   @RequestParam String title) {
        checkListService.addChecklistItem(userId, coupleId, category, title);
        return ResponseEntity.ok("체크리스트 항목이 추가되었습니다.");
    }

    @GetMapping("/{coupleId}")
    @Operation(summary = "체크리스트 보기")
    public ResponseEntity<List<CheckListDto>> getChecklistByCouple(@PathVariable Long coupleId) {
        List<CheckListDto> checkList = checkListService.getChecklistByCouple(coupleId);
        return ResponseEntity.ok(checkList);
    }

    @PutMapping("/update/{checklistId}")
    @Operation(summary = "체크 업데이트")
    public ResponseEntity<String> updateChecklistItemStatus(@PathVariable Long checklistId,
                                                            @RequestParam boolean isChecked) {
        checkListService.updateChecklistItemStatus(checklistId, isChecked);
        return ResponseEntity.ok("체크리스트 상태가 업데이트되었습니다.");
    }

    @DeleteMapping("/delete/{checklistId}")
    @Operation(summary = "체크리스트 삭제")
    public ResponseEntity<String> deleteChecklistItem(@PathVariable Long checklistId) {
        checkListService.deleteChecklistItem(checklistId);
        return ResponseEntity.ok("체크리스트 항목이 삭제되었습니다.");
    }
}
