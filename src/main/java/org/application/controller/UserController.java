package org.application.controller;

import org.application.dto.UserDto;
import org.application.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public ResponseEntity<UserDto> recupererUtilisateurParId(@PathVariable(name = "userId") Long id) {
        return ResponseEntity.ok(userService.recupererUtilisateurParId(id));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable(name = "userId") Long id) {
        userService.supprimerUtilisateur(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserDto> creerUtilisateur(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.creerUtilisateur(userDto));
    }

}
