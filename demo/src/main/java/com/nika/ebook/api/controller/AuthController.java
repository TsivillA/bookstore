package com.nika.ebook.api.controller;

import com.nika.ebook.api.constants.SwaggerConstant;
import com.nika.ebook.domain.MessageResponse;
import com.nika.ebook.domain.jwt.JwtResponse;
import com.nika.ebook.domain.user.UserLoginRequest;
import com.nika.ebook.domain.user.UserRegisterRequest;
import com.nika.ebook.persistance.service.AuthService;
import com.nika.ebook.persistance.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.nika.ebook.api.constants.AllowedOrigins.ALLOWED_ORIGIN;
import static com.nika.ebook.api.constants.ResponseConstants.*;
import static com.nika.ebook.api.constants.RouteConstants.*;

@RestController
@RequestMapping(AUTH_API)
@CrossOrigin(origins = ALLOWED_ORIGIN, maxAge = 3600)
@RequiredArgsConstructor
@Api(tags = {SwaggerConstant.AUTH_TAG})
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @ApiOperation(value = "Authenticate User", notes = "Authenticate user and generate jwt token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User has been successfully authenticated"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @PostMapping(SIGN_IN)
    public ResponseEntity<?> authUser(@RequestBody UserLoginRequest loginRequest){
        JwtResponse response = authService.loginUser(loginRequest);
        if(Objects.isNull(response)){
            return ResponseEntity.internalServerError().body("Could not authenticate user");
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Register User", notes = "Register user and save to database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User has been successfully registered"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @PostMapping(REGISTER)
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest signUpRequest) {
        if(userService.existsByUsername(signUpRequest.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username exists!"));
        }
        if(userService.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email exists!"));
        }
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }
}
