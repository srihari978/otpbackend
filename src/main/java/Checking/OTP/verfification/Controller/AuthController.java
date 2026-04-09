
package Checking.OTP.verfification.Controller;

import Checking.OTP.verfification.Service.AuthService;
import Checking.OTP.verfification.dto.LoginDto;
import Checking.OTP.verfification.dto.RegisterDto;
import Checking.OTP.verfification.dto.Restdto;
import Checking.OTP.verfification.dto.Userdto;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth") // ✅ correct way

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto) {
        return authService.login(dto);
    }


    @PostMapping("/forgot-password")
    public String forgot(@RequestBody Userdto userdto) {
        System.out.println(userdto);
        return authService.forgotPassword(userdto.getEmail());
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Restdto restdto) {
        return authService.resetPassword(
                restdto.getEmail(),
                restdto.getOtp(),
                restdto.getNewPassword()
        );
    }
}