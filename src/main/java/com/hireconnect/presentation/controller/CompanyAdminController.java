package com.hireconnect.presentation.controller;

import com.hireconnect.core.service.CompanyAdminService;
import com.hireconnect.core.utils.UUIDUtils;
import com.hireconnect.presentation.dto.companyAdmin.CreateCompanyAdmin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company/{companyId}/admins")
public class CompanyAdminController {
    private final CompanyAdminService companyAdminService;

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<?> grantAdmin(@PathVariable String companyId, @RequestBody @Valid CreateCompanyAdmin payload) {
        UUID userUUID = UUIDUtils.fromString(payload.getUserId());
        UUID companyUUID = UUIDUtils.fromString(companyId);

        this.companyAdminService.grantAdminPermission(companyUUID, userUUID);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Admin permission granted successfully.");
        response.put("companyId", companyUUID);
        response.put("userId", userUUID);

        return ResponseEntity.ok(response);
    }
}
