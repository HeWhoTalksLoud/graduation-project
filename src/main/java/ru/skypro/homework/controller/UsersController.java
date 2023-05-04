package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.UsersService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Пользователи")
public class UsersController {

    private final UsersService usersService;

    @Operation(
            summary = "Обновление пароля", tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NewPasswordDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDTO newPasswordDTO,
                                         Authentication authentication) {
        UserDTO dto = usersService.getAuthorisedUser(authentication);
        usersService.setPassword(dto.getId(), newPasswordDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить информацию об авторизованном пользователе", tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getAuthorisedUserInfo(Authentication authentication) {
        UserDTO dto = usersService.getAuthorisedUser(authentication);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDTO> setAuthorisedUserInfo(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(usersService.updateUser(userDTO));
    }

    @Operation(
            summary = "Обновить информацию об авторизованном пользователе", tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content), //где получить?
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PatchMapping(value = "/me/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> setAuthorisedUserAvatar(@RequestPart("image") MultipartFile image,
                                                     Authentication authentication) {
        UserDTO dto = usersService.getAuthorisedUser(authentication);
        usersService.setAvatar(dto.getId(), image);
        return ResponseEntity.ok().build();
    }
}
