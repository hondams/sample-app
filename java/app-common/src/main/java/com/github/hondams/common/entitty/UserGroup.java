package com.github.hondams.common.entitty;

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
public class UserGroup {

    private String groupId;
    private String groupName;
    private List<String> roles = new ArrayList<>();
}
