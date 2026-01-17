package com.github.hondams.common.entitty;

import com.github.hondams.common.value.Gender;
import com.github.hondams.fw.entity.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Entity<String> {

    private String userId;
    private String userName;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String postalCd; // 郵便番号（例: 123-4567）
    private String prefecture; // 都道府県
    private String address;// 市区町村＋大字・丁目＋番地
    private String room;// 建物名・部屋番号
    private LocalDate birthDate;
    private String createdAt;
    private String updatedAt;
    private LocalDateTime createdBy;
    private LocalDateTime updatedBy;
    private int version;
    private List<UserGroup> userGroups = new ArrayList<>();
    private List<String> roles = new ArrayList<>();

    @Override
    public String getId() {
        return this.userId;
    }
}
