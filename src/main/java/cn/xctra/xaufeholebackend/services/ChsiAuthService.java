package cn.xctra.xaufeholebackend.services;

import cn.hutool.crypto.digest.BCrypt;
import cn.xctra.xaufeholebackend.database.dto.ChsiAuthDto;
import cn.xctra.xaufeholebackend.database.dto.ChsiUserDataDto;
import cn.xctra.xaufeholebackend.database.entities.UserEntity;
import cn.xctra.xaufeholebackend.database.repositories.UserRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by rain on 2023/12/8
 **/
@Service
public class ChsiAuthService {

    private final UserRepository userRepository;

    @Autowired
    public ChsiAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<String> register(ChsiAuthDto dto) {
        if (dto.getChsiCode() == null) return Optional.of("无效的学信网验证码");
        Optional<ChsiUserDataDto> dataOpt = fetchChsiUserData(dto.getChsiCode());
        if (!dataOpt.isPresent()) return Optional.of("无效的学信网验证码");
        ChsiUserDataDto data = dataOpt.get();
        if (!data.getSchool().startsWith("重庆邮电大学")) return Optional.of("学校不在白名单列表内");
        long id = Long.parseLong(data.getStuId());
        if (userRepository.existsById(id)) return Optional.of("账号已存在");
        UserEntity entity = new UserEntity(id);
        entity.setPassword(BCrypt.hashpw(dto.getPassword()));
        entity.setStudentID(Long.parseLong(data.getStuId()));
        userRepository.save(entity);
        // 把 stuId 传递给外部
        dto.setStuId(data.getStuId());
        return Optional.empty();
    }

    public Optional<String> login(ChsiAuthDto dto) {
        Optional<UserEntity> userOpt = userRepository.findById((long) dto.getStuId().hashCode());
        if (!userOpt.isPresent()) {
            return Optional.of("账号或密码错误");
        }
        UserEntity user = userOpt.get();
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            return Optional.of("账号或密码错误");
        }
        return Optional.empty();
    }

    private Optional<ChsiUserDataDto> fetchChsiUserData(String code) {
        try {
            Document document = Jsoup.connect("https://www.chsi.com.cn/xlcx/bg.do?vcode=" + code).get();
            ChsiUserDataDto data = new ChsiUserDataDto();
            document.select(".report-info-item").forEach(element -> {
                String value = element.select(".value").text();
                String label = element.select(".label").text();
                if (label.equals("院校")) {
                    data.setSchool(value);
                }
                if (label.equals("学号")) {
                    data.setStuId(value);
                }
            });
            if (data.getStuId() == null || data.getSchool() == null) {
                return Optional.empty();
            }
            System.out.println(data);
            return Optional.of(data);
        } catch (Throwable throwable) {
            return Optional.empty();
        }
    }
}
