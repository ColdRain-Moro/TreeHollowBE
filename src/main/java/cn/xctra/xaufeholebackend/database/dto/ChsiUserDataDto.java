package cn.xctra.xaufeholebackend.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rain on 2023/12/8
 *
 * 只需要学号和学校信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChsiUserDataDto {
    private String school;
    private String stuId;
}
