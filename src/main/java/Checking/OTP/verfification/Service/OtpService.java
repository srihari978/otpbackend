package Checking.OTP.verfification.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
    @Service
    public class OtpService {

        private Map<String, String> otpStore = new HashMap<>();
        private Map<String, Long> otpExpiry = new HashMap<>();

        public String generateOtp(String email) {
            String otp = String.valueOf(100000 + new Random().nextInt(900000));

            otpStore.put(email, otp);
            otpExpiry.put(email, System.currentTimeMillis() + 5 * 60 * 1000);

            return otp;
        }

        public boolean verifyOtp(String email, String otp) {
            if (!otpStore.containsKey(email)) return false;

            if (System.currentTimeMillis() > otpExpiry.get(email)) return false;

            return otpStore.get(email).equals(otp);
        }

        public void clearOtp(String email) {
            otpStore.remove(email);
            otpExpiry.remove(email);
        }
    }