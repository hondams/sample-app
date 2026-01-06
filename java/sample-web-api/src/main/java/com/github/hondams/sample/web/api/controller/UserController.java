package com.github.hondams.sample.web.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    @Override
    public ResponseEntity<Void> createUser(com.github.hondams.sample.web.api.model.User user) {
        // TODO: 実装
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<com.github.hondams.sample.web.api.model.User> getUserById(
            Integer userId) {
        // TODO: 実装（サンプルのダミー応答）
        com.github.hondams.sample.web.api.model.User u =
                new com.github.hondams.sample.web.api.model.User();
        u.setId(userId != null ? userId.longValue() : null);
        u.setName("山田 太郎");
        u.setEmail("tarou.yamada@example.com");
        return ResponseEntity.ok(u);
    }

    @Override
    public ResponseEntity<java.util.List<com.github.hondams.sample.web.api.model.User>> listUsers() {
        // TODO: 実装（サンプルのダミー応答）
        com.github.hondams.sample.web.api.model.User u =
                new com.github.hondams.sample.web.api.model.User();
        u.setId(1L);
        u.setName("山田 太郎");
        u.setEmail("tarou.yamada@example.com");
        java.util.List<com.github.hondams.sample.web.api.model.User> list =
                new java.util.ArrayList<>();
        list.add(u);
        return ResponseEntity.ok(list);
    }

}
