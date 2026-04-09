package Checking.OTP.verfification.Service;

import Checking.OTP.verfification.Entity.User;
import Checking.OTP.verfification.Repository.UserRepository;
import Checking.OTP.verfification.dto.LoginDto;
import Checking.OTP.verfification.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    // REGISTER
    public String register(RegisterDto dto) {

        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));

        userRepository.save(user);

        return "Registered successfully";
    }

    // LOGIN
    public String login(LoginDto dto) {

        User user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful";
    }

    // Step 1: Send OTP
    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) return "User not found";

        String otp = otpService.generateOtp(email);
        emailService.sendOtp(email, otp);

        return "OTP sent to email";
    }

    // Step 2: Verify OTP & Reset Password
    public String resetPassword(String email, String otp, String newPassword) {

        if (!otpService.verifyOtp(email, otp)) {
            return "Invalid or Expired OTP";
        }

        User user = userRepository.findByEmail(email);
        System.out.println(newPassword);
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        otpService.clearOtp(email);

        return "Password reset successful";
    }
}

