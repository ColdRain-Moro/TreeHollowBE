package cn.xctra.xaufeholebackend.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rain on 2023/12/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChsiAuthDto {
    // 学号
    private String stuId;
    // 学信网验证码
    private String chsiCode;
    // 是否是注册模式
    private boolean registerMode;
    // 密码
    private String password;
    // 验证码
    private String captcha;
}
